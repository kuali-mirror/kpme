package org.kuali.hr.time.scheduler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Iterator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.rice.kns.service.KNSServiceLocator;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * A temporary Service class to serialize the Object into XML file
 * 
 */
public class TimeBlockSerializerService {
	/**
	 * This method will read all the timeBlock objects from DB and will serialize it to ObjData.xml file in user home folder
	 * @throws FileNotFoundException
	 */
	public void serialize() throws FileNotFoundException {
		// Fetching data using BO service
		Collection timeBlocks = KNSServiceLocator.getBusinessObjectService()
				.findAll(TimeBlock.class);
		Iterator<TimeBlock> itr = timeBlocks.iterator();
		while (itr.hasNext()) {

			TimeBlock timeBlockObj = itr.next();
			XStream xs = new XStream(new DomDriver());
			FileOutputStream fos;
			// writting it to file temporarily 'temp.xml' file will be created in user home directory 
			fos = new FileOutputStream(System.getProperty("user.home")
					+ "\\ObjData.xml", true);
			xs.toXML(timeBlockObj, fos);
		}

	}
}
