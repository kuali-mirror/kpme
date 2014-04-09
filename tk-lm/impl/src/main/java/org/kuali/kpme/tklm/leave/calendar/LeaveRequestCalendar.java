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
package org.kuali.kpme.tklm.leave.calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.kuali.kpme.core.calendar.CalendarParent;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.block.LeaveBlockBo;
import org.kuali.kpme.tklm.leave.calendar.web.LeaveRequestCalendarDay;
import org.kuali.kpme.tklm.leave.calendar.web.LeaveRequestCalendarWeek;
import org.kuali.kpme.tklm.leave.request.approval.web.LeaveRequestApprovalRow;
import org.kuali.kpme.tklm.leave.workflow.LeaveRequestDocument;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;


public class LeaveRequestCalendar extends CalendarParent {
	
	private static final long serialVersionUID = -1038397053444003582L;
    private static final Logger LOG = Logger.getLogger(LeaveRequestCalendar.class);
    private List<LeaveRequestApprovalRow> requestList = new ArrayList<LeaveRequestApprovalRow>();
    private String calendarType;
   
    public LeaveRequestCalendar(DateTime beginDateTime, DateTime endDateTime, Map<String, List<LeaveRequestDocument>> leaveReqDocsMap, Map<String, List<LeaveBlock>> leaveBlocksMap, String calendarType) {
    	this.calendarType = calendarType;
        setBeginDateTime(beginDateTime);
    	setEndDateTime(endDateTime);
    	
        DateTime currentDisplayDateTime = new DateTime(beginDateTime.getMillis());
        DateTime endDisplayDateTime = new DateTime(endDateTime.getMillis());

        // Fill in the days if the first day or end day is in the middle of the week
        if (currentDisplayDateTime.getDayOfWeek() != DateTimeConstants.SUNDAY) {
            currentDisplayDateTime = currentDisplayDateTime.minusDays(currentDisplayDateTime.getDayOfWeek());
        }
        if (endDisplayDateTime.getDayOfWeek() != DateTimeConstants.SATURDAY) {
            endDisplayDateTime = endDisplayDateTime.plusDays(DateTimeConstants.SATURDAY - endDisplayDateTime.getDayOfWeek());
        }

        LeaveRequestCalendarWeek leaveReqCalendarWeek = new LeaveRequestCalendarWeek();
        Integer dayNumber = 0;
        
        while (currentDisplayDateTime.isBefore(endDisplayDateTime) || currentDisplayDateTime.isEqual(endDisplayDateTime)) {
            LeaveRequestCalendarDay leaveReqCalendarDay = new LeaveRequestCalendarDay();
            
            // If the day is not within the current pay period, mark them as read only (gray)
            if (StringUtils.equalsIgnoreCase("M", calendarType) && (currentDisplayDateTime.isBefore(getBeginDateTime()) 
            		|| currentDisplayDateTime.isEqual(getEndDateTime()) 
            		|| currentDisplayDateTime.isAfter(getEndDateTime()))) {
                leaveReqCalendarDay.setGray(true);
            } else {
                // This is for the div id of the days on the calendar.
                // It creates a day id like day_11/01/2011 which will make day parsing easier in the javascript.
               leaveReqCalendarDay.setDayNumberDelta(dayNumber);
    
               List<LeaveRequestDocument> reqDocs = leaveReqDocsMap.get(currentDisplayDateTime.toLocalDate().toString());
               List<LeaveBlock> leaveBlocks = leaveBlocksMap.get(currentDisplayDateTime.toLocalDate().toString());
               List<LeaveRequestApprovalRow> rowList = new ArrayList<LeaveRequestApprovalRow>();
               if (reqDocs == null) {
            	   reqDocs = Collections.emptyList();
               } 
               if(leaveBlocks == null) {
            	   leaveBlocks = Collections.emptyList();
               }
               List<String> leaveBlockIds = new ArrayList<String>();
               if(!reqDocs.isEmpty()) {
	               for(LeaveRequestDocument lrd : reqDocs) {
	            	    LeaveBlock lb = lrd.getLeaveBlock();
	            	    leaveBlockIds.add(lb.getLmLeaveBlockId());
	            	    String principalId = lb.getPrincipalId();
	            	   	LeaveRequestApprovalRow aRow = new LeaveRequestApprovalRow();
	            	   	// Set Employee Name 
	            	    EntityNamePrincipalName entityNamePrincipalName = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
	            		if(entityNamePrincipalName != null) {
	            			aRow.setPrincipalId(principalId);
	            			aRow.setEmployeeName(entityNamePrincipalName.getDefaultName() == null ? StringUtils.EMPTY : entityNamePrincipalName.getDefaultName().getCompositeName());
	            		}
		       			aRow.setLeaveRequestDocId(lrd.getDocumentNumber());
		       			aRow.setLeaveCode(lb.getEarnCode());
		       			aRow.setRequestedDate(TKUtils.formatDate(lb.getLeaveLocalDate()));
		       			aRow.setRequestedHours(lb.getLeaveAmount().toString());
		       			aRow.setDescription(lrd.getDescription() == null ? lb.getDescription() : lrd.getDescription());
		       			aRow.setAssignmentTitle(lb.getAssignmentTitle());
		       			aRow.setRequestStatus(lb.getRequestStatusString().toLowerCase());
		       			rowList.add(aRow);
	               }
               }
               
               if(!leaveBlocks.isEmpty()) {
            	   for(LeaveBlock lb: leaveBlocks) {
            		   if(!leaveBlockIds.contains(lb.getLmLeaveBlockId())) {
	            		   	String principalId = lb.getPrincipalId();
		            	   	LeaveRequestApprovalRow aRow = new LeaveRequestApprovalRow();
		            	   	// Set Employee Name 
		            	    EntityNamePrincipalName entityNamePrincipalName = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
		            		if(entityNamePrincipalName != null) {
		            			aRow.setPrincipalId(principalId);
		            			aRow.setEmployeeName(entityNamePrincipalName.getDefaultName() == null ? StringUtils.EMPTY : entityNamePrincipalName.getDefaultName().getCompositeName());
		            		}
			       			aRow.setLeaveCode(lb.getEarnCode());
			       			aRow.setRequestedDate(TKUtils.formatDate(lb.getLeaveLocalDate()));
			       			aRow.setRequestedHours(lb.getLeaveAmount().toString());
			       			aRow.setRequestStatus(lb.getRequestStatusString().toLowerCase());
			       			aRow.setAssignmentTitle(lb.getAssignmentTitle());
			       			rowList.add(aRow);
            		   }
            	   }
               }
               leaveReqCalendarDay.setLeaveReqRows(rowList);
               if(!rowList.isEmpty() && rowList.size() > 0) {
            	   requestList.addAll(rowList);
               }
               dayNumber++;
            }
            
            leaveReqCalendarDay.setDayNumberString(currentDisplayDateTime.dayOfMonth().getAsShortText());
            leaveReqCalendarDay.setDateString(currentDisplayDateTime.toString(HrConstants.DateTimeFormats.BASIC_DATE_FORMAT));

            leaveReqCalendarWeek.getDays().add(leaveReqCalendarDay);
            
            if (leaveReqCalendarWeek.getDays().size() == DateTimeConstants.DAYS_PER_WEEK) {
                getWeeks().add(leaveReqCalendarWeek);
                leaveReqCalendarWeek = new LeaveRequestCalendarWeek();
            }

            currentDisplayDateTime = currentDisplayDateTime.plusDays(1);
        }

        if (!leaveReqCalendarWeek.getDays().isEmpty()) {
            getWeeks().add(leaveReqCalendarWeek);
        }

    }


	public List<LeaveRequestApprovalRow> getRequestList() {
		return requestList;
	}
	
	public String getCalendarTitle() {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.equalsIgnoreCase("W", calendarType)) {
            sb.append(getBeginDateTime().toString("dd MMM yyyy"));
            sb.append(" - ");
            sb.append(getEndDateTime().toString("dd MMM yyyy"));
        } else {
        	sb.append(getBeginDateTime().toString("MMMM y"));
        }

        return sb.toString();
    }
    
}
