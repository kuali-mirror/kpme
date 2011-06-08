package org.kuali.hr.time.approval.web;

import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONValue;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TimeApprovalAction extends TkAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;

        taaf.setDocHeaders(TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeadersByField(taaf.getSearchField(), taaf.getTerm()));

        return super.execute(mapping, form, request, response);
    }

    public ActionForward load(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        TKUser tkUser = TKContext.getUser();
        taaf.setName(tkUser.getCurrentPerson().getName());
        taaf.setPayBeginDate(TKUtils.createDate(10, 1, 2011, 0, 0, 0));
        taaf.setPayEndDate(TKUtils.createDate(10, 15, 2011, 0, 0, 0));

        taaf.setPayCalendarLabels(TkServiceLocator.getTimeApproveService().getPayCalendarLabelsForApprovalTab(taaf.getPayBeginDate(), taaf.getPayEndDate()));
        taaf.setApprovalTimeSummaryRows(TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(taaf.getPayBeginDate(), taaf.getPayEndDate()));


        return mapping.findForward("basic");
    }

    public ActionForward getMoreDocument(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        Criteria crit = new Criteria();
        crit.addGreaterThan("documentId", taaf.getLastDocumentId());
        List<TimesheetDocumentHeader> documentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getSortedDocumentHeaders(taaf.getOrderBy(), taaf.getOrderDirection(), taaf.getRows());

        String outputHtml = "";
        for (TimesheetDocumentHeader documentHeader : documentHeaders) {
            outputHtml += "<tr>" +
                    "<td><span id='" + documentHeader.getDocumentId() + "' class='document'>" + documentHeader.getDocumentId() + "</span></td>" +
                    "<td>" + documentHeader.getPrincipalId() + "</td>" +
                    "<td><label for='checkbox'><input type='checkbox'/></label>" +
                    "</tr>";
        }

        taaf.setOutputString(outputHtml);

        return mapping.findForward("ws");
    }

    public ActionForward searchDocumentHeaders(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        List<String> results = TkServiceLocator.getTimesheetDocumentHeaderService().getValueByField(taaf.getSearchField(), taaf.getTerm().toLowerCase());
        taaf.setOutputString(JSONValue.toJSONString(results));

        return mapping.findForward("ws");

    }

    public ActionForward getSortedDocumentHeaders(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        String orderBy = "";
        List<TimesheetDocumentHeader> documentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getSortedDocumentHeaders(taaf.getOrderBy(), taaf.getOrderDirection(), taaf.getRows());
        taaf.setDocHeaders(documentHeaders);

        return mapping.findForward("basic");
    }

}
