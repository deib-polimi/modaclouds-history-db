package it.polimi.modaclouds.hdb.manager;

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
	
	public static String DEFAULT_PATH = "/results";
	public static String DEFAULT_PATH_MODEL = "/model/resources";
	
	public static String DEFAULT_BASEPATH = "http://localhost";
	public static String QUEUE_HOST = "localhost";
	public static String QUEUE_RESULTS = "hdb_results";
	public static String QUEUE_MODELS = "hdb_models";
	public static String QUEUE_DELTA_MODELS = "hdb_delta_models";
	
	public static String FUSEKI_HOST = "http://localhost:3030";
	public static String FUSEKI_BASEURI = "http://www.modaclouds.eu/historydb/";
	public static String FUSEKI_MONITORING = FUSEKI_BASEURI + "monitoring-data/";
	public static String FUSEKI_MODEL = FUSEKI_BASEURI + "model/";
	public static String FUSEKI_MODEL_DAILY = FUSEKI_BASEURI + "meta/model/";
	public static String FUSEKI_DELTAS_MODEL = FUSEKI_BASEURI + "model/deltas/";
	public static String FUSEKI_DELTAS_MODEL_DAILY = FUSEKI_BASEURI + "meta/model/deltas/";
	
	public static final String EXAMPLE_RESULT_FILE = "example-result.rdf";
	public static final String EXAMPLE_MODEL_FILE = "example-model.json";
	
	public static void saveConfiguration(String filePath) throws IOException {
		FileOutputStream fos = new FileOutputStream(filePath);
		Properties prop = new Properties();
		
		prop.put("DEFAULT_PORT", Integer.toString(DEFAULT_PORT));
		prop.put("DEFAULT_PATH", DEFAULT_PATH);
		prop.put("DEFAULT_PATH_MODEL", DEFAULT_PATH_MODEL);
		prop.put("DEFAULT_BASEPATH", DEFAULT_BASEPATH);
		prop.put("QUEUE_RESULTS", QUEUE_RESULTS);
		prop.put("QUEUE_MODELS", QUEUE_MODELS);
		prop.put("QUEUE_DELTA_MODELS", QUEUE_DELTA_MODELS);
		prop.put("QUEUE_HOST", QUEUE_HOST);
		prop.put("FUSEKI_HOST", FUSEKI_HOST);
		prop.put("FUSEKI_BASEURI", FUSEKI_BASEURI);
		prop.put("FUSEKI_MONITORING", FUSEKI_MONITORING);
		prop.put("FUSEKI_MODEL", FUSEKI_MODEL);
		prop.put("FUSEKI_MODEL_DAILY", FUSEKI_MODEL_DAILY);
		prop.put("FUSEKI_DELTAS_MODEL", FUSEKI_DELTAS_MODEL);
		prop.put("FUSEKI_DELTAS_MODEL_DAILY", FUSEKI_DELTAS_MODEL_DAILY);

		prop.store(fos, "HDB configuration properties");
		fos.flush();
	}
	
	public static void loadConfiguration(String filePath) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(filePath);
		prop.load(fis);
		
		DEFAULT_PATH = prop.getProperty("DEFAULT_PATH", DEFAULT_PATH);
		DEFAULT_PATH_MODEL = prop.getProperty("DEFAULT_PATH_MODEL", DEFAULT_PATH_MODEL);
		DEFAULT_BASEPATH = prop.getProperty("DEFAULT_BASEPATH", DEFAULT_BASEPATH);
		QUEUE_RESULTS = prop.getProperty("QUEUE_RESULTS", QUEUE_RESULTS);
		QUEUE_MODELS = prop.getProperty("QUEUE_MODELS", QUEUE_MODELS);
		QUEUE_DELTA_MODELS = prop.getProperty("QUEUE_DELTA_MODELS", QUEUE_DELTA_MODELS);
		QUEUE_HOST = prop.getProperty("QUEUE_HOST", QUEUE_HOST);
		FUSEKI_HOST = prop.getProperty("FUSEKI_HOST", FUSEKI_HOST);
		FUSEKI_BASEURI = prop.getProperty("FUSEKI_BASEURI", FUSEKI_BASEURI);
		FUSEKI_MONITORING = prop.getProperty("FUSEKI_MONITORING", FUSEKI_MONITORING);
		FUSEKI_MODEL = prop.getProperty("FUSEKI_BASEURI_MODEL", FUSEKI_MODEL);
		FUSEKI_MODEL_DAILY = prop.getProperty("FUSEKI_BASEURI_MODEL_DAILY", FUSEKI_MODEL_DAILY);
		FUSEKI_DELTAS_MODEL = prop.getProperty("FUSEKI_DELTAS_MODEL", FUSEKI_DELTAS_MODEL);
		FUSEKI_DELTAS_MODEL_DAILY = prop.getProperty("FUSEKI_DELTAS_MODEL_DAILY", FUSEKI_DELTAS_MODEL_DAILY);
		try {
			DEFAULT_PORT = Integer.parseInt(prop.getProperty("DEFAULT_PORT", Integer.toString(DEFAULT_PORT)));
		} catch (NumberFormatException e) {
			logger.error("Argh!", e);
		}
	}
}