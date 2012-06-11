package org.kuali.hr.lm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class LMConstants {
	public static final String SERVICE_TIME_YEAR = "year";
	public static final String SERVICE_TIME_MONTHS = "month";
	
	public static final class ACCRUAL_EARN_INTERVAL{
		public static final String DAILY = "daily";
		public static final String WEEKLY = "weekly";
		public static final String BI_WEEKLY = "biweekly";
		public static final String SEMI_MONTHLY = "semimonthly";
		public static final String MONTHLY = "monthly";
		public static final String YEARLY = "yearly";
		public static final String NO_ACCRUAL = "noaccrual";
	}
	
	// KPME-1347 Kagata
	public static final Map<String, String> ACCRUAL_EARN_INTERVAL_MAP = new HashMap<String, String>(7);
    static {
    	ACCRUAL_EARN_INTERVAL_MAP.put("D", "Daily");
    	ACCRUAL_EARN_INTERVAL_MAP.put("W", "Weekly");
    	ACCRUAL_EARN_INTERVAL_MAP.put("B", "Bi-Weekly");
    	ACCRUAL_EARN_INTERVAL_MAP.put("S", "Semi-Monthly");
    	ACCRUAL_EARN_INTERVAL_MAP.put("M", "Monthly");
    	ACCRUAL_EARN_INTERVAL_MAP.put("Y", "Yearly");
    	ACCRUAL_EARN_INTERVAL_MAP.put("N", "No Accrual");
    }
    
	
	// Action history
	public static final class ACTION{
		public static final String DELETE = "D";
		public static final String ADD = "A";
		public static final String MODIFIED = "M";
	}
	
	// Request status
	public static final class REQUEST_STATUS{
		public static final String PLANNED="P";
		public static final String REQUESTED="R";
		public static final String APPROVED="A";
		public static final String RECORDED = "C";
		public static final String DISAPPROVED = "D";
		public static final String DEFERRED="F";
		public static final String ACCURAL = "Accural";
		public static final String USAGE = "USAGE";
	}
	
	 public static final Map<String, String> REQUEST_STATUS_STRINGS = new HashMap<String, String>(6);

	    static {
	    	REQUEST_STATUS_STRINGS.put(REQUEST_STATUS.PLANNED, "Planned");
	    	REQUEST_STATUS_STRINGS.put(REQUEST_STATUS.REQUESTED, "Requested");
	    	REQUEST_STATUS_STRINGS.put(REQUEST_STATUS.APPROVED, "Approved"); 
	    	REQUEST_STATUS_STRINGS.put(REQUEST_STATUS.RECORDED, "Recorded"); 
	    	REQUEST_STATUS_STRINGS.put(REQUEST_STATUS.DISAPPROVED, "Disapproved");
	    	REQUEST_STATUS_STRINGS.put(REQUEST_STATUS.DEFERRED, "Deferred"); 
	    }
	    
	 public static final Map<String, String> ROUND_OPTION_MAP = new HashMap<String, String>(2);
	 static {
		 ROUND_OPTION_MAP.put("T", "Traditional");
		 ROUND_OPTION_MAP.put("R", "Truncate");
	 }
	 
	 public static final class RECORD_METHOD {
		 public static final String TIME = "T";
		 public static final String HOUR = "H";
		 public static final String AMOUNT = "A";
		 public static final String DAY = "D";
	 }
	 
	 public static final Map<String, String> RECORD_METHOD_MAP = new HashMap<String, String>(2);
	 static {
		 RECORD_METHOD_MAP.put(RECORD_METHOD.TIME, "Time");
		 RECORD_METHOD_MAP.put(RECORD_METHOD.HOUR, "Hours");
		 RECORD_METHOD_MAP.put(RECORD_METHOD.AMOUNT, "Amount");
		 RECORD_METHOD_MAP.put(RECORD_METHOD.DAY, "Days");
	 }
	
	 public static final Map<String, String> ACCRUAL_BALANCE_ACTION_MAP = new LinkedHashMap<String, String>(2);
	 static {
		 ACCRUAL_BALANCE_ACTION_MAP.put("N", "None");
		 ACCRUAL_BALANCE_ACTION_MAP.put("U", "Usage");
		 ACCRUAL_BALANCE_ACTION_MAP.put("A", "Adjustment");
	 }
}
