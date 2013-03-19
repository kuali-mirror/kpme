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
package org.kuali.hr.time.detail.web;

import java.sql.Date;
import java.text.DateFormat;
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.simple.JSONValue;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationUtil;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.earncodegroup.EarnCodeGroup;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.krad.util.GlobalVariables;

public class ActionFormUtils {

   // public static void validateHourLimit(TimeDetailActionFormBase tdaf) throws Exception {
   //     List<String> warningMessages = TkServiceLocator.getTimeOffAccrualService().validateAccrualHoursLimit(tdaf.getTimesheetDocument());
   //      addUniqueWarningsToForm(tdaf, warningMessages);
   // }

    public static void addWarningTextFromEarnGroup(TimeDetailActionFormBase tdaf) throws Exception {
        List<String> warningMessages = TkServiceLocator.getEarnCodeGroupService().warningTextFromEarnCodeGroupsOfDocument(tdaf.getTimesheetDocument());
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

    public static Map<String, String> buildAssignmentStyleClassMap(List<TimeBlock> timeBlocks, List<LeaveBlock> leaveBlocks) {
    	Map<String, String> aMap = new HashMap<String, String>();
        List<String> assignmentKeys = new ArrayList<String>();

        for (TimeBlock tb : timeBlocks) {
            if (!assignmentKeys.contains(tb.getAssignmentKey())) {
                assignmentKeys.add(tb.getAssignmentKey());
            }
        }
        for(LeaveBlock lb : leaveBlocks) {
        	if (!assignmentKeys.contains(lb.getAssignmentKey())) {
                assignmentKeys.add(lb.getAssignmentKey());
            }
        }
        Collections.sort(assignmentKeys);

        for (int i = 0; i < assignmentKeys.size(); i++) {
            // pick a color from a five color palette
            aMap.put(assignmentKeys.get(i), "assignment" + Integer.toString(i % 5));
        }

        return aMap;
    }
    
    public static Map<String, String> buildAssignmentStyleClassMap(List<TimeBlock> timeBlocks) {
        Map<String, String> aMap = new HashMap<String, String>();
        List<String> assignmentKeys = new ArrayList<String>();

        for (TimeBlock tb : timeBlocks) {
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
    public static String getTimeBlocksJson(List<TimeBlock> timeBlocks) {

        if (timeBlocks == null || timeBlocks.size() == 0) {
            return "";
        }

        List<Map<String, Object>> timeBlockList = new LinkedList<Map<String, Object>>();
        String timezone = TkServiceLocator.getTimezoneService().getUserTimezone();

        for (TimeBlock timeBlock : timeBlocks) {
            Map<String, Object> timeBlockMap = new LinkedHashMap<String, Object>();

            WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(timeBlock.getWorkArea(), new java.sql.Date(timeBlock.getEndTimestamp().getTime()));
            String workAreaDesc = workArea.getDescription();

            // Roles
            Boolean isAnyApprover = TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isAnyApproverActive();
            timeBlockMap.put("isApprover", isAnyApprover);
            timeBlockMap.put("isSynchronousUser", timeBlock.getClockLogCreated());

            // Permissions
            timeBlockMap.put("canEditTb", TkServiceLocator.getPermissionsService().canEditTimeBlock(timeBlock));
            timeBlockMap.put("canEditTBOvt", TkServiceLocator.getPermissionsService().canEditOvertimeEarnCode(timeBlock));
            timeBlockMap.put("canAddTB", TkServiceLocator.getPermissionsService().canAddTimeBlock());

            if (TkServiceLocator.getPermissionsService().canEditTimeBlockAllFields(timeBlock)) {
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
            timeBlockMap.put("earnCode", timeBlock.getEarnCode());
            timeBlockMap.put("earnCodeDesc", TkServiceLocator.getEarnCodeService().getEarnCode(timeBlock.getEarnCode(), TKUtils.getCurrentDate()).getDescription());
            //TODO: need to cache this or pre-load it when the app boots up
            // EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode(timeBlock.getEarnCode(), new java.sql.Date(timeBlock.getBeginTimestamp().getTime()));
            timeBlockMap.put("earnCodeType", timeBlock.getEarnCodeType());

            // TODO: Cleanup the start / end time related properties. We certainly don't need all of them.
            // The ones which are used by the javascript are startDate, endDate, startTime, endTime, startTimeHourMinute, and endTimeHourMinute
            timeBlockMap.put("start", start.toString(ISODateTimeFormat.dateTimeNoMillis()));
            timeBlockMap.put("end", end.toString(ISODateTimeFormat.dateTimeNoMillis()));
            timeBlockMap.put("startDate", start.toString(TkConstants.DT_BASIC_DATE_FORMAT));
            timeBlockMap.put("endDate", end.toString(TkConstants.DT_BASIC_DATE_FORMAT));
            timeBlockMap.put("startNoTz", start.toString(ISODateTimeFormat.dateHourMinuteSecond()));
            timeBlockMap.put("endNoTz", end.toString(ISODateTimeFormat.dateHourMinuteSecond()));
            // start / endTimeHourMinute fields are for only for the display purpose
            timeBlockMap.put("startTimeHourMinute", start.toString(TkConstants.DT_BASIC_TIME_FORMAT));
            timeBlockMap.put("endTimeHourMinute", end.toString(TkConstants.DT_BASIC_TIME_FORMAT));
            // start / endTime are the actual fields used by the adding / editing timeblocks
            timeBlockMap.put("startTime", start.toString(TkConstants.DT_MILITARY_TIME_FORMAT));
            timeBlockMap.put("endTime", end.toString(TkConstants.DT_MILITARY_TIME_FORMAT));
            timeBlockMap.put("id", timeBlock.getTkTimeBlockId() == null ? null : timeBlock.getTkTimeBlockId().toString());
            timeBlockMap.put("hours", timeBlock.getHours());
            timeBlockMap.put("amount", timeBlock.getAmount());
            timeBlockMap.put("timezone", timezone);
            timeBlockMap.put("assignment", new AssignmentDescriptionKey(timeBlock.getJobNumber(), timeBlock.getWorkArea(), timeBlock.getTask()).toAssignmentKeyString());
            timeBlockMap.put("tkTimeBlockId", timeBlock.getTkTimeBlockId() != null ? timeBlock.getTkTimeBlockId() : "");
            timeBlockMap.put("lunchDeleted", timeBlock.isLunchDeleted());

            List<Map<String, Object>> timeHourDetailList = new LinkedList<Map<String, Object>>();
            for (TimeHourDetail timeHourDetail : timeBlock.getTimeHourDetails()) {
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
        }

//        Map<String, Map<String, Object>> jsonMappedList = new HashMap<String, Map<String, Object>>();
//        for (Map<String, Object> tbm : timeBlockList) {
//            String id = (String) tbm.get("id");
//            jsonMappedList.put(id, tbm);
//        }
        return JSONValue.toJSONString(timeBlockList);
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
        for (LeaveBlock leaveBlock : leaveBlocks) {
        	Map<String, Object> leaveBlockMap = new LinkedHashMap<String, Object>();
        	leaveBlockMap.put("title", leaveBlock.getAssignmentTitle());
        	leaveBlockMap.put("assignment", leaveBlock.getAssignmentKey());
        	leaveBlockMap.put("earnCode", leaveBlock.getEarnCode());
        	leaveBlockMap.put("lmLeaveBlockId", leaveBlock.getLmLeaveBlockId());
        	leaveBlockMap.put("leaveAmount", leaveBlock.getLeaveAmount().toString());
        	DateTime leaveDate = new DateTime(leaveBlock.getLeaveDate());
        	leaveBlockMap.put("leaveDate", leaveDate.toString(TkConstants.DT_BASIC_DATE_FORMAT));
        	leaveBlockMap.put("id", leaveBlock.getLmLeaveBlockId());
        	leaveBlockMap.put("canTransfer", TkServiceLocator.getPermissionsService().canTransferSSTOUsage(leaveBlock));
        	
        	
        	leaveBlockList.add(leaveBlockMap);
        }
    	return JSONValue.toJSONString(leaveBlockList);
    }
    
    public static Map<String, String> getPayPeriodsMap(List<CalendarEntries> payPeriods) {
    	// use linked map to keep the order of the pay periods
    	Map<String, String> pMap = Collections.synchronizedMap(new LinkedHashMap<String, String>());
    	if (payPeriods == null || payPeriods.isEmpty()) {
            return pMap;
        }
    	payPeriods.removeAll(Collections.singletonList(null));
    	Collections.sort(payPeriods);  // sort the pay period list by getBeginPeriodDate
    	Collections.reverse(payPeriods);  // newest on top
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        for (CalendarEntries pce : payPeriods) {
        	if(pce != null && pce.getHrCalendarEntriesId()!= null && pce.getBeginPeriodDate() != null && pce.getEndPeriodDate() != null) {
        		//pMap.put(pce.getHrCalendarEntriesId(), sdf.format(pce.getBeginPeriodDate()) + " - " + sdf.format(pce.getEndPeriodDate()));
                //getting one millisecond of the endperioddate to match the actual pay period. i.e. pay period end at the 11:59:59:59...PM of that day
                pMap.put(pce.getHrCalendarEntriesId(), sdf.format(pce.getBeginPeriodDate()) + " - " + sdf.format((DateUtils.addMilliseconds(pce.getEndPeriodDate(),-1))));
        	}
        }
        
    	return pMap;
    }
    
    // detect if the passed-in calendar entry is the current one
    public static boolean getOnCurrentPeriodFlag(CalendarEntries pce) {
    	Date currentDate = TKUtils.getTimelessDate(null);
    	String viewPrincipal = TKUser.getCurrentTargetPersonId();
        CalendarEntries calendarEntry = TkServiceLocator.getCalendarService().getCurrentCalendarDates(viewPrincipal,  currentDate);

        if(pce != null && calendarEntry != null && calendarEntry.equals(pce)) {
    		return true;
    	}
    	return false;
    }
     
    public static String getUnitOfTimeForEarnCode(EarnCode earnCode) {
//    	AccrualCategory acObj = null;
//    	if(earnCode.getAccrualCategory() != null) {
//    		acObj = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCode.getAccrualCategory(), TKUtils.getCurrentDate());
//    	}
//    	String unitTime = (acObj!= null ? acObj.getUnitOfTime() : earnCode.getRecordMethod()) ;
    	String unitTime = earnCode.getRecordMethod() ;
    	return unitTime;
    }
    
    public static int getPlanningMonthsForEmployee(String principalid) {
		int plannningMonths = 0;
		PrincipalHRAttributes principalHRAttributes = TkServiceLocator
				.getPrincipalHRAttributeService().getPrincipalCalendar(
						principalid, TKUtils.getCurrentDate());
		if (principalHRAttributes != null
				&& principalHRAttributes.getLeavePlan() != null) {
			LeavePlan lp = TkServiceLocator.getLeavePlanService()
					.getLeavePlan(principalHRAttributes.getLeavePlan(),
							TKUtils.getCurrentDate());
			if (lp != null && lp.getPlanningMonths() != null) {
				plannningMonths = Integer.parseInt(lp.getPlanningMonths());
			}
		}
		return plannningMonths;
    }
    
    public static List<CalendarEntries> getAllCalendarEntriesForYear(List<CalendarEntries> entries, String year) {
    	List<CalendarEntries> results = new ArrayList<CalendarEntries>();
    	DateFormat df = new SimpleDateFormat("yyyy");
    	 
    	for(CalendarEntries ce : entries) {
    		if(df.format(ce.getBeginPeriodDate()).equals(year)) {
    			results.add(ce);
    		}
    	}
    	return results;
    }
    
}

