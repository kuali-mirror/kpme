package org.kuali.khr.hub.tests.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//@CucumberOptions(format = {"pretty", "html:target/cucumber-htmlreport","json-pretty:target/cucumber-report.json"})
@CucumberOptions(
		format = {"pretty", "html:target/cucumber-htmlreport"}, 
		glue = {"org.kuali.khr.hub.steps", 
				"org.kuali.khr.tk.clock.steps", 
				"org.kuali.khr.tk.time_detail.steps"},
		features = "src/test/resources/features"
				
		)

public class RunCukesUIT {
}