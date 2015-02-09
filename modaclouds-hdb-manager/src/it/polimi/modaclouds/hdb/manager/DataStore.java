package it.polimi.modaclouds.hdb.manager;

import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.query.DatasetAccessor;
import com.hp.hpl.jena.query.DatasetAccessorFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

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
	private DatasetAccessor datasetAccessor;
	
	public DataStore(String host) {
		this.host = host;
		
		datasetAccessor = DatasetAccessorFactory.createHTTP(host);
		logger.debug("Connection created to the fuseki store at {}.", host);
	}
	
	public DataStore() {
		this(Configuration.FUSEKI_HOST);
	}
	
	private boolean add(String graphUri, Model model) {
		datasetAccessor.add(graphUri, model);
		
		logger.debug("Model added to the datastore.");
		
		return true;
	}
	
	public boolean addResult(String jsonRdfDatum) {
		Model model = ModelFactory.createDefaultModel();
		model.read(new StringReader(jsonRdfDatum), null, "RDF/JSON");
		
		long timestamp = -1;
		
		for (StmtIterator iter = model.listStatements(); iter.hasNext();) {
			Statement stmt      = iter.nextStatement();
		    Property  predicate = stmt.getPredicate();
		    RDFNode   object    = stmt.getObject();
		    
		    String name = predicate.toString();
		    int i = 0;
		    if (( i = name.indexOf('#') ) < 0)
		    	continue;
		    name = name.substring(i + 1);
		    
		    String value = "";
		    
		    if (!object.isLiteral())
		    	continue;
		    value = object.asLiteral().getValue().toString();

		    logger.debug("{}: {}", name, value);
		    
		    if (name.equalsIgnoreCase("timestamp"))
		    	try {
		    		timestamp = Long.valueOf(value);
		    	} catch (NumberFormatException e) {
		    		logger.error("Argh!", e);
		    	}
		}
		
		if (timestamp > -1) {
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
			
			String uri = Configuration.FUSEKI_MONITORING + c.getTimeInMillis();
			
			return add(uri, model);
		}
		
		return false;
	}
	
	public boolean addModel(String jsonRdfDatum) {
		Model model = ModelFactory.createDefaultModel();
		model.read(new StringReader(jsonRdfDatum), null, "RDF/JSON");
		
		long timestamp = -1;
		
		for (StmtIterator iter = model.listStatements(); iter.hasNext();) {
			Statement stmt      = iter.nextStatement();
		    Property  predicate = stmt.getPredicate();
		    RDFNode   object    = stmt.getObject();
		    
		    String name = predicate.toString();
		    int i = 0;
		    if (( i = name.indexOf('#') ) < 0)
		    	continue;
		    name = name.substring(i + 1);
		    
		    String value = "";
		    
		    if (!object.isLiteral())
		    	continue;
		    value = object.asLiteral().getValue().toString();

		    logger.debug("{}: {}", name, value);
		    
		    if (name.equalsIgnoreCase("timestamp"))
		    	try {
		    		timestamp = Long.valueOf(value);
		    	} catch (NumberFormatException e) {
		    		logger.error("Argh!", e);
		    	}
		}
		
		if (timestamp > -1) {
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
			
			String uri = Configuration.FUSEKI_MONITORING + c.getTimeInMillis();
			
			return add(uri, model);
		}
		
		return false;
	}
	
	public boolean addDeltaModel(String jsonRdfDatum) {
		Model model = ModelFactory.createDefaultModel();
		model.read(new StringReader(jsonRdfDatum), null, "RDF/JSON");
		
		long timestamp = -1;
		
		for (StmtIterator iter = model.listStatements(); iter.hasNext();) {
			Statement stmt      = iter.nextStatement();
		    Property  predicate = stmt.getPredicate();
		    RDFNode   object    = stmt.getObject();
		    
		    String name = predicate.toString();
		    int i = 0;
		    if (( i = name.indexOf('#') ) < 0)
		    	continue;
		    name = name.substring(i + 1);
		    
		    String value = "";
		    
		    if (!object.isLiteral())
		    	continue;
		    value = object.asLiteral().getValue().toString();

		    logger.debug("{}: {}", name, value);
		    
		    if (name.equalsIgnoreCase("timestamp"))
		    	try {
		    		timestamp = Long.valueOf(value);
		    	} catch (NumberFormatException e) {
		    		logger.error("Argh!", e);
		    	}
		}
		
		if (timestamp > -1) {
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
			
			String uri = Configuration.FUSEKI_MONITORING + c.getTimeInMillis();
			
			return add(uri, model);
		}
		
		return false;
	}
}
