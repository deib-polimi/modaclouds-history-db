package it.polimi.modaclouds.hdb.metrics_observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.polimi.modaclouds.hdb.metrics_observer.rest.Listener;
import it.polimi.modaclouds.hdb.metrics_observer.rest.Producer;

public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
//		Listener.main(args);
		
		
		Listener.RUNNING_TIME = -1;
		Listener l = new Listener();
        l.start();
        
        logger.debug("Listener started!");
        
        try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        logger.debug("Starting the producer...");
        Producer.test(30);
	}
}
