package it.polimi.modaclouds.hdb.manager.data;

import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * An object of this class represent the monitoring data sent as a RDF/JSON file.
 * 
 * @author Riccardo B. Desantis
 *
 */
public class MonitoringData {
	
	private static final Logger logger = LoggerFactory.getLogger(MonitoringData.class);
	
	private com.hp.hpl.jena.rdf.model.Model model;
	private long timestamp;
	private String metric;
	private double value;
	private String resourceId;

	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	private MonitoringData() { }
	
	public com.hp.hpl.jena.rdf.model.Model getModel() {
		return model;
	}

	public void setModel(com.hp.hpl.jena.rdf.model.Model model) {
		this.model = model;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public static MonitoringData resultFromRdfJson(String jsonRdfDatum) {
		if (jsonRdfDatum == null)
			return null;
		
		MonitoringData r = new MonitoringData();
		
		r.model = ModelFactory.createDefaultModel();
		com.hp.hpl.jena.rdf.model.Model model = ModelFactory.createDefaultModel();
		/*r.*/model.read(new StringReader(jsonRdfDatum), null, "RDF/JSON");
		
		r.timestamp = -1;
		
		Resource subject = null;
		{
			String subjectUri = "mo:";
			int i = jsonRdfDatum.indexOf('"');
			i = jsonRdfDatum.indexOf(':', i+1);
			int j = jsonRdfDatum.indexOf('"', i+1);
			subjectUri += jsonRdfDatum.substring(i+1, j);
			
			subject = r.model.createResource(subjectUri);
		}
		
		for (StmtIterator iter = /*r.*/model.listStatements(); iter.hasNext();) {
			Statement stmt      = iter.nextStatement();
		    Property  predicate = stmt.getPredicate();
		    RDFNode   object    = stmt.getObject();
		    
		    r.model.add(subject, predicate, object);
		    
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
		    		r.timestamp = Long.valueOf(value);
		    	} catch (NumberFormatException e) {
		    		logger.error("Argh!", e);
		    	}
		    else if (name.equalsIgnoreCase("metric"))
		    	r.metric = value;
		    else if (name.equalsIgnoreCase("value"))
		    	try {
		    		r.value = Double.valueOf(value);
		    	} catch (NumberFormatException e) {
		    		logger.error("Argh!", e);
		    	}
		    else if (name.equalsIgnoreCase("resourceId"))
		    	r.resourceId = value;
		}
		
		if (r.timestamp > -1)
			return r;
		
		return null;
	}
}
