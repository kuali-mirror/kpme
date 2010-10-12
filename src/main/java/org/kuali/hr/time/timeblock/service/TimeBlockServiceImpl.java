package org.kuali.hr.time.timeblock.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.clock.web.ClockActionForm;
import org.kuali.hr.time.detail.web.TimeDetailActionForm;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.dao.TimeBlockDao;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;

public class TimeBlockServiceImpl implements TimeBlockService {
	
	private static final Logger LOG = Logger.getLogger(TimeBlockServiceImpl.class);
	private TimeBlockDao timeBlockDao;

	public void setTimeBlockDao(TimeBlockDao timeBlockDao) {
		this.timeBlockDao = timeBlockDao;
	}

	// TODO: need to figure out how to get the correct user's timezone
	@Override
	public TimeBlock saveTimeBlock(TimesheetActionForm form) {

		Timestamp beginTimestamp = null;
		Timestamp endTimestamp = null;
		BigDecimal hours = new BigDecimal("0.0");
		
		AssignmentDescriptionKey key = new AssignmentDescriptionKey(form.getSelectedAssignment());
		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(form.getTimesheetDocument(), form.getSelectedAssignment());
		
		if(form instanceof ClockActionForm) {
			ClockActionForm caf = (ClockActionForm) form;
			long beginTime = caf.getLastClockTimestamp().getTime();
			beginTimestamp = new Timestamp(beginTime);
			endTimestamp = caf.getClockLog().getClockTimestamp();
			hours = TKUtils.getHoursBetween(endTimestamp.getTime(), beginTimestamp.getTime());
		}
		else {
			TimeDetailActionForm tdaf = (TimeDetailActionForm) form; 
			
			// TODO: need to figure out what begin time and end time should be used
			// for earn codes like VAC
			if(tdaf.getHours() != null) {
				Calendar start = Calendar.getInstance();
				Calendar end = Calendar.getInstance();
				beginTimestamp = new Timestamp(tdaf.getStartTime());
				endTimestamp = new Timestamp(tdaf.getEndTime());
				start.setTimeInMillis(beginTimestamp.getTime());
				end.setTimeInMillis(endTimestamp.getTime());
				hours = tdaf.getHours();
			}
			else {
				beginTimestamp = new Timestamp(tdaf.getStartTime());
				endTimestamp = new Timestamp(tdaf.getEndTime());
  				hours = TKUtils.getHoursBetween(endTimestamp.getTime(), beginTimestamp.getTime());
			}
		}

    	TimeBlock tb = new TimeBlock();
    	tb.setDocumentId(form.getTimesheetDocument().getDocumentHeader().getDocumentId().toString());
    	tb.setJobNumber(key.getJobNumber());
    	tb.setWorkArea(key.getWorkArea());
    	tb.setTask(key.getTask());
    	tb.setTkWorkAreaId(assignment.getWorkAreaObj().getTkWorkAreaId());
    	tb.setHrJobId(assignment.getJob().getHrJobId());
	    Long tkTaskId = null;
	    for(Task task : assignment.getWorkAreaObj().getTasks()) {
	    	if(task.getTask().compareTo(key.getTask()) == 0 ) {
	    		tkTaskId = task.getTkTaskId();
	    		break;
	    	}
	    }
    	tb.setTkTaskId(tkTaskId);
    	tb.setEarnCode(form.getSelectedEarnCode());
    	tb.setBeginTimestamp(beginTimestamp);
//    	tb.setBeginTimestampTimezone(clockLog.getClockTimestampTimezone());
    	tb.setEndTimestamp(endTimestamp);
//    	tb.setEndTimestampTimezone(clockLog.getClockTimestampTimezone());
    	tb.setClockLogCreated(true);
    	tb.setHours(hours);
    	tb.setUserPrincipalId(TKContext.getUser().getPrincipalId());
    	tb.setTimestamp(new Timestamp(System.currentTimeMillis()));

    	timeBlockDao.saveOrUpdate(tb);
		
		return tb;
	}

	public List<TimeBlock> getTimeBlocksByPeriod(String principalId, Date beginDate, Date endDate) {
		return timeBlockDao.getTimeBlocksByPeriod(principalId, beginDate, endDate);
	}

	public TimeBlock deleteTimeBlock(TimeDetailActionForm tdaf) {
		List<TimeBlock> timeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
		for(TimeBlock tb : timeBlocks) {
			if(tb.getTkTimeBlockId().compareTo(tdaf.getTkTimeBlockId()) == 0) {
				timeBlockDao.deleteTimeBlock(tb);
				return tb;
			}
		}
		
		LOG.error("no matched time block to delete");
		return new TimeBlock();
	}

