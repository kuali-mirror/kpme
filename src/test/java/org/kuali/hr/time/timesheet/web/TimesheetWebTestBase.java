package org.kuali.hr.time.timesheet.web;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.Ignore;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.web.TkLoginFilter;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Ignore
public class TimesheetWebTestBase extends TkTestCase {

    public static final Date JAN_AS_OF_DATE = new Date((new DateTime(2010, 1, 1, 0, 0, 0, 0, TkConstants.SYSTEM_DATE_TIME_ZONE)).getMillis());
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
        TkLoginFilter.TEST_ID = "admin";
    }

    public static String getTimesheetDocumentUrl(String tdocId) {
        return BASE_DETAIL_URL + tdocId;
    }

    /**
     * Uses an ID hack to manipulate the current Test user Login.
     *
     */
    public static HtmlPage loginAndGetTimeDetailsHtmlPage(String principalId, String tdocId, boolean assertValid) throws Exception {
        TkLoginFilter.TEST_ID = principalId;

        Person person = KIMServiceLocator.getPersonService().getPerson(principalId);
        assertNotNull(person);
        assertEquals(person.getPrincipalId(), principalId);

        HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getTimesheetDocumentUrl(tdocId));
        assertNotNull(page);
        //HtmlUnitUtil.createTempFile(page, "Login-"+principalId);

        String pageAsText = page.asText();
        if (assertValid) {
            assertTrue("Login info not present.", pageAsText.contains("Employee Id:"));
            assertTrue("Login info not present.", pageAsText.contains(person.getName()));
            assertTrue("Wrong Document Loaded.", pageAsText.contains(tdocId));
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
