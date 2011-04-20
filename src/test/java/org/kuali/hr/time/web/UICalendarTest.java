package org.kuali.hr.time.web;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UICalendarTest extends SeleneseTestCase {
    @Before
    public void setUp() throws Exception {
        selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:9080/");
        try {
            selenium.start();
        } catch (Exception e) {

        }
    }

    @Test
    public void testAddTimeBlock() throws Exception {
        selenium.open("/tk-dev/TimeDetail.do");
        selenium.type("__login_user", "fran");
        selenium.click("//input[@name='login']");
        selenium.waitForPageToLoad("30000");
        selenium.mouseDownAt("css=td.fc-day10", "10,10");
        selenium.mouseUpAt("css=td.fc-day10", "10,10");
        selenium.type("beginTimeField", "08:15 AM");
        selenium.type("endTimeField", "05:15 PM");
        selenium.click("//button[@type='button']");
        selenium.waitForPageToLoad("30000");
        verifyTrue(selenium.isTextPresent("08:15 AM"));
        verifyTrue(selenium.isTextPresent("05:15 PM"));
    }

    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }
}

