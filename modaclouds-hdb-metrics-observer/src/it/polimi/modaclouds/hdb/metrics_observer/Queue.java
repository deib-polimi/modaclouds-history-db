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
package it.polimi.modaclouds.hdb.metrics_observer;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	
	private static ExecutorService execService = null;
	
	public Queue(String queueHost, String queueName) {
		this.queueName = queueName;
		this.queueHost = queueHost;
		
		if (execService == null)
			execService = Executors.newCachedThreadPool();
	}
	
	private class AddExecutor extends Thread {
		private String message;
		private Queue queue;
		
		public AddExecutor(String message) {
			this.message = message;
			queue = new Queue(queueHost, queueName);
		}
		
		public void run() {
		    try {
		    	queue.connect();
		    	queue.internalAddMessage(message);
		    } catch (Exception e) {
		    	logger.error("Error while dealing with the queue.", e);
		    } finally {
		    	try {
		    		queue.close();
		    	} catch (Exception e) {
		    		logger.error("Error while dealing with the queue.", e);
		    	}
		    }
		}
	}
	
	private class GetExecutor implements Callable<String> {
		private Queue queue;
		
		public GetExecutor() {
			queue = new Queue(queueHost, queueName);
		}

		@Override
		public String call() {
			String ret = null;
			
			try {
		    	queue.connect();
		    	ret = queue.internalGetMessage();
		    } catch (Exception e) {
		    	logger.error("Error while dealing with the queue.", e);
		    } finally {
		    	try {
		    		queue.close();
		    	} catch (Exception e) {
		    		logger.error("Error while dealing with the queue.", e);
		    	}
		    }
			
			return ret;
		}
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
	
	private void internalAddMessage(String message) throws IOException {
		channel.queueDeclare(queueName, true, false, false, null);
		
		channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		logger.debug("Message added:\n{}", message);
	}
	
	public void addMessage(String message) {
		execService.submit(new AddExecutor(message));
	}
	
	public String getMessage() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, ExecutionException {
		return (String) execService.submit(new GetExecutor()).get();
	}
	
	private String internalGetMessage() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		channel.queueDeclare(queueName, true, false, false, null);
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
	    channel.basicConsume(queueName, /*true*/false, consumer);

	    QueueingConsumer.Delivery delivery = null;
	    delivery = consumer.nextDelivery();
	    
	    if (delivery == null)
	    	return null;
	    
	    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	    
	    String message = new String(delivery.getBody());
	    logger.debug("Message received:\n{}", message);
	    
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
