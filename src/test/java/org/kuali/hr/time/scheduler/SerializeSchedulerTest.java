package org.kuali.hr.time.scheduler;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.rice.kns.service.KNSServiceLocator;

/**
 * This is temporary class its purpose is to add a TimeBlock Object into
 * Database So that Serialize job can do its work
 * 
 * @author
 * 
 */
public class SerializeSchedulerTest extends TkTestCase {
	@Test
	public void timeBlockSaveTest() throws Exception {
		//This is just to add temporarily TimeBlock Object it must be rolled back from DB manually this is just to test Scheduled Job Temporarily 
		TimeBlock timeBlock = new TimeBlock();
		timeBlock.setAmount(new	java.math.BigDecimal(10));
		timeBlock.setBeginTimestamp(new	java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
		timeBlock.setBeginTimestampTimezone("beginTZ");
		timeBlock.setClockLogCreated(true);
		timeBlock.setEarnCode("CD");
		timeBlock.setEndTimestamp(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
		timeBlock.setEndTimestampTimezone("endTimestampTimezone");
		timeBlock.setHours(new java.math.BigDecimal(10));
		timeBlock.setJobNumber(1l);
		timeBlock.setTaskId(10l);
		timeBlock.setUserPrincipalId("userPrincipalId");
		timeBlock.setTimestamp(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
		timeBlock.setWorkAreaId(10l);
		//Uncomment following line to save the Object into DB
		//KNSServiceLocator.getBusinessObjectService().save(timeBlock);
	
	}


}
