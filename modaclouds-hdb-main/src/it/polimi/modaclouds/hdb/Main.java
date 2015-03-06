package it.polimi.modaclouds.hdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		
		System.out.println("\nHistory-DB\n");
		
		if (args.length > 0) {
			
			boolean help = false;
			for (int i = 0; i < args.length && !help; ++i)
				if (args[i].equals("-help") || args[i].equals("-h") || args[i].equals("--help"))
					help = true;
			
			
			if (help) {
				
				System.out.println(
"Usage: historydb [options]\n" +
"  Options:\n" +
"    -queueip\n" +
"       Queue endpoint IP address\n" +
"       Default: 127.0.0.1\n" +
"    -queueport\n" +
"       Queue endpoint port\n" +
"       Default: 5672\n" +
"    -dbip\n" +
"       DB endpoint IP address\n" +
"       Default: 127.0.0.1\n" +
"    -dbpath\n" +
"       DB URL path\n" +
"       Default: /ds\n" +
"    -dbport\n" +
"       DB endpoint port\n" +
"       Default: 3030\n" +
"    -listenerport\n" +
"       Listener endpoint port\n" +
"       Default: 31337\n" +
"    -fakemessages\n" +
"       Test the tool sending a number of fake messages at the beginning\n" +
"       Default: 0\n" +
"    -waitfakemessages\n" +
"       The ms to wait between each fake message\n" +
"       Default: 1000 (1 second)\n" +
"    -help | -h | --help\n" +
"       Shows this message."
						);
				
				return;
			}
		}
		
		perform(args);
	}
	
	public static void perform(String[] args) {
		it.polimi.modaclouds.hdb.manager.Main.perform(args);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.error("Error while waiting!", e);
		}
		
		it.polimi.modaclouds.hdb.metrics_observer.Main.perform(args);
	}
	
	public static void perform() {
		perform(null);
	}

}
