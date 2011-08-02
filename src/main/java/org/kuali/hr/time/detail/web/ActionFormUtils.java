package org.kuali.hr.time.detail.web;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timesheet.TimesheetDocument;

import java.util.*;

public class ActionFormUtils {

    public static void validateHourLimit(TimeDetailActionFormBase tdaf) throws Exception {
    	tdaf.setWarningJason("");
        JSONArray warnMsgJson = TkServiceLocator.getTimeOffAccrualService().validateAccrualHoursLimit(tdaf.getTimesheetDocument());
        if (!warnMsgJson.isEmpty()) {
        	tdaf.setWarningJason(JSONValue.toJSONString(warnMsgJson));
        }
    }

    public static String getTimeBlockJSONMap(TimesheetDocument tsd, List<TimeBlock> blocks) {
        List<Map<String, Object>> jsonList = getTimeBlocksJson(blocks, null);
        Map<String, Map<String, Object>> jsonMappedList = new HashMap<String, Map<String, Object>>();
        for (Map<String,Object> tbm : jsonList) {
            String id = (String)tbm.get("id");
            jsonMappedList.put(id, tbm);
        }
        return JSONValue.toJSONString(jsonMappedList);
    }

    public static String getTimeBlocksForOutput(TimesheetDocument tsd, List<TimeBlock> timeBlocks) {
        return JSONValue.toJSONString(getTimeBlocksJson(timeBlocks, buildAssignmentStyleClassMap(tsd)));
    }

    public static Map<String, String> buildAssignmentStyleClassMap(TimesheetDocument tsd) {
          Map<String, String> aMap = new HashMap<String, String>();
          List<String> assignmentKeys = new ArrayList<String>();
          List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(tsd.getPrincipalId(), tsd.getAsOfDate());

          for(Assignment assignment: assignments) {
              AssignmentDescriptionKey aKey = new AssignmentDescriptionKey(assignment.getJobNumber(),
                      assignment.getWorkArea(), assignment.getTask());
              assignmentKeys.add(aKey.toAssignmentKeyString());
          }
          Collections.sort(assignmentKeys);

          for(int i = 0; i< assignmentKeys.size(); i++) {
              aMap.put(assignmentKeys.get(i), "assignment"+ Integer.toString(i));
          }

          return aMap;
    }

    private static List<Map<String, Object>> getTimeBlocksJson(List<TimeBlock> timeBlocks, Map<String, String> assignmentStyleClassMap) {

        if (timeBlocks == null || timeBlocks.size() == 0) {
            return new ArrayList<Map<String, Object>>();
        }

        List<Map<String, Object>> timeBlockList = new LinkedList<Map<String, Object>>();
        String timezone = TkServiceLocator.getTimezoneService().getUserTimeZone();
        timeBlocks = TkServiceLocator.getTimezoneService().translateForTimezone(timeBlocks, timezone);

        for (TimeBlock timeBlock : timeBlocks) {
            Map<String, Object> timeBlockMap = new LinkedHashMap<String, Object>();

            //String assignmentKey = TKUtils.formatAssignmentKey(timeBlock.getJobNumber(), timeBlock.getWorkArea(), timeBlock.getTask());
            String workAreaDesc = TkServiceLocator.getWorkAreaService().getWorkArea(timeBlock.getWorkArea(), new java.sql.Date(timeBlock.getEndTimestamp().getTime())).getDescription();

            String cssClass = "";
            if(assignmentStyleClassMap != null && assignmentStyleClassMap.containsKey(timeBlock.getAssignmentKey())) {
            	cssClass = assignmentStyleClassMap.get(timeBlock.getAssignmentKey());
            }
            timeBlockMap.put("assignmentCss", cssClass);
            timeBlockMap.put("editable",  TkServiceLocator.getTimeBlockService().isTimeBlockEditable(timeBlock.getUserPrincipalId()));

            // DateTime object in jodatime is immutable. If manipulation of a datetime obj is necessary, use MutableDateTime instead.
            // ^^ to whoever wrote this: The point of immutable objects is to ensure consistency, if you're going to alter the object
            //    and you start with an immutable, just create another immutable. Similar to how you would use a BigDecimal. We are not
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

            timeBlockMap.put("title", workAreaDesc);
            timeBlockMap.put("earnCode", timeBlock.getEarnCode());
            //TODO: need to cache this or pre-load it when the app boots up
            // EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode(timeBlock.getEarnCode(), new java.sql.Date(timeBlock.getBeginTimestamp().getTime()));
            timeBlockMap.put("earnCodeType", timeBlock.getEarnCodeType());

            timeBlockMap.put("start", start.toDateTime().toString(ISODateTimeFormat.dateTimeNoMillis()));
            timeBlockMap.put("end", end.toDateTime().toString(ISODateTimeFormat.dateTimeNoMillis()));
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
                if(StringUtils.equals(timeHourDetail.getEarnCode(), "LUN")) {
                    timeBlockMap.put("lunchDeduction", true);
                }

                timeHourDetailList.add(timeHourDetailMap);
            }
            timeBlockMap.put("timeHourDetails", JSONValue.toJSONString(timeHourDetailList));

            timeBlockList.add(timeBlockMap);
        }

        return timeBlockList;
    }

}
