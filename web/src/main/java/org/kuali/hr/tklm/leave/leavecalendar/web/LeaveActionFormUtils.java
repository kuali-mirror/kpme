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
package org.kuali.hr.tklm.leave.leavecalendar.web;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.json.simple.JSONValue;
import org.kuali.hr.core.assignment.AssignmentDescriptionKey;
import org.kuali.hr.core.workarea.WorkArea;
import org.kuali.hr.tklm.leave.leaveblock.LeaveBlock;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.hr.tklm.time.util.TKContext;
import org.kuali.hr.tklm.time.util.TkConstants;

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
            
            if(leaveBlock.getBeginTimestamp() != null && leaveBlock.getEndTimestamp() != null) {
	            DateTime start = new DateTime(leaveBlock.getBeginTimestamp().getTime());
	        	DateTime end = new DateTime(leaveBlock.getEndTimestamp().getTime());
	        	LeaveBlockMap.put("startTimeHourMinute", start.toString(TkConstants.DT_BASIC_TIME_FORMAT));
	            LeaveBlockMap.put("endTimeHourMinute", end.toString(TkConstants.DT_BASIC_TIME_FORMAT));
	            LeaveBlockMap.put("startTime", start.toString(TkConstants.DT_MILITARY_TIME_FORMAT));
	            LeaveBlockMap.put("endTime", end.toString(TkConstants.DT_MILITARY_TIME_FORMAT));
            }
        		
            WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(leaveBlock.getWorkArea(), leaveBlock.getLeaveLocalDate());
            String workAreaDesc = workArea == null ? "" : workArea.getDescription();
            // Roles
            Boolean isAnyApprover = TKContext.isAnyApprover();
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
            LeaveBlockMap.put("leaveAmount", leaveBlock.getLeaveAmount());
            LeaveBlockMap.put("description", leaveBlock.getDescription());
            LeaveBlockMap.put("leaveBlockType", leaveBlock.getLeaveBlockType());
            LeaveBlockMap.put("editable", leaveBlock.isEditable());
            LeaveBlockMap.put("requestStatus", leaveBlock.getRequestStatus());
            LeaveBlockMap.put("canTransfer", TkServiceLocator.getLMPermissionService().canTransferSSTOUsage(leaveBlock));
            leaveBlockList.add(LeaveBlockMap);
        }

        
        return JSONValue.toJSONString(leaveBlockList);
        
    }

}
