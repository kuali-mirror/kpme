package org.kuali.hr.pm;

import java.util.LinkedHashMap;
import java.util.Map;

public class PMConstants {
	
	 public static final String WILDCARD_CHARACTER = "*";
	
	 public static final class PSTN_QLFR_TYPE_VALUE {
	        public static final String TEXT = "T";
	        public static final String NUMBER = "n";
	        public static final String SELECT = "S";
	 }
	
	 public static final Map<String, String> PSTN_QLFR_TYPE_VALUE_MAP = new LinkedHashMap<String, String>(2);
	 static {
		 PSTN_QLFR_TYPE_VALUE_MAP.put(PSTN_QLFR_TYPE_VALUE.TEXT, "Text");
		 PSTN_QLFR_TYPE_VALUE_MAP.put(PSTN_QLFR_TYPE_VALUE.NUMBER, "Number");
		 PSTN_QLFR_TYPE_VALUE_MAP.put(PSTN_QLFR_TYPE_VALUE.SELECT, "Select");
	 }
}
