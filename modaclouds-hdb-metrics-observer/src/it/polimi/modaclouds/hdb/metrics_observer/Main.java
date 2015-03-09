/**
 * Copyright ${year} deib-polimi
 * Contact: deib-polimi <riccardobenito.desantis@polimi.it>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package it.polimi.modaclouds.hdb.metrics_observer;

import it.polimi.modaclouds.hdb.metrics_observer.rest.Producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		perform(new String[] {"-fakemessages", "300", "-waitfakemessages", "10"});
	}
	
	public static void perform(String[] args) {
		logger.info("HDB Metrics Observer starting...");
		
		Configuration.loadFromEnrivonmentVariables();
		Configuration.loadFromSystemProperties();
		Configuration.loadFromArguments(args);
		
		MetricsObserver.RUNNING_TIME = -1;
		MetricsObserver mo = new MetricsObserver();
		mo.start();
		logger.debug("MetricsObserver started!");
		
		logger.info("HDB Metrics Observer started!");
		
		int fakeMessages = -1;
		int waitFakeMessages = -1;
		
		if (args != null)
			for (int i = 0; i < args.length; i+=2) {
				if (args[i].equals("-fakemessages") && args.length >= i+2) {
					try {
						fakeMessages = Integer.parseInt(args[i+1]);
					} catch (Exception e) { }
				} else if (args[i].equals("-waitfakemessages") && args.length >= i+2) {
					try {
						waitFakeMessages = Integer.parseInt(args[i+1]);
					} catch (Exception e) { }
				} else {
					i--;
				}
			}
        
		if (fakeMessages > 0) {
	        try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
	        logger.debug("Starting the producer...");
	        if (waitFakeMessages >= 0)
	        	Producer.test(fakeMessages, waitFakeMessages);
	        else 
	        	Producer.test(fakeMessages, 1000);
		}
        
	}
	
	public static void perform() {
		perform(null);
	}
}
