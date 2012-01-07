package org.kuali.hr.time.detail.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.assignment.AssignmentDescriptionKey;
import org.kuali.hr.time.detail.validation.TimeDetailValidationService;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.web.struts.form.KualiMaintenanceForm;

public class TimeDetailWSAction extends TimesheetAction {

    private static final Logger LOG = Logger.getLogger(TimeDetailWSAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.execute(mapping, form, request, response);
    }

    /**
     * This is an ajax call triggered after a user submits the time entry form.
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
    public ActionForward validateTimeEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailActionFormBase tdaf = (TimeDetailActionFormBase) form;
        JSONArray errorMsgList = new JSONArray();

        List<String> errors = TimeDetailValidationService.validateTimeEntryDetails(tdaf);
        errorMsgList.addAll(errors);

        tdaf.setOutputString(JSONValue.toJSONString(errorMsgList));
        return mapping.findForward("ws");
    }

    //this is an ajax call for the assignment maintenance page
    public ActionForward getDepartmentForJobNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KualiMaintenanceForm kualiForm = (KualiMaintenanceForm) form;

        String principalId = (String) request.getAttribute("principalId");
        Long jobNumber = (Long) request.getAttribute("jobNumber");

        Job job = TkServiceLocator.getJobSerivce().getJob(principalId, jobNumber, TKUtils.getCurrentDate());
        kualiForm.setAnnotation(job.getDept());

        return mapping.findForward("ws");
    }

    // this is an ajax call
    public ActionForward getEarnCodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailWSActionForm tdaf = (TimeDetailWSActionForm) form;
        StringBuilder earnCodeString = new StringBuilder();
        if (StringUtils.isBlank(tdaf.getSelectedAssignment())) {
            earnCodeString.append("<option value=''>-- select an assignment first --</option>");
        } else {
            List<Assignment> assignments = tdaf.getTimesheetDocument().getAssignments();
            AssignmentDescriptionKey key = new AssignmentDescriptionKey(tdaf.getSelectedAssignment());
            for (Assignment assignment : assignments) {
                if (assignment.getJobNumber().compareTo(key.getJobNumber()) == 0 &&
                        assignment.getWorkArea().compareTo(key.getWorkArea()) == 0 &&
                        assignment.getTask().compareTo(key.getTask()) == 0) {
                    List<EarnCode> earnCodes = TkServiceLocator.getEarnCodeService().getEarnCodes(assignment, tdaf.getTimesheetDocument().getAsOfDate());
                    for (EarnCode earnCode : earnCodes) {
                        if (earnCode.getEarnCode().equals(TkConstants.HOLIDAY_EARN_CODE)
                                && !(TKContext.getUser().getCurrentRoles().isSystemAdmin() || TKContext.getUser().getCurrentRoles().isTimesheetApprover())) {
                            continue;
                        }
                        if (!(assignment.getTimeCollectionRule().isClockUserFl() &&
                                StringUtils.equals(assignment.getJob().getPayTypeObj().getRegEarnCode(), earnCode.getEarnCode()) && StringUtils.equals(TKContext.getPrincipalId(), assignment.getPrincipalId()))) {
                            earnCodeString.append("<option value='").append(earnCode.getEarnCode()).append("_").append(earnCode.getEarnCodeType()).append("'>");
                            earnCodeString.append(earnCode.getEarnCode()).append(" : ").append(earnCode.getDescription());
                            earnCodeString.append("</option>");
                        }
                    }
                }
            }
        }
        LOG.info(tdaf.toString());
        tdaf.setOutputString(earnCodeString.toString());
        return mapping.findForward("ws");
    }

    public ActionForward getOvertimeEarnCodes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeDetailWSActionForm tdaf = (TimeDetailWSActionForm) form;
        StringBuilder earnCodeString = new StringBuilder();
        List<EarnCode> overtimeEarnCodes = TkServiceLocator.getEarnCodeService().getOvertimeEarnCodes(TKUtils.getCurrentDate());

        for (EarnCode earnCode : overtimeEarnCodes) {
            earnCodeString.append("<option value='").append(earnCode.getEarnCode()).append("'>");
            earnCodeString.append(earnCode.getEarnCode()).append(" : ").append(earnCode.getDescription());
            earnCodeString.append("</option>");
        }

        LOG.info(tdaf.toString());
        tdaf.setOutputString(earnCodeString.toString());
        return mapping.findForward("ws");
    }

}
