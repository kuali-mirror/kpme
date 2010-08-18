package org.kuali.hr.time.clock.web;

import java.math.BigDecimal;
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
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class ClockAction extends TkAction {

    	private static final Logger LOG = Logger.getLogger(ClockAction.class);
    	public static final SimpleDateFormat SDF =  new SimpleDateFormat("EEE, MMMM d yyyy HH:mm:ss, zzzz");

    	@Override
    	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	    LOG.debug("Calling execute.");
    	    ClockActionForm clockActionForm = (ClockActionForm) form;
    	    String principalId = TKContext.getUser().getPrincipalId();
    	    List<Assignment> assignments = TkServiceLocator.getAssignmentService().getCurrentlyValidActiveAssignments(principalId);
    	    ClockLog clockLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);

    	    clockActionForm.setPrincipalId(principalId);
    	    clockActionForm.setAssignments(assignments);

    	    if(clockLog == null) {
    	    	clockActionForm.setCurrentClockAction(TkConstants.CLOCK_IN);
    	    }
    	    else {
    	    	clockActionForm.setCurrentClockAction(clockLog.getNextValidClockAction());

	    	    Calendar calendar = clockLog.getClockTimestamp();
	    	    clockActionForm.setLastClockActionTimestampFormatted(SDF.format(calendar.getTime()));

	    	    // this may need to be changed in the future
	    	    SimpleDateFormat dateTimeForOutput = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss");
	    	    clockActionForm.setLastClockActionTimestamp(dateTimeForOutput.format(calendar.getTime()));
    	    }

    	    request.setAttribute("principalId", principalId);
    	    request.setAttribute("assignments", assignments);

    	    return super.execute(mapping, form, request, response);
    	}

    	public ActionForward clockAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	    ClockActionForm caf = (ClockActionForm)form;

    	    LOG.debug("Clock action: " + caf.getCurrentClockAction());
    	    // TODO: Validate that clock action is valid for this user

    	    String principalId = TKContext.getUser().getPrincipalId();
    	    ClockLog cl = new ClockLog();
    	    cl.setClockAction(caf.getCurrentClockAction());
    	    cl.setPrincipalId(principalId);
    	    cl.setClockTimestamp(Calendar.getInstance(TkConstants.GMT_TIME_ZONE));
    	    cl.setClockTimestampTimezone(TKUtils.getTimeZone());
    	    cl.setIpAddress(request.getRemoteAddr());
    	    cl.setJobNumber(0L);
    	    cl.setWorkAreaId(0L);
    	    cl.setTaskId(0L);
    	    cl.setUserPrincipalId(principalId);

    	    TkServiceLocator.getClockLogService().saveClockAction(cl);
    	    caf.setCurrentClockAction(cl.getNextValidClockAction());
    	    caf.setLastClockActionTimestampFormatted(SDF.format(cl.getClockTimestamp().getTime()));

    	    // end time
    	    // the clock action logic is reversed, so check if the clock action is "clocked in."
    	    if(StringUtils.equals(caf.getCurrentClockAction(), "CI")) {

    	    	SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss");
    	    	long beginTime = sdf.parse(caf.getLastClockActionTimestamp()).getTime();
    	    	long endTime = cl.getClockTimestamp().getTime().getTime();
    	    	cl.getClockTimestamp().getTimeInMillis();
    	    	Timestamp beginTimestamp = new Timestamp(beginTime);
    	    	Timestamp endTimestamp = new Timestamp(endTime);

    	    	BigDecimal hours = TKUtils.getHoursBetween(endTime, beginTime);

    	    	TimeBlock tb = new TimeBlock();
    	    	tb.setJobNumber(0L);
    	    	tb.setWorkAreaId(0L);
    	    	tb.setTaskId(0L);
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
