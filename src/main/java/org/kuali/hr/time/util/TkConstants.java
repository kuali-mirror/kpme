package org.kuali.hr.time.util;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.rice.kew.util.KEWConstants;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

public class TkConstants {
    public static final int DEFAULT_CACHE_TIME = 900;
    public static final String TK_TARGET_USER_RETURN = "tkTargetReturn";
    public static final String TK_TARGET_USER_PERSON = "tkTargetPerson";

    public static final String WILDCARD_CHARACTER = "%";
    public static final Long WILDCARD_LONG = -1L;

    //in days
    public static final Integer LENGTH_OF_WORK_SCHEDULE = 10;

    public static final String CLOCK_IN = "CI";
    public static final String CLOCK_OUT = "CO";
    public static final String LUNCH_IN = "LI"; // Coming back from Lunch
    public static final String LUNCH_OUT = "LO"; // Leaving for Lunch

    public static final List<String> ON_THE_CLOCK_CODES = new ArrayList<String>();

    static {
        ON_THE_CLOCK_CODES.add(CLOCK_IN);
        ON_THE_CLOCK_CODES.add(LUNCH_IN);
    }

    // action history
    public static final String DELETE = "DELETE";
    public static final String ADD = "ADD";
    public static final String MODIFIED = "MODIFIED";

    // earn code type
    public static final String EARN_CODE_HOUR = "HOUR";
    public static final String EARN_CODE_TIME = "TIME";
    public static final String EARN_CODE_AMOUNT = "AMOUNT";
    public static final String EARN_CODE_OVT = "OVT";
    public static final String EARN_CODE_CPE = "CPE";

    public static final String GMT_TIME_ZONE_ID = "Etc/GMT";
    public static final TimeZone GMT_TIME_ZONE = TimeZone.getTimeZone(GMT_TIME_ZONE_ID);
    public static final String SYSTEM_TIME_ZONE = "America/Indianapolis";

    public static final DateTimeZone SYSTEM_DATE_TIME_ZONE = DateTimeZone.forID(TkConstants.SYSTEM_TIME_ZONE);
    public static DateTimeFormatter DT_BASIC_TIME_FORMAT = DateTimeFormat.forPattern("hh:mm aa");
    public static DateTimeFormatter DT_MILITARY_TIME_FORMAT = DateTimeFormat.forPattern("H:mm");
    public static DateTimeFormatter DT_BASIC_DATE_FORMAT = DateTimeFormat.forPattern("MM/dd/yyyy");
    public static DateTimeFormatter DT_ABBREV_DATE_FORMAT = DateTimeFormat.forPattern("MM/dd");
    public static DateTimeFormatter DT_FULL_DATE_TIME_FORMAT = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm aa");

    public static final int BIG_DECIMAL_SCALE = 2;
    public static final RoundingMode BIG_DECIMAL_SCALE_ROUNDING = RoundingMode.HALF_EVEN;
    public static final BigDecimal BIG_DECIMAL_SCALED_ZERO = BigDecimal.ZERO.setScale(TkConstants.BIG_DECIMAL_SCALE, TkConstants.BIG_DECIMAL_SCALE_ROUNDING);
    public static final BigDecimal BIG_DECIMAL_NEGATIVE_ONE = new BigDecimal("-1");

    public static final MathContext MATH_CONTEXT = new MathContext(5, BIG_DECIMAL_SCALE_ROUNDING);
    public static final BigDecimal BIG_DECIMAL_1000 = BigDecimal.TEN.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
    public static final BigDecimal BIG_DECIMAL_60 = BigDecimal.TEN.multiply(new BigDecimal("6"));
    public static final BigDecimal BIG_DECIMAL_MS_IN_H = BIG_DECIMAL_1000.multiply(BIG_DECIMAL_60).multiply(BIG_DECIMAL_60);
    public static final BigDecimal BIG_DECIMAL_HRS_IN_DAY = new BigDecimal(24);

    public static final String SUPER_USER = TkConstants.ROLE_TK_SYS_ADMIN;

    //Used to map job locations to specify the timezone for display purposes
    public static final Map<String, String> LOCATION_TO_TIME_ZONE_MAP = new HashMap<String, String>();

