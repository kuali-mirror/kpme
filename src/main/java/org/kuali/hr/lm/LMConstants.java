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
package org.kuali.hr.lm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class LMConstants {
	public static final String SERVICE_TIME_YEAR = "year";
	public static final String SERVICE_TIME_MONTHS = "month";

    public static final class ACCRUAL_EARN_INTERVAL_CODE{
        public static final String DAILY = "D";
        public static final String WEEKLY = "W";
        public static final String SEMI_MONTHLY = "S";
        public static final String MONTHLY = "M";
        public static final String YEARLY = "Y";
        public static final String NO_ACCRUAL = "N";
        public static final String PAY_CAL = "P";
    }

	public static final class ACCRUAL_EARN_INTERVAL{
		public static final String DAILY = "daily";
		public static final String WEEKLY = "weekly";
		public static final String SEMI_MONTHLY = "semimonthly";
		public static final String MONTHLY = "monthly";
		public static final String YEARLY = "yearly";
		public static final String NO_ACCRUAL = "noaccrual";
		public static final String PAY_CAL = "paycalendar";
	}
	
	// KPME-1347 Kagata
	public static final Map<String, String> ACCRUAL_EARN_INTERVAL_MAP = new HashMap<String, String>(7);
    static {
    	ACCRUAL_EARN_INTERVAL_MAP.put(ACCRUAL_EARN_INTERVAL_CODE.DAILY, "Daily");
    	ACCRUAL_EARN_INTERVAL_MAP.put(ACCRUAL_EARN_INTERVAL_CODE.WEEKLY, "Weekly");
    	ACCRUAL_EARN_INTERVAL_MAP.put(ACCRUAL_EARN_INTERVAL_CODE.SEMI_MONTHLY, "Semi-Monthly");
    	ACCRUAL_EARN_INTERVAL_MAP.put(ACCRUAL_EARN_INTERVAL_CODE.MONTHLY, "Monthly");
    	ACCRUAL_EARN_INTERVAL_MAP.put(ACCRUAL_EARN_INTERVAL_CODE.YEARLY, "Yearly");
    	ACCRUAL_EARN_INTERVAL_MAP.put(ACCRUAL_EARN_INTERVAL_CODE.NO_ACCRUAL, "No Accrual");
    	ACCRUAL_EARN_INTERVAL_MAP.put(ACCRUAL_EARN_INTERVAL_CODE.PAY_CAL, "Pay Calendar");
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
		public static final String DISAPPROVED = "D";
		public static final String DEFERRED="F";
		public static final String USAGE = "U";
	}
	
	 public static final Map<String, String> REQUEST_STATUS_STRINGS = new HashMap<String, String>(6);

	    static {
	    	REQUEST_STATUS_STRINGS.put(REQUEST_STATUS.PLANNED, "Planned");
	    	REQUEST_STATUS_STRINGS.put(REQUEST_STATUS.REQUESTED, "Requested");
	    	REQUEST_STATUS_STRINGS.put(REQUEST_STATUS.APPROVED, "Approved"); 
	    	REQUEST_STATUS_STRINGS.put(REQUEST_STATUS.DISAPPROVED, "Disapproved");
	    	REQUEST_STATUS_STRINGS.put(REQUEST_STATUS.DEFERRED, "Deferred");
	    	REQUEST_STATUS_STRINGS.put(REQUEST_STATUS.USAGE, "Usage");
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
	 
	 
	 public static final String LEAVE_CALENDAR_TYPE = "leaveCalendar";
	 
	 public static final class LEAVE_BLOCK_TYPE {
		 public static final String LEAVE_CALENDAR = "LC";
		 public static final String TIME_CALENDAR = "TC";
		 public static final String ACCRUAL_SERVICE = "AS";
		 public static final String BALANCE_TRANSFER = "BT";
         public static final String LEAVE_PAYOUT = "LP";
		 public static final String DONATION_MAINT = "DM";
		 public static final String LEAVE_ADJUSTMENT_MAINT = "LAM";
	 }

	 public static final Map<String, String> LEAVE_BLOCK_TYPE_MAP = new HashMap<String, String>(4);
	 static {
	    	LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE, "Accrual Service");
	    	LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.BALANCE_TRANSFER, "Balance Transfer");
	    	LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.LEAVE_PAYOUT, "Leave Payout");
	    	LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.DONATION_MAINT, "Donation");
	    	LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.LEAVE_ADJUSTMENT_MAINT, "Leave Adjustment");
	 }
	    
    public static final class ACTION_AT_MAX_BAL {
        public static final String LOSE = "L";
        public static final String TRANSFER = "T";
        public static final String PAYOUT = "P";
    }

    public static final class MAX_BAL_ACTION_FREQ {
    	//As of revision 3882, constant LEAVE_APPROVE given value LA to remove discrepancy for value
    	//being set in LM_ACCRUAL_CATEGORY_RULES_T.MAX_BAL_ACTION_FREQ. Constant was defined as "L"
    	//table value being set is "LA"
        public static final String LEAVE_APPROVE = "LA";
        public static final String YEAR_END = "YE";
		public static final String ON_DEMAND = "OD";
    }
	 
	 public static final String STATUS_CHANGE_EARN_CODE = "Accrual Note";
	 
	 public static final String RUN_ACCRUAL_FROM_CALENDAR = "run.accrual.from.calendar";   // controls if accrual should be ran from Leave calendar
	 
	 public static final String INITIATE_LEAVE_REQUEST_ACTION = "kpme.lm.leaveCalendar.initiate.leaveRequest.action";
	 public static final class INITIATE_LEAVE_REQUEST_ACTION_OPTIONS {
	        public static final String DELETE = "DELETE";
	        public static final String APPROVE = "APPROVE";
	 }
	 
	 public static final class TIME_APPROVAL_TYPE {
		 public static final String TIME = "time";
		 public static final String LEAVE = "leave";
		 public static final String ALL = "all";
	 }
	 
	 public static int DELINQUENT_LEAVE_CALENDARS_LIMIT = 2;
	 
	 public static final Map<String, String> EARN_CODE_USAGE_LIMIT_MAP = new LinkedHashMap<String, String>(2);
	 static {
		 EARN_CODE_USAGE_LIMIT_MAP.put("I", "Include");
		 EARN_CODE_USAGE_LIMIT_MAP.put("E", "Exclude");
	 }
	 
	 public static final class BALANCE_TRANSFER_TYPE {
		 public static final String LEAVE_APPROVE = "LA";
		 public static final String YEAR_END = "Y";
		 public static final String ON_DEMAND = "O";
		 public static final String MAINTENANCE = "M";
		 public static final String CARRY_OVER = "C";
	 }
}
