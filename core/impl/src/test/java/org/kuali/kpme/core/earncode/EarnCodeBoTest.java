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
package org.kuali.kpme.core.earncode;

import java.util.HashMap;
import java.util.Map;

import org.kuali.kpme.core.api.earncode.EarnCode;

public class EarnCodeBoTest {

	 private static Map<String, EarnCode> testEarnCodes;
	 static {
		 testEarnCodes = new HashMap<String, EarnCode>();
		 EarnCode.Builder earncode = EarnCode.Builder.create("TST-EarnCode");
		 earncode.setHrEarnCodeId("KPME-TEST-0001");
		 earncode.setEarnCode("TST-EarnCode");
		 earncode.setDescription("This is the description for earncode");
		 earncode.setActive(true);
		 earncode.setId(earncode.getHrEarnCodeId());
		 earncode.setVersionNumber(1L);
		 earncode.setUserPrincipalId("admin");
         earncode.setObjectId("d9e94e3a-cbb7-11e3-9cd3-51a754ad6a0a");
         testEarnCodes.put(earncode.getEarnCode(), earncode.build());
	 }
	 
	 public static EarnCode getTestEarnCode(String EarnCode) {
	        return testEarnCodes.get(EarnCode);
	 }
}
