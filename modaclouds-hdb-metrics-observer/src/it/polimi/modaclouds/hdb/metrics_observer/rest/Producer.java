package it.polimi.modaclouds.hdb.metrics_observer.rest;

import it.polimi.modaclouds.hdb.metrics_observer.Configuration;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used for generating (fake?) messages that are then read by the Listener.
 * 
 * @author Riccardo B. Desantis
 *
 */
public class Producer {
	
	private int port;
	
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	
	private static final Random r = new Random(UUID.randomUUID().getMostSignificantBits());
	
	public Producer(int port) {
		this.port = port;
	}
	
	public Producer() {
		this(Configuration.DEFAULT_PORT);
	}
	
	private void sendMessage(String path, String body, Method method) {
		Client client = new Client(new Context(), Protocol.HTTP);
		
		ClientResource request = new ClientResource(Configuration.DEFAULT_BASEPATH + ":" + port + path);
		
		Representation representation = null;
		if (method == Method.POST)
			representation = request.post(body);
		else if (method == Method.PUT)
			representation = request.put(body);
		
		if (representation != null) {
			logger.debug("Message sent!");
			try {
				logger.debug("Answer:\n{}", representation.getText());
			} catch (IOException e) {
				logger.error("Argh!", e);
			}
		} else {
			logger.debug("Error in sending the message! Method not recognized.");
		}
		
		try {
			client.stop();
		} catch (Exception e) {
			logger.error("Argh!", e);
		}
	}
	
	public void sendResult() {
		String body = "";
		
		Scanner sc = new Scanner(this.getClass().getResourceAsStream("/" + Configuration.EXAMPLE_RESULT_FILE));
		
		while (sc.hasNextLine())
			body += sc.nextLine() + "\n";
		
		sc.close();
		
		body = String.format(body, UUID.randomUUID().toString(), System.currentTimeMillis());
		
		this.sendMessage(Configuration.DEFAULT_PATH, body, Method.POST);
	}
	
	public void sendModel() {
		String body = "";
		
		Scanner sc = new Scanner(this.getClass().getResourceAsStream("/" + Configuration.EXAMPLE_MODEL_FILE));
		
		while (sc.hasNextLine())
			body += sc.nextLine() + "\n";
		
		sc.close();
		
//		body = String.format(body, UUID.randomUUID().toString(), System.currentTimeMillis());
		
		if (r.nextInt(101) > 50)
			this.sendMessage(Configuration.DEFAULT_PATH_MODEL, body, Method.PUT);
		else
			this.sendMessage(Configuration.DEFAULT_PATH_MODEL, body, Method.POST);
	}
	
	public static void test(int msgs, int wait) {
		Producer p = new Producer();
		for (int i = 1; i <= msgs; ++i) {
			
			if (r.nextInt(101) > 50)
				p.sendResult();
			else
				p.sendModel();
			
			if (wait > 0 && i < msgs)
				try {
					Thread.sleep(wait);
				} catch (Exception e) {}
		}
	}
	
	public static void test(int msgs) {
		test(msgs, 1000);
	}
	
	public static void main(String[] args) {
		test(1);
	}
	
}
