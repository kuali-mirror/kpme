package org.kuali.hr.time.timeblock.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeHourDetail;
import org.kuali.hr.time.timeblock.dao.TimeBlockDao;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;

public class TimeBlockServiceImpl implements TimeBlockService {
	
	private static final Logger LOG = Logger.getLogger(TimeBlockServiceImpl.class);
	private TimeBlockDao timeBlockDao;

	public void setTimeBlockDao(TimeBlockDao timeBlockDao) {
		this.timeBlockDao = timeBlockDao;
	}

	//This function is used to build timeblocks that span days
	public List<TimeBlock> buildTimeBlocksSpanDates(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument, 
														Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours){
		Calendar beginCal = GregorianCalendar.getInstance();
		beginCal.setTimeInMillis(beginTimestamp.getTime());
		Calendar beginCalCompare = GregorianCalendar.getInstance();
		beginCalCompare.setTimeInMillis(endTimestamp.getTime());
		beginCalCompare.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH), beginCal.get(Calendar.DATE));
		if(beginCalCompare.before(beginCal)){
			beginCalCompare.add(Calendar.DATE, 1);
		}
		List<Interval> dayInt = TKUtils.getDaySpanForPayCalendarEntry(timesheetDocument.getPayCalendarEntry());
		TimeBlock firstTimeBlock = new TimeBlock();
		List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
		for(Interval dayIn : dayInt){
			if(dayIn.contains(beginTimestamp.getTime())){
				if(dayIn.contains(beginCalCompare.getTimeInMillis())){
					firstTimeBlock = createTimeBlock(timesheetDocument, beginTimestamp, new Timestamp(beginCalCompare.getTimeInMillis()), 
										assignment, earnCode,hours);
					lstTimeBlocks.add(firstTimeBlock);
				} else {
					//TODO move this to prerule validation
					//throw validation error if this case met error
				}
			}
		}
		
		DateTime beginTime = new DateTime(beginCal.getTimeInMillis());
		DateTime endTime = new DateTime(endTimestamp.getTime());
		
		DateTime endOfFirstDay = new DateTime(firstTimeBlock.getEndTimestamp());
		long diffInMillis = endOfFirstDay.minus(beginTime.getMillis()).getMillis();
		DateTime currTime = beginTime.plusDays(1);
		while(currTime.isBefore(endTime) || currTime.isEqual(endTime)){
			Timestamp begin = new Timestamp(currTime.getMillis());
			Timestamp end = new Timestamp((currTime.plus(diffInMillis).getMillis()));
			TimeBlock tb = createTimeBlock(timesheetDocument, begin, end, assignment, earnCode,hours);
			currTime = currTime.plusDays(1);
			lstTimeBlocks.add(tb);
		}
		return lstTimeBlocks;
	}
	
	
	public List<TimeBlock> buildTimeBlocks(Assignment assignment, String earnCode, TimesheetDocument timesheetDocument, 
						Timestamp beginTimestamp, Timestamp endTimestamp, BigDecimal hours){
		//Create 1 or many timeblocks if the span of timeblocks exceed more than one 
		//day that is determined by pay period day(24 hrs + period begin date)
		Interval firstDay = null;
		List<Interval> dayIntervals = TKUtils.getDaySpanForPayCalendarEntry(timesheetDocument.getPayCalendarEntry());
		List<TimeBlock> lstTimeBlocks = new ArrayList<TimeBlock>();
		for(Interval dayInt : dayIntervals){
			//on second day of span so safe to assume doesnt go furthur than this
			if(firstDay != null){
				TimeBlock tb = createTimeBlock(timesheetDocument, new Timestamp(dayInt.getStartMillis()), endTimestamp, assignment, earnCode,hours);
				lstTimeBlocks.add(tb);
				break;
			}
			if(dayInt.contains(beginTimestamp.getTime()) ){
				firstDay = dayInt;
				if(dayInt.contains(endTimestamp.getTime())){
					//create one timeblock if contained in one day interval
					TimeBlock tb = createTimeBlock(timesheetDocument, beginTimestamp, endTimestamp, assignment, earnCode,hours);
					tb.setBeginTimestamp(beginTimestamp);
					tb.setEndTimestamp(endTimestamp);
					lstTimeBlocks.add(tb);
					break;
				} else {
					//create a timeblock that wraps the 24 hr day
					TimeBlock tb = createTimeBlock(timesheetDocument, beginTimestamp, endTimestamp, assignment, earnCode,hours);
					tb.setBeginTimestamp(beginTimestamp);
					tb.setEndTimestamp(new Timestamp(firstDay.getEndMillis()));
					lstTimeBlocks.add(tb);
				}
			}
		}
		return lstTimeBlocks;
	}
	
	public void saveTimeBlocks(List<TimeBlock> oldTimeBlocks, List<TimeBlock> newTimeBlocks){
		List<TimeBlock> alteredTimeBlocks = new ArrayList<TimeBlock>();
		boolean persist = true;
		for(TimeBlock tb : newTimeBlocks){
			for(TimeBlock tbOld : oldTimeBlocks){
				if(tb.equals(tbOld)){
					persist = false;
					break;
				}
			}
			if(persist){
				alteredTimeBlocks.add(tb);
			}
		}
		for(TimeBlock tb : alteredTimeBlocks){
			timeBlockDao.saveOrUpdate(tb);
		}
		
	}

	
	public TimeBlock createTimeBlock(TimesheetDocument timesheetDocument, Timestamp beginTime, Timestamp endTime, Assignment assignment, String earnCode, BigDecimal hours){
		TimeBlock tb = new TimeBlock();
    	tb.setDocumentId(timesheetDocument.getDocumentHeader().getDocumentId().toString());
    	tb.setJobNumber(assignment.getJobNumber());
    	tb.setWorkArea(assignment.getWorkArea());
    	tb.setTask(assignment.getTask());
    	tb.setTkWorkAreaId(assignment.getWorkAreaObj().getTkWorkAreaId());
    	tb.setHrJobId(assignment.getJob().getHrJobId());
	    Long tkTaskId = null;
	    for(Task task : assignment.getWorkAreaObj().getTasks()) {
	    	if(task.getTask().compareTo(assignment.getTask()) == 0 ) {
	    		tkTaskId = task.getTkTaskId();
	    		break;
	    	}
	    }
    	tb.setTkTaskId(tkTaskId);
    	tb.setEarnCode(earnCode);
    	tb.setBeginTimestamp(beginTime);
    	//TODO add timezeon things
//    	tb.setBeginTimestampTimezone(clockLog.getClockTimestampTimezone());
    	tb.setEndTimestamp(endTime);
//    	tb.setEndTimestampTimezone(clockLog.getClockTimestampTimezone());
    	tb.setClockLogCreated(true);
    	tb.setHours(TKUtils.getHoursBetween(beginTime.getTime(), endTime.getTime()));
    	if(tb.getHours().compareTo(BigDecimal.ZERO) == 0){
    		tb.setHours(hours);
    	}
    	tb.setUserPrincipalId(TKContext.getUser().getPrincipalId());
    	tb.setTimestamp(new Timestamp(System.currentTimeMillis()));
    	
    	return tb;
	}

	public TimeBlock getTimeBlock(String timeBlockId) {
		return timeBlockDao.getTimeBlock(timeBlockId);
	}
	
	public List<Map<String,Object>> getTimeBlocksForOurput(TimesheetDocument tsd) {
		
		List<TimeBlock> timeBlocks = tsd.getTimeBlocks();
		
		if(timeBlocks == null || timeBlocks.size() == 0) {
			return new ArrayList<Map<String,Object>>();
		}
		
		List<Map<String,Object>> timeBlockList = new LinkedList<Map<String,Object>>();

		for(TimeBlock timeBlock : timeBlocks) {
			Map<String,Object> timeBlockMap = new LinkedHashMap<String, Object>();
		
			String assignmentKey = TKUtils.formatAssignmentKey(timeBlock.getJobNumber(), timeBlock.getWorkArea(), timeBlock.getTask());
			String workAreaDesc = TkServiceLocator.getAssignmentService().getAssignment(tsd, assignmentKey).getWorkAreaObj().getDescription();
			
			timeBlockMap.put("title", workAreaDesc + " : " + timeBlock.getEarnCode());
			timeBlockMap.put("start", new java.util.Date(timeBlock.getBeginTimestamp().getTime()).toString());
			timeBlockMap.put("end", new java.util.Date(timeBlock.getEndTimestamp().getTime()).toString());
			timeBlockMap.put("id", timeBlock.getTkTimeBlockId().toString());
			timeBlockMap.put("earnCode", timeBlock.getEarnCode());
			timeBlockMap.put("hours", timeBlock.getHours());
			
			Calendar beginTimeCal = Calendar.getInstance();
			beginTimeCal.setTimeInMillis(timeBlock.getBeginTimestamp().getTime());
			Calendar endTimeCal = Calendar.getInstance();
			endTimeCal.setTimeInMillis(timeBlock.getEndTimestamp().getTime());

			// check if the pay period is the standard one
			String isStandardPayPeriod = endTimeCal.get(Calendar.HOUR_OF_DAY) == 0 ? "true" : "false";
			timeBlockMap.put("isStandardPayPeriod", isStandardPayPeriod);
			
			// if it is NOT a standard pay period, do the virtual day logic
			if(StringUtils.equals(isStandardPayPeriod, "false")) {
				
//				java.util.Date beginPeriodDateTime = tsd.getPayCalendarEntry().getBeginPeriodDateTime();
//				Calendar beginPayPeriodCal = Calendar.getInstance();
//				beginPayPeriodCal.setTime(beginPeriodDateTime);
				java.util.Date endPeriodDateTime = tsd.getPayCalendarEntry().getEndPeriodDateTime();
				Calendar endPayPeriodCal = Calendar.getInstance();
				endPayPeriodCal.setTime(endPeriodDateTime);
				
				// need to plus one for the pay period end hour, since 11:59:59 should be considered as 12
				timeBlockMap.put("isWithinVirtualDay", endTimeCal.get(Calendar.HOUR_OF_DAY) <= endPayPeriodCal.get(Calendar.HOUR_OF_DAY)+1 ? "true" : "false");
//				timeBlockMap.put("isPushTimeBlockNeeded", beginTimeCal.get(Calendar.HOUR_OF_DAY) == beginPayPeriodCal.get(Calendar.HOUR_OF_DAY) ? "true" : "false");
			}

			timeBlockList.add(timeBlockMap);
		}
		
		return timeBlockList;
	
	}

	@Override
	public void deleteTimeBlock(TimeBlock timeBlock) {
		timeBlockDao.deleteTimeBlock(timeBlock);
		
	}
	
	public List<TimeBlock> resetTimeHourDetail(List<TimeBlock> origTimeBlocks){
		for(TimeBlock tb : origTimeBlocks){
			List<TimeHourDetail> timeHourDetails = new ArrayList<TimeHourDetail>();
			TimeHourDetail timeHourDetail = new TimeHourDetail();
			timeHourDetail.setEarnCode(tb.getEarnCode());
			timeHourDetail.setHours(tb.getHours());
			timeHourDetail.setTkTimeBlockId(tb.getTkTimeBlockId());
			timeHourDetails.add(timeHourDetail);
			tb.setTimeHourDetails(timeHourDetails);
		}
		
		return origTimeBlocks;
	}
	
	public List<TimeBlock> getTimeBlocks(Long documentId){
		return timeBlockDao.getTimeBlocks(documentId);
	}

}
