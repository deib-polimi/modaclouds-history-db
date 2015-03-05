/**
 * Copyright ${year} deib-polimi
 * Contact: deib-polimi <riccardobenito.desantis@polimi.it>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package it.polimi.modaclouds.hdb.manager;

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
	private String queueHost;
	
	private Channel channel;
	private Connection connection;
	
	private static final Logger logger = LoggerFactory.getLogger(Queue.class);
	
	public Queue(String queueHost, String queueName) throws IOException {
		this.queueName = queueName;
		this.queueHost = queueHost;
	}
	
	private boolean connected = false;
	
	private void connect() throws IOException {
		if (connected)
			return;
		
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(queueHost);
	    connection = factory.newConnection();
	    channel = connection.createChannel();
	    logger.debug("Connected to the queue {} on {}.", queueName, queueHost);
	    
	    connected = true;
	}
	
	public void addMessage(String message) throws IOException {
		connect();
		
		channel.queueDeclare(queueName, true, false, false, null);
		
		channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		logger.debug("Message added:\n{}", message);
		
		close();
	}
	
	public String getMessage() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		return getMessage(-1);
	}
	
	public String getMessage(int timeout) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		connect();
		
		channel.queueDeclare(queueName, true, false, false, null);
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
	    channel.basicConsume(queueName, /*true*/false, consumer);

	    QueueingConsumer.Delivery delivery = null;
	    if (timeout > 0)
	    	delivery = consumer.nextDelivery(timeout);
	    else
	    	delivery = consumer.nextDelivery();
	    
	    if (delivery == null)
	    	return null;
	    
	    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	    
	    String message = new String(delivery.getBody());
	    logger.debug("Message received:\n{}", message);
	    
	    close();
	    
	    return message;
	}
	
	public int count() {
		try {
			connect();
			
			AMQP.Queue.DeclareOk dok = channel.queueDeclare(queueName, true, false, false, null);
			int count = dok.getMessageCount();
			logger.debug("Messages in the queue: {}.", count);
			
			close();
			
			return count;
		} catch (Exception e) {
			logger.error("Error while checking the number of messages in the queue!", e);
			return -1;
		}
	}
	
	private void close() throws IOException {
		if (!connected)
			return;
		
		channel.close();
		connection.close();
		logger.debug("Connection to the queue closed.");
		
		connected = false;
	}
	
	public Queue(String queueName) throws IOException {
		this(Configuration.QUEUE_HOST, queueName);
	}
}
