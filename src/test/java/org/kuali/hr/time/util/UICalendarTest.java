package org.kuali.hr.time.util;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UICalendarTest extends SeleneseTestCase {
    @Before
    public void setUp() throws Exception {
//        selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://ci.kpme.kuali.org:9080/");
        selenium = new DefaultSelenium("localhost", 4444, "*firefox /Applications/Firefox.app/Contents/MacOS/firefox-bin", "http://ci.kpme.kuali.org:9080/");
        // set speed to 1 sec between each action. this is mainly for the ajax call to get the earn code.
        selenium.setSpeed("1000");
        try {
            selenium.start();
        } catch (Exception e) {

        }
    }

    /**
     * Test adding and deleting a timeblock
     *
     * @throws Exception
     */
    @Test
    public void testAddAndDeleteTimeBlock() throws Exception {
        openPageAndLogin("/tk-dev/TimeDetail.do");
        selenium.waitForPageToLoad("5000");
        selenium.click("id=day_9");
        selenium.select("id=assignment", "value=1_1234_1");
        selenium.type("id=beginTimeField", "08:00 AM");
        selenium.type("id=endTimeField", "05:00 PM");
        selenium.click("//button[@type='button']");
        selenium.waitForPageToLoad("5000");
        selenium.click("class=event-delete");
        selenium.waitForPageToLoad("3000");
        verifyFalse(selenium.isTextPresent("08:00 AM"));
        verifyFalse(selenium.isTextPresent("05:00 PM"));
    }

    /**
     * This is to test that the entry fields for time / hours / amount should be correspondent with the selected earn code
     *
     * @throws Exception
     */
    @Test
    public void testTestEarnCodeSwitch() throws Exception {
        openPageAndLogin("/tk-dev/TimeDetail.do");
        selenium.click("id=day_10");
        selenium.select("id=assignment", "label=work area description : $0.00 Rcd 1 TEST-DEPT");
        // verify the time entry fields are presented when the earn code type is "TIME"
        verifyTrue(selenium.isTextPresent("exact:In:"));
        verifyTrue(selenium.isTextPresent("exact:Out:"));
        verifyTrue(selenium.isTextPresent("RGH : REGULAR"));
        // verify the time entry fields are presented when the earn code type is "HOURS"
        selenium.select("id=earnCode", "label=SCK : SICK");
        verifyTrue(selenium.isTextPresent("exact:Hours:"));
        // verify the time entry fields are presented when the earn code type is "AMOUNT"
        selenium.select("id=earnCode", "label=TIP : Tips");
        verifyTrue(selenium.isTextPresent("exact:Amount:"));
    }

    /**
     * Test clocking in and out
     *
     * @throws Exception
     */
    @Test
    public void testTestClockInAndOut() throws Exception {
        openPageAndLogin("/tk-dev/Clock.do");
        selenium.waitForPageToLoad("3000");
        selenium.select("id=assignment", "label=work area description : $0.00 Rcd 1 TEST-DEPT");
        // clock in
        selenium.click("id=clock-button");
        selenium.waitForPageToLoad("3000");
        // verify the clock out button is presented
        verifyEquals("Clock Out", selenium.getValue("id=clock-button"));
        // clock out
        selenium.click("id=clock-button");
        // go to the time detail page and make sure clocking in/out is successful
        selenium.click("link=Time Detail");
        selenium.waitForPageToLoad("3000");
        verifyTrue(selenium.isTextPresent("exact:RGN: REGULAR"));
    }

    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }

    private void openPageAndLogin(String pageLink) {
        selenium.open(pageLink);
        selenium.type("__login_user", "admin");
        selenium.click("//input[@name='login']");
        selenium.waitForPageToLoad("2000");
    }
}


