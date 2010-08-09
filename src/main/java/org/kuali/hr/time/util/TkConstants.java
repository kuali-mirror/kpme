package org.kuali.hr.time.util;

import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class TkConstants {

    public static final String CLOCK_IN = "CI";
    public static final String CLOCK_OUT = "CO";
    public static final String LUNCH_IN = "LI";
    public static final String LUNCH_OUT = "LO";
    public static final String BREAK_IN = "BI";
    public static final String BREAK_OUT = "BO";

    public static final String   GMT_TIME_ZONE_ID = "Etc/GMT";
    public static final TimeZone GMT_TIME_ZONE    = TimeZone.getTimeZone(GMT_TIME_ZONE_ID);
    public static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy");
    public static final MathContext MATH_CONTEXT = new MathContext(5,RoundingMode.HALF_EVEN);
    
    public static final String SUPER_USER = TkConstants.ROLE_TK_SYS_ADMIN;
    
    /**
     * The following ROLE_* constants need to match what is in the workflow database.  They will be
     * used internally to obtain a reference to the underlying IDs in the workflow system.
     */
    public static final String ROLE_NAMESAPCE    = "KUALI";
    public static final String ROLE_TK_APPROVER  = "TK_APPROVER";
    public static final String ROLE_TK_EMPLOYEE  = "TK_EMPLOYEE";
    public static final String ROLE_TK_ORG_ADMIN = "TK_ORG_ADMIN";
    public static final String ROLE_TK_SYS_ADMIN = "TK_SYS_ADMIN";
    public static final String ROLE_WORK_AREA_QUALIFIER_ID = "workAreaId";
    public static final List<String> ROLE_ASSIGNMENT_FOR_WORK_AREA = new ArrayList<String>(2);
    public static final Map<String,String> ROLE_NAME_TO_DESCRIPTION_MAP = new HashMap<String,String>();
    static {
    	ROLE_ASSIGNMENT_FOR_WORK_AREA.add(TkConstants.ROLE_TK_APPROVER);
    	
    	ROLE_NAME_TO_DESCRIPTION_MAP.put(TkConstants.ROLE_TK_APPROVER, "Approver");
    	ROLE_NAME_TO_DESCRIPTION_MAP.put(TkConstants.ROLE_TK_EMPLOYEE, "Employee");
    	ROLE_NAME_TO_DESCRIPTION_MAP.put(TkConstants.ROLE_TK_ORG_ADMIN, "Org Administrator");
    	ROLE_NAME_TO_DESCRIPTION_MAP.put(TkConstants.ROLE_TK_SYS_ADMIN, "System Administrator");
    }
}
