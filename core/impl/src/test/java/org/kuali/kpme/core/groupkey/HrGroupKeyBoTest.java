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
