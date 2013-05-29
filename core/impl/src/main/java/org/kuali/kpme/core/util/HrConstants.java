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
package org.kuali.kpme.core.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.core.leaveplan.LeavePlan;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.document.DocumentStatus;

public class HrConstants {
	
	/**
	 * The following were moved up from TkConstants
	 * @author dgodfrey
	 *
	 */
	
    public static final int DEFAULT_CACHE_TIME = 900;
    public static final String TK_TARGET_USER_PERSON = "tkTargetPerson";

	public static final class DOCUMENT_ACTIONS {
		public static final String ROUTE = "R";
		public static final String APPROVE = "A";
		public static final String DISAPPROVE = "D";
	}

	public static final class BATCH_JOB_ACTIONS {
		public static final String BATCH_JOB_INITIATE = "BI";
		public static final String BATCH_JOB_ROUTE = "BR";
		public static final String BATCH_JOB_APPROVE = "BA";
	}

	public static final class ROUTE_STATUS {
		public static final String INITIATED = DocumentStatus.INITIATED.getCode();
		public static final String ENROUTE = DocumentStatus.ENROUTE.getCode();
		public static final String FINAL = DocumentStatus.FINAL.getCode();
		public static final String CANCEL = DocumentStatus.CANCELED.getCode();
		public static final String SAVED = DocumentStatus.SAVED.getCode();
		public static final String DISAPPROVED = DocumentStatus.DISAPPROVED.getCode();
		public static final String EXCEPTION = DocumentStatus.EXCEPTION.getCode();
	}

    // Pay Frequency Type
    public static final class PAY_FREQUENCY {
        public static final String PAY_FREQUENCY_WEEK = "W";
        public static final String PAY_FREQUENCY_BI_WEEK = "BW";
        public static final String PAY_FREQUENCY_SEMI_MONTH = "SM";
        public static final String PAY_FREQUENCY_MONTH = "M";
    }

	public static final Map<String, String> DOC_ROUTE_STATUS = new HashMap<String, String>(8);

	static {
		DOC_ROUTE_STATUS.put(KewApiConstants.ROUTE_HEADER_INITIATED_CD, KewApiConstants.ROUTE_HEADER_INITIATED_LABEL);
		DOC_ROUTE_STATUS.put(KewApiConstants.ROUTE_HEADER_CANCEL_CD, KewApiConstants.ROUTE_HEADER_CANCEL_LABEL);
		DOC_ROUTE_STATUS.put(KewApiConstants.ROUTE_HEADER_ENROUTE_CD, KewApiConstants.ROUTE_HEADER_ENROUTE_LABEL);
		DOC_ROUTE_STATUS.put(KewApiConstants.ROUTE_HEADER_FINAL_CD, KewApiConstants.ROUTE_HEADER_FINAL_LABEL);
		DOC_ROUTE_STATUS.put(KewApiConstants.ROUTE_HEADER_DISAPPROVED_CD, KewApiConstants.ROUTE_HEADER_DISAPPROVED_LABEL);
		DOC_ROUTE_STATUS.put(KewApiConstants.ROUTE_HEADER_EXCEPTION_CD, KewApiConstants.ROUTE_HEADER_EXCEPTION_LABEL);
		DOC_ROUTE_STATUS.put(KewApiConstants.ROUTE_HEADER_SAVED_CD, KewApiConstants.ROUTE_HEADER_SAVED_LABEL);
	}

	public static final class BATCH_JOB_NAMES {
		public static final String INITIATE = "Initiate";
		public static final String END_PAY_PERIOD = "End Pay Period";
		public static final String END_REPORTING_PERIOD = "End Reporting Period";
		public static final String EMPLOYEE_APPROVAL = "Employee Approval";
		public static final String MISSED_PUNCH_APPROVAL = "Missed Punch Approval";
		public static final String SUPERVISOR_APPROVAL = "Supervisor Approval";
	}

	public static final Map<String, String> DOCUMENT_STATUS = new HashMap<String, String>();

	static {
		DOCUMENT_STATUS.put("I", "Initiated");
		DOCUMENT_STATUS.put("S", "Saved");
		DOCUMENT_STATUS.put("R", "Enroute");
		DOCUMENT_STATUS.put("F", "Final");
	}

	public static final List<String> TIME_ZONES = new ArrayList<String>();

	static {
		TIME_ZONES.add("America/Chicago");
		TIME_ZONES.add("America/Denver");
		TIME_ZONES.add("America/Detroit");
		TIME_ZONES.add("America/Indiana/Indianapolis");
		TIME_ZONES.add("America/Phoenix");
	}

