package it.polimi.modaclouds.hdb.metrics_observer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains a list of all the default strings and variables.
 * 
 * @author Riccardo B. Desantis
 *
 */
public abstract class Configuration {
	
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
	
	public static int DEFAULT_PORT = 31337;
	public static String DEFAULT_PATH = "/test";
	public static String DEFAULT_BASEPATH = "http://localhost";
	public static String QUEUE_NAME = "queue_hdb_1";
	public static String QUEUE_HOST = "localhost";

	public static final String EXAMPLE_MSG_FILE = "example-msg.rdf";
	
	public static void saveConfiguration(String filePath) throws IOException {
		FileOutputStream fos = new FileOutputStream(filePath);
		Properties prop = new Properties();
		
		prop.put("DEFAULT_PORT", Integer.toString(DEFAULT_PORT));
		prop.put("DEFAULT_PATH", DEFAULT_PATH);
		prop.put("DEFAULT_BASEPATH", DEFAULT_BASEPATH);
		prop.put("QUEUE_NAME", QUEUE_NAME);
		prop.put("QUEUE_HOST", QUEUE_HOST);

		prop.store(fos, "HDB Metrics-Observer configuration properties");
		fos.flush();
	}
	
	public static void loadConfiguration(String filePath) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(filePath);
		prop.load(fis);
		
		DEFAULT_PATH = prop.getProperty("DEFAULT_PATH", DEFAULT_PATH);
		DEFAULT_BASEPATH = prop.getProperty("DEFAULT_BASEPATH", DEFAULT_BASEPATH);
		QUEUE_NAME = prop.getProperty("QUEUE_NAME", QUEUE_NAME);
		QUEUE_HOST = prop.getProperty("QUEUE_HOST", QUEUE_HOST);
		try {
			DEFAULT_PORT = Integer.parseInt(prop.getProperty("DEFAULT_PORT", Integer.toString(DEFAULT_PORT)));
		} catch (NumberFormatException e) {
			logger.error("Argh!", e);
		}
	}
}
