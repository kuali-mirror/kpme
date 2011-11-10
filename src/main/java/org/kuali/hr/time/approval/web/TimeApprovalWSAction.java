package org.kuali.hr.time.approval.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONValue;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

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
        TKUser user = TKContext.getUser();
        List<Map<String, String>> results = new LinkedList<Map<String, String>>();
        // the dates come from the begin / end date on the form
        Date beginDate = new SimpleDateFormat("MM/dd/yyyy").parse(taaf.getPayBeginDateForSearch());
        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(taaf.getPayEndDateForSearch());
        List<String> principalIds = TkServiceLocator.getTimeApproveService().getPrincipalIdsByAssignment(user.getWorkAreasFromUserRoles(), new java.sql.Date(endDate.getTime()), taaf.getSelectedCalendarGroup());

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
                    TkServiceLocator.getTimeApproveService().getPrincipalDocumehtHeader(principalIds, beginDate, endDate);

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

}
