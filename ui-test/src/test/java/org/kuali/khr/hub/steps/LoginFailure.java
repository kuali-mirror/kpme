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
package org.kuali.khr.hub.steps;

//import static org.junit.Assert.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginFailure {

	@Given("^that a user is drunk$")
	public void that_a_user_is_drunk() throws Throwable {
//	    throw new PendingException();
	}

	@When("^he tries to login$")
	public void he_tries_to_login() throws Throwable {
	}

	@Then("^the computer squirts water in his eye$")
	public void the_computer_squirts_water_in_his_eye() throws Throwable {
//		fail("Hey, stop squirting water in my eye!!");
	}
}
