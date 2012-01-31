package org.kuali.hr.time.detail.web;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kns.service.KNSServiceLocator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ActualTimeInquiryWebTest extends TkTestCase {
	private String documentId;
	private TimeBlock timeBlock;
	@Test
	public void testActualTimeInquiry() throws Exception {
		String baseUrl = TkTestConstants.Urls.TIME_DETAIL_URL;
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	assertNotNull(page);
    	assertTrue("Clock Page contains Actual Time Inquiry Button", page.asText().contains("Actual Time Inquiry"));
	  	
	  	String atiUrl = baseUrl + "?methodToCall=actualTimeInquiry";
		HtmlPage testPage1 = HtmlUnitUtil.gotoPageAndLogin(atiUrl);
		assertNotNull(testPage1);
    	assertTrue("Actual Time Inquiry page contains close Button", testPage1.asText().contains("Close"));
    	assertTrue("Actual Time Inquiry page contains No value found message", testPage1.asText().contains("No values match this search."));
    	
    	this.createTB();
    	this.changeGracePeriodRule();
    	atiUrl = baseUrl + "?methodToCall=actualTimeInquiry&documentId=" + documentId;
    	HtmlPage testPage2 = HtmlUnitUtil.gotoPageAndLogin(atiUrl);
    	assertTrue("Actual Time Inquiry page contains One item retrived message", testPage2.asText().contains("One item retrieved."));
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
		timeBlock.setClockLogCreated(Boolean.TRUE);
		timeBlock.setTkWorkAreaId("1");
		timeBlock.setTkTaskId("1");
		timeBlock.setHrJobId("1");
		List<TimeBlock> tbList = new ArrayList<TimeBlock>();
		documentId = this.maxDocumentId().toString();
		timeBlock.setDocumentId(documentId);
		tbList.add(timeBlock);
		TkServiceLocator.getTimeBlockService().saveTimeBlocks(tbList);

		TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId.toString());
		td.setTimeBlocks(tbList);
	}
	
	public void changeGracePeriodRule() {
		GracePeriodRule gracePeriodRule = TkServiceLocator.getGracePeriodService().getGracePeriodRule(timeBlock.getBeginDate());
		gracePeriodRule.setHourFactor(new BigDecimal("1"));
		KNSServiceLocator.getBusinessObjectService().save(gracePeriodRule);
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
}
