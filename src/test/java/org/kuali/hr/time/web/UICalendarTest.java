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
        // set speed to 1 sec between each action. this is mainly for the ajax call to get the earn code.
        selenium.setSpeed("1000");
        try {
            selenium.start();
        } catch (Exception e) {

        }
    }

    @Test
    public void testAddTimeBlock() throws Exception {
        selenium.open("/tk-dev/TimeDetail.do");
        selenium.type("__login_user", "admin");
        selenium.click("//input[@name='login']");
        selenium.mouseDownAt("css=td.fc-day10", "10,10");
        selenium.mouseUpAt("css=td.fc-day10", "10,10");
        selenium.select("assignment","value=30_30_30");
        selenium.type("beginTimeField", "08:15 AM");
        selenium.type("endTimeField", "05:15 PM");
        selenium.click("//button[@type='button']");
//        selenium.clickAt("//button[@type='button']", "10,10");
        verifyTrue(selenium.isTextPresent("08:15 AM"));
        verifyTrue(selenium.isTextPresent("05:15 PM"));
    }

    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }
}


