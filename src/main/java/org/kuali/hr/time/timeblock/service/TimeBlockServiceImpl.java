package org.kuali.hr.time.timeblock.service;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.json.simple.JSONValue;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timeblock.dao.TimeBlockDao;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

public class TimeBlockServiceImpl implements TimeBlockService {

    private static final Logger LOG = Logger.getLogger(TimeBlockServiceImpl.class);
    private TimeBlockDao timeBlockDao;

    public void setTimeBlockDao(TimeBlockDao timeBlockDao) {
        this.timeBlockDao = timeBlockDao;
    }

    //This function is used to build timeblocks that span days
    public List<TimeBlock> buildTimeBlocksSpanDates(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument,
                                                    Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours, Boolean isClockLogCreated) {
        Calendar beginCal = GregorianCalendar.getInstance();
        beginCal.setTimeInMillis(beginTimestamp.getTime());
        Calendar beginCalCompare = GregorianCalendar.getInstance();
        beginCalCompare.setTimeInMillis(endTimestamp.getTime());
        beginCalCompare.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH), beginCal.get(Calendar.DATE));
        if (beginCalCompare.before(beginCal)) {
            beginCalCompare.add(Calendar.DATE, 1);
        }
        List<Interval> dayInt = TKUtils.getDaySpanForPayCalendarEntry(timesheetDocument.getPayCalendarEntry());
        TimeBlock firstTimeBlock = new TimeBlock();
        List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
        for (Interval dayIn : dayInt) {
            if (dayIn.contains(beginTimestamp.getTime())) {
                if (dayIn.contains(beginCalCompare.getTimeInMillis())) {
                    firstTimeBlock = createTimeBlock(timesheetDocument, beginTimestamp, new Timestamp(beginCalCompare.getTimeInMillis()),
                            assignment, earnCode, hours, false);
                    lstTimeBlocks.add(firstTimeBlock);
                } else {
                    //TODO move this to prerule validation
                    //throw validation error if this case met error
                }
            }
        }

        DateTime beginTime = new DateTime(beginCal.getTimeInMillis(), TkConstants.SYSTEM_DATE_TIME_ZONE);
        DateTime endTime = new DateTime(endTimestamp.getTime(), TkConstants.SYSTEM_DATE_TIME_ZONE);

        DateTime endOfFirstDay = new DateTime(firstTimeBlock.getEndTimestamp(), TkConstants.SYSTEM_DATE_TIME_ZONE);
        long diffInMillis = endOfFirstDay.minus(beginTime.getMillis()).getMillis();
        DateTime currTime = beginTime.plusDays(1);
        while (currTime.isBefore(endTime) || currTime.isEqual(endTime)) {
            Timestamp begin = new Timestamp(currTime.getMillis());
            Timestamp end = new Timestamp((currTime.plus(diffInMillis).getMillis()));
            TimeBlock tb = createTimeBlock(timesheetDocument, begin, end, assignment, earnCode, hours, false);
            currTime = currTime.plusDays(1);
            lstTimeBlocks.add(tb);
        }
        return lstTimeBlocks;
    }


    public List<TimeBlock> buildTimeBlocks(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument,
                                           Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours, Boolean isClockLogCreated) {

        //Create 1 or many timeblocks if the span of timeblocks exceed more than one
        //day that is determined by pay period day(24 hrs + period begin date)
        Interval firstDay = null;
        List<Interval> dayIntervals = TKUtils.getDaySpanForPayCalendarEntry(timesheetDocument.getPayCalendarEntry());
        List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
        for (Interval dayInt : dayIntervals) {
            //on second day of span so safe to assume doesnt go furthur than this
            if (firstDay != null) {
                TimeBlock tb = createTimeBlock(timesheetDocument, new Timestamp(dayInt.getStartMillis()), endTimestamp, assignment, earnCode, hours, isClockLogCreated);
                lstTimeBlocks.add(tb);
                break;
            }
            if (dayInt.contains(beginTimestamp.getTime())) {
                firstDay = dayInt;
                if (dayInt.contains(endTimestamp.getTime())) {
                    //create one timeblock if contained in one day interval
                    TimeBlock tb = createTimeBlock(timesheetDocument, beginTimestamp, endTimestamp, assignment, earnCode, hours, isClockLogCreated);
                    tb.setBeginTimestamp(beginTimestamp);
                    tb.setEndTimestamp(endTimestamp);
                    lstTimeBlocks.add(tb);
                    break;
                } else {
                    // create a timeblock that wraps the 24 hr day
                    TimeBlock tb = createTimeBlock(timesheetDocument, beginTimestamp, new Timestamp(dayInt.getEndMillis()), assignment, earnCode, hours, isClockLogCreated);
                    tb.setBeginTimestamp(beginTimestamp);
                    tb.setEndTimestamp(new Timestamp(firstDay.getEndMillis()));
                    lstTimeBlocks.add(tb);
                }
            }
        }
        return lstTimeBlocks;
    }

    public void saveTimeBlocks(List<TimeBlock> oldTimeBlocks, List<TimeBlock> newTimeBlocks) {
        List<TimeBlock> alteredTimeBlocks = new ArrayList<TimeBlock>();
        for (TimeBlock tb : newTimeBlocks) {
            boolean persist = true;
            for (TimeBlock tbOld : oldTimeBlocks) {
                if (tb.equals(tbOld)) {
                    persist = false;
                    break;
                }
            }
            if (persist) {
                alteredTimeBlocks.add(tb);
            }
        }
        for (TimeBlock tb : alteredTimeBlocks) {
            TkServiceLocator.getTimeHourDetailService().removeTimeHourDetails(tb.getTkTimeBlockId());
            timeBlockDao.saveOrUpdate(tb);
        }

    }


    public TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, Timestamp beginTime, Timestamp endTime, Assignment assignment, String earnCode, BigDecimal hours, Boolean clockLogCreated) {
        String tz = TkServiceLocator.getTimezoneService().getUserTimeZone();
        EarnCode earnCodeObj = TkServiceLocator.getEarnCodeService().getEarnCode(earnCode, timesheetDocument.getAsOfDate());

        TimeBlock tb = new TimeBlock();
        tb.setDocumentId(timesheetDocument.getDocumentHeader().getDocumentId());
        tb.setJobNumber(assignment.getJobNumber());
        tb.setWorkArea(assignment.getWorkArea());
        tb.setTask(assignment.getTask());
        tb.setTkWorkAreaId(assignment.getWorkAreaObj().getTkWorkAreaId());
        tb.setHrJobId(assignment.getJob().getHrJobId());
        Long tkTaskId = null;
        for (Task task : assignment.getWorkAreaObj().getTasks()) {
            if (task.getTask().compareTo(assignment.getTask()) == 0) {
                tkTaskId = task.getTkTaskId();
                break;
            }
        }
        tb.setTkTaskId(tkTaskId);
        tb.setEarnCode(earnCode);
        tb.setBeginTimestamp(beginTime);
        tb.setBeginTimestampTimezone(tz);
        tb.setEndTimestamp(endTime);
        tb.setEndTimestampTimezone(tz);
        // only calculate the hours from the time fields if the passed in hours is zero
        if(hours.compareTo(BigDecimal.ZERO) == 0) {
        	hours = TKUtils.getHoursBetween(beginTime.getTime(), endTime.getTime());
        }
        //If earn code has an inflate min hours check if it is greater than zero
        //and compare if the hours specified is less than min hours awarded for this
        //earn code
        if (earnCodeObj.getInflateMinHours() != null) {
            if ((earnCodeObj.getInflateMinHours().compareTo(BigDecimal.ZERO) != 0) &&
                    earnCodeObj.getInflateMinHours().compareTo(hours) > 0) {
                hours = earnCodeObj.getInflateMinHours();
            }
        }
        //If earn code has an inflate factor multiple hours specified by the factor
        if (earnCodeObj.getInflateFactor() != null) {
            if ((earnCodeObj.getInflateFactor().compareTo(new BigDecimal(1.0)) != 0)) {
                hours = earnCodeObj.getInflateFactor().multiply(hours, TkConstants.MATH_CONTEXT);
            }
        }

        tb.setHours(hours);
        tb.setClockLogCreated(clockLogCreated);
        tb.setUserPrincipalId(TKContext.getUser().getPrincipalId());
        tb.setTimestamp(new Timestamp(System.currentTimeMillis()));

        tb.setTimeHourDetails(this.createTimeHourDetails(tb.getEarnCode(), tb.getHours(), tb.getTkTimeBlockId()));
        tb.setTimeBlockHistories(this.createTimeBlockHistories(tb, TkConstants.ACTIONS.ADD_TIME_BLOCK));

        return tb;
    }

    public TimeBlock getTimeBlock(Long tkTimeBlockId) {
        return timeBlockDao.getTimeBlock(tkTimeBlockId);
    }

    public String getTimeBlocksForOutput(TimesheetDocument tsd) {
        List<TimeBlock> timeBlocks = new TkTimeBlockAggregate(tsd.getTimeBlocks(), tsd.getPayCalendarEntry()).getFlattenedTimeBlockList();
        return JSONValue.toJSONString(getTimeBlocksJson(timeBlocks));
    }

    private List<Map<String, Object>> getTimeBlocksJson(List<TimeBlock> timeBlocks) {

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

            DateTime start = timeBlock.getBeginTimeDisplay();
            DateTime end = timeBlock.getEndTimeDisplay();

            /**
             * This is the timeblock backward pushing logic.
             * the purpose of this is to accommodate the virtual day mode where the start/end period time is not from 12a to 12a.
             * A timeblock will be pushed back if the timeblock is still within the previous interval
             */
            if (timeBlock.isPushBackward()) {
                start.minusDays(1);
                end.minusDays(1);
            }

            timeBlockMap.put("title", workAreaDesc + " : " + timeBlock.getEarnCode());
            timeBlockMap.put("start", start.toString());
            timeBlockMap.put("end", end.toString());
            timeBlockMap.put("id", timeBlock.getTkTimeBlockId().toString());
            timeBlockMap.put("earnCode", timeBlock.getEarnCode());
            //TODO: need to cache this or pre-load it when the app boots up
            EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode(timeBlock.getEarnCode(), new java.sql.Date(timeBlock.getBeginTimestamp().getTime()));
            timeBlockMap.put("earnCodeType", earnCode == null ? TkConstants.EARN_CODE_TIME : earnCode.getEarnCodeType());
            timeBlockMap.put("hours", timeBlock.getHours());
            timeBlockMap.put("timezone", timezone);
            timeBlockMap.put("assignment", new AssignmentDescriptionKey(timeBlock.getJobNumber(), timeBlock.getWorkArea(), timeBlock.getTask()).toAssignmentKeyString());
            timeBlockMap.put("tkTimeBlockId", timeBlock.getTkTimeBlockId() != null ? timeBlock.getTkTimeBlockId() : "");

            List<Map<String, Object>> timeHourDetailList = new LinkedList<Map<String, Object>>();
            for (TimeHourDetail timeHourDetail : timeBlock.getTimeHourDetails()) {
                Map<String, Object> timeHourDetailMap = new LinkedHashMap<String, Object>();
                timeHourDetailMap.put("earnCode", timeHourDetail.getEarnCode());
                timeHourDetailMap.put("hours", timeHourDetail.getHours());
                timeHourDetailMap.put("amount", timeHourDetail.getAmount());

                timeHourDetailList.add(timeHourDetailMap);
            }
            timeBlockMap.put("timeHourDetails", JSONValue.toJSONString(timeHourDetailList));

            timeBlockList.add(timeBlockMap);
        }

        return timeBlockList;
    }

    @Override
    public void deleteTimeBlock(TimeBlock timeBlock) {
        timeBlockDao.deleteTimeBlock(timeBlock);

    }

    public void resetTimeHourDetail(List<TimeBlock> origTimeBlocks) {
        for (TimeBlock tb : origTimeBlocks) {
            tb.setTimeHourDetails(createTimeHourDetails(tb.getEarnCode(), tb.getHours(), tb.getTkTimeBlockId()));
        }
    }

    private List<TimeHourDetail> createTimeHourDetails(String earnCode, BigDecimal hours, Long timeBlockId) {
        List<TimeHourDetail> timeHourDetails = new ArrayList<TimeHourDetail>();

        TimeHourDetail timeHourDetail = new TimeHourDetail();
        timeHourDetail.setEarnCode(earnCode);
        timeHourDetail.setHours(hours);
        timeHourDetail.setTkTimeBlockId(timeBlockId);
        timeHourDetails.add(timeHourDetail);

        return timeHourDetails;
    }

    private List<TimeBlockHistory> createTimeBlockHistories(TimeBlock tb, String actionHistory) {
        List<TimeBlockHistory> tbhs = new ArrayList<TimeBlockHistory>();

        TimeBlockHistory tbh = new TimeBlockHistory(tb);
        tbh.setActionHistory(actionHistory);

        tbhs.add(tbh);

        return tbhs;
    }

    public List<TimeBlock> getTimeBlocks(Long documentId) {
        return timeBlockDao.getTimeBlocks(documentId);
    }

}