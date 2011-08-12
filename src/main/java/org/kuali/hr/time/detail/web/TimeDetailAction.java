package org.kuali.hr.time.detail.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.TkCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.timesheet.web.TimesheetActionForm;
import org.kuali.hr.time.timesummary.AssignmentRow;
import org.kuali.hr.time.timesummary.EarnGroupSection;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.util.TkTimeBlockAggregate;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kns.exception.AuthorizationException;

public class TimeDetailAction extends TimesheetAction {

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        super.checkTKAuthorization(form, methodToCall); // Checks for read access first.

        TimesheetActionForm taForm = (TimesheetActionForm)form;
        TKUser user = TKContext.getUser();
        UserRoles roles = user.getCurrentRoles(); // either backdoor or actual
        String docid = taForm.getDocumentId();

        // Check for write access to Timeblock.
        if (StringUtils.equals(methodToCall, "addTimeBlock") || StringUtils.equals(methodToCall, "deleteTimeBlock")) {
            if (!roles.isDocumentWritable(docid)) {
                throw new AuthorizationException(roles.getPrincipalId(), "TimeDetailAction", "");
            }
        }
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.execute(mapping, form, request, response);
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        tdaf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(tdaf.getTimesheetDocument(), false));

        // TODO: may need to revisit this:
        // when adding / removing timeblocks, it should update the timeblocks on the timesheet document,e
        // so that we can directly fetch the timeblocks from the document
        List<TimeBlock> timeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(tdaf.getTimesheetDocument().getDocumentHeader().getDocumentId()));

        // Handle User preference / timezone information (pushed up from TkCalendar to avoid duplication)
        String timezone = TkServiceLocator.getTimezoneService().getUserTimezone();
        TkServiceLocator.getTimezoneService().translateForTimezone(timeBlocks, timezone);

        TimeSummary ts = TkServiceLocator.getTimeSummaryService().getTimeSummary(tdaf.getTimesheetDocument(), timeBlocks);
    	tdaf.setAssignStyleClassMap(ActionFormUtils.buildAssignmentStyleClassMap(tdaf.getTimesheetDocument()));
        Map<String, String> aMap = tdaf.getAssignStyleClassMap();
        // set css classes for each assignment row
        for(EarnGroupSection section: ts.getSections()) {
        	for(AssignmentRow assignRow: section.getAssignmentRows()) {
        		if(assignRow.getAssignmentKey() != null && aMap.containsKey(assignRow.getAssignmentKey())) {
                	assignRow.setCssClass(aMap.get(assignRow.getAssignmentKey()));
                } else {
                	assignRow.setCssClass("");
                }
        	}

        }
        tdaf.setTimeSummary(ts);
        ActionFormUtils.validateHourLimit(tdaf);

        // Set calendar
        TkTimeBlockAggregate aggregate = new TkTimeBlockAggregate(timeBlocks, tdaf.getTimesheetDocument().getPayCalendarEntry());
        TkCalendar cal = TkCalendar.getCalendar(aggregate);
        cal.assignAssignmentStyle(aMap);
        tdaf.setTkCalendar(cal);

        tdaf.setTimeBlockString(ActionFormUtils.getTimeBlockJSONMap(tdaf.getTimesheetDocument(), aggregate.getFlattenedTimeBlockList()));

        if(tdaf.getTimesheetDocument().getDocumentHeader().getDocumentStatus().equals(TkConstants.ROUTE_STATUS.FINAL) ||
        		TKContext.getUser().isSystemAdmin()) {
        	tdaf.setDocEditable("false");
        } else if(StringUtils.equals(tdaf.getTimesheetDocument().getPrincipalId(), TKContext.getUser().getPrincipalId())){
        	tdaf.setDocEditable("true");
        } else {
        	if(TKContext.getUser().isSystemAdmin() || TKContext.getUser().isLocationAdmin() || TKContext.getUser().isDepartmentAdmin() ||
        			TKContext.getUser().isReviewer() || TKContext.getUser().isApprover()){
        		tdaf.setDocEditable("true");
        	}
        	if(TKContext.getUser().isGlobalViewOnly() || TKContext.getUser().isDepartmentViewOnly()){
        		tdaf.setDocEditable("false");
        	}
        }

        return forward;
    }


    /**
     * This method involves creating an object-copy of every TimeBlock on the
     * time sheet for overtime re-calculation.
     *
     * @throws Exception
     */
    public ActionForward deleteTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        //Grab timeblock to be deleted from form
        List<TimeBlock> timeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
        TimeBlock deletedTimeBlock = null;
        for (TimeBlock tb : timeBlocks) {
            if (tb.getTkTimeBlockId().compareTo(tdaf.getTkTimeBlockId()) == 0) {
                deletedTimeBlock = tb;
                break;
            }
        }
        //Remove from the list of timeblocks
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(tdaf.getTimesheetDocument().getTimeBlocks().size());
        for (TimeBlock b : tdaf.getTimesheetDocument().getTimeBlocks()) {
            referenceTimeBlocks.add(b.copy());
        }

        // simple pointer, for clarity
        List<TimeBlock> newTimeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
        newTimeBlocks.remove(deletedTimeBlock);

        //Delete timeblock
        TkServiceLocator.getTimeBlockService().deleteTimeBlock(deletedTimeBlock);
        // Add a row to the history table
        TimeBlockHistory tbh = new TimeBlockHistory(deletedTimeBlock);
        tbh.setActionHistory(TkConstants.ACTIONS.DELETE_TIME_BLOCK);
        TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tbh);
        TkServiceLocator.getTimeBlockService().resetTimeHourDetail(newTimeBlocks);
        TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, newTimeBlocks, tdaf.getPayCalendarDates(), tdaf.getTimesheetDocument());
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks);

        return mapping.findForward("basic");
    }

    /**
     * This method involves creating an object-copy of every TimeBlock on the
     * time sheet for overtime re-calculation.
     *
     * @throws Exception
     */
    public ActionForward addTimeBlock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;

        // This is for updating a timeblock
        // If tkTimeBlockId is not null and the new timeblock is valid, delete the existing timeblock and a new one will be created after submitting the form.
        if (tdaf.getTkTimeBlockId() != null) {
            TimeBlock tb = TkServiceLocator.getTimeBlockService().getTimeBlock(tdaf.getTkTimeBlockId());
            TimeBlockHistory tbh = new TimeBlockHistory(tb);
            if (tb != null) {
                TkServiceLocator.getTimeBlockService().deleteTimeBlock(tb);

                // mark the original timeblock as updated in the history table
                tbh.setActionHistory(TkConstants.ACTIONS.UPDATE_TIME_BLOCK);
                TkServiceLocator.getTimeBlockHistoryService().saveTimeBlockHistory(tbh);

                // delete the timeblock from the memory
                tdaf.getTimesheetDocument().getTimeBlocks().remove(tb);
            }
        }

        Assignment assignment = TkServiceLocator.getAssignmentService().getAssignment(tdaf.getTimesheetDocument(), tdaf.getSelectedAssignment());


        // Surgery point - Need to construct a Date/Time with Appropriate Timezone.
        Timestamp startTime = TKUtils.convertDateStringToTimestamp(tdaf.getStartDate(), tdaf.getStartTime());
        Timestamp endTime = TKUtils.convertDateStringToTimestamp(tdaf.getEndDate(), tdaf.getEndTime());

        // We need a  cloned reference set so we know whether or not to
        // persist any potential changes without making hundreds of DB calls.
        List<TimeBlock> referenceTimeBlocks = new ArrayList<TimeBlock>(tdaf.getTimesheetDocument().getTimeBlocks().size());
        for (TimeBlock tb : tdaf.getTimesheetDocument().getTimeBlocks()) {
            referenceTimeBlocks.add(tb.copy());
        }

        // This is just a reference, for code clarity, the above list is actually
        // separate at the object level.
        List<TimeBlock> newTimeBlocks = tdaf.getTimesheetDocument().getTimeBlocks();
        DateTime startTemp = new DateTime(startTime);
        DateTime endTemp = new DateTime(endTime);
        if (StringUtils.equals(tdaf.getAcrossDays(), "y")
        		&& !(endTemp.getDayOfYear() - startTemp.getDayOfYear() <= 1
        				&& endTemp.getHourOfDay() == 0)) {
            newTimeBlocks.addAll(TkServiceLocator.getTimeBlockService().buildTimeBlocksSpanDates(assignment,
                    tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument(), startTime,
                    endTime, tdaf.getHours(), tdaf.getAmount(), false));
        } else {
            newTimeBlocks.addAll(TkServiceLocator.getTimeBlockService().buildTimeBlocks(assignment,
                    tdaf.getSelectedEarnCode(), tdaf.getTimesheetDocument(), startTime,
                    endTime, tdaf.getHours(), tdaf.getAmount(), false));
        }

        TkServiceLocator.getTimeBlockService().resetTimeHourDetail(newTimeBlocks);

        TkServiceLocator.getTkRuleControllerService().applyRules(TkConstants.ACTIONS.ADD_TIME_BLOCK, newTimeBlocks, tdaf.getPayCalendarDates(), tdaf.getTimesheetDocument());
        TkServiceLocator.getTimeBlockService().saveTimeBlocks(referenceTimeBlocks, newTimeBlocks);
        //call history service

        ActionFormUtils.validateHourLimit(tdaf);
        return mapping.findForward("basic");
    }
    
    public ActionForward loadDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailActionForm tdaf = (TimeDetailActionForm) form;
        String viewPrincipal = TKContext.getUser().getPrincipalId();
        TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(tdaf.getDocumentId());
        PayCalendarEntries payCalendarEntries = TkServiceLocator.getPayCalendarSerivce().getPayCalendarDatesByPayEndDate(viewPrincipal, tsdh.getPayEndDate());
        TimesheetDocument td = TkServiceLocator.getTimesheetService().openTimesheetDocument(viewPrincipal, payCalendarEntries);
        setupDocumentOnFormContext(tdaf, td);
        
    	return mapping.findForward("basic");
    }
}
