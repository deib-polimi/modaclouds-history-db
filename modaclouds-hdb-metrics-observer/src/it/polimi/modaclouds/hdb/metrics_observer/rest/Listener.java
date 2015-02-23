package it.polimi.modaclouds.hdb.metrics_observer.rest;

import it.polimi.modaclouds.hdb.metrics_observer.Configuration;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An object of this class is a listener for a specific port and a specific kind of message.
 * 
 * @author Riccardo B. Desantis
 * 
 */
public class Listener extends Thread {
    
    private Component component;
    
    private static final Logger logger = LoggerFactory.getLogger(Listener.class);
    
    private int port;
    
    public Listener(int port) {
        component = new Component();
        this.port = port;
        component.getServers().add(Protocol.HTTP, port);
        component.getClients().add(Protocol.FILE);
    }
    
    public boolean add(String path, Class<? extends ServerResource> clazz) {
    	component.getDefaultHost().attach(path, clazz);
        logger.debug("Listener created for port {} and path {}.", port, path);
        
        return true;
    }
    
    public Listener() {
        this(Configuration.DEFAULT_PORT);
    }
    
    public static int RUNNING_TIME = 100000;
    
    private boolean started = false;
    
    public boolean isStarted() {
    	return started;
    }
    
    @Override
    public void run() {
    	started = true;
    	
    	try {
			component.start();
			logger.debug("Component started.");
		} catch (Exception e) {
			logger.error("Error while starting the listener!", e);
		}
    	if (RUNNING_TIME > 0) {
	    	try {
	    		Thread.sleep(RUNNING_TIME);
	    	} catch (Exception e) { }
	    	try {
				component.stop();
				logger.debug("Component stopped.");
			} catch (Exception e) {
				logger.error("Error while stopping the listener!", e);
			}
    	}
    	
    	started = false;
    }
    
}
