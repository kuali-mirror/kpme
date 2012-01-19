package org.kuali.hr.time.detail.web;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.simple.JSONValue;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;

import java.util.*;

public class ActionFormUtils {

    public static void validateHourLimit(TimeDetailActionFormBase tdaf) throws Exception {
        List<String> warningMessages = TkServiceLocator.getTimeOffAccrualService().validateAccrualHoursLimit(tdaf.getTimesheetDocument());
        addUniqueWarningsToForm(tdaf, warningMessages);
    }

    public static void addWarningTextFromEarnGroup(TimeDetailActionFormBase tdaf) throws Exception {
        List<String> warningMessages = TkServiceLocator.getEarnGroupService().warningTextFromEarnGroupsOfDocument(tdaf.getTimesheetDocument());
        addUniqueWarningsToForm(tdaf, warningMessages);
    }

    public static void addUniqueWarningsToForm(TimeDetailActionFormBase tdaf, List<String> warningMessages) {
        if (!warningMessages.isEmpty()) {
            Set<String> aSet = new HashSet<String>();
            aSet.addAll(warningMessages);
            aSet.addAll(tdaf.getWarnings());
            List<String> aList = new ArrayList<String>();
            aList.addAll(aSet);
            tdaf.setWarnings(aList);
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

            // KPME-1216
            Boolean isAnyApprover = TKContext.getUser().getCurrentRoles().isAnyApproverActive();
            if (StringUtils.equals(workArea.getOvertimeEditRole(), TkConstants.ROLE_TK_APPROVER)) {
                timeBlockMap.put("overtimeEditable", isAnyApprover);
            }
   
            timeBlockMap.put("editable", TkServiceLocator.getTimeBlockService().isTimeBlockEditable(timeBlock).toString());
            timeBlockMap.put("synchronous", timeBlock.getClockLogCreated());
            
            timeBlockMap.put("editTBOvt", TkServiceLocator.getPermissionsService().canEditOvertimeEarnCode(timeBlock));
            timeBlockMap.put("addTB", TkServiceLocator.getPermissionsService().canAddTimeBlock());
            
            if (TkServiceLocator.getPermissionsService().canEditTimeBlock(timeBlock)) {
	            if (TkServiceLocator.getPermissionsService().canEditTimeBlockAllFields(timeBlock)) {
	            	  timeBlockMap.put("editTBAll", true);
	            	  timeBlockMap.put("editTBAssgOnly", false);
	            } else {
	            	  timeBlockMap.put("editTBAll", false);
	            	  timeBlockMap.put("editTBAssgOnly", true);
	            }
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
  
            timeBlockMap.put("start", start.toString(ISODateTimeFormat.dateTimeNoMillis()));
            timeBlockMap.put("end", end.toString(ISODateTimeFormat.dateTimeNoMillis()));
            timeBlockMap.put("startDate", start.toString(TkConstants.DT_BASIC_DATE_FORMAT));
            timeBlockMap.put("endDate", end.toString(TkConstants.DT_BASIC_DATE_FORMAT));
            timeBlockMap.put("startNoTz", start.toString(ISODateTimeFormat.dateHourMinuteSecond()));
            timeBlockMap.put("endNoTz", end.toString(ISODateTimeFormat.dateHourMinuteSecond()));
            timeBlockMap.put("startTimeHourMinute", start.toString(TkConstants.DT_MILITARY_TIME_FORMAT));
            timeBlockMap.put("endTimeHourMinute", end.toString(TkConstants.DT_MILITARY_TIME_FORMAT));
            timeBlockMap.put("startTime", start.toString(TkConstants.DT_BASIC_TIME_FORMAT));
            timeBlockMap.put("endTime", end.toString(TkConstants.DT_BASIC_TIME_FORMAT));
            timeBlockMap.put("id", timeBlock.getTkTimeBlockId().toString());
            timeBlockMap.put("hours", timeBlock.getHours());
            timeBlockMap.put("amount", timeBlock.getAmount());
            timeBlockMap.put("timezone", timezone);
            timeBlockMap.put("assignment", new AssignmentDescriptionKey(timeBlock.getJobNumber(), timeBlock.getWorkArea(), timeBlock.getTask()).toAssignmentKeyString());
            timeBlockMap.put("tkTimeBlockId", timeBlock.getTkTimeBlockId() != null ? timeBlock.getTkTimeBlockId() : "");

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

}
