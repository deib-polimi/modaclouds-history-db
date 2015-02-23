package it.polimi.modaclouds.hdb.metrics_observer;

import it.polimi.modaclouds.hdb.metrics_observer.rest.Producer;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		perform(null, true);
	}
	
	public static void perform(String configurationFile, boolean useFakeData) {
		logger.info("HDB Metrics Observer starting...");
		
		if (configurationFile != null && new File(configurationFile).canRead()) {
			try { 
				Configuration.loadConfiguration(configurationFile);
				logger.info("Configuration file loaded.");
			} catch (Exception e) {
				logger.error("Error while reading the configuration file.", e);
				logger.info("Going on using the default configuration.");
			}
		} else {
			logger.info("No configuration file provided or the file provided wasn't accessible. The process will go on using the default values.");
		}
		
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
