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
package it.polimi.modaclouds.hdb.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		perform(args);
	}
	
	public static void perform(String[] args) {
		logger.info("HDB Manager starting...");
		
		Configuration.loadFromEnrivonmentVariables();
		Configuration.loadFromSystemProperties();
		Configuration.loadFromArguments(args);
		
		DataStore.reset();
		
		Manager.RUNNING_TIME = -1;
		Manager m1 = new Manager(Configuration.QUEUE_RESULTS);
		m1.start();
		Manager m2 = new Manager(Configuration.QUEUE_MODELS);
		m2.start();
		Manager m3 = new Manager(Configuration.QUEUE_DELTA_MODELS);
		m3.start();
		Manager m4 = new Manager(Configuration.QUEUE_MODELS_DELETE);
		m4.start();
		
		logger.debug("Managers started!");
		
		logger.info("HDB Manager started!");
	}
	
	public static void perform() {
		perform(null);
	}
}
