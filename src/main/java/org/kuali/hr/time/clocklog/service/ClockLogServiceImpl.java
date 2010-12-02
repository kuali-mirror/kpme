package org.kuali.hr.time.clocklog.service;

import java.sql.Timestamp;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.clocklog.dao.ClockLogDao;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;

public class ClockLogServiceImpl implements ClockLogService {

	private ClockLogDao clockLogDao;

	public ClockLogServiceImpl() {
	}

	public void saveClockLog(ClockLog clockLog) {
		clockLogDao.saveOrUpdate(clockLog);
	}
	
	@Override
	public ClockLog buildClockLog(Timestamp clockTimestamp, String selectedAssign, TimesheetDocument timesheetDocument, String clockAction, String ip) {
		String principalId = TKContext.getUser().getPrincipalId();
		
	    ClockLog clockLog = new ClockLog();
	    clockLog.setPrincipalId(principalId);
	    AssignmentDescriptionKey assignmentDesc = TkServiceLocator.getAssignmentService().getAssignmentDescriptionKey(selectedAssign);
	    Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(timesheetDocument, selectedAssign);
	    clockLog.setJob(timesheetDocument.getJob(assignment.getJobNumber()));
	    clockLog.setJobNumber(assignment.getJobNumber());
	    clockLog.setWorkArea(assignment.getWorkArea());
	    clockLog.setTkWorkAreaId(assignment.getWorkAreaObj().getTkWorkAreaId());
	    
	    Long tkTaskId = null;
	    for(Task task : assignment.getWorkAreaObj().getTasks()) {
	    	if(task.getTask().compareTo(assignmentDesc.getTask()) == 0 ) {
	    		tkTaskId = task.getTkTaskId();
	    		break;
	    	}
	    }
	    clockLog.setTask(assignmentDesc.getTask());
	    clockLog.setTkTaskId(tkTaskId);
	    
	    // TODO: This timezone is not correct, we will need to make a javascript call.
	    clockLog.setClockTimestamp(clockTimestamp);//Calendar.getInstance(TkConstants.GMT_TIME_ZONE));
	    clockLog.setClockAction(clockAction);
	    clockLog.setIpAddress(ip);
	    clockLog.setHrJobId(assignment.getJob().getHrJobId());
	    clockLog.setUserPrincipalId(principalId);
	    clockLog.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    
	    return clockLog;
	}

	public void setClockLogDao(ClockLogDao clockLogDao) {
		this.clockLogDao = clockLogDao;
	}

	public ClockLog getLastClockLog(String principalId) {
		return clockLogDao.getLastClockLog(principalId);
	}

	public ClockLog getLastClockLog(String principalId, String clockAction) {
		return clockLogDao.getLastClockLog(principalId, clockAction);
	}
	
}
