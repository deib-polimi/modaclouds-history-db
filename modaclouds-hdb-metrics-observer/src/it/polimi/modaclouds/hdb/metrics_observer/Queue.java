package it.polimi.modaclouds.hdb.metrics_observer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;


/**
 * Queue handler for the project.
 * 
 * @author Riccardo B. Desantis
 *
 */
public class Queue {
	
	private String queueName;
	
	private Channel channel;
	private Connection connection;
	
	private static final Logger logger = LoggerFactory.getLogger(Queue.class);
	
	public Queue(String queueHost, String queueName) throws IOException {
		this.queueName = queueName;
		
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(queueHost);
	    connection = factory.newConnection();
	    channel = connection.createChannel();
	    logger.debug("Connected to the queue {} on {}.", queueName, queueHost);
	}
	
	public void addMessage(String message) throws IOException {
		channel.queueDeclare(queueName, true, false, false, null);
		
		channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		logger.debug("Message added:\n{}", message);
	}
	
	public String getMessage() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		return getMessage(-1);
	}
	
	public String getMessage(int timeout) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		channel.queueDeclare(queueName, true, false, false, null);
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
	    channel.basicConsume(queueName, true, consumer);

	    QueueingConsumer.Delivery delivery;
	    if (timeout > 0)
	    	delivery = consumer.nextDelivery(timeout);
	    else
	    	delivery = consumer.nextDelivery();
	    if (delivery == null)
	    	return null;
	    
	    String message = new String(delivery.getBody());
	    logger.debug("Message received:\n{}", message);
	    
	    return message;
	}
	
	public int count() {
		try {
			AMQP.Queue.DeclareOk dok = channel.queueDeclare(queueName, true, false, false, null);
			int count = dok.getMessageCount();
			logger.debug("Messages in the queue: {}.", count);
			return count;
		} catch (Exception e) {
			logger.error("Error while checking the number of messages in the queue!", e);
			return -1;
		}
	}
	
	public void close() throws IOException {
		channel.close();
		connection.close();
		logger.debug("Connection to the queue closed.");
	}
	
	public Queue(String queueName) throws IOException {
		this(Configuration.QUEUE_HOST, queueName);
	}
	
}
