package org.kuali.hr.time.document.header;

import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class DocumentHeaderTest extends TkTestCase {
	
	
	
	@Test
	public void testDocumentHeaderPrevFetch() throws Exception{
		TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader("admin", 2345L);
		assertTrue(tdh!=null && tdh.getDocumentId() == 1234L);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		TimesheetDocumentHeader tdh = new TimesheetDocumentHeader();
		tdh.setDocumentId(1234L);
		tdh.setPrincipalId("admin");
		tdh.setPayBeginDate(TKUtils.getCurrentDate());
		tdh.setPayEndDate(TKUtils.getCurrentDate());
		
		KNSServiceLocator.getBusinessObjectService().save(tdh);
		
		tdh = new TimesheetDocumentHeader();
		tdh.setDocumentId(1000L);
		tdh.setPrincipalId("admin");
		tdh.setPayBeginDate(TKUtils.getCurrentDate());
		tdh.setPayEndDate(TKUtils.getCurrentDate());
		
		KNSServiceLocator.getBusinessObjectService().save(tdh);
		
		tdh = new TimesheetDocumentHeader();
		tdh.setDocumentId(2345L);
		tdh.setPrincipalId("admin");
		tdh.setPayBeginDate(TKUtils.getCurrentDate());
		tdh.setPayEndDate(TKUtils.getCurrentDate());
		
		KNSServiceLocator.getBusinessObjectService().save(tdh);
	}
}
