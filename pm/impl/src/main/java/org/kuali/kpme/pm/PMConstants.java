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
package org.kuali.kpme.pm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.KPMEConstants;

public class PMConstants {
	
	public static final String WILDCARD_CHARACTER = "*";

    public static final String PSTN_QLFR_TEXT = "Text";
    public static final String PSTN_QLFR_NUMBER = "Number";
    public static final String PSTN_QLFR_SELECT = "Select";
	
	 
	 public static final List<String> PSTN_QLFR_TYPE_VALUE_LIST = new ArrayList<String>();

	 static {
		 PSTN_QLFR_TYPE_VALUE_LIST.add(PSTN_QLFR_TEXT);
		 PSTN_QLFR_TYPE_VALUE_LIST.add(PSTN_QLFR_NUMBER);
		 PSTN_QLFR_TYPE_VALUE_LIST.add(PSTN_QLFR_SELECT);
	 }

	 
	 public static final class PSTN_CLSS_QLFR_VALUE {
	        public static final String EQUAL = "=";
	        public static final String GREATER_THAN = ">";
	        public static final String GREATER_EQUAL = ">=";
	        public static final String LESS_THAN = "<";
	        public static final String LESS_EQUAL = "<=";
	 }
	 
	 public static final class PSTN_CLSS_QLFR_STRING_VALUE {
	        public static final String EQUAL = "Equal to";
	        public static final String GREATER_THAN = "Greater than";
	        public static final String GREATER_EQUAL = "Greater than or equal to";
	        public static final String LESS_THAN = "Less than";
	        public static final String LESS_EQUAL = "Less than or equal to";
	 }
	
	 public static final Map<String, String> PSTN_CLSS_QLFR_VALUE_MAP = new LinkedHashMap<String, String>(2);
	 static {
		 PSTN_CLSS_QLFR_VALUE_MAP.put(PSTN_CLSS_QLFR_VALUE.EQUAL, PSTN_CLSS_QLFR_VALUE.EQUAL);
		 PSTN_CLSS_QLFR_VALUE_MAP.put(PSTN_CLSS_QLFR_VALUE.GREATER_THAN, PSTN_CLSS_QLFR_VALUE.GREATER_THAN);
		 PSTN_CLSS_QLFR_VALUE_MAP.put(PSTN_CLSS_QLFR_VALUE.GREATER_EQUAL, PSTN_CLSS_QLFR_VALUE.GREATER_EQUAL);
		 PSTN_CLSS_QLFR_VALUE_MAP.put(PSTN_CLSS_QLFR_VALUE.LESS_THAN, PSTN_CLSS_QLFR_VALUE.LESS_THAN);
		 PSTN_CLSS_QLFR_VALUE_MAP.put(PSTN_CLSS_QLFR_VALUE.LESS_EQUAL, PSTN_CLSS_QLFR_VALUE.LESS_EQUAL);
	 }

    public static final class CacheNamespace {
        public static final String MODULE_NAME = "pm";
        public static final String NAMESPACE_PREFIX = KPMEConstants.CacheNamespace.ROOT_NAMESPACE_PREFIX + "/"
                + MODULE_NAME + "/";
    }
    
    
    // Position Process Category
    public static final String PSTN_PROCESS_NEW = "New";
    public static final String PSTN_PROCESS_REORG = "Reorganization";
    public static final String PSTN_PROCESS_RECLASS = "Reclassification";
    public static final String PSTN_PROCESS_UPDATE = "Data Update";
    public static final String PSTN_PROCESS_STATUS = "Change Status";
    
    public static final List<String> PSTN_PROCESS_LIST = new ArrayList<String>();
	static {
		 PSTN_PROCESS_LIST.add(PSTN_PROCESS_NEW);
		 PSTN_PROCESS_LIST.add(PSTN_PROCESS_REORG);
		 PSTN_PROCESS_LIST.add(PSTN_PROCESS_RECLASS);
		 PSTN_PROCESS_LIST.add(PSTN_PROCESS_UPDATE);
		 PSTN_PROCESS_LIST.add(PSTN_PROCESS_STATUS);
	}
	
	// Position Status
	public static final class PSTN_STATUS {
		public static final String NEW = "New";
	    public static final String FILLED = "Filled";
	    public static final String VACANT = "Vacant";
	    public static final String INACTIVE = "Inactive";
	}
	
    public static final Map<String, String> PSTN_STATUS_MAP = new LinkedHashMap<String, String>(2);
	static {
		PSTN_STATUS_MAP.put(PSTN_STATUS.NEW, PSTN_STATUS.NEW);
		PSTN_STATUS_MAP.put(PSTN_STATUS.FILLED, PSTN_STATUS.FILLED);
		PSTN_STATUS_MAP.put(PSTN_STATUS.VACANT, PSTN_STATUS.VACANT);
		PSTN_STATUS_MAP.put(PSTN_STATUS.INACTIVE, PSTN_STATUS.INACTIVE);
	}
}
