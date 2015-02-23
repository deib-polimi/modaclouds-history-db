package it.polimi.modaclouds.hdb.manager;

import it.polimi.modaclouds.hdb.manager.data.Model;
import it.polimi.modaclouds.hdb.manager.data.MonitoringData;
import it.polimi.modaclouds.monitoring.kb.api.DeserializationException;
import it.polimi.modaclouds.monitoring.kb.api.FusekiKBAPI;
import it.polimi.modaclouds.monitoring.kb.api.SerializationException;
import it.polimi.modaclouds.monitoring.kb.api.Util;
import it.polimi.modaclouds.qos_models.monitoring_ontology.MO;
import it.polimi.modaclouds.qos_models.monitoring_ontology.MOVocabulary;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.query.DatasetAccessor;
import com.hp.hpl.jena.query.DatasetAccessorFactory;

/**
 * 
 * This class hides the datastore used behind. It is now implemented for working with Fuseki. 
 * 
 * @author Riccardo B. Desantis
 *
 */
public class DataStore {
	
	private static final Logger logger = LoggerFactory.getLogger(DataStore.class);
	
	@SuppressWarnings("unused")
	private String host;
	
	private static FusekiKBAPI knowledgeBaseModels = null;
	
	private static DatasetAccessor datasetAccessor = null;
	
	public DataStore(String host) {
		this.host = host;
		
		if (knowledgeBaseModels == null) {
			knowledgeBaseModels = new FusekiKBAPI(host);
			knowledgeBaseModels.uploadOntology(MO.model, "models");
		}
		
		if (datasetAccessor == null) {
			datasetAccessor = DatasetAccessorFactory.createHTTP(host + "/data");
		}
		
		logger.debug("Connection created to the fuseki store at {}.", host);
	}
	
	public DataStore() {
		this(Configuration.FUSEKI_HOST);
	}
	
	private boolean add(String graphUri, com.hp.hpl.jena.rdf.model.Model model) {
		new AddExecutor(graphUri, model).start();
		return true;
	}
	
	public boolean addMonitoringData(String jsonRdfDatum) {
		MonitoringData r = MonitoringData.resultFromRdfJson(jsonRdfDatum);
		
		if (r == null)
			return false;
		
		String graphUri = Configuration.FUSEKI_MONITORING + hourTimestamp(r.getTimestamp());
		
		boolean res = add(graphUri, r.getModel());
		
		if (res)
			logger.info("Monitoring data added to the datastore.");
		else
			logger.error("Error while adding the monitoring data added to the datastore.");
		
		return res;
		
	}
	
	public boolean addModel(String jsonDatum) {
		Model m = Model.modelFromJson(jsonDatum);	
		
		if (m == null)
			return false;
		
		try {
			long timestamp = System.currentTimeMillis();
			
//			String graphUri = Configuration.FUSEKI_MODEL + timestamp;
			
			String bak = it.polimi.modaclouds.monitoring.kb.api.Config.graphsNamespace;
			
			it.polimi.modaclouds.monitoring.kb.api.Config.graphsNamespace = Configuration.FUSEKI_MODEL;
			String graphName = Long.toString(timestamp);
			
			knowledgeBaseModels.add(m.getResources(), MOVocabulary.resourceIdParameterName, graphName);
			boolean res1 = true; // we suppose that it always works
			
			logger.debug("Model added to the datastore.");
			
			it.polimi.modaclouds.monitoring.kb.api.Config.graphsNamespace = bak;
			
			String dailyGraphUri = Configuration.FUSEKI_MODEL_DAILY + dayTimestamp(timestamp);
			boolean res2 = add(dailyGraphUri, Model.getNameModel(Configuration.FUSEKI_MODEL + graphName, timestamp));
			
			if (res2)
				logger.info("Info on the model added in the daily graph in the datastore.");
			else
				logger.error("Error while adding the info on the model in the daily graph in the datastore.");
			
			return res1 && res2;
		} catch (SerializationException | DeserializationException e) {
			logger.error("Error while saving the model to the datastore!", e);
		}
		
		return false;
		
	}
	
