package it.polimi.modaclouds.hdb;

import it.polimi.modaclouds.hdb.manager.Configuration;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	private static final String DEFAULT_CONF = "hdbconf.properties";

	public static void main(String[] args) {
		String configurationFile = null;
		
		if (args.length > 0) {
			if (args[0].equals("-UseDefaultConfiguration")) {
				String f = DEFAULT_CONF;
				if (args.length > 1) {
					f = args[1];
					while (!new File(f).exists())
						f = f + "_1";
				}
				
				try {
					Configuration.saveConfiguration(f);
					configurationFile = f;
				} catch (IOException e) {
					logger.error("Error while saving the default configuration file!", e);
				}
			} else if (!args[0].startsWith("-")) {
				String f = args[0];
				
				if (!new File(f).exists()) {
					try {
						Configuration.saveConfiguration(f);
						configurationFile = f;
					} catch (IOException e) {
						logger.error("Error while saving the default configuration file!", e);
					}
				} else if (new File(f).canRead()) {
					configurationFile = f;
				} else {
					logger.error("File configuration ({}) ignored because it doesn't exists.", f);
				}
			}
		} else {
			String f = DEFAULT_CONF;
			
			if (!new File(f).exists()) {
				try {
					Configuration.saveConfiguration(f);
					configurationFile = f;
				} catch (IOException e) {
					logger.error("Error while saving the default configuration file!", e);
				}
			} else if (new File(f).canRead()) {
				configurationFile = f;
			} else {
				logger.error("File configuration ({}) ignored because it doesn't exists.", f);
			}
		}
		
		perform(configurationFile);
	}
	
	public static void perform(String configurationFile) {
		it.polimi.modaclouds.hdb.manager.Main.perform(configurationFile);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.error("Error while waiting!", e);
		}
		
		it.polimi.modaclouds.hdb.metrics_observer.Main.perform(configurationFile, true);
	}
	
	public static void perform() {
		perform(null);
	}

}
