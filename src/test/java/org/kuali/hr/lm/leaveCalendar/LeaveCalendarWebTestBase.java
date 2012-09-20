package org.kuali.hr.lm.leaveCalendar;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.util.filter.TestAutoLoginFilter;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import java.sql.Date;


public class LeaveCalendarWebTestBase extends KPMETestCase {
    public static final Date JAN_AS_OF_DATE = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
    public static final String USER_PRINCIPAL_ID = "admin";

    private String baseDetailURL = ""; // moved to setUp() method -- initialization order for TkTestConstants problem.

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setBaseDetailURL(TkTestConstants.Urls.LEAVE_CALENDAR_URL + "?documentId=");
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        //TkLoginFilter.TEST_ID = "admin";
        TestAutoLoginFilter.OVERRIDE_ID = "";
    }

    public String getLeaveCalendarUrl(String tdocId) {
        return getBaseDetailURL() + tdocId;
    }

    /**
     * Uses an ID hack to manipulate the current Test user Login.
     *
     */
    public synchronized HtmlPage loginAndGetLeaveCalendarHtmlPage(String principalId, String tdocId, boolean assertValid) throws Exception {

        Person person = KimApiServiceLocator.getPersonService().getPerson(principalId);
        Assert.assertNotNull(person);
        Assert.assertEquals(person.getPrincipalId(), principalId);
        TestAutoLoginFilter.OVERRIDE_ID = principalId;
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getLeaveCalendarUrl(tdocId));
        TestAutoLoginFilter.OVERRIDE_ID = "";
        Assert.assertNotNull(page);
        HtmlUnitUtil.createTempFile(page, "Login-"+principalId);

        String pageAsText = page.asText();
        if (assertValid) {
            Assert.assertTrue("Login info not present.", pageAsText.contains("Employee Id:"));
            Assert.assertTrue("Login info not present for " + person.getName() , pageAsText.contains(person.getName()));
            Assert.assertTrue("Wrong Document Loaded.", pageAsText.contains(tdocId));
        }

        return page;
    }

    public String getBaseDetailURL() {
        return baseDetailURL;
    }

    public void setBaseDetailURL(String baseDetailURL) {
        this.baseDetailURL = baseDetailURL;
    }
}
