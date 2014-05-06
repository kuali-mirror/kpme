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
package org.kuali.kpme.core.groupkey;

import java.util.HashMap;
import java.util.Map;

import org.kuali.kpme.core.api.groupkey.HrGroupKey;


public class HrGroupKeyBoTest {

	 private static Map<String, HrGroupKey> testGroupKeys;
	 static {
		 testGroupKeys = new HashMap<String, HrGroupKey>();
		 HrGroupKey.Builder grpKey = HrGroupKey.Builder.create();
		 grpKey.setGroupKeyCode("ISU-IA");
		 grpKey.setActive(true);
		 
		 grpKey.setId(grpKey.getGroupKeyCode());
		 grpKey.setVersionNumber(1L);
         grpKey.setObjectId("d9e94e3a-cbb7-11e3-9cd3-51a754ad6a0a");
         testGroupKeys.put(grpKey.getGroupKeyCode(), grpKey.build());
	 }
	 
	 public static HrGroupKey getTestHrGroupKey(String hrGroupKey) {
	        return testGroupKeys.get(hrGroupKey);
	 }
}