    static {
        LOCATION_TO_TIME_ZONE_MAP.put("NW", "America/Chicago");
    }

    /**
     * The following ROLE_* constants need to match what is in the workflow database.  They will be
     * used internally to obtain a reference to the underlying IDs in the workflow system.
     * <p/>
     * Used in *ValuesFinder classes for maintenance page dropdowns.
     */
    public static final String ROLE_NAMESAPCE = "KUALI";
    public static final String ROLE_TK_GLOBAL_VO = "TK_GLOBAL_VO";
    public static final String ROLE_TK_DEPT_VO = "TK_DEPT_VO";
    public static final String ROLE_LV_DEPT_VO = "LV_DEPT_VO"; // KPME-1411
    public static final String ROLE_TK_LOCATION_VO = "TK_LOCATION_VO";
    public static final String ROLE_TK_REVIEWER = "TK_REVIEWER";
    public static final String ROLE_TK_APPROVER = "TK_APPROVER";
    public static final String ROLE_TK_APPROVER_DELEGATE = "TK_APPROVER_DELEGATE";
    public static final String ROLE_TK_EMPLOYEE = "TK_EMPLOYEE";
    public static final String ROLE_TK_LOCATION_ADMIN = "TK_ORG_ADMIN";
    public static final String ROLE_TK_DEPT_ADMIN = "TK_DEPT_ADMIN";
    public static final String ROLE_LV_DEPT_ADMIN = "LV_DEPT_ADMIN"; // KPME-1411
    public static final String ROLE_TK_SYS_ADMIN = "TK_SYS_ADMIN";
    public static final String ROLE_WORK_AREA_QUALIFIER_ID = "workArea";
    public static final List<String> ROLE_ASSIGNMENT_FOR_WORK_AREA = new ArrayList<String>(2);
    public static final List<String> ROLE_ASSIGNMENT_FOR_USER_ROLES = new ArrayList<String>(6);
    public static final Map<String, String> ALL_ROLES_MAP = new HashMap<String, String>();
    public static final List<String> ROLE_ASSIGNMENT_FOR_WORK_AREA_OT_EDIT = new ArrayList<String>(3);

