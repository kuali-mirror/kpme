package org.kuali.hr.time.web;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UICalendarTest extends SeleneseTestCase {
    @Before
    public void setUp() throws Exception {
        selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://ci.kpme.kuali.org:9080/");
//        selenium = new DefaultSelenium("localhost", 4444, "*firefox /Applications/Firefox.app/Contents/MacOS/firefox-bin", "http://localhost:8080/");
        // set speed to 1 sec between each action. this is mainly for the ajax call to get the earn code.
        selenium.setSpeed("1000");
        try {
            selenium.start();
        } catch (Exception e) {

        }
    }

    @Test
    public void testAddAndDeleteTimeBlock() throws Exception {
        selenium.open("/tk-dev/TimeDetail.do");
        selenium.type("__login_user", "admin");
        selenium.click("//input[@name='login']");
        selenium.click("id=day_10");
        selenium.select("id=assignment", "value=1_1234_1");
        selenium.type("id=beginTimeField", "08:00 AM");
        selenium.type("id=endTimeField", "05:00 PM");
        selenium.click("//button[@type='button']");
        selenium.waitForPageToLoad("2000");
        selenium.click("class=event-delete");
        verifyFalse(selenium.isTextPresent("08:00 AM"));
        verifyFalse(selenium.isTextPresent("05:00 PM"));
    }

    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }
}