	/**
	 * 
	 * Refactoring starts here from TkConstants...
	 * 
	 */
	public static final Map<String, String> SERVICE_UNIT_OF_TIME = new LinkedHashMap<String, String>(3);

	static {
		SERVICE_UNIT_OF_TIME.put("Y", "Years");
		SERVICE_UNIT_OF_TIME.put("M", "Months");
		//SERVICE_UNIT_OF_TIME.put("H", "Hours");
	}

	public static final Map<String, String> UNIT_OF_TIME = new LinkedHashMap<String, String>(2);

	static {
		UNIT_OF_TIME.put("D", "Days");
		UNIT_OF_TIME.put("H", "Hours");
	}

	public static final Map<String, String> MAX_BAL_FLAG = new LinkedHashMap<String, String>(2);

	static {
		MAX_BAL_FLAG.put("Y", "Yes");
		MAX_BAL_FLAG.put("N", "No");
	}

	public static final Map<String, String> MAX_BALANCE_ACTION_FREQUENCY = new LinkedHashMap<String, String>(3);

	static {
		MAX_BALANCE_ACTION_FREQUENCY.put("LA", "Leave Approve");
		MAX_BALANCE_ACTION_FREQUENCY.put("YE", "Year End");
		MAX_BALANCE_ACTION_FREQUENCY.put("OD", "On Demand");
		//MAX_BALANCE_ACTION_FREQUENCY.put("NA", "Not Applicable");
	}

	public static final String ASSIGNMENT_KEY_DELIMITER = "_";
	public static final String HOLIDAY_EARN_CODE = "HOL";
	// Special System earn code to represent lunch deductions.
	public static final String LUNCH_EARN_CODE = "LUN";

	public static final String TK_TARGET_USER_RETURN = "tkTargetReturn";

	public static final String WILDCARD_CHARACTER = "%";
	public static final Long WILDCARD_LONG = -1L;

	public static final DateTimeFormatter DT_BASIC_DATE_FORMAT = DateTimeFormat.forPattern("MM/dd/yyyy");

	public static final String FLSA_STATUS_NON_EXEMPT ="NE";
    public static final String FLSA_STATUS_EXEMPT ="E";

	// Timesheet document ID request parameter name
	public static final String TIMESHEET_DOCUMENT_ID_REQUEST_NAME = "tdocid";

	// earn code type
	public static final String EARN_CODE_HOUR = "H";
	public static final String EARN_CODE_TIME = "T";
	public static final String EARN_CODE_AMOUNT = "A";
	public static final String EARN_CODE_OVT = "OVT";

	public static final String EARN_CODE_DAY = "D";

	public static final Map<String, Set<String>> CLASS_INQUIRY_KEY_MAP = new HashMap<String, Set<String>>(4);

	static {
		Set<String> keys = new HashSet<String>();
		keys.add("leavePlan");
		keys.add("effectiveDate");
		CLASS_INQUIRY_KEY_MAP.put(LeavePlan.class.getName(), keys);

		keys = new HashSet<String>();
		keys.add("accrualCategory");
		keys.add("effectiveDate");
		CLASS_INQUIRY_KEY_MAP.put(AccrualCategory.class.getName(), keys);

		keys = new HashSet<String>();
		keys.add("earnCode");
		keys.add("effectiveDate");
		CLASS_INQUIRY_KEY_MAP.put(EarnCode.class.getName(), keys);
	}


	/**
	 * 
	 * The following were moved up from LMConstants
	 * 
	 * @author dgodfrey
	 *
	 */

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
	public static final class ACTION {
		public static final String DELETE = "D";
		public static final String ADD = "A";
		public static final String MODIFIED = "M";
	}

	public static final class ACTION_AT_MAX_BALANCE {
		public static final String LOSE = "L";
		public static final String TRANSFER = "T";
		public static final String PAYOUT = "P";
	}

	public static final Map<String, String> ACTION_AT_MAX_BALANCE_MAP = new LinkedHashMap<String, String>(3);

	static {
		ACTION_AT_MAX_BALANCE_MAP.put(ACTION_AT_MAX_BALANCE.TRANSFER, "Transfer");
		ACTION_AT_MAX_BALANCE_MAP.put(ACTION_AT_MAX_BALANCE.PAYOUT, "Payout");
		ACTION_AT_MAX_BALANCE_MAP.put(ACTION_AT_MAX_BALANCE.LOSE, "Lose");
		//ACTION_AT_MAX_BALANCE.put("NA", "Not Applicable");
	}

