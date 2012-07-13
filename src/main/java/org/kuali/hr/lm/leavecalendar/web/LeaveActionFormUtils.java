package org.kuali.hr.lm.leavecalendar.web;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.simple.JSONValue;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;

import java.util.*;

public class LeaveActionFormUtils {

    /**
     * This method will build the JSON data structure needed for calendar
     * manipulation and processing on the client side. 
     *
     * @param leaveBlocks
     * @return
     */
    public static String getLeaveBlocksJson(List<LeaveBlock> leaveBlocks) {

        if (leaveBlocks == null || leaveBlocks.size() == 0) {
            return "";
        }

        List<Map<String, Object>> leaveBlockList = new LinkedList<Map<String, Object>>();
        String timezone = TkServiceLocator.getTimezoneService().getUserTimezone();

        for (LeaveBlock leaveBlock : leaveBlocks) {
            Map<String, Object> LeaveBlockMap = new LinkedHashMap<String, Object>();
           
            WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(leaveBlock.getWorkArea(), leaveBlock.getLeaveDate());
            String workAreaDesc = workArea == null ? "" : workArea.getDescription();
            // Roles
            Boolean isAnyApprover = TKContext.getUser().getCurrentRoles().isAnyApproverActive();
            LeaveBlockMap.put("isApprover", isAnyApprover);
           
            LeaveBlockMap.put("documentId", leaveBlock.getDocumentId());
            LeaveBlockMap.put("title", workAreaDesc);
            DateTime dtLeaveDate = new DateTime(leaveBlock.getLeaveDate());
            LeaveBlockMap.put("leaveDate", dtLeaveDate.toString(TkConstants.DT_BASIC_DATE_FORMAT));
            LeaveBlockMap.put("id", leaveBlock.getLmLeaveBlockId().toString());
            LeaveBlockMap.put("timezone", timezone);
            LeaveBlockMap.put("assignment", new AssignmentDescriptionKey(leaveBlock.getJobNumber(), leaveBlock.getWorkArea(), leaveBlock.getTask()).toAssignmentKeyString());
            LeaveBlockMap.put("lmLeaveBlockId", leaveBlock.getLmLeaveBlockId() != null ? leaveBlock.getLmLeaveBlockId() : "");
            LeaveBlockMap.put("earnCode", leaveBlock.getEarnCode());
            LeaveBlockMap.put("earnCodeId", leaveBlock.getEarnCodeId());
            LeaveBlockMap.put("leaveAmount", leaveBlock.getLeaveAmount());
            LeaveBlockMap.put("description", leaveBlock.getDescription());
            LeaveBlockMap.put("leaveBlockType", leaveBlock.getLeaveBlockType());
            LeaveBlockMap.put("editable", leaveBlock.isEditable());
            leaveBlockList.add(LeaveBlockMap);
        }

        
        return JSONValue.toJSONString(leaveBlockList);
        
    }

}
