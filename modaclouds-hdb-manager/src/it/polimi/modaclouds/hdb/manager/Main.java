package it.polimi.modaclouds.hdb.manager;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		perform(null);
	}
	
	public static void perform(String configurationFile) {
		logger.info("HDB Manager starting...");
		
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
		
		DataStore.reset();
		
		Manager.RUNNING_TIME = -1;
		Manager m1 = new Manager(Configuration.QUEUE_RESULTS);
		m1.start();
		Manager m2 = new Manager(Configuration.QUEUE_MODELS);
		m2.start();
		Manager m3 = new Manager(Configuration.QUEUE_DELTA_MODELS);
		m3.start();
		Manager m4 = new Manager(Configuration.QUEUE_MODELS_DELETE);
		m4.start();
		
		logger.debug("Managers started!");
		
		logger.info("HDB Manager started!");
	}
	
	public static void perform() {
		perform(null);
	}
}
