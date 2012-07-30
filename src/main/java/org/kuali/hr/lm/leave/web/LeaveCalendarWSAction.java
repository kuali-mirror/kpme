package org.kuali.hr.lm.leave.web;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.earncodesec.EarnCodeType;
import org.kuali.hr.lm.leavecalendar.LeaveCalendarDocument;
import org.kuali.hr.lm.leavecalendar.validation.LeaveCalendarValidationService;
import org.kuali.hr.lm.leavecalendar.web.LeaveCalendarForm;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.util.GlobalVariables;

public class LeaveCalendarWSAction extends TkAction {

    private static final Logger LOG = Logger.getLogger(LeaveCalendarWSAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //return super.execute(mapping, form, request, response);
        LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;

        String documentId = lcf.getDocumentId();
        // if the reload was trigger by changing of the selectedPayPeriod, use the passed in parameter as the calendar entry id
        String calendarEntryId = StringUtils.isNotBlank(request.getParameter("selectedPP")) ? request.getParameter("selectedPP") : lcf.getCalEntryId();

        // Here - viewPrincipal will be the principal of the user we intend to
        // view, be it target user, backdoor or otherwise.
        String viewPrincipal = TKUser.getCurrentTargetPerson().getPrincipalId();
        CalendarEntries calendarEntry = null;

        LeaveCalendarDocument lcd = null;
        // By handling the prev/next in the execute method, we are saving one
        // fetch/construction of a TimesheetDocument. If it were broken out into
        // methods, we would first fetch the current document, and then fetch
        // the next one instead of doing it in the single action.
        if (StringUtils.isNotBlank(documentId)) {
            lcd = TkServiceLocator.getLeaveCalendarService()
                    .getLeaveCalendarDocument(documentId);
            calendarEntry = lcd.getCalendarEntry();
        } else if (StringUtils.isNotBlank(calendarEntryId)) {
            // do further procedure
            calendarEntry = TkServiceLocator.getCalendarEntriesService()
                    .getCalendarEntries(calendarEntryId);
            lcd = TkServiceLocator.getLeaveCalendarService()
                    .getLeaveCalendarDocument(viewPrincipal, calendarEntry);
        } else {
            // Default to whatever is active for "today".
            Date currentDate = TKUtils.getTimelessDate(null);
            calendarEntry = TkServiceLocator.getCalendarService()
                    .getCurrentCalendarDatesForLeaveCalendar(viewPrincipal, currentDate);
            lcd = TkServiceLocator.getLeaveCalendarService()
                    .openLeaveCalendarDocument(viewPrincipal, calendarEntry);
        }

        lcf.setCalendarEntry(calendarEntry);
        lcf.setAssignmentDescriptions(TkServiceLocator.getAssignmentService().getAssignmentDescriptions(lcd));

        if (lcd != null) {
            setupDocumentOnFormContext(lcf, lcd);
        } else {
            LOG.error("Null leave calendar document in LeaveCalendarAction.");
        }

        ActionForward forward = super.execute(mapping, form, request, response);
        if (forward.getRedirect()) {
            return forward;
        }

        return forward;
    }

        
    public ActionForward getEarnCodeInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//System.out.println("Leave code info called >>>>>>>>>>>>>>>");
    	LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;
        LOG.info(lcf.toString());
        EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCodeById(lcf.getSelectedEarnCode());
        AccrualCategory acObj = null;
    	if(earnCode.getAccrualCategory() != null) {
    		acObj = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(earnCode.getAccrualCategory(), TKUtils.getCurrentDate());
    	}
    	String unitTime = (acObj!= null ? acObj.getUnitOfTime() : earnCode.getRecordMethod()) ;
        Map<String, Object> earnCodeMap = new HashMap<String, Object>();
        earnCodeMap.put("unitOfTime", unitTime);
        earnCodeMap.put("defaultAmountofTime", earnCode.getDefaultAmountofTime());
        earnCodeMap.put("fractionalTimeAllowed", earnCode.getFractionalTimeAllowed());
        lcf.setOutputString(JSONValue.toJSONString(earnCodeMap));
        return mapping.findForward("ws");
    }

    public ActionForward getEarnCodeJson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //TODO: copied from TimeDetailWSAction.  Need to reduce code duplication
        LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;
        List<Map<String, Object>> earnCodeList = new LinkedList<Map<String, Object>>();

        if (StringUtils.isNotBlank(lcf.getSelectedAssignment())) {
            List<Assignment> assignments = lcf.getLeaveCalendarDocument().getAssignments();
            AssignmentDescriptionKey key = new AssignmentDescriptionKey(lcf.getSelectedAssignment());
            for (Assignment assignment : assignments) {
                if (assignment.getJobNumber().compareTo(key.getJobNumber()) == 0 &&
                        assignment.getWorkArea().compareTo(key.getWorkArea()) == 0 &&
                        assignment.getTask().compareTo(key.getTask()) == 0) {
                    List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(assignment, new java.sql.Date(TKUtils.convertDateStringToTimestamp(lcf.getStartDate()).getTime()), EarnCodeType.LEAVE.getCode());
                    for (EarnCode earnCode : earnCodes) {
                        // TODO: minimize / compress the crazy if logics below
                        if (earnCode.getEarnCode().equals(TkConstants.HOLIDAY_EARN_CODE)
                                && !(TkUserRoles.getUserRoles(GlobalVariables.getUserSession().getPrincipalId()).isSystemAdmin())) {
                            continue;
                        }
                        //TODO: I don't understand why this if statement is used.  We only want the EarnCode if one or more of these conditions is false, but why?
                        if (!(assignment.getTimeCollectionRule().isClockUserFl() &&
                                StringUtils.equals(assignment.getJob().getPayTypeObj().getRegEarnCode(), earnCode.getEarnCode()) && StringUtils.equals(TKContext.getPrincipalId(), assignment.getPrincipalId()))) {
                            Map<String, Object> earnCodeMap = new HashMap<String, Object>();
                            earnCodeMap.put("assignment", assignment.getAssignmentKey());
                            earnCodeMap.put("earnCode", earnCode.getEarnCode());
                            earnCodeMap.put("desc", earnCode.getDescription());
                            earnCodeMap.put("type", earnCode.getEarnCodeType());
                            earnCodeMap.put("earnCodeId", earnCode.getHrEarnCodeId());
                            earnCodeList.add(earnCodeMap);
                        }
                    }
                }
            }
        }
        //LOG.info(lcf.toString());
        lcf.setOutputString(JSONValue.toJSONString(earnCodeList));
        return mapping.findForward("ws");
    }
    
    /**
     * This is an ajax call triggered after a user submits the leave entry form.
     * If there is any error, it will return error messages as a json object.
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return jsonObj
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ActionForward validateLeaveEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	LeaveCalendarWSForm lcf = (LeaveCalendarWSForm) form;
        JSONArray errorMsgList = new JSONArray();

        List<String> errors = LeaveCalendarValidationService.validateLaveEntryDetails(lcf);
        errorMsgList.addAll(errors);

        lcf.setOutputString(JSONValue.toJSONString(errorMsgList));
        
        return mapping.findForward("ws");
    }

    protected void setupDocumentOnFormContext(LeaveCalendarForm leaveForm,
                                              LeaveCalendarDocument lcd) {
        leaveForm.setLeaveCalendarDocument(lcd);
    }

}
