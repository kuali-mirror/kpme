package org.kuali.hr.time.timeblock.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.kuali.hr.time.util.TkConstants;

public class TimeBlockServiceImpl implements TimeBlockService {

	private TimeBlockDao timeBlockDao;

	public void setTimeBlockDao(TimeBlockDao timeBlockDao) {
		this.timeBlockDao = timeBlockDao;
	}

	// TODO: need to figure out how to get the correct user timezone
	@Override
	public TimeBlock saveTimeBlock(TimesheetActionForm form) {

		Timestamp beginTimestamp;
		Timestamp endTimestamp;
		
		AssignmentDescriptionKey key = new AssignmentDescriptionKey(form.getSelectedAssignment());
		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(form.getTimesheetDocument(), form.getSelectedAssignment());
		
		if(form instanceof ClockActionForm) {
			ClockActionForm caf = (ClockActionForm) form;
			long beginTime = caf.getLastClockTimestamp().getTime();
			beginTimestamp = new Timestamp(beginTime);
			endTimestamp = caf.getClockLog().getClockTimestamp();
		}
		else {
			TimeDetailActionForm tdaf = (TimeDetailActionForm) form; 
			beginTimestamp = new Timestamp(tdaf.getStartTime());
			endTimestamp = new Timestamp(tdaf.getEndTime());
		}

    	BigDecimal hours = TKUtils.getHoursBetween(endTimestamp.getTime(), beginTimestamp.getTime());

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
    	tb.setEarnCode(TkConstants.EARN_CODE_RGH);
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

	public void deleteTimeBlock(TimeDetailActionForm tdaf) {
		List<TimeBlock> timeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
		for(TimeBlock tb : timeBlocks) {
			if(tb.getTkTimeBlockId().compareTo(tdaf.getTkTimeBlockId()) == 0) {
				timeBlockDao.deleteTimeBlock(tb);
				break;
			}
		}
	}

	public TimeBlock getTimeBlock(String timeBlockId) {
		return timeBlockDao.getTimeBlock(timeBlockId);
	}
	
	// TODO: need to figure out how to get the correct user timezone
	public List<TimeBlock> saveTimeBlockList(TimeDetailActionForm form) {
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		begin.setTimeInMillis(form.getStartTime());
		end.setTimeInMillis(form.getEndTime() );
		
		AssignmentDescriptionKey key = new AssignmentDescriptionKey(form.getSelectedAssignment());
		Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(form.getTimesheetDocument(), form.getSelectedAssignment());
		
		List<TimeBlock> timeBlockList = new LinkedList<TimeBlock>();

		long daysBetween = TKUtils.getDaysBetween(begin, end);
		for (int i = 0; i < daysBetween; i++) {

			Calendar b = (Calendar)begin.clone();
			Calendar e = (Calendar)end.clone();

			b.set(Calendar.DATE, begin.get(Calendar.DATE) + i);
			e.set(Calendar.DATE, begin.get(Calendar.DATE) + i);

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
	    	tb.setEarnCode(TkConstants.EARN_CODE_RGH);
	    	tb.setBeginTimestamp(new Timestamp(b.getTimeInMillis()));
//	    	tb.setBeginTimestampTimezone(clockLog.getClockTimestampTimezone());
	    	tb.setEndTimestamp(new Timestamp(e.getTimeInMillis()));
//	    	tb.setEndTimestampTimezone(clockLog.getClockTimestampTimezone());
	    	tb.setClockLogCreated(true);
	    	BigDecimal hours = TKUtils.getHoursBetween(e.getTime().getTime(), b.getTime().getTime());
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

			timeBlockList.add(timeBlockMap);
		}
		
		return timeBlockList;
	
	}

}
