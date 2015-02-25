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
		perform(null, true);
	}
	
	public static void perform(String[] args, boolean useFakeData) {
		logger.info("HDB Metrics Observer starting...");
		
		Configuration.loadFromEnrivonmentVariables();
		Configuration.loadFromSystemProperties();
		Configuration.loadFromArguments(args);
		
		MetricsObserver.RUNNING_TIME = -1;
		MetricsObserver mo = new MetricsObserver();
		mo.start();
		logger.debug("MetricsObserver started!");
		
		logger.info("HDB Metrics Observer started!");
        
		if (useFakeData) {
	        try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
	        logger.debug("Starting the producer...");
	        Producer.test(30);
		}
        
	}
	
	public static void perform() {
		perform(null, true);
	}
}
