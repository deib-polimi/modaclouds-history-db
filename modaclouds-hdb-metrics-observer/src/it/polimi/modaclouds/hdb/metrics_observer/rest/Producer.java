package it.polimi.modaclouds.hdb.metrics_observer.rest;

import it.polimi.modaclouds.hdb.metrics_observer.Configuration;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import org.restlet.Client;
import org.restlet.Context;
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
	
	public Producer(int port) {
		this.port = port;
	}
	
	public Producer() {
		this(Configuration.DEFAULT_PORT);
	}
	
	public void sendMessage(String path, String body) {
		Client client = new Client(new Context(), Protocol.HTTP);
		
		ClientResource request = new ClientResource(Configuration.DEFAULT_BASEPATH + ":" + port + path);
		Representation representation = request.post(body);
		
		logger.debug("Message sent!");
		try {
			logger.debug("Answer:\n{}", representation.getText());
		} catch (IOException e) {
			logger.error("Argh!", e);
		}
		
		try {
			client.stop();
		} catch (Exception e) {
			logger.error("Argh!", e);
		}
	}
	
	public void sendMessage(String body) {
		this.sendMessage(Configuration.DEFAULT_PATH, body);
	}
	
	public void sendMessage() {
		String body = "";
		
		Scanner sc = new Scanner(this.getClass().getResourceAsStream("/" + Configuration.EXAMPLE_MSG_FILE));
		
		while (sc.hasNextLine())
			body += sc.nextLine() + "\n";
		
		sc.close();
		
		body = String.format(body, UUID.randomUUID().toString() /*"a.unique.id.for.the.datum"*/, System.currentTimeMillis());
		
		
		this.sendMessage(Configuration.DEFAULT_PATH, body);
	}
	
	public static void test(int msgs, int wait) {
		Producer p = new Producer();
		for (int i = 1; i <= msgs; ++i) {
			p.sendMessage();
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
