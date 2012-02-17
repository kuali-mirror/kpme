package org.kuali.hr.time.approval.web;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.json.simple.JSONValue;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.timesummary.TimeSummary;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

public class TimeApprovalWSAction extends TkAction {

    private static final Logger LOG = Logger.getLogger(TimeApprovalWSAction.class);

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.execute(mapping, form, request, response);
    }

    /**
     * Action called via AJAX. (ajaj really...)
     * <p/>
     * This search returns quick-results to the search box for the user to further
     * refine upon. The end value can then be form submitted.
     */
    public ActionForward searchApprovalRows(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        List<Map<String, String>> results = new LinkedList<Map<String, String>>();
        Date beginDate = new SimpleDateFormat("MM/dd/yyyy").parse(taaf.getPayBeginDateForSearch());
        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(taaf.getPayEndDateForSearch());
        
        List<String> principalIds = TkServiceLocator.getTimeApproveService().getPrincipalIdsByDeptAndWorkArea(taaf.getSelectedDept(), taaf.getSelectedWorkArea(), new java.sql.Date(beginDate.getTime()), new java.sql.Date(endDate.getTime()), taaf.getSelectedPayCalendarGroup());
        List<TKPerson> persons = TkServiceLocator.getPersonService().getPersonCollection(principalIds);
        
        if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_PRINCIPAL)) {
            for (String id : principalIds) {
                if(StringUtils.contains(id, taaf.getSearchTerm())) {
                    Map<String, String> labelValue = new HashMap<String, String>();
                    labelValue.put("id", id);
                    labelValue.put("result", id);
                    results.add(labelValue);
                }
            }
        } else if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_DOCID)) {
            Map<String, TimesheetDocumentHeader> principalDocumentHeaders =
                    TkServiceLocator.getTimeApproveService().getPrincipalDocumehtHeader(persons, beginDate, endDate);

            for (Map.Entry<String,TimesheetDocumentHeader> entry : principalDocumentHeaders.entrySet()) {
                if (StringUtils.contains(entry.getValue().getDocumentId(), taaf.getSearchTerm())) {
                    Map<String, String> labelValue = new HashMap<String, String>();
                    labelValue.put("id", entry.getValue().getDocumentId() + " (" + entry.getValue().getPrincipalId() + ")");
                    labelValue.put("result", entry.getValue().getPrincipalId());
                    results.add(labelValue);
                }

            }
        }

        taaf.setOutputString(JSONValue.toJSONString(results));

        return mapping.findForward("ws");
    }
    
    public ActionForward getTimeSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;

        TimesheetDocument td = TkServiceLocator.getTimesheetService().getTimesheetDocument(taaf.getDocumentId());
		TimeSummary ts = TkServiceLocator.getTimeSummaryService().getTimeSummary(td);


        taaf.setOutputString(ts.toJsonString());

        return mapping.findForward("ws");
    }
}
