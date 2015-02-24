package it.polimi.modaclouds.hdb.metrics_observer;

import it.polimi.modaclouds.hdb.metrics_observer.rest.Producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		perform(null, true);
	}
	
	public static void perform(String[] args, boolean useFakeData) {
		logger.info("HDB Metrics Observer starting...");
		
		Configuration.loadFromEnrivonmentVariables();
		Configuration.loadFromSystemProperties();
		Configuration.loadFromArguments(args);
		
		MetricsObserver.RUNNING_TIME = -1;
		MetricsObserver mo = new MetricsObserver();
		mo.start();
		logger.debug("MetricsObserver started!");
		
		logger.info("HDB Metrics Observer started!");
        
		if (useFakeData) {
	        try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
	        logger.debug("Starting the producer...");
	        Producer.test(30);
		}
        
	}
	
	public static void perform() {
		perform(null, true);
	}
}
