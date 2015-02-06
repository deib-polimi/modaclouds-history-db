package it.polimi.hdb.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A manager keeps polling the queue for new packets, then read them and saves them correctly in the datastore.
 * 
 * @author Riccardo B. Desantis
 *
 */
public class Manager extends Thread {
	
	private static final Logger logger = LoggerFactory.getLogger(Manager.class);
	
	private Queue queue;
	private DataStore dataStore;
	
	public Manager() {
		try {
			queue = new Queue();
			dataStore = new DataStore();
			logger.debug("Manager initialized.");
		} catch (Exception e) {
			logger.error("Argh!", e);
		}
	}
	
	public static int RUNNING_TIME = 100000;
	
	@Override
	public void run() {
		logger.debug("Manager started.");
		long x = System.currentTimeMillis();
		long y = x;
		int runningTime = RUNNING_TIME;
		if (runningTime < 0)
			runningTime = Integer.MAX_VALUE;
		while (y - x < runningTime) {
			String msg = null;
			try {
				msg = queue.getMessage();
			} catch (Exception e) {
				logger.error("Argh!", e);
			}
			if (msg == null)
				continue;
			logger.debug("Message received:\n{}", msg);
			parseMessage(msg);
			
			y = System.currentTimeMillis();
		}
		try{
			queue.close();
		} catch (Exception e) {
			logger.error("Argh!", e);
		}
	}
	
	public void parseMessage(String msg) {
		logger.debug("Message to be parsed:\n{}", msg);
		dataStore.add(msg);
	}
	
	public static void main(String[] args) {
		Manager.RUNNING_TIME = -1;
		new Manager().start();
	}
}
