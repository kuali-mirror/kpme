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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.kuali.kpme.core.KPMEConstants;
import org.kuali.rice.kew.api.document.DocumentStatus;

public class TkConstants {

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

    // Calendar Types
    public static final String CALENDAR_TYPE_PAY = "Pay";
    public static final String CALENDAR_TYPE_LEAVE = "Leave";
    
    public static final String EARN_CODE_CPE = "CPE";
    
    // action history
    public static final String DELETE = "DELETE";
    public static final String ADD = "ADD";
    public static final String MODIFIED = "MODIFIED";
    
    public static final Map<String,String> ACTION_HISTORY_CODES = new HashMap<String,String>();
    
    static {
    	ACTION_HISTORY_CODES.put("A", "Add");
    	ACTION_HISTORY_CODES.put("M", "Modify");
    	ACTION_HISTORY_CODES.put("D", "Delete");
    }

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
    public static final List<String> ROLE_ASSIGNMENT_FOR_USER_ROLES = new ArrayList<String>(6);
    public static final Map<String, String> ALL_ROLES_MAP = new HashMap<String, String>();

    static {
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_GLOBAL_VO);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_DEPT_VO);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_DEPT_ADMIN);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_REVIEWER);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_APPROVER);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_APPROVER_DELEGATE);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_LOCATION_ADMIN);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_LOCATION_VO);
        ROLE_ASSIGNMENT_FOR_USER_ROLES.add(TkConstants.ROLE_TK_SYS_ADMIN);

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
        li.add(LUNCH_OUT);

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

    // document ID request parameter name
    public static final String DOCUMENT_ID_REQUEST_NAME = "docid";
    //Threshold in hours for clockin highlighting on approvers tab
    public static final Integer NUMBER_OF_HOURS_CLOCKED_IN_APPROVE_TAB_HIGHLIGHT = 12;

    public static final String GMT_TIME_ZONE_ID = "Etc/GMT";
    public static final TimeZone GMT_TIME_ZONE = TimeZone.getTimeZone(GMT_TIME_ZONE_ID);
    //public static final String SYSTEM_TIME_ZONE = TimeZone.getDefault().getDisplayName();

    //public static final DateTimeZone SYSTEM_DATE_TIME_ZONE = DateTimeZone.forID(TKUtils.getSystemTimeZone());
    public static final DateTimeFormatter DT_BASIC_TIME_FORMAT = DateTimeFormat.forPattern("hh:mm aa");
    public static final DateTimeFormatter DT_MILITARY_TIME_FORMAT = DateTimeFormat.forPattern("H:mm");
    public static final DateTimeFormatter DT_ABBREV_DATE_FORMAT = DateTimeFormat.forPattern("MM/dd");
    public static final DateTimeFormatter DT_FULL_DATE_TIME_FORMAT = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm aa");

    public static DateTimeFormatter DT_JUST_DAY_FORMAT = DateTimeFormat.forPattern("dd");
    

    public static final Map<String, String> EMPLOYEE_OVERRIDE_TYPE = new LinkedHashMap<String, String>(5);

    static {
        EMPLOYEE_OVERRIDE_TYPE.put("MB", "Max Balance");
        EMPLOYEE_OVERRIDE_TYPE.put("MTA", "Max Transfer Amount");
        EMPLOYEE_OVERRIDE_TYPE.put("MPA", "Max Payout Amount");
        EMPLOYEE_OVERRIDE_TYPE.put("MU", "Max Usage");
        EMPLOYEE_OVERRIDE_TYPE.put("MAC", "Max Annual Carryover");
    }

    public static final String DAILY_OVT_CODE = "DOT";

    public static final class CacheNamespace {
        public static final String MODULE_NAME = "tklm";
        public static final String NAMESPACE_PREFIX = KPMEConstants.CacheNamespace.ROOT_NAMESPACE_PREFIX + "/"
                + MODULE_NAME +'/';
    }
    
    public static final List<String> EMPLOYEE_APPROVAL_DOC_STATUS = new ArrayList<String>();
    static {
    	EMPLOYEE_APPROVAL_DOC_STATUS.add(DocumentStatus.INITIATED.getCode());
    	EMPLOYEE_APPROVAL_DOC_STATUS.add(DocumentStatus.SAVED.getCode());
    }
    
    public static final List<String> MISSEDPUNCH_APPROVAL_TIME_DOC_STATUS = new ArrayList<String>();
    static {
    	MISSEDPUNCH_APPROVAL_TIME_DOC_STATUS.add(DocumentStatus.INITIATED.getCode());
    	MISSEDPUNCH_APPROVAL_TIME_DOC_STATUS.add(DocumentStatus.SAVED.getCode());
    	MISSEDPUNCH_APPROVAL_TIME_DOC_STATUS.add(DocumentStatus.ENROUTE.getCode());
    }
    

    public static final Map<String, Integer> FLSA_WEEK_END_DAY = new LinkedHashMap<String, Integer>(7);

    //used to determine last day of the FLSA week based on the FLSA start day, i.e if the start day is "Sun" the
    // last day would be 6 ("Sat")
    static {
        FLSA_WEEK_END_DAY.put("Sun",6);
        FLSA_WEEK_END_DAY.put("Mon",7);
        FLSA_WEEK_END_DAY.put("Tues",1);
        FLSA_WEEK_END_DAY.put("Wed",2);
        FLSA_WEEK_END_DAY.put("Thur",3);
        FLSA_WEEK_END_DAY.put("Fri",4);
        FLSA_WEEK_END_DAY.put("Sat",5);
    }
}
