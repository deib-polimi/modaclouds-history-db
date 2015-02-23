package it.polimi.modaclouds.hdb.metrics_observer;

import it.polimi.modaclouds.hdb.metrics_observer.rest.Listener;

import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class of the project that will initialize everything.
 * 
 * @author Riccardo B. Desantis
 *
 */
public class MetricsObserver {
	
	private static final Logger logger = LoggerFactory.getLogger(MetricsObserver.class);
	
	private Listener listener;
	
	public MetricsObserver() {
		listener = new Listener();
		listener.add(Configuration.DEFAULT_PATH, ResultsServerResource.class);
		listener.add(Configuration.DEFAULT_PATH_MODEL, ModelsServerResource.class);
	}
	
	public static class ResultsServerResource extends ServerResource {
        @Post
        public String addResult(String message) {
        	// Print the requested URI path
            String res = "Resource URI  : " + getReference() + '\n' + "Root URI      : "
                    + getRootRef() + '\n' + "Routed part   : "
                    + getReference().getBaseRef() + '\n' + "Remaining part: "
                    + getReference().getRemainingPart();
        	logger.debug("\n{}", res);
        	
            try {
                Queue queue = new Queue(Configuration.QUEUE_RESULTS);
                queue.addMessage(message);
                queue.close();
                
                logger.info("Monitoring data added to the queue.");
                
                return "Message added to the queue!\n" + message;
            } catch (Exception e) {
                logger.error("Error while adding the monitoring data to the queue.", e);
                return "Error while adding the message to the queue!\n" + message;
            }
        }
    }
    
    public static class ModelsServerResource extends ServerResource {
        @Post
        public String addDeltaModel(String message) {
        	// Print the requested URI path
            String res = "Resource URI  : " + getReference() + '\n' + "Root URI      : "
                    + getRootRef() + '\n' + "Routed part   : "
                    + getReference().getBaseRef() + '\n' + "Remaining part: "
                    + getReference().getRemainingPart();
        	logger.debug("\n{}", res);
        	
            try {
                Queue queue = new Queue(Configuration.QUEUE_DELTA_MODELS);
                queue.addMessage(message);
                queue.close();
                
                logger.info("Update to a model added to the queue.");
                
                return "Message added to the queue!\n" + message;
            } catch (Exception e) {
                logger.error("Error while adding the update to the model to the queue.", e);
                return "Error while adding the message to the queue!\n" + message;
            }
        }
        
        @Put
        public String addModel(String message) {
        	// Print the requested URI path
            String res = "Resource URI  : " + getReference() + '\n' + "Root URI      : "
                    + getRootRef() + '\n' + "Routed part   : "
                    + getReference().getBaseRef() + '\n' + "Remaining part: "
                    + getReference().getRemainingPart();
        	logger.debug("\n{}", res);
        	
            try {
                Queue queue = new Queue(Configuration.QUEUE_MODELS);
                queue.addMessage(message);
                queue.close();
                
                logger.info("Model added to the queue.");
                
                return "Message added to the queue!\n" + message;
            } catch (Exception e) {
                logger.error("Error while adding the model to the queue.", e);
                return "Error while adding the message to the queue!\n" + message;
            }
        }
        
        @Delete
        public String deleteModel() {
        	// Print the requested URI path
            String res = "Resource URI  : " + getReference() + '\n' + "Root URI      : "
                    + getRootRef() + '\n' + "Routed part   : "
                    + getReference().getBaseRef() + '\n' + "Remaining part: "
                    + getReference().getRemainingPart();
        	logger.debug("\n{}", res);
        	
        	String id = getReference().getRemainingPart();
        	
        	if (id.length() == 0 || id.indexOf('/') != 0)
        		return "No id found in the request, abort";
        	
        	id = id.substring(1);
        	
            try {
                Queue queue = new Queue(Configuration.QUEUE_MODELS_DELETE);
                queue.addMessage(id);
                queue.close();
                
                logger.info("Cancellation of a model added to the queue.");
                
                return "Message added to the queue!\n" + id;
            } catch (Exception e) {
                logger.error("Error while adding the cancellation of a model to the queue.", e);
                return "Error while adding the message to the queue!\n" + id;
            }
        }
    }
    
    public static int RUNNING_TIME = 100000;
    
    public void start() {
    	if (listener.isStarted())
    		return;
    	
    	Listener.RUNNING_TIME = RUNNING_TIME;
    	listener.start();
    }
}