    static {
        ROLE_ASSIGNMENT_FOR_WORK_AREA.add(TkConstants.ROLE_TK_APPROVER);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_GLOBAL_VO);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_DEPT_VO);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_DEPT_ADMIN);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_REVIEWER);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_APPROVER);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_APPROVER_DELEGATE);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_LOCATION_ADMIN);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_LOCATION_VO);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_SYS_ADMIN);

        ROLE_ASSIGNMENT_FOR_WORK_AREA_OT_EDIT.add(TkConstants.ROLE_TK_EMPLOYEE);
        ROLE_ASSIGNMENT_FOR_WORK_AREA_OT_EDIT.add(TkConstants.ROLE_TK_APPROVER);
        ROLE_ASSIGNMENT_FOR_WORK_AREA_OT_EDIT.add(TkConstants.ROLE_TK_APPROVER_DELEGATE);
        ROLE_ASSIGNMENT_FOR_WORK_AREA_OT_EDIT.add(TkConstants.ROLE_TK_DEPT_ADMIN);

        ALL_ROLES_MAP.put(TkConstants.ROLE_TK_REVIEWER, "Reviewer"); // attach at 'work area' level, like approvers without departmental rules
        ALL_ROLES_MAP.put(TkConstants.ROLE_TK_GLOBAL_VO, "Global View Only"); // can see everything in the system, but not modify
        ALL_ROLES_MAP.put(TkConstants.ROLE_TK_DEPT_VO, "Time Department View Only"); // can only see objects belonging to a department
        ALL_ROLES_MAP.put(TkConstants.ROLE_LV_DEPT_VO, "Leave Department View Only"); // kpme1411
        
        ALL_ROLES_MAP.put(TkConstants.ROLE_TK_LOCATION_VO, "Location View Only");
        ALL_ROLES_MAP.put(TkConstants.ROLE_TK_APPROVER, "Approver"); // attach at 'work area', view only departmental rules
        ALL_ROLES_MAP.put(TkConstants.ROLE_TK_APPROVER_DELEGATE, "Approver Delegate"); // attach at 'work area'
        ALL_ROLES_MAP.put(TkConstants.ROLE_TK_EMPLOYEE, "Employee"); // only people with active assignments have this role.
        ALL_ROLES_MAP.put(TkConstants.ROLE_TK_LOCATION_ADMIN, "Location Admin"); // location admin rename
        ALL_ROLES_MAP.put(TkConstants.ROLE_TK_DEPT_ADMIN, "Time Department Admin");
        ALL_ROLES_MAP.put(TkConstants.ROLE_LV_DEPT_ADMIN, "Leave Department Admin"); // kpme1411
        ALL_ROLES_MAP.put(TkConstants.ROLE_TK_SYS_ADMIN, "System Admin");

    }

    public static final class ACTIONS {
        public static final String CLOCK_IN = "clockIn";
        public static final String CLOCK_OUT = "clockOut";
        public static final String ADD_TIME_BLOCK = "addTimeBlock";
        public static final String UPDATE_TIME_BLOCK = "updateTimeBlock";
        public static final String DELETE_TIME_BLOCK = "deleteTimeBlock";
    }

    public static final List<String> ClOCK_ACTIONS = new ArrayList<String>();

    static {
        ClOCK_ACTIONS.add("CI");
        ClOCK_ACTIONS.add("CO");
        ClOCK_ACTIONS.add("LI"); // Coming back for Lunch
        ClOCK_ACTIONS.add("LO"); // Leaving for Lunch
    }

    /**
     * Simplistic state transition map, created statically for speed.
     */
    public static final Map<String, Set<String>> CLOCK_ACTION_TRANSITION_MAP = new HashMap<String, Set<String>>(4);

    static {
        Set<String> ci = new HashSet<String>();
        ci.add(LUNCH_OUT);
        ci.add(CLOCK_OUT);

        Set<String> co = new HashSet<String>();
        co.add(CLOCK_IN);

        Set<String> li = new HashSet<String>();
        li.add(CLOCK_OUT);

        Set<String> lo = new HashSet<String>();
        lo.add(LUNCH_IN);

        CLOCK_ACTION_TRANSITION_MAP.put(CLOCK_IN, ci);
        CLOCK_ACTION_TRANSITION_MAP.put(CLOCK_OUT, co);
        CLOCK_ACTION_TRANSITION_MAP.put(LUNCH_IN, li);
        CLOCK_ACTION_TRANSITION_MAP.put(LUNCH_OUT, lo);
    }

    // available Clock actions to choose from when user's modifying an existing Missed Punch Document based on
    // the initial clock action
    public static final Map<String, Set<String>> CLOCK_AVAILABLE_ACTION_MAP = new HashMap<String, Set<String>>(4);

    static {
        Set<String> ci = new HashSet<String>();
        ci.add(CLOCK_IN);

        Set<String> co = new HashSet<String>();
        co.add(CLOCK_OUT);
        co.add(LUNCH_OUT);

        Set<String> li = new HashSet<String>();
        li.add(LUNCH_IN);

        Set<String> lo = new HashSet<String>();
        lo.add(CLOCK_OUT);
        lo.add(LUNCH_OUT);

        CLOCK_AVAILABLE_ACTION_MAP.put(CLOCK_IN, ci);
        CLOCK_AVAILABLE_ACTION_MAP.put(CLOCK_OUT, co);
        CLOCK_AVAILABLE_ACTION_MAP.put(LUNCH_IN, li);
        CLOCK_AVAILABLE_ACTION_MAP.put(LUNCH_OUT, lo);
    }
    
    public static final Map<String, String> CLOCK_ACTION_STRINGS = new HashMap<String, String>(4);

    static {
        CLOCK_ACTION_STRINGS.put(CLOCK_IN, "Clock In");
        CLOCK_ACTION_STRINGS.put(CLOCK_OUT, "Clock Out");
        CLOCK_ACTION_STRINGS.put(LUNCH_IN, "Lunch In"); // Coming back for Lunch
        CLOCK_ACTION_STRINGS.put(LUNCH_OUT, "Lunch Out"); // Going to Lunch
    }

    public static final class TIMESHEET_ACTIONS {
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
        public static final String INITIATED = "I";
        public static final String ENROUTE = "R";
        public static final String FINAL = "F";
        public static final String CANCEL = "X";
        public static final String SAVED = "S";
    }

    public static final Map<String, String> DOC_ROUTE_STATUS = new HashMap<String, String>(8);

    static {
        DOC_ROUTE_STATUS.put(KEWConstants.ROUTE_HEADER_INITIATED_CD, KEWConstants.ROUTE_HEADER_INITIATED_LABEL);
        DOC_ROUTE_STATUS.put(KEWConstants.ROUTE_HEADER_CANCEL_CD, KEWConstants.ROUTE_HEADER_CANCEL_LABEL);
        DOC_ROUTE_STATUS.put(KEWConstants.ROUTE_HEADER_ENROUTE_CD, KEWConstants.ROUTE_HEADER_ENROUTE_LABEL);
        DOC_ROUTE_STATUS.put(KEWConstants.ROUTE_HEADER_FINAL_CD, KEWConstants.ROUTE_HEADER_FINAL_LABEL);
        DOC_ROUTE_STATUS.put(KEWConstants.ROUTE_HEADER_APPROVED_CD, KEWConstants.ROUTE_HEADER_APPROVED_LABEL);
        DOC_ROUTE_STATUS.put(KEWConstants.ROUTE_HEADER_DISAPPROVED_CD, KEWConstants.ROUTE_HEADER_DISAPPROVED_LABEL);
        DOC_ROUTE_STATUS.put(KEWConstants.ROUTE_HEADER_EXCEPTION_CD, KEWConstants.ROUTE_HEADER_EXCEPTION_LABEL);
        DOC_ROUTE_STATUS.put(KEWConstants.ROUTE_HEADER_SAVED_CD, KEWConstants.ROUTE_HEADER_SAVED_LABEL);
    }

    public static final class BATCH_JOB_ENTRY_STATUS {
        public static final String SCHEDULED = "S";
        public static final String RUNNING = "R";
        public static final String FINISHED = "F";
        public static final String EXCEPTION = "E";
    }

    public static final class BATCH_JOB_NAMES {
        public static final String INITIATE = "Initiate";
        public static final String APPROVE = "Approve";
        public static final String PAY_PERIOD_END = "Pay Period End";
        public static final String SUPERVISOR_APPROVAL = "Supervisor Approval";
        public static final String BATCH_APPROVE_MISSED_PUNCH = "Batch Approve Missed Punch";
    }

    public static final String BATCH_JOB_USER_PRINCIPAL_ID = "admin";

    public static final String ASSIGNMENT_KEY_DELIMITER = "_";
    public static final String HOLIDAY_EARN_CODE = "HOL";
    // Special System earn code to represent lunch deductions.
    public static final String LUNCH_EARN_CODE = "LUN";

    // calendar navigation
    public static final String NEXT_TIMESHEET = "next";
    public static final String PREV_TIMESHEET = "prev";

    // Timesheet document ID request parameter name
    public static final String TIMESHEET_DOCUMENT_ID_REQUEST_NAME = "tdocid";
    // document ID request parameter name
    public static final String DOCUMENT_ID_REQUEST_NAME = "docid";
    //Threshold in hours for clockin highlighting on approvers tab
    public static final Integer NUMBER_OF_HOURS_CLOCKED_IN_APPROVE_TAB_HIGHLIGHT = 12;

    public static final List<String> TIME_ZONES = new ArrayList<String>();

    static {
        TIME_ZONES.add("America/Chicago");
        TIME_ZONES.add("America/Denver");
        TIME_ZONES.add("America/Detroit");
        TIME_ZONES.add("America/Indiana/Indianapolis");
        TIME_ZONES.add("America/Phoenix");
    }

    public String getLUNCH_EARN_CODE() {
        return LUNCH_EARN_CODE;
    }

    public String getEARN_CODE_AMOUNT() {
        return EARN_CODE_AMOUNT;
    }

    public String getHOLIDAY_EARN_CODE() {
        return HOLIDAY_EARN_CODE;
    }

    public static final Map<String, String> DOCUMENT_STATUS = new HashMap<String, String>();

    static {
        DOCUMENT_STATUS.put("I", "Initiated");
        DOCUMENT_STATUS.put("S", "Saved");
        DOCUMENT_STATUS.put("R", "Enroute");
        DOCUMENT_STATUS.put("F", "Final");
    }

    public static class ConfigSettings {
        public static final String SESSION_TIMEOUT = "session.timeout";
    }

    public static final Integer PAGE_SIZE = 20;
    public static final String APPROVAL_TABLE_ID = "row";

    public static final String IP_SEPERATOR = ".";
    public static final String IP_WILDCARD_PATTERN = "(%|(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))";

    public static final String TASK_DEFAULT_DESP = "Default";

    public static final Map<String, String> ACCRUAL_EARN_INTERVAL = new LinkedHashMap<String, String>(6);

        static {
        	ACCRUAL_EARN_INTERVAL.put("D", "Daily");
        	ACCRUAL_EARN_INTERVAL.put("W", "Weekly");
        	ACCRUAL_EARN_INTERVAL.put("S", "Semi-Monthly");
        	ACCRUAL_EARN_INTERVAL.put("M", "Monthly");
        	ACCRUAL_EARN_INTERVAL.put("Y", "Yearly");
        	ACCRUAL_EARN_INTERVAL.put("N", "No Accrual");
        }

        public static final Map<String, String> SERVICE_UNIT_OF_TIME = new LinkedHashMap<String, String>(3);

        static {
        	SERVICE_UNIT_OF_TIME.put("Y", "Years");
        	SERVICE_UNIT_OF_TIME.put("M", "Months");
        	SERVICE_UNIT_OF_TIME.put("H", "Hours");
        }

        public static final Map<String, String> UNIT_OF_TIME = new LinkedHashMap<String, String>(2);

        static {
        	UNIT_OF_TIME.put("D", "Days");
        	UNIT_OF_TIME.put("H", "Hours");
        }

        public static final Map<String, String> MAX_BALANCE_ACTION_FREQUENCY = new LinkedHashMap<String, String>(3);

        static {
        	MAX_BALANCE_ACTION_FREQUENCY.put("LA", "Leave Approve");
        	MAX_BALANCE_ACTION_FREQUENCY.put("YE", "Year End");
        	MAX_BALANCE_ACTION_FREQUENCY.put("NA", "Not Applicable");
        }

        public static final Map<String, String> ACTION_AT_MAX_BALANCE = new LinkedHashMap<String, String>(3);

        static {
        	ACTION_AT_MAX_BALANCE.put("T", "Transfer");
        	ACTION_AT_MAX_BALANCE.put("P", "Payout");
        	ACTION_AT_MAX_BALANCE.put("L", "Lose");
        	ACTION_AT_MAX_BALANCE.put("NA", "Not Applicable");
        }

        public static final Map<String, String> UNUSED_TIME = new LinkedHashMap<String, String>(3);

        static {
        	UNUSED_TIME.put("NUTA", "No Unused Time Allowed");
        	UNUSED_TIME.put("T", "Transfer");
        	UNUSED_TIME.put("B", "Bank");
        }

        public static final Map<String, String> EMPLOYEE_OVERRIDE_TYPE = new LinkedHashMap<String, String>(5);

        static {
        	EMPLOYEE_OVERRIDE_TYPE.put("MB", "Max Balance");
        	EMPLOYEE_OVERRIDE_TYPE.put("MTA", "Max Transfer Amount");
        	EMPLOYEE_OVERRIDE_TYPE.put("MPA", "Max Payout Amount");
        	EMPLOYEE_OVERRIDE_TYPE.put("MU", "Max Usage");
        	EMPLOYEE_OVERRIDE_TYPE.put("MAC", "Max Annual Carryover");
        }

    public static final String DAILY_OVT_CODE = "DOT";
}
