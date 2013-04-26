package org.kuali.hr.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.document.DocumentStatus;

public class HrConstants {

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

    public static final Integer PAGE_SIZE = 20;
    public static final String APPROVAL_TABLE_ID = "row";
    public static final String TASK_DEFAULT_DESP = "Default";
}