	public static final class MAX_BAL_ACTION_FREQ {
		public static final String LEAVE_APPROVE = "LA";
		public static final String YEAR_END = "YE";
		public static final String ON_DEMAND = "OD";
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

	public static final Map<String, String> ROUND_OPTION_MAP = new HashMap<String, String>(2);
	static {
		ROUND_OPTION_MAP.put("T", "Traditional");
		ROUND_OPTION_MAP.put("R", "Truncate");
	}


	public static final Map<String, String> REQUEST_STATUS_STRINGS = new HashMap<String, String>(6);

	static {
		REQUEST_STATUS_STRINGS.put(HrConstants.REQUEST_STATUS.PLANNED, "Planned");
		REQUEST_STATUS_STRINGS.put(HrConstants.REQUEST_STATUS.REQUESTED, "Requested");
		REQUEST_STATUS_STRINGS.put(HrConstants.REQUEST_STATUS.APPROVED, "Approved"); 
		REQUEST_STATUS_STRINGS.put(HrConstants.REQUEST_STATUS.DISAPPROVED, "Disapproved");
		REQUEST_STATUS_STRINGS.put(HrConstants.REQUEST_STATUS.DEFERRED, "Deferred");
		REQUEST_STATUS_STRINGS.put(HrConstants.REQUEST_STATUS.USAGE, "Usage");
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
		ACCRUAL_BALANCE_ACTION_MAP.put(ACCRUAL_BALANCE_ACTION.NONE, "None");
		ACCRUAL_BALANCE_ACTION_MAP.put(ACCRUAL_BALANCE_ACTION.USAGE, "Usage");
		ACCRUAL_BALANCE_ACTION_MAP.put(ACCRUAL_BALANCE_ACTION.ADJUSTMENT, "Adjustment");
	}

	//No occurrences found in workspace
	public static final class ACCRUAL_BALANCE_ACTION {
		public static final String NONE = "N";
		public static final String USAGE = "U";
		public static final String ADJUSTMENT = "A";
	}

	public static final Map<String, String> EARN_CODE_USAGE_LIMIT_MAP = new LinkedHashMap<String, String>(2);
	static {
		EARN_CODE_USAGE_LIMIT_MAP.put("I", "Include");
		EARN_CODE_USAGE_LIMIT_MAP.put("E", "Exclude");
	}

	public static final String LEAVE_CALENDAR_TYPE = "leaveCalendar";
	public static final String PAY_CALENDAR_TYPE = "payCalendar";
	public static final Integer PAGE_SIZE = 20;
	public static final String APPROVAL_TABLE_ID = "row";
	public static final String TASK_DEFAULT_DESP = "Default";
	public static final int BIG_DECIMAL_SCALE = 2;
	public static final RoundingMode BIG_DECIMAL_SCALE_ROUNDING = RoundingMode.HALF_EVEN;
	public static final BigDecimal BIG_DECIMAL_SCALED_ZERO = BigDecimal.ZERO.setScale(HrConstants.BIG_DECIMAL_SCALE, HrConstants.BIG_DECIMAL_SCALE_ROUNDING);
    public static final MathContext MATH_CONTEXT = new MathContext(5, BIG_DECIMAL_SCALE_ROUNDING);

    public static final BigDecimal BIG_DECIMAL_NEGATIVE_ONE = new BigDecimal("-1");
    public static final BigDecimal BIG_DECIMAL_1000 = BigDecimal.TEN.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
    public static final BigDecimal BIG_DECIMAL_60 = BigDecimal.TEN.multiply(new BigDecimal("6"));
    public static final BigDecimal BIG_DECIMAL_MS_IN_H = BIG_DECIMAL_1000.multiply(BIG_DECIMAL_60).multiply(BIG_DECIMAL_60);
    public static final BigDecimal BIG_DECIMAL_MS_IN_M = BIG_DECIMAL_1000.multiply(BIG_DECIMAL_60);
    public static final BigDecimal BIG_DECIMAL_HRS_IN_DAY = new BigDecimal(24);

    public static final class CacheNamespace {
        public static final String MODULE_NAME = "core";
        public static final String NAMESPACE_PREFIX = KPMEConstants.CacheNamespace.ROOT_NAMESPACE_PREFIX + "/"
                + MODULE_NAME + "/";
        public static final String KPME_GLOBAL_CACHE_NAME = NAMESPACE_PREFIX + "Global";
    }
}