	public boolean deleteModel(String id) {
		if (id == null || id.length() == 0)
			return false;
		
		long timestamp = System.currentTimeMillis();
		
		String graphUri = Configuration.FUSEKI_MODELS_DELETE + timestamp;
		
		boolean res1 = add(graphUri, Model.getDeleteModel(id, timestamp));
		
		if (res1)
			logger.info("Info on the cancellation of a model added in the datastore.");
		else
			logger.error("Error while adding the info on the cancellation of a model in the datastore.");
		
		String dailyGraphUri = Configuration.FUSEKI_MODELS_DELETE_DAILY + dayTimestamp(timestamp);
		boolean res2 = add(dailyGraphUri, Model.getNameModel(Configuration.FUSEKI_MODELS_DELETE + timestamp, timestamp));
		
		if (res2)
			logger.info("Info on the cancellation of a model added in the daily graph in the datastore.");
		else
			logger.error("Error while adding the info on the cancellation of a model in the daily graph in the datastore.");
		
		return res1 && res2;
	}
	
	public boolean addDeltaModel(String jsonDatum) {
		Model m = Model.modelFromJson(jsonDatum);	
		
		if (m == null)
			return false;
		
		try {
			long timestamp = System.currentTimeMillis();
			
//			String graphUri = Configuration.FUSEKI_DELTAS_MODEL + timestamp;
			
			String bak = it.polimi.modaclouds.monitoring.kb.api.Config.graphsNamespace;

			it.polimi.modaclouds.monitoring.kb.api.Config.graphsNamespace = Configuration.FUSEKI_DELTAS_MODEL;
			String graphName = Long.toString(timestamp);
			
			knowledgeBaseModels.add(m.getResources(), MOVocabulary.resourceIdParameterName, graphName);
			boolean res1 = true; // we suppose that it always works
			logger.info("Updated model added to the datastore.");
			
			it.polimi.modaclouds.monitoring.kb.api.Config.graphsNamespace = bak;
			
			String dailyGraphUri = Configuration.FUSEKI_DELTAS_MODEL_DAILY + dayTimestamp(timestamp);
			boolean res2 = add(dailyGraphUri, Model.getNameModel(Configuration.FUSEKI_DELTAS_MODEL + graphName, timestamp));
			
			if (res2)
				logger.info("Info on the update of a model added in the daily graph in the datastore.");
			else
				logger.error("Error while adding the info on the update of a model in the daily graph in the datastore.");
			
			return res1 && res2;
		} catch (SerializationException | DeserializationException e) {
			logger.error("Error while saving the information on the update of a model!", e);
		}
		
		return false;
		
	}
	
	private static long hourTimestamp(long timestamp) {
		Date d = null;
		try {
			d = new Date(timestamp);
		} catch (Exception e) {
			logger.error("Argh!", e);
			d = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTimeInMillis();
	}
	
	private static long dayTimestamp(long timestamp) {
		Date d = null;
		try {
			d = new Date(timestamp);
		} catch (Exception e) {
			logger.error("Argh!", e);
			d = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTimeInMillis();
	}
	
	private class AddExecutor extends Thread {
		private String graphUri;
		private com.hp.hpl.jena.rdf.model.Model model;
		
		public AddExecutor(String graphUri, com.hp.hpl.jena.rdf.model.Model model) {
			this.graphUri = graphUri;
			this.model = model;
		}
		
		public void run() {
			datasetAccessor.add(graphUri, model);
			logger.debug("Model added to the datastore.");
		}

	}
	
	public static void reset(String host) {
//		UpdateRequest query = UpdateFactory.create("DROP all", Syntax.syntaxSPARQL_11);
//		UpdateProcessor execUpdate = UpdateExecutionFactory.createRemote(query, host + "/update");
//		execUpdate.execute();
	}
	
	public static void reset() {
		reset(Configuration.FUSEKI_HOST);
	}
	
	public static String encodeURL(String URL) {
		return Util.urlEncode(URL);
	}
}
