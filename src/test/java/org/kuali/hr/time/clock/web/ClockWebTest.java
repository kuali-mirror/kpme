package org.kuali.hr.time.clock.web;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class ClockWebTest extends TkTestCase {
	
	private String documentId;
	private String tbId;
	private TimeBlock timeBlock;
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	public Long maxDocumentId() {
		Collection aCol = KNSServiceLocator.getBusinessObjectService().findAll(TimesheetDocumentHeader.class);
		Long maxId = new Long(-1);
		Iterator<TimesheetDocumentHeader> itr = aCol.iterator();
		while (itr.hasNext()) {
			TimesheetDocumentHeader tdh = itr.next();
			Long temp = new Long(tdh.getDocumentId());
			if(temp > maxId) {
				maxId = temp;
			}
		}
		return maxId;
	}
	
	public Long maxTimeBlockId() {
		Collection aCol = KNSServiceLocator.getBusinessObjectService().findAll(TimeBlock.class);
		Long maxId = new Long(-1);
		Iterator<TimeBlock> itr = aCol.iterator();
		while (itr.hasNext()) {
			TimeBlock tb = itr.next();
			Long temp = new Long(tb.getTkTimeBlockId());
			if(temp > maxId) {
				maxId = temp;
			}
		}
		return maxId;
	}
	
	public void createTB() {
		timeBlock = new TimeBlock();
		timeBlock.setUserPrincipalId("admin");
		timeBlock.setJobNumber(2L);
		timeBlock.setWorkArea(1234L);
		timeBlock.setTask(1L);
		timeBlock.setEarnCode("RGN");
		Timestamp beginTimestamp = new Timestamp(System.currentTimeMillis());
		timeBlock.setBeginTimestamp(beginTimestamp);
		Timestamp endTimestamp = new Timestamp(System.currentTimeMillis());
		timeBlock.setEndTimestamp(endTimestamp);
		TimeHourDetail timeHourDetail = new TimeHourDetail();
		timeHourDetail.setEarnCode("RGN");
		timeHourDetail.setHours(new BigDecimal(2.0));
		timeBlock.getTimeHourDetails().add(timeHourDetail);
		timeBlock.setHours(new BigDecimal(2.0));
		List<TimeBlock> tbList = new ArrayList<TimeBlock>();
		documentId = this.maxDocumentId().toString();
		timeBlock.setDocumentId(documentId);
		tbList.add(timeBlock);
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(tbList);
		
		tbId = timeBlock.getTkTimeBlockId().toString();
		TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId.toString());
		td.setTimeBlocks(tbList);
		
	}
	
	@Test
	public void testDistributeTB() throws Exception {
		String baseUrl = TkTestConstants.Urls.CLOCK_URL;
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	assertNotNull(page);
    	assertTrue("Clock Page contains Distribute Button", page.asText().contains("Distribute Time Blocks"));
    	this.createTB();
    	this.setWebClient(page.getWebClient());
    	HtmlElement element = page.getElementByName("distributeTime");
	  	assertNotNull(element);
//	  	HtmlPage testPage1 = element.click();
//	  	assertTrue("Distribute Page contains Close button", testPage1.asText().contains("Close"));
//        assertTrue("Distribute Page contains Close button", testPage1.asText().contains("Edit"));
	  	
    	// timeDistribute.jsp
    	String distributeUrl = baseUrl + "?methodToCall=distributeTimeBlocks";
    	HtmlPage page1 = HtmlUnitUtil.gotoPageAndLogin(distributeUrl);
        assertTrue("Distribute Page contains Close button", page1.asText().contains("Close"));
        assertTrue("Distribute Page contains Close button", page1.asText().contains("Edit"));
        
        element = page1.getElementByName("editTimeBlock");
	  	assertNotNull(element);
	  	assertTrue("Onclick attribute of Edit button contains", element.getAttribute("onclick").contains("Clock.do?methodToCall=editTimeBlock&editTimeBlockId="));

	  	if(tbId == null) {
	  		tbId = this.maxTimeBlockId().toString();
	  	}
	  	
	  	//editTimeBlock.jsp
	  	String editUrl = baseUrl + "?methodToCall=editTimeBlock&editTimeBlockId=" + tbId;
    	HtmlPage page3 = HtmlUnitUtil.gotoPageAndLogin(editUrl);
	  	
	  	// editTimeBlock.jsp
	  	assertTrue("Edit Time Blocks Page contains Cancel button", page3.asText().contains("Add"));
	  	assertTrue("Edit Time Blocks Page contains Save button", page3.asText().contains("Save"));
	  	assertTrue("Edit Time Blocks Page contains Cancel button", page3.asText().contains("Cancel"));
	  	
	  	element = page3.getElementByName("addTimeBlock");
	  	assertNotNull(element);
	  	assertTrue("Onclick attribute of Add button contains", element.getAttribute("onclick").contains("javascript: addTimeBlockRow(this.form);"));
	  	
	  	this.setWebClient(page3.getWebClient());
	  	
//	  	HtmlPage page4 = element.click();
//	  	assertTrue("Edit Time Blocks Page contains Cancel button", page4.asText().contains("Add"));
	  	
	}
	
	public void setWebClient(WebClient webClient) {
		webClient.setJavaScriptEnabled(true);
		webClient.setThrowExceptionOnScriptError(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.waitForBackgroundJavaScript(10000);
	}

}
