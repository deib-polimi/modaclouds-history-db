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
		
		System.out.println("\nHistory-DB\n");
		
		if (args.length > 0) {
			if (args[0].equals("-Help") || args[0].equals("-h") || args[0].equals("--h")) {
				System.out.println(
						"To start the tool you can just call the file without any parameter:\n" + 
						"the program will look for the default configuration file,\n" +
						"\t" + DEFAULT_CONF + "\n" +
						"in the same folder of this executable. If the file isn't readable or doesn't exist\n" +
						"at all, it will be created with the default parameters.\n\n" +
						"You could also call the program with a parameter, that would be the name of the file\n"
						+ "that will be used as the configuration file.\n\n"
						+ "Lastly, it is possible to use -UseDefaultConfiguration as a parameter to reset the\n"
						+ "configuration file (the default if no other parameter is passed, or otherwise if a\n"
						+ "file name is passed it will try to save the configuration to that file if it doesn't exist."
						);
				return;
			} else if (args[0].equals("-UseDefaultConfiguration")) {
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
