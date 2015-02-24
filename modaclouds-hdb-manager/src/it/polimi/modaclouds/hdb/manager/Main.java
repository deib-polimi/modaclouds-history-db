package it.polimi.modaclouds.hdb.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		perform(args);
	}
	
	public static void perform(String[] args) {
		logger.info("HDB Manager starting...");
		
		Configuration.loadFromEnrivonmentVariables();
		Configuration.loadFromSystemProperties();
		Configuration.loadFromArguments(args);
		
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
