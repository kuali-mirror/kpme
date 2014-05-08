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
