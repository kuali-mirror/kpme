/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.tklm.time.scheduler;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Iterator;

import org.kuali.hr.tklm.time.timeblock.TimeBlock;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * A temporary Service class to serialize the Object into XML file
 * 
 */
public class TimeBlockSerializerService {
	/**
	 * This method will read all the timeBlock objects from DB and will
	 * serialize it to ObjData.xml file in user home folder
	 * 
	 * @throws FileNotFoundException
	 */
	public void serializeToXML() throws FileNotFoundException {
		// Fetching data using BO service
		Collection timeBlocks = KRADServiceLocator.getBusinessObjectService()
				.findAll(TimeBlock.class);
		Iterator<TimeBlock> itr = timeBlocks.iterator();
		while (itr.hasNext()) {
			TimeBlock timeBlockObj = itr.next();
			XStream xs = new XStream(new DomDriver());
			FileOutputStream fos;
			// writting it to file temporarily 'temp.xml' file will be created
			// in user home directory
			fos = new FileOutputStream(System.getProperty("user.home")
					+ "\\TimeBlockData.xml", true);
			xs.toXML(timeBlockObj, fos);
		}

	}

	public void serializeToCSV() throws FileNotFoundException {
		// Fetching data using BO service
		Collection timeBlocks = KRADServiceLocator.getBusinessObjectService()
				.findAll(TimeBlock.class);
		Iterator<TimeBlock> itr = timeBlocks.iterator();
		while (itr.hasNext()) {

			TimeBlock timeBlockObj = itr.next();
			try {
				// Create file
				FileWriter fstream = new FileWriter(System.getProperty("user.home")
						+ "\\TimeBlockData.csv", true);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(timeBlockObj.toCSVString());
				// Close the output BufferedWriter
				out.close();
			} catch (Exception e) {// Catch exception if any
				e.printStackTrace();
			}
		}

	}

}
