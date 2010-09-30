package org.kuali.hr.time.timeblock.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.clock.web.ClockActionForm;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.detail.web.TimeDetailActionForm;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.dao.TimeBlockDao;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class TimeBlockServiceImpl implements TimeBlockService {

	private TimeBlockDao timeBlockDao;

	public void setTimeBlockDao(TimeBlockDao timeBlockDao) {
		this.timeBlockDao = timeBlockDao;
	}

	public void saveTimeBlock(TimeBlock timeBlock) {
		timeBlockDao.saveOrUpdate(timeBlock);
	}
	
	@Override
	public TimeBlock saveTimeBlock(ClockActionForm form) {

		ClockLog clockLog = form.getClockLog();
		AssignmentDescriptionKey key = new AssignmentDescriptionKey(form.getSelectedAssignment());
		
    	long beginTime = form.getLastClockTimestamp().getTime();
    	Timestamp beginTimestamp = new Timestamp(beginTime);
    	Timestamp endTimestamp = clockLog.getClockTimestamp();

    	BigDecimal hours = TKUtils.getHoursBetween(endTimestamp.getTime(), beginTimestamp.getTime());

    	TimeBlock tb = new TimeBlock();
    	tb.setDocumentId(form.getTimesheetDocument().getDocumentHeader().getDocumentId().toString());
    	tb.setJobNumber(key.getJobNumber());
    	tb.setWorkArea(key.getWorkArea());
    	tb.setTask(key.getTask());
    	tb.setTkWorkAreaId(clockLog.getTkWorkAreaId());
    	tb.setHrJobId(clockLog.getHrJobId());
    	tb.setTkTaskId(clockLog.getTkTaskId());
    	tb.setEarnCode(TkConstants.EARN_CODE_RGH);
    	tb.setBeginTimestamp(beginTimestamp);
    	tb.setBeginTimestampTimezone(clockLog.getClockTimestampTimezone());
    	tb.setEndTimestamp(endTimestamp);
    	tb.setEndTimestampTimezone(clockLog.getClockTimestampTimezone());
    	tb.setClockLogCreated(true);
    	tb.setHours(hours);
    	tb.setUserPrincipalId(TKContext.getUser().getPrincipalId());
    	tb.setTimestamp(new Timestamp(System.currentTimeMillis()));

    	saveTimeBlock(tb);
		
		return tb;
	}

	public List<TimeBlock> getTimeBlocksByPeriod(String principalId, Date beginDate, Date endDate) {
		return timeBlockDao.getTimeBlocksByPeriod(principalId, beginDate, endDate);
	}

	public void deleteTimeBlock(TimeBlock timeBlock) {
		timeBlockDao.deleteTimeBlock(timeBlock);

	}

	public TimeBlock getTimeBlock(String timeBlockId) {
		return timeBlockDao.getTimeBlock(timeBlockId);
	}

	public void saveTimeBlockList(List<TimeBlock> timeBlockList) {
		timeBlockDao.saveOrUpdate(timeBlockList);
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
//			timeBlockMap.put("start", timeBlock.getBeginTimestamp().getTime());
//			timeBlockMap.put("end", timeBlock.getEndTimestamp().getTime());
			timeBlockMap.put("id", timeBlock.getTkTimeBlockId().toString());

			timeBlockList.add(timeBlockMap);
		}
		
		return timeBlockList;
	
	}

}
