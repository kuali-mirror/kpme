/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.khr.hub.tests.cucumber;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
//@CucumberOptions(format = {"pretty", "html:target/cucumber-htmlreport","json-pretty:target/cucumber-report.json"})
@CucumberOptions(
		format = {"pretty", "html:target/cucumber-htmlreport"}, 
		glue = {"org.kuali.khr.hub.steps", 
				"org.kuali.khr.tk.steps",
				"org.kuali.khr.lm.steps",
				"org.kuali.khr.pm.steps"},
		features = "src/test/resources/features"
				
		)

public class RunCukesUIT {
}