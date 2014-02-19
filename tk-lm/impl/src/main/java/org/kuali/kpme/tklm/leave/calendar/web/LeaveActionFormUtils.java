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
package org.kuali.kpme.tklm.leave.calendar.web;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.json.simple.JSONValue;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.workarea.WorkAreaContract;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.leave.request.approval.web.LeaveRequestApprovalRow;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;

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
        String timezone = HrServiceLocator.getTimezoneService().getUserTimezone();

        for (LeaveBlockContract leaveBlock : leaveBlocks) {
            Map<String, Object> LeaveBlockMap = new LinkedHashMap<String, Object>();
            
            if(leaveBlock.getBeginDateTime() != null && leaveBlock.getEndDateTime() != null) {
	            DateTime start = leaveBlock.getBeginDateTime();
	        	DateTime end = leaveBlock.getEndDateTime();
	        	LeaveBlockMap.put("startTimeHourMinute", start.toString(TkConstants.DT_BASIC_TIME_FORMAT));
	            LeaveBlockMap.put("endTimeHourMinute", end.toString(TkConstants.DT_BASIC_TIME_FORMAT));
	            LeaveBlockMap.put("startTime", start.toString(TkConstants.DT_MILITARY_TIME_FORMAT));
	            LeaveBlockMap.put("endTime", end.toString(TkConstants.DT_MILITARY_TIME_FORMAT));
            }
        		
            WorkAreaContract workArea = HrServiceLocator.getWorkAreaService().getWorkAreaWithoutRoles(leaveBlock.getWorkArea(), leaveBlock.getLeaveLocalDate());
            String workAreaDesc = workArea == null ? "" : workArea.getDescription();
            // Roles
            Boolean getAnyApprover = HrContext.isAnyApprover();
            LeaveBlockMap.put("isApprover", getAnyApprover);
           
            LeaveBlockMap.put("documentId", leaveBlock.getDocumentId());
            LeaveBlockMap.put("title", workAreaDesc);
            DateTime dtLeaveDate = leaveBlock.getLeaveLocalDate().toDateTimeAtStartOfDay();
            LeaveBlockMap.put("leaveDate", dtLeaveDate.toString(HrConstants.DT_BASIC_DATE_FORMAT));
        		
            LeaveBlockMap.put("id", leaveBlock.getLmLeaveBlockId());
            LeaveBlockMap.put("timezone", timezone);
            LeaveBlockMap.put("assignment", new AssignmentDescriptionKey(leaveBlock.getJobNumber(), leaveBlock.getWorkArea(), leaveBlock.getTask()).toAssignmentKeyString());
            LeaveBlockMap.put("lmLeaveBlockId", leaveBlock.getLmLeaveBlockId() != null ? leaveBlock.getLmLeaveBlockId() : "");
            LeaveBlockMap.put("earnCode", leaveBlock.getEarnCode());
            LeaveBlockMap.put("leaveAmount", leaveBlock.getLeaveAmount());
            LeaveBlockMap.put("description", leaveBlock.getDescription());
            LeaveBlockMap.put("leaveBlockType", leaveBlock.getLeaveBlockType());
            LeaveBlockMap.put("editable", LmServiceLocator.getLMPermissionService().canEditLeaveBlock(HrContext.getPrincipalId(), leaveBlock));
            LeaveBlockMap.put("requestStatus", leaveBlock.getRequestStatus());
            LeaveBlockMap.put("canTransfer", LmServiceLocator.getLMPermissionService().canTransferSSTOUsage(leaveBlock));
            leaveBlockList.add(LeaveBlockMap);
        }

        
        return JSONValue.toJSONString(leaveBlockList);
        
    }
    
    
    public static String getLeaveRequestsJson(List<LeaveRequestApprovalRow> reqRows) {

        if (reqRows == null || reqRows.size() == 0) {
            return "";
        }

        List<Map<String, Object>> leaveRequestList = new LinkedList<Map<String, Object>>();

        for (LeaveRequestApprovalRow requestRow : reqRows) {
            Map<String, Object> leaveRequestMap = new LinkedHashMap<String, Object>();
            leaveRequestMap.put("id", requestRow.getLeaveRequestDocId());
            leaveRequestMap.put("documentId", requestRow.getLeaveRequestDocId());
            leaveRequestMap.put("leaveDate", requestRow.getRequestedDate()); 
            leaveRequestMap.put("assignmentTitle", requestRow.getAssignmentTitle());
            leaveRequestMap.put("leaveHours", requestRow.getRequestedHours());
            leaveRequestMap.put("principalId", requestRow.getPrincipalId());
            leaveRequestMap.put("principalName", requestRow.getEmployeeName());
            leaveRequestMap.put("leaveCode", requestRow.getLeaveCode());
            leaveRequestList.add(leaveRequestMap);
        }
        
        return JSONValue.toJSONString(leaveRequestList);
        
    }

}
