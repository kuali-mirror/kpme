package org.kuali.hr.time.clock.web;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
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
            if (temp > maxId) {
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
            if (temp > maxId) {
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

    @Ignore
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

        if (tbId == null) {
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

    /**
     * This test is to :
     * 1) make sure the seconds on clockTimestamp and timestamp are preserved when there is no grace period rule.
     * 2) the clock in / out button is correctly rendered
     *
     * @throws Exception
     */
    @Test
    public void testClockActionWithoutGracePeriodRule() throws Exception {
        // Make sure there is no active grace period rule
        GracePeriodRule gpr = TkServiceLocator.getGracePeriodService().getGracePeriodRule(TKUtils.getCurrentDate());
        if (gpr != null && gpr.isActive()) {
            gpr.setActive(false);
            KNSServiceLocator.getBusinessObjectService().save(gpr);
        }

        // Clock in
        clockIn();
        // Make sure clock out button is rendered
        ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog("admin");
        // Make sure both timestamps preserve seconds
        assertTrue("The seconds on clock timestamp should be preserved", new DateTime(lastClockLog.getClockTimestamp().getTime()).getSecondOfMinute() != 0);
        assertTrue("The seconds on timestamp should be preserved", new DateTime(lastClockLog.getTimestamp().getTime()).getSecondOfMinute() != 0);

        // Clock out
        clockOut();
        // Make sure both timestamps preserve seconds
        lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog("admin");
        assertTrue("The seconds on clock timestamp should be preserved", new DateTime(lastClockLog.getClockTimestamp().getTime()).getSecondOfMinute() != 0);
        assertTrue("The seconds on timestamp should be preserved", new DateTime(lastClockLog.getTimestamp().getTime()).getSecondOfMinute() != 0);
    }

    @Test
    public void testClockActionWithGracePeriodRule() throws Exception {
        GracePeriodRule gpr = new GracePeriodRule();
        gpr.setTkGracePeriodRuleId("1");
        gpr.setEffectiveDate(TKUtils.createDate(1, 1, 2010, 0, 0, 0));
        gpr.setHourFactor(new BigDecimal(3));
        gpr.setTimestamp(new Timestamp(System.currentTimeMillis()));
        
        gpr.setActive(true);
        KNSServiceLocator.getBusinessObjectService().save(gpr);

        // Clock in
        clockIn();
        // Make sure clock out button is rendered
        ClockLog lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog("admin");
        // Make sure both timestamps preserve seconds
        assertTrue("The seconds on clock timestamp should NOT be preserved", new DateTime(lastClockLog.getClockTimestamp().getTime()).getSecondOfMinute() == 0);
        assertTrue("The seconds on timestamp should be preserved", new DateTime(lastClockLog.getTimestamp().getTime()).getSecondOfMinute() != 0);

        // Clock out
        clockOut();
        // Make sure both timestamps preserve seconds
        lastClockLog = TkServiceLocator.getClockLogService().getLastClockLog("admin");
        assertTrue("The seconds on clock timestamp should NOT be preserved", new DateTime(lastClockLog.getClockTimestamp().getTime()).getSecondOfMinute() == 0);
        assertTrue("The seconds on timestamp should be preserved", new DateTime(lastClockLog.getTimestamp().getTime()).getSecondOfMinute() != 0);
    }

    private HtmlPage clockIn() throws Exception {

        // Clock in
        HtmlPage page = clockAction(TkConstants.CLOCK_IN);

        // Make sure clock in button is rendered
        HtmlUnitUtil.createTempFile(page);
        assertTrue("The clock out button should have displayed", page.asText().contains("Clock Out"));
        assertTrue("The clock out button should have displayed", page.asText().contains("Take Lunch"));

        return page;
    }

    private HtmlPage clockOut() throws Exception {
        // Clock out
        HtmlPage page = clockAction(TkConstants.CLOCK_OUT);

        // Make sure clock in button is rendered
        HtmlUnitUtil.createTempFile(page);
        assertTrue("The clock out button should have displayed", page.asText().contains("Clock In"));

        return page;
    }

    /**
     * This method is used for clocking in and out.
     * For some reason, htmlunit couldn't click the clock action button correctly.
     * It's probably because we bind a onClick event to the button instead of submitting the form.
     *
     * @param clockAction
     * @return HtmlPage page
     */
    private HtmlPage clockAction(String clockAction) throws Exception {
        String baseUrl = TkTestConstants.Urls.CLOCK_URL;
        String actionUrl = baseUrl + "?methodToCall=clockAction&selectedAssignment=30_30_30&currentClockAction=" + clockAction;
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(actionUrl);
        assertNotNull("The login page shouldn't be null", page);
        Thread.sleep(3000);
        return page;
    }

}
