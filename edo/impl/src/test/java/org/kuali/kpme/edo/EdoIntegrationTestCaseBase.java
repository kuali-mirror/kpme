package org.kuali.kpme.edo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;


public class EdoIntegrationTestCaseBase  {
	
    private Selenium selenium;

    @Before
    public void setUp() throws Exception {
        selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080/edo-dev/");
        selenium.start();
    }
    
    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }
    
	public Selenium getSelenium() {
		return selenium;
	}

	public void setSelenium(Selenium selenium) {
		this.selenium = selenium;
	}
	
	


}
