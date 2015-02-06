package it.polimi.modaclouds.hdb.metrics_observer.rest;

import it.polimi.modaclouds.hdb.metrics_observer.Configuration;
import it.polimi.modaclouds.hdb.metrics_observer.Queue;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.restlet.resource.Post;
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
    
    public Listener(int port, String path) {
        component = new Component();
        component.getServers().add(Protocol.HTTP, port);
        component.getClients().add(Protocol.FILE);
        component.getDefaultHost().attach(path, Listener.MyServerResource.class);
        logger.debug("Listener created for port {} and path {}.", port, path);
    }
    
    public static class MyServerResource extends ServerResource {
        @Post
        public String addMessage(String message) {
        	// Print the requested URI path
            String res = "Resource URI  : " + getReference() + '\n' + "Root URI      : "
                    + getRootRef() + '\n' + "Routed part   : "
                    + getReference().getBaseRef() + '\n' + "Remaining part: "
                    + getReference().getRemainingPart();
        	logger.debug("\n{}", res);
        	
            try {
                Queue queue = new Queue();
                queue.addMessage(message);
                queue.close();
                return "Message added to the queue!\n" + message;
            } catch (Exception e) {
                logger.error("Argh!", e);
                return "Error while adding the message to the queue!\n" + message;
            }
        }
    }
    
    public static int RUNNING_TIME = 100000;
    
    @Override
    public void run() {
    	try {
			component.start();
			logger.debug("Component started.");
		} catch (Exception e) {
			logger.error("Argh!", e);
		}
    	if (RUNNING_TIME > 0) {
	    	try {
	    		Thread.sleep(RUNNING_TIME);
	    	} catch (Exception e) { }
	    	try {
				component.stop();
				logger.debug("Component stopped.");
			} catch (Exception e) {
				logger.error("Argh!", e);
			}
    	}
    }
    
    public Listener() {
        this(Configuration.DEFAULT_PORT, Configuration.DEFAULT_PATH);
    }
    
    public static void main(String[] args) {
    	Listener.RUNNING_TIME = -1;
        new Listener().start();
    }
}