	public TimeBlock getTimeBlock(String timeBlockId) {
		return timeBlockDao.getTimeBlock(timeBlockId);
	}
	
	// TODO: need to figure out how to get the correct user timezone
	public List<TimeBlock> saveTimeBlockList(TimeDetailActionForm form) {
		Timestamp beginTimestamp = null;
		Timestamp endTimestamp = null;
		BigDecimal hours = new BigDecimal("0.0");
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		if(form.getHours() != null) {
			beginTimestamp = new Timestamp(form.getStartTime());
			endTimestamp = new Timestamp(form.getEndTime());
			start.setTimeInMillis(beginTimestamp.getTime());
			end.setTimeInMillis(endTimestamp.getTime());
			hours = form.getHours();
		}
		else {
			beginTimestamp = new Timestamp(form.getStartTime());
			endTimestamp = new Timestamp(form.getEndTime());
			hours = TKUtils.getHoursBetween(endTimestamp.getTime(), beginTimestamp.getTime());
		}
		
		AssignmentDescriptionKey key = new AssignmentDescriptionKey(form.getSelectedAssignment());
		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(form.getTimesheetDocument(), form.getSelectedAssignment());
		
		List<TimeBlock> timeBlockList = new LinkedList<TimeBlock>();

		long daysBetween = TKUtils.getDaysBetween(start, end);
		for (int i = 0; i <= daysBetween; i++) {

			Calendar b = (Calendar)start.clone();
			Calendar e = (Calendar)end.clone();

			b.set(Calendar.DATE, start.get(Calendar.DATE) + i);
			e.set(Calendar.DATE, start.get(Calendar.DATE) + i);

	       	TimeBlock tb = new TimeBlock();
	    	tb.setDocumentId(form.getTimesheetDocument().getDocumentHeader().getDocumentId().toString());
	    	tb.setJobNumber(key.getJobNumber());
	    	tb.setWorkArea(key.getWorkArea());
	    	tb.setTask(key.getTask());
	    	tb.setTkWorkAreaId(assignment.getWorkAreaObj().getTkWorkAreaId());
	    	tb.setHrJobId(assignment.getJob().getHrJobId());
		    Long tkTaskId = null;
		    for(Task task : assignment.getWorkAreaObj().getTasks()) {
		    	if(task.getTask().compareTo(key.getTask()) == 0 ) {
		    		tkTaskId = task.getTkTaskId();
		    		break;
		    	}
		    }
	    	tb.setTkTaskId(tkTaskId);
	    	tb.setEarnCode(form.getSelectedEarnCode());
	    	tb.setBeginTimestamp(new Timestamp(b.getTimeInMillis()));
//	    	tb.setBeginTimestampTimezone(clockLog.getClockTimestampTimezone());
	    	tb.setEndTimestamp(new Timestamp(e.getTimeInMillis()));
//	    	tb.setEndTimestampTimezone(clockLog.getClockTimestampTimezone());
	    	tb.setClockLogCreated(true);
	    	tb.setHours(hours);
	    	tb.setUserPrincipalId(TKContext.getUser().getPrincipalId());
	    	tb.setTimestamp(new Timestamp(System.currentTimeMillis()));

	    	timeBlockList.add(tb);
		}
		timeBlockDao.saveOrUpdate(timeBlockList);
		
		return timeBlockList;
	}
	
	@Override
	public List<Map<String,Object>> getTimeBlocksForOurput(TimeDetailActionForm form) {
		List<TimeBlock> timeBlocks = form.getTimesheetDocument().getTimeBlocks();

		List<Map<String,Object>> timeBlockList = new LinkedList<Map<String,Object>>();

		for(TimeBlock timeBlock : timeBlocks) {
			Map<String,Object> timeBlockMap = new LinkedHashMap<String, Object>();

			timeBlockMap.put("title", "HRMS Java Team : " + timeBlock.getEarnCode());
			timeBlockMap.put("start", new java.util.Date(timeBlock.getBeginTimestamp().getTime()).toString());
			timeBlockMap.put("end", new java.util.Date(timeBlock.getEndTimestamp().getTime()).toString());
			timeBlockMap.put("id", timeBlock.getTkTimeBlockId().toString());
			timeBlockMap.put("hours", timeBlock.getHours());

			timeBlockList.add(timeBlockMap);
		}
		
		return timeBlockList;
	
	}

	@Override
	public void deleteTimeBlock(TimeBlock timeBlock) {
		timeBlockDao.deleteTimeBlock(timeBlock);
		
	}

	@Override
	public List<TimeBlock> getTimeBlocksByPeriod(String principalId,
			java.util.Date beginDate, java.util.Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	private TimeBlock buildTimeBlock()

}
