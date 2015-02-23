package it.polimi.modaclouds.hdb.manager.data;

import it.polimi.modaclouds.hdb.manager.DataStore;
import it.polimi.modaclouds.qos_models.monitoring_ontology.CloudProvider;
import it.polimi.modaclouds.qos_models.monitoring_ontology.InternalComponent;
import it.polimi.modaclouds.qos_models.monitoring_ontology.Location;
import it.polimi.modaclouds.qos_models.monitoring_ontology.Method;
import it.polimi.modaclouds.qos_models.monitoring_ontology.PaaSService;
import it.polimi.modaclouds.qos_models.monitoring_ontology.Resource;
import it.polimi.modaclouds.qos_models.monitoring_ontology.VM;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class Model {
	
	private static final Logger logger = LoggerFactory.getLogger(Model.class);

	private Set<CloudProvider> cloudProviders;
	private Set<Location> locations;
	private Set<VM> vMs;
	private Set<PaaSService> paaSServices;
	private Set<InternalComponent> internalComponents;
	private Set<Method> methods;
	
	public void add(CloudProvider cloudProvider) {
		if (cloudProviders == null)
			cloudProviders = new HashSet<CloudProvider>();
		cloudProviders.add(cloudProvider);
	}
	public void add(Location location) {
		if (locations == null)
			locations = new HashSet<Location>();
		locations.add(location);
	}
	public void add(VM vM) {
		if (vMs == null)
			vMs = new HashSet<VM>();
		vMs.add(vM);
	}
	public void add(PaaSService paaSService) {
		if (paaSServices == null)
			paaSServices = new HashSet<PaaSService>();
		paaSServices.add(paaSService);
	}
	public void add(InternalComponent internalComponent) {
		if (internalComponents == null)
			internalComponents = new HashSet<InternalComponent>();
		internalComponents.add(internalComponent);
	}
	public void add(Method method) {
		if (methods == null)
			methods = new HashSet<Method>();
		methods.add(method);
	}
	
	public Set<CloudProvider> getCloudProviders() {
		return cloudProviders;
	}
	public void setCloudProviders(Set<CloudProvider> cloudProviders) {
		this.cloudProviders = cloudProviders;
	}
	public Set<Location> getLocations() {
		return locations;
	}
	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}
	public Set<VM> getvMs() {
		return vMs;
	}
	public void setvMs(Set<VM> vMs) {
		this.vMs = vMs;
	}
	public Set<PaaSService> getPaaSServices() {
		return paaSServices;
	}
	public void setPaaSServices(Set<PaaSService> paaSServices) {
		this.paaSServices = paaSServices;
	}
	public Set<InternalComponent> getInternalComponents() {
		return internalComponents;
	}
	public void setInternalComponents(Set<InternalComponent> internalComponents) {
		this.internalComponents = internalComponents;
	}
	public Set<Method> getMethods() {
		return methods;
	}
	public void setMethods(Set<Method> methods) {
		this.methods = methods;
	}
	
	public Set<Resource> getResources() {
		Set<Resource> resources = new HashSet<Resource>();
		resources.addAll(nullable(cloudProviders));
		resources.addAll(nullable(locations));
		resources.addAll(nullable(vMs));
		resources.addAll(nullable(paaSServices));
		resources.addAll(nullable(internalComponents));
		resources.addAll(nullable(methods));
		return resources;
	}
	
	private <T> Collection<T> nullable(
			Set<T> collection) {
		return collection == null? new HashSet<T>() : collection;
	}
	
	public static Model modelFromJson(String jsonDatum) {
		if (jsonDatum == null)
			return null;
		
		try {
			Gson gson = new Gson();
			Model deserialised = gson.fromJson(jsonDatum, Model.class);
			return deserialised;
		} catch (Exception e) {
			logger.error("Error while parsing the JSON!", e);
			return null;
		}
	}
	
	private com.hp.hpl.jena.rdf.model.Model model = null;
	
	public com.hp.hpl.jena.rdf.model.Model getModel() {
		if (model != null)
			return model;
		
		model = ModelFactory.createDefaultModel();
		
		return model;
	}
	
	public static com.hp.hpl.jena.rdf.model.Model getNameModel(String graphUri, long timestamp) {
		com.hp.hpl.jena.rdf.model.Model nameModel = ModelFactory.createDefaultModel();
		
		com.hp.hpl.jena.rdf.model.Resource   subject   = null;
		com.hp.hpl.jena.rdf.model.Property   property  = null;
		com.hp.hpl.jena.rdf.model.Statement  statement = null;
		
		subject = nameModel.createResource(graphUri);
		property = nameModel.createProperty("mo:timestamp");
		
		statement = nameModel.createLiteralStatement(subject, property, timestamp);
		nameModel.add(statement);
		
		return nameModel;
	}
	
	public static com.hp.hpl.jena.rdf.model.Model getDeleteModel(String id, long timestamp) {
		com.hp.hpl.jena.rdf.model.Model deleteModel = ModelFactory.createDefaultModel();
		
		com.hp.hpl.jena.rdf.model.Resource   subject   = null;
		com.hp.hpl.jena.rdf.model.Property   property  = null;
		com.hp.hpl.jena.rdf.model.Statement  statement = null;
		
		subject = deleteModel.createResource("mo:" + DataStore.encodeURL(id));
		property = deleteModel.createProperty("http://www.modaclouds.eu/rdfs/1.0/deletedmodel#id");
		
		statement = deleteModel.createLiteralStatement(subject, property, id);
		deleteModel.add(statement);
		
		subject = deleteModel.createResource("mo:" + DataStore.encodeURL(id));
		property = deleteModel.createProperty("http://www.modaclouds.eu/rdfs/1.0/deletedmodel#timestamp");
		
		statement = deleteModel.createLiteralStatement(subject, property, timestamp);
		deleteModel.add(statement);
		
		return deleteModel;
	}

}