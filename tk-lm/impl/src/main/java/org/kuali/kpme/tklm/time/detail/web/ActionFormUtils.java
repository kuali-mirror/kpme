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
package org.kuali.kpme.tklm.time.detail.web;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;
import org.json.simple.JSONValue;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.leaveplan.LeavePlanContract;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.api.workarea.WorkArea;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlockContract;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.api.time.timeblock.TimeBlockContract;
import org.kuali.kpme.tklm.api.time.timehourdetail.TimeHourDetailContract;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.krad.util.GlobalVariables;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActionFormUtils {

   // public static void validateHourLimit(TimeDetailActionFormBase tdaf) throws Exception {
   //     List<String> warningMessages = TkServiceLocator.getTimeOffAccrualService().validateAccrualHoursLimit(tdaf.getTimesheetDocument());
   //      addUniqueWarningsToForm(tdaf, warningMessages);
   // }

    public static void addWarningTextFromEarnGroup(TimeDetailActionFormBase tdaf) throws Exception {
        List<String> warningMessages = new ArrayList<String>();
        
        TimesheetDocument tdoc = tdaf.getTimesheetDocument();
        
        warningMessages = HrServiceLocator.getEarnCodeGroupService().getWarningTextFromEarnCodeGroups(tdoc.getEarnCodeMap());
        addUniqueWarningsToForm(tdaf, warningMessages);
    }

    public static void addUnapprovedIPWarningFromClockLog(TimeDetailActionFormBase tdaf) {
    	List<String> warningMessages = new ArrayList<String>();
    	Set<String> aSet = new HashSet<String>();
    	if(tdaf.getTimesheetDocument() != null) {
	    	List<TimeBlock> tbList = tdaf.getTimesheetDocument().getTimeBlocks();
	    	if(CollectionUtils.isNotEmpty(tbList)) {
		    	 aSet.addAll(TkServiceLocator.getClockLogService().getUnapprovedIPWarning(tbList));
		        
	    	}
    	}
    	warningMessages.addAll(aSet);
    	addUniqueWarningsToForm(tdaf, warningMessages);
    }
    
    public static void addUniqueWarningsToForm(TimeDetailActionFormBase tdaf, List<String> warningMessages) {
        if (!warningMessages.isEmpty()) {
            Set<String> aSet = new HashSet<String>();
            aSet.addAll(warningMessages);
            aSet.addAll(tdaf.getWarningMessages()); //Only warnings. TODO: Do we need actions and info messages here?

            List<String> aList = new ArrayList<String>();
            aList.addAll(aSet);
            tdaf.setWarningMessages(aList);
        }
    }

//    public static String getTimeBlockJSONMap(List<TimeBlock> blocks) {
//        List<Map<String, Object>> jsonList = getTimeBlocksJson(blocks, null);
//        Map<String, Map<String, Object>> jsonMappedList = new HashMap<String, Map<String, Object>>();
//        for (Map<String, Object> tbm : jsonList) {
//            String id = (String) tbm.get("id");
//            jsonMappedList.put(id, tbm);
//        }
//        return JSONValue.toJSONString(jsonMappedList);
//    }

    public static Map<String, String> buildAssignmentStyleClassMap(List<? extends TimeBlockContract> timeBlocks, List<? extends LeaveBlockContract> leaveBlocks) {
    	Map<String, String> aMap = new HashMap<String, String>();
        List<String> assignmentKeys = new ArrayList<String>();

        for (TimeBlockContract tb : timeBlocks) {
            if (!assignmentKeys.contains(tb.getAssignmentKey())) {
                assignmentKeys.add(tb.getAssignmentKey());
            }
        }
        for(LeaveBlockContract lb : leaveBlocks) {
        	if (!assignmentKeys.contains(lb.getAssignmentKey())) {
                assignmentKeys.add(lb.getAssignmentKey());
            }
        }
        Collections.sort(assignmentKeys);

        for (int i = 0; i < assignmentKeys.size(); i++) {
            // pick a color from a 15 color palette
            aMap.put(assignmentKeys.get(i), "assignment" + Integer.toString(i % 15));
        }

        return aMap;
    }
    
    public static Map<String, String> buildAssignmentStyleClassMap(List<? extends TimeBlockContract> timeBlocks) {
        Map<String, String> aMap = new HashMap<String, String>();
        List<String> assignmentKeys = new ArrayList<String>();

        for (TimeBlockContract tb : timeBlocks) {
            if (!assignmentKeys.contains(tb.getAssignmentKey())) {
                assignmentKeys.add(tb.getAssignmentKey());
            }
        }

        Collections.sort(assignmentKeys);

        for (int i = 0; i < assignmentKeys.size(); i++) {
            // pick a color from a five color palette
            aMap.put(assignmentKeys.get(i), "assignment" + Integer.toString(i % 5));
        }

        return aMap;
    }

    /**
     * This method will build the JSON data structure needed for calendar
     * manipulation and processing on the client side. Start and End times here
     * are based on the pre-timezone adjusted times startDisplayTime, and
     * endDisplayTime.
     *
     * @param timeBlocks
     * @return
     */
    public static String getTimeBlocksJson(List<? extends TimeBlockContract> timeBlocks) {
        if (timeBlocks == null || timeBlocks.size() == 0) {
            return "";
        }

        List<Map<String, Object>> timeBlockList = new LinkedList<Map<String, Object>>();
        String timezone = HrServiceLocator.getTimezoneService().getUserTimezone();

        String principalId = GlobalVariables.getUserSession().getPrincipalId();

        boolean isAnyApprover = HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay())
                || HrServiceLocator.getKPMERoleService().principalHasRole(principalId, KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.APPROVER_DELEGATE.getRoleName(), LocalDate.now().toDateTimeAtStartOfDay());

        for (TimeBlockContract timeBlock : timeBlocks) {
            Map<String, Object> timeBlockMap = new LinkedHashMap<String, Object>();

            WorkArea workArea = HrServiceLocator.getWorkAreaService().getWorkArea(timeBlock.getWorkArea(), timeBlock.getEndDateTime().toLocalDate());
            String workAreaDesc = workArea.getDescription();

            timeBlockMap.put("isApprover", isAnyApprover);
            timeBlockMap.put("isSynchronousUser", timeBlock.isClockLogCreated());

            timeBlockMap.put("canEditTb", TkServiceLocator.getTKPermissionService().canEditTimeBlock(principalId, timeBlock));
            timeBlockMap.put("canEditTBOvt", TkServiceLocator.getTKPermissionService().canEditOvertimeEarnCode(principalId, timeBlock));

            if (TkServiceLocator.getTKPermissionService().canEditTimeBlockAllFields(principalId, timeBlock)) {
                timeBlockMap.put("canEditTBAll", true);
                timeBlockMap.put("canEditTBAssgOnly", false);
            } else {
                timeBlockMap.put("canEditTBAll", false);
                timeBlockMap.put("canEditTBAssgOnly", true);
            }

            //    tracking any kind of 'mutating' state with this object, it's just a one off modification under a specific circumstance.
            DateTime start = timeBlock.getBeginTimeDisplay();
            DateTime end = timeBlock.getEndTimeDisplay();

            /**
             * This is the timeblock backward pushing logic.
             * the purpose of this is to accommodate the virtual day mode where the start/end period time is not from 12a to 12a.
             * A timeblock will be pushed back if the timeblock is still within the previous interval
             */
            if (timeBlock.isPushBackward()) {
                start = start.minusDays(1);
                end = end.minusDays(1);
            }

            timeBlockMap.put("documentId", timeBlock.getDocumentId());
            timeBlockMap.put("title", workAreaDesc);
            EarnCodeContract ec = HrServiceLocator.getEarnCodeService().getEarnCode(timeBlock.getEarnCode(), timeBlock.getBeginDateTime().toLocalDate());
            timeBlockMap.put("earnCode", timeBlock.getEarnCode());
            timeBlockMap.put("earnCodeDesc", ec != null ? ec.getDescription() : StringUtils.EMPTY);
            //TODO: need to cache this or pre-load it when the app boots up
            // EarnCode earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(timeBlock.getEarnCode(), timeBlock.getBeginDateTime().toLocalDate());
            timeBlockMap.put("earnCodeType", timeBlock.getEarnCodeType());

            // TODO: Cleanup the start / end time related properties. We certainly don't need all of them.
            // The ones which are used by the javascript are startDate, endDate, startTime, endTime, startTimeHourMinute, and endTimeHourMinute
            timeBlockMap.put("start", start.toString(ISODateTimeFormat.dateTimeNoMillis()));
            timeBlockMap.put("end", end.toString(ISODateTimeFormat.dateTimeNoMillis()));
            timeBlockMap.put("startDate", start.toString(HrConstants.DateTimeFormats.BASIC_DATE_FORMAT));
            timeBlockMap.put("endDate", end.toString(HrConstants.DateTimeFormats.BASIC_DATE_FORMAT));
            timeBlockMap.put("startNoTz", start.toString(ISODateTimeFormat.dateHourMinuteSecond()));
            timeBlockMap.put("endNoTz", end.toString(ISODateTimeFormat.dateHourMinuteSecond()));
            // start / endTimeHourMinute fields are for only for the display purpose
            timeBlockMap.put("startTimeHourMinute", start.toString(TkConstants.DT_BASIC_TIME_FORMAT));
            timeBlockMap.put("endTimeHourMinute", end.toString(TkConstants.DT_BASIC_TIME_FORMAT));
            // start / endTime are the actual fields used by the adding / editing timeblocks
            timeBlockMap.put("startTime", start.toString(TkConstants.DT_MILITARY_TIME_FORMAT));
            timeBlockMap.put("endTime", end.toString(TkConstants.DT_MILITARY_TIME_FORMAT));
            timeBlockMap.put("id", timeBlock.getTkTimeBlockId() == null ? null : timeBlock.getTkTimeBlockId());
            timeBlockMap.put("hours", timeBlock.getHours());
            timeBlockMap.put("amount", timeBlock.getAmount());
            timeBlockMap.put("timezone", timezone);
            timeBlockMap.put("assignment", new AssignmentDescriptionKey(timeBlock.getGroupKeyCode(), timeBlock.getJobNumber(), timeBlock.getWorkArea(), timeBlock.getTask()).toAssignmentKeyString());
            timeBlockMap.put("tkTimeBlockId", timeBlock.getTkTimeBlockId() != null ? timeBlock.getTkTimeBlockId() : "");
            timeBlockMap.put("lunchDeleted", timeBlock.isLunchDeleted());

            List<Map<String, Object>> timeHourDetailList = new LinkedList<Map<String, Object>>();
            for (TimeHourDetailContract timeHourDetail : timeBlock.getTimeHourDetails()) {
                Map<String, Object> timeHourDetailMap = new LinkedHashMap<String, Object>();
                timeHourDetailMap.put("earnCode", timeHourDetail.getEarnCode());
                timeHourDetailMap.put("hours", timeHourDetail.getHours());
                timeHourDetailMap.put("amount", timeHourDetail.getAmount());

                // if there is a lunch hour deduction, add a flag to the timeBlockMap
                if (StringUtils.equals(timeHourDetail.getEarnCode(), "LUN")) {
                    timeBlockMap.put("lunchDeduction", true);
                }

                timeHourDetailList.add(timeHourDetailMap);
            }
            timeBlockMap.put("timeHourDetails", JSONValue.toJSONString(timeHourDetailList));

            timeBlockList.add(timeBlockMap);
            //System.out.println("\n\n\n\n");
            //System.out.println(sw.prettyPrint());
            //System.out.println("\n\n\n\n");
        }
//        Map<String, Map<String, Object>> jsonMappedList = new HashMap<String, Map<String, Object>>();
//        for (Map<String, Object> tbm : timeBlockList) {
//            String id = (String) tbm.get("id");
//            jsonMappedList.put(id, tbm);
//        }
        String value = JSONValue.toJSONString(timeBlockList);

        return value;
    }
    
    
    /**
     * This method will build the leave blocks JSON data structure needed for calendar
     * manipulation and processing on the client side.
     *
     * @param leaveBlocks
     * @return
     */
    public static String getLeaveBlocksJson(List<LeaveBlock> leaveBlocks) {
    	if (CollectionUtils.isEmpty(leaveBlocks)) {
            return "";
        }
        List<Map<String, Object>> leaveBlockList = new LinkedList<Map<String, Object>>();
        for (LeaveBlockContract leaveBlock : leaveBlocks) {
        	Map<String, Object> leaveBlockMap = new LinkedHashMap<String, Object>();
        	leaveBlockMap.put("title", leaveBlock.getAssignmentTitle());
        	leaveBlockMap.put("assignment", leaveBlock.getAssignmentKey());
        	leaveBlockMap.put("earnCode", leaveBlock.getEarnCode());
        	leaveBlockMap.put("lmLeaveBlockId", leaveBlock.getLmLeaveBlockId());
        	leaveBlockMap.put("leaveAmount", leaveBlock.getLeaveAmount().toString());
        	DateTime leaveDate = leaveBlock.getLeaveLocalDate().toDateTimeAtStartOfDay();
        	leaveBlockMap.put("leaveDate", leaveDate.toString(HrConstants.DateTimeFormats.BASIC_DATE_FORMAT));
        	leaveBlockMap.put("id", leaveBlock.getLmLeaveBlockId());
        	leaveBlockMap.put("canTransfer", LmServiceLocator.getLMPermissionService().canTransferSSTOUsage(leaveBlock));
        	leaveBlockMap.put("startDate", leaveDate.toString(HrConstants.DateTimeFormats.BASIC_DATE_FORMAT));
        	leaveBlockMap.put("endDate", leaveDate.toString(HrConstants.DateTimeFormats.BASIC_DATE_FORMAT));
        	
        	if(leaveBlock.getBeginDateTime() != null && leaveBlock.getEndDateTime() != null) {
	            DateTime start = leaveBlock.getBeginDateTime();
	        	DateTime end = leaveBlock.getEndDateTime();
	        	leaveBlockMap.put("startTimeHourMinute", start.toString(TkConstants.DT_BASIC_TIME_FORMAT));
	        	leaveBlockMap.put("endTimeHourMinute", end.toString(TkConstants.DT_BASIC_TIME_FORMAT));
	        	leaveBlockMap.put("startTime", start.toString(TkConstants.DT_MILITARY_TIME_FORMAT));
	        	leaveBlockMap.put("endTime", end.toString(TkConstants.DT_MILITARY_TIME_FORMAT));
	        	leaveBlockMap.put("startDate", start.toString(HrConstants.DateTimeFormats.BASIC_DATE_FORMAT));
	        	leaveBlockMap.put("endDate", end.toString(HrConstants.DateTimeFormats.BASIC_DATE_FORMAT));
            }
        	
        	leaveBlockList.add(leaveBlockMap);
        }
    	return JSONValue.toJSONString(leaveBlockList);
    }
    
    public static Map<String, String> getPayPeriodsMap(List<CalendarEntry> payPeriods, String viewPrincipal) {
    	// use linked map to keep the order of the pay periods
    	Map<String, String> pMap = Collections.synchronizedMap(new LinkedHashMap<String, String>());
    	if (payPeriods == null || payPeriods.isEmpty()) {
            return pMap;
        }
    	payPeriods.removeAll(Collections.singletonList(null));
    	Collections.sort(payPeriods);  // sort the pay period list by getBeginPeriodDate
    	Collections.reverse(payPeriods);  // newest on top
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        for (CalendarEntry pce : payPeriods) {
        	// Check if service date of user is after the Calendar entry
            DateTime asOfDate = pce.getEndPeriodFullDateTime().minusDays(1);
    		PrincipalHRAttributes principalHRAttributes = null;
    		String formattedBeginDate = HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.print(pce.getBeginPeriodFullDateTime());
            String formattedEndDate = HrConstants.DateTimeFormats.BASIC_DATE_FORMAT.print(pce.getEndPeriodFullDateTime().minusMillis(1));
            String formattedRange = formattedBeginDate + " - " + formattedEndDate;

    		if(viewPrincipal != null) {
    			principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(viewPrincipal, asOfDate.toLocalDate());
    		} else {
                pMap.put(pce.getHrCalendarEntryId(), formattedRange);
    		}
    		
    		if(principalHRAttributes != null && pce != null && pce.getHrCalendarEntryId()!= null && pce.getBeginPeriodFullDateTime() != null && pce.getEndPeriodFullDateTime() != null ) {
    			LocalDate startCalDate = principalHRAttributes.getServiceLocalDate();
    			if(startCalDate != null) {
    				if(!(pce.getBeginPeriodFullDateTime().compareTo(startCalDate.toDateTimeAtStartOfDay()) < 0)) {
    	        		//pMap.put(pce.getHrCalendarEntriesId(), sdf.format(pce.getBeginPeriodDate()) + " - " + sdf.format(pce.getEndPeriodDate()));
    	                //getting one millisecond of the endperioddate to match the actual pay period. i.e. pay period end at the 11:59:59:59...PM of that day
    	                pMap.put(pce.getHrCalendarEntryId(), formattedRange);
            		} 
            	} else {
            		pMap.put(pce.getHrCalendarEntryId(), formattedRange);
            	}
    		} 
    		
        }
        
    	return pMap;
    }
    
    // detect if the passed-in calendar entry is the current one
    public static boolean isOnCurrentPeriodFlag(CalendarEntry pce) {
    	String viewPrincipal = HrContext.getTargetPrincipalId();
        CalendarEntry calendarEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarDates(viewPrincipal, new LocalDate().toDateTimeAtStartOfDay());

        return pce != null && calendarEntry != null && calendarEntry.equals(pce);
    }
     
    public static String getUnitOfTimeForEarnCode(EarnCodeContract earnCode) {
    	return earnCode.getRecordMethod() ;
    }
    
    public static int getPlanningMonthsForEmployee(String principalid) {
		int plannningMonths = 0;
		PrincipalHRAttributes principalHRAttributes = HrServiceLocator
				.getPrincipalHRAttributeService().getPrincipalCalendar(
						principalid, LocalDate.now());
		if (principalHRAttributes != null
				&& principalHRAttributes.getLeavePlan() != null) {
			LeavePlanContract lp = HrServiceLocator.getLeavePlanService()
					.getLeavePlan(principalHRAttributes.getLeavePlan(),
							LocalDate.now());
			if (lp != null && lp.getPlanningMonths() != null) {
				plannningMonths = Integer.parseInt(lp.getPlanningMonths());
			}
		}
		return plannningMonths;
    }
    
    public static List<CalendarEntry> getAllCalendarEntriesForYear(List<CalendarEntry> calendarEntries, String year) {
    	List<CalendarEntry> allCalendarEntriesForYear = new ArrayList<CalendarEntry>();
    	
	    for (CalendarEntry calendarEntry : calendarEntries) {
	    	String calendarEntryYear = calendarEntry.getBeginPeriodFullDateTime().toString("yyyy");
	    	if (StringUtils.equals(calendarEntryYear, year)) {
	    		allCalendarEntriesForYear.add(calendarEntry);
	    	}
	    }
    	
    	return allCalendarEntriesForYear;
    }


    
}

