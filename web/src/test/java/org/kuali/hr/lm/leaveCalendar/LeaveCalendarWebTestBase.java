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
package org.kuali.hr.lm.leaveCalendar;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.util.filter.TestAutoLoginFilter;
import org.kuali.kpme.core.KPMETestCase;
import org.kuali.kpme.core.util.HtmlUnitUtil;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public abstract class LeaveCalendarWebTestBase extends KPMETestCase {
    private static final Logger LOG = Logger.getLogger(LeaveCalendarWebTestBase.class);
    public static final DateTime JAN_AS_OF_DATE = new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
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
        //KPMELoginFilter.TEST_ID = "admin";
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
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), getLeaveCalendarUrl(tdocId));
        TestAutoLoginFilter.OVERRIDE_ID = "";
        Assert.assertNotNull(page);
        HtmlUnitUtil.createTempFile(page, "Login-"+principalId);
        LOG.debug(page.asText());
        String pageAsText = page.asText();
        if (assertValid) {
            Assert.assertTrue("Login info not present.", pageAsText.contains("Employee Id:"));
            Assert.assertTrue("Login info not present for " + person.getName() , pageAsText.contains(person.getName()));
            Assert.assertTrue("Wrong Document Loaded.", pageAsText.contains(tdocId));
        }

        return page;
    }

    /**
     * Uses an ID hack to manipulate the current Test user Login.
     *
     */
    public synchronized HtmlPage login(String principalId, String tdocId, boolean assertValid) throws Exception {

        Person person = KimApiServiceLocator.getPersonService().getPerson(principalId);
        Assert.assertNotNull(person);
        Assert.assertEquals(person.getPrincipalId(), principalId);
        TestAutoLoginFilter.OVERRIDE_ID = principalId;
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), getLeaveCalendarUrl(tdocId));
        TestAutoLoginFilter.OVERRIDE_ID = "";
        Assert.assertNotNull(page);

        return page;
    }
    
    public String getBaseDetailURL() {
        return baseDetailURL;
    }

    public void setBaseDetailURL(String baseDetailURL) {
        this.baseDetailURL = baseDetailURL;
    }
}
