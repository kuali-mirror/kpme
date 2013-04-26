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
package org.kuali.hr.time.timesheet.web;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Assert;
import org.junit.Ignore;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.util.filter.TestAutoLoginFilter;
import org.kuali.kpme.tklm.common.TKUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Ignore
public class TimesheetWebTestBase extends KPMETestCase {
    private static final Logger LOG = Logger.getLogger(TimesheetWebTestBase.class);
    public static final DateTime JAN_AS_OF_DATE = new DateTime(2010, 1, 1, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
    public static final String USER_PRINCIPAL_ID = "admin";
    public static String BASE_DETAIL_URL = ""; // moved to setUp() method -- initialization order for TkTestConstants problem.

    /**
     * @throws Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        BASE_DETAIL_URL = TkTestConstants.Urls.TIME_DETAIL_URL + "?documentId=";
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        //KPMELoginFilter.TEST_ID = "admin";
        TestAutoLoginFilter.OVERRIDE_ID = "";
    }

    public static String getTimesheetDocumentUrl(String tdocId) {
        return BASE_DETAIL_URL + tdocId;
    }

    /**
     * Uses an ID hack to manipulate the current Test user Login.
     *
     */
    public static synchronized HtmlPage loginAndGetTimeDetailsHtmlPage(WebClient webClient, String principalId, String tdocId, boolean assertValid) throws Exception {

        Person person = KimApiServiceLocator.getPersonService().getPerson(principalId);
        Assert.assertNotNull(person);
        Assert.assertEquals(person.getPrincipalId(), principalId);
        TestAutoLoginFilter.OVERRIDE_ID = principalId;
        // need to create new web client for new user
        webClient.getPage(new URL(TkTestConstants.Urls.LOG_OUT_URL));
        webClient.closeAllWindows();
        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(webClient, getTimesheetDocumentUrl(tdocId));
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
     * Examines the JSON structure that is written to each output TimeDetails
     * page.
     * @param json The JSON Object to examine
     * @param thdList The (optional) list of Time Hour Details values
     * @param checkValues The list of values to check for in the JSON object
     * @return true if the JSON object contains the required values, false otherwise.
     */
    public static boolean checkJSONValues(JSONObject json, List<Map<String,Object>> thdList, Map<String,Object> checkValues) {
        boolean contains = false;

        for(Object eso : json.entrySet()) {
             // This is the outer TimeBlock ID -> Time Block Data mapping
             Map.Entry e = (Map.Entry)eso;
             JSONObject innerMap = (JSONObject)e.getValue(); // the values we actually care about.
             boolean localContains = true;
             for (Map.Entry<String,Object> cve : checkValues.entrySet()) {
                 Object joValue = innerMap.get(cve.getKey());
                 Object cvValue = cve.getValue();

                 // Null Checks..
                 if (cvValue == null && joValue == null)
                     localContains &= true;
                 else if (joValue == null || cvValue == null)
                     localContains = false;
                 else
                     localContains &= StringUtils.equals(joValue.toString(), cvValue.toString());
             }

             // Check Time Hour Details
             if (localContains && thdList != null) {
                 JSONArray thdjarray = (JSONArray) JSONValue.parse((String) innerMap.get("timeHourDetails"));
                 // For each user provided THD check element
                 for (Map<String,Object> u_thd_o : thdList) {
                     // Look at each Inner TimeHourDetails Map
                     boolean thd_l = false;
                     for (final Object o : thdjarray) {
                         thd_l |= checkJSONValues(new JSONObject() {{ put("outer", o); }}, null, u_thd_o);
                     }
                     localContains &= thd_l;
                 }
             }

             contains |= localContains;
        }

        return contains;
    }

    public static boolean checkJSONValues(String json, List<Map<String,Object>> thdList, Map<String,Object> checkValues) {
        return checkJSONValues((JSONObject)JSONValue.parse(json), thdList, checkValues);
    }
}
