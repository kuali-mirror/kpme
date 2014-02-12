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
package org.kuali.kpme.tklm.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.util.HrConstants;


public class LMConstants {
		 
	 public static final class LEAVE_BLOCK_TYPE {
		 public static final String LEAVE_CALENDAR = "LC";
		 public static final String TIME_CALENDAR = "TC";
		 public static final String ACCRUAL_SERVICE = "AS";
		 public static final String BALANCE_TRANSFER = "BT";
         public static final String LEAVE_PAYOUT = "LP";
		 public static final String DONATION_MAINT = "DM";
		 public static final String LEAVE_ADJUSTMENT_MAINT = "LAM";
		 public static final String CARRY_OVER = "CO";
		 public static final String CARRY_OVER_ADJUSTMENT = "COA";
	 }

	 public static final Map<String, String> LEAVE_BLOCK_TYPE_MAP = new LinkedHashMap<String, String>(8);
	 static {
		 	LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.LEAVE_CALENDAR, "Leave Calendar");
		 	LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.TIME_CALENDAR, "Time Calendar");
	    	LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE, "Accrual Service");
	    	LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.BALANCE_TRANSFER, "Balance Transfer");
            LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.CARRY_OVER, "Carry Over");
            LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.CARRY_OVER_ADJUSTMENT, "Carry Over Adjustment");
	    	LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.DONATION_MAINT, "Donation");
            LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.LEAVE_ADJUSTMENT_MAINT, "Leave Adjustment");
            LEAVE_BLOCK_TYPE_MAP.put(LEAVE_BLOCK_TYPE.LEAVE_PAYOUT, "Leave Payout");


	 }
	    
	 public static final String STATUS_CHANGE_EARN_CODE = "Accrual Note";
	 
	 //leave block identification depends on this string.
	 public static final String PAYOUT_FORFEIT_LB_DESCRIPTION = "Forfeited payout amount";
	 public static final String TRANSFER_FORFEIT_LB_DESCRIPTION = "Forfeited balance transfer amount";
	 
	 public static final String RUN_ACCRUAL_FROM_CALENDAR = "run.accrual.from.calendar";   // controls if accrual should be ran from Leave calendar
	 public static final String ALLOW_CLOCKINGEMPLOYYE_FROM_INVALIDLOCATION = "kpme.allow.clockingEmployee.from.invalidLocation";
	 
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
	 
	 public static final int DELINQUENT_LEAVE_CALENDARS_LIMIT = 2;
	 
	 public static final class BALANCE_TRANSFER_TYPE {
		 public static final String ACCRUAL_TRIGGERED = "AT";
		 public static final String SSTO = "TO";
		 public static final String MAINTENANCE = "MT";
		 public static final String CARRY_OVER = "CO";
	 }
	 
	public static final class UNUSED_TIME{
		public static final String NO_UNUSED = "NUTA";
        public static final String TRANSFER = "T";
        public static final String BANK = "B";
	}
		
	public static final Map<String, String> UNUSED_TIME_MAP = new HashMap<String, String>(7);
    static {
    	UNUSED_TIME_MAP.put(UNUSED_TIME.NO_UNUSED, "No Unused Time Allowed");
    	UNUSED_TIME_MAP.put(UNUSED_TIME.TRANSFER, "Transfer");
    	UNUSED_TIME_MAP.put(UNUSED_TIME.BANK, "Bank");
    }
    
    public static final String MAX_CARRY_OVER_ADJUSTMENT = "Max carry over adjustment";
    
    public static final List<String> YTD_EARNED_LEAVE_BLOCK_TYPES = new ArrayList<String>();
    static {
    	YTD_EARNED_LEAVE_BLOCK_TYPES.add(LMConstants.LEAVE_BLOCK_TYPE.ACCRUAL_SERVICE);
    	YTD_EARNED_LEAVE_BLOCK_TYPES.add(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
    	YTD_EARNED_LEAVE_BLOCK_TYPES.add(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_PAYOUT);
    	YTD_EARNED_LEAVE_BLOCK_TYPES.add(LMConstants.LEAVE_BLOCK_TYPE.DONATION_MAINT);
    	YTD_EARNED_LEAVE_BLOCK_TYPES.add(LMConstants.LEAVE_BLOCK_TYPE.CARRY_OVER_ADJUSTMENT);
    }
    
    public static final List<String> ADJUSTMENT_YTD_EARNED_LEAVE_BLOCK_TYPES = new ArrayList<String>();
    static {
    	ADJUSTMENT_YTD_EARNED_LEAVE_BLOCK_TYPES.add(LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR);
    	ADJUSTMENT_YTD_EARNED_LEAVE_BLOCK_TYPES.add(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR);
    	ADJUSTMENT_YTD_EARNED_LEAVE_BLOCK_TYPES.add(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_ADJUSTMENT_MAINT);
    }
    
    
    public static final List<String> USAGE_LEAVE_BLOCK_TYPES = new ArrayList<String>();
    static {
    	USAGE_LEAVE_BLOCK_TYPES.add(LMConstants.LEAVE_BLOCK_TYPE.TIME_CALENDAR);
    	USAGE_LEAVE_BLOCK_TYPES.add(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR);
    	USAGE_LEAVE_BLOCK_TYPES.add(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_ADJUSTMENT_MAINT);
    }
    
    public static final List<String> PENDING_LEAVE_BLOCK_STATUS = new ArrayList<String>();
    static {
    	PENDING_LEAVE_BLOCK_STATUS.add(HrConstants.REQUEST_STATUS.PLANNED);
    	PENDING_LEAVE_BLOCK_STATUS.add(HrConstants.REQUEST_STATUS.REQUESTED);
    	PENDING_LEAVE_BLOCK_STATUS.add(HrConstants.REQUEST_STATUS.APPROVED);
    }
}
