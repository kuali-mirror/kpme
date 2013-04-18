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
package org.kuali.hr.time.clock.location.validation;

import org.junit.Assert;
import org.junit.Test;

public class ClockLocationRuleRuleTest extends Assert {

    static String[] validIpAddresses;
    static String[] invalidIpAddresses;
    
    static {
	validIpAddresses = new String[] {
		"1.1.1.1",
		"%.2.3.4",
		"1.%.3.4",
		"1.2.%.4",
		"1.2.3.%",
		"%.%.3.4",
		"1.%.%.4",
		"1.2.%.%",
		"%.%.%.4",
		"1.%.%.%",
		"255.255.255.255",
		"%.%.%.%",
		"1.2.%",
		"%",
		"127.%"
	};
	
	invalidIpAddresses = new String[] {
		"-1.0.0.0",
		"1.2.3.256",
		"256.2.3.1",
		"1.2.3",
		"random text",
		" 1.2.3.4 ",
		"1.2.3.4.5",
		"%1.2.3.4%",      // TODO: We may want this to be valid.
		"999.999.999.999",
		"",
		".123.2.4"
	};
    }
    
    @Test
    public void testValidateIpAddress() throws Exception {
	ClockLocationRuleRule clrr = new ClockLocationRuleRule();
	
	for (String ip : validIpAddresses) {
	    assertTrue("IP address " + ip + " should be valid.", clrr.validateIpAddress(ip));
	}
	
	for (String ip : invalidIpAddresses) {
	    assertFalse("IP address " + ip + " should be invalid.", clrr.validateIpAddress(ip));
	}
    }
}
