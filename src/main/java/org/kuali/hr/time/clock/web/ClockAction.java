package org.kuali.hr.time.clock.web;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class ClockAction extends TimesheetAction {

    	private static final Logger LOG = Logger.getLogger(ClockAction.class);
    	public static final SimpleDateFormat SDF =  new SimpleDateFormat("EEE, MMMM d yyyy HH:mm:ss, zzzz");

    	@Override
    	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    		ActionForward forward = super.execute(mapping, form, request, response);
    	    LOG.debug("Calling execute.");
    	    ClockActionForm clockActionForm = (ClockActionForm) form;
    	    String principalId = TKContext.getUser().getPrincipalId();
    	    Date payPeriodEndDate = null; 
    	    List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, payPeriodEndDate);
//    	    DepartmentEarnCode deptEarnCode = TkServiceLocator.getDepartmentEarnCodeService().getDepartmentEarnCodeList( )
    	    ClockLog clockLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);

    	    clockActionForm.setPrincipalId(principalId);

    	    if(clockLog == null) {
    	    	clockActionForm.setCurrentClockAction(TkConstants.CLOCK_IN);
    	    }
	   	    else {
    	    	clockActionForm.setCurrentClockAction(clockLog.getNextValidClockAction());

	    	    Timestamp clockTimestamp = clockLog.getClockTimestamp();
	    	    clockActionForm.setLastClockAction(clockTimestamp);
    	    }

    	    return forward;
    	}

    	public ActionForward clockAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	    ClockActionForm caf = (ClockActionForm)form;

    	    LOG.debug("Clock action: " + caf.getCurrentClockAction());
    	    // TODO: Validate that clock action is valid for this user

    	    String principalId = TKContext.getUser().getPrincipalId();
    	    ClockLog cl = new ClockLog();
    	    cl.setClockAction(caf.getCurrentClockAction());
    	    cl.setPrincipalId(principalId);
    	    cl.setClockTimestamp(new Timestamp(System.currentTimeMillis()));//Calendar.getInstance(TkConstants.GMT_TIME_ZONE));
    	    //
    	    // TODO: This timezone is not correct, we will need to make a javascript call.
    	    cl.setClockTimestampTimezone(TKUtils.getTimeZone());
    	    cl.setIpAddress(request.getRemoteAddr());
    	    cl.setJobNumber(0L);
    	    cl.setWorkArea(0L);
    	    cl.setTask(0L);
    	    cl.setUserPrincipalId(principalId);

    	    TkServiceLocator.getClockLogService().saveClockAction(cl);
    	    caf.setCurrentClockAction(cl.getNextValidClockAction());
    	    caf.setLastClockAction(cl.getClockTimestamp());
    	    //caf.setLastClockActionTimestampFormatted(SDF.format(cl.getClockTimestamp().getTime()));
    	    //caf.setLastClockActionTimestamp(cl.getClockTimestamp());

    	    // end time
    	    // the clock action logic is reversed, so check if the clock action is "clocked in."
    	    if(StringUtils.equals(caf.getCurrentClockAction(), "CI")) {
    	    	long beginTime = caf.getLastClockAction().getTime();
    	    	Timestamp beginTimestamp = new Timestamp(beginTime);
    	    	Timestamp endTimestamp = cl.getClockTimestamp();

    	    	BigDecimal hours = TKUtils.getHoursBetween(endTimestamp.getTime(), beginTimestamp.getTime());

    	    	TimeBlock tb = new TimeBlock();
    	    	tb.setJobNumber(0L);
    	    	tb.setWorkArea(0L);
    	    	tb.setTask(0L);
    	    	tb.setEarnCode("RGN");
    	    	tb.setBeginTimestamp(beginTimestamp);
    	    	tb.setEndTimestamp(endTimestamp);
    	    	tb.setClockLogCreated(true);
    	    	tb.setHours(hours);
    	    	tb.setUserPrincipalId(principalId);
    	    	tb.setTimestamp(new Timestamp(System.currentTimeMillis()));
    	    	tb.setBeginTimestampTimezone(cl.getClockTimestampTimezone());
    	    	tb.setEndTimestampTimezone(cl.getClockTimestampTimezone());

    	    	TkServiceLocator.getTimeBlockService().saveTimeBlock(tb);

//    	    	TimeBlockHistory tbs = new TimeBlockHistory(tb);
//    	    	TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tbs);
    	    }
    	    return mapping.findForward("basic");
    	}
}
