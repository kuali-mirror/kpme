package org.kuali.hr.time.clock.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;

public class ClockAction extends TkAction {

    	private static final Logger LOG = Logger.getLogger(ClockAction.class);
    	public static final SimpleDateFormat SDF =  new SimpleDateFormat("EEE, MMMM d yyyy HH:mm:ss, zzzz");

    	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	    LOG.debug("Calling execute.");
    	    String principalId = TKContext.getUser().getPrincipalId();
    	    List<Assignment> assignments = TkServiceLocator.getAssignmentService().getCurrentlyValidActiveAssignments(principalId);
    	    ClockLog clockLog = TkServiceLocator.getClockLogService().getLastClockLog(principalId);

    	    ((ClockActionForm)form).setPrincipalId(principalId);
    	    ((ClockActionForm)form).setAssignments(assignments);
    	    ((ClockActionForm)form).setClockAction(clockLog.getNextValidClockAction());

    	    Calendar calendar = clockLog.getClockTimestamp();
    	    ((ClockActionForm)form).setLastClockActionTimestampFormatted(SDF.format(calendar.getTime()));

    	    // this may need to be changed in the future
    	    SimpleDateFormat dateTimeForOutput = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss");
    	    ((ClockActionForm)form).setLastClockActionTimestamp(dateTimeForOutput.format(calendar.getTime()));

    	    request.setAttribute("principalId", principalId);
    	    request.setAttribute("assignments", assignments);

    	    return super.execute(mapping, form, request, response);
    	}

    	public ActionForward clockAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	    ClockActionForm caf = (ClockActionForm)form;

    	    LOG.debug("Clock action: " + caf.getClockAction());
    	    // TODO: Validate that clock action is valid for this user

    	    String principalId = TKContext.getUser().getPrincipalId();
    	    ClockLog cl = new ClockLog();
    	    cl.setClockAction(caf.getClockAction());
    	    cl.setPrincipalId(principalId);
    	    cl.setClockTimestamp(Calendar.getInstance(TkConstants.GMT_TIME_ZONE));
    	    cl.setClockTimestampTimezone(TKUtils.getTimeZone());
    	    cl.setIpAddress(request.getRemoteAddr());
    	    cl.setJobNumber(0);
    	    cl.setWorkAreaId(0L);
    	    cl.setTaskId(0L);
    	    cl.setUserPrincipalId(principalId);

    	    TkServiceLocator.getClockLogService().saveClockAction(cl);
    	    return mapping.findForward("basic");
    	}
}
