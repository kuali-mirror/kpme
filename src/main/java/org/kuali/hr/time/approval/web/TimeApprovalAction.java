package org.kuali.hr.time.approval.web;

import org.apache.commons.lang.StringUtils;
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
        TimeApprovalActionForm taf = (TimeApprovalActionForm) form;

        Criteria crit = new Criteria();
        if (StringUtils.isNotBlank(taf.getSearchField()) && StringUtils.isNotBlank(taf.getTerm())) {
            crit.addEqualTo(taf.getSearchField(), taf.getTerm());
        }
        taf.setDocHeaders(TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(crit, 0, 5));

        return super.execute(mapping, form, request, response);
    }

    public ActionForward load(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taf = (TimeApprovalActionForm) form;
        TKUser tkUser = TKContext.getUser();
        taf.setName(tkUser.getCurrentPerson().getName());
        taf.setPayBeginDate(TKUtils.createDate(10, 1, 2011, 0, 0, 0));
        taf.setPayEndDate(TKUtils.createDate(10, 15, 2011, 0, 0, 0));

        taf.setPayCalendarLabels(TkServiceLocator.getTimeApproveService().getPayCalendarLabelsForApprovalTab(taf.getPayBeginDate(), taf.getPayEndDate()));
        taf.setApprovalTimeSummaryRows(TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(taf.getPayBeginDate(), taf.getPayEndDate()));


        return mapping.findForward("basic");
    }

    public ActionForward getMoreDocument(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taf = (TimeApprovalActionForm) form;
        Criteria crit = new Criteria();
        crit.addGreaterThan("documentId", taf.getLastDocumentId());
        List<TimesheetDocumentHeader> documentHeaders = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeaders(crit, 0, 5);

        String outputHtml = "";
        for (TimesheetDocumentHeader documentHeader : documentHeaders) {
            outputHtml += "<tr>" +
                    "<td><span id='" + documentHeader.getDocumentId() + "' class='document'>" + documentHeader.getDocumentId() + "</span></td>" +
                    "<td>" + documentHeader.getPrincipalId() + "</td>" +
                    "<td><label for='checkbox'><input type='checkbox'/></label>" +
                    "</tr>";
        }

        taf.setOutputString(outputHtml);

        return mapping.findForward("ws");
    }

    public ActionForward searchDocumentHeaders(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        TimeApprovalActionForm taf = (TimeApprovalActionForm) form;
        Criteria crit = new Criteria();
        crit.addLike(taf.getSearchField(), "%" + taf.getTerm().toLowerCase() + "%");
        List<String> results = TkServiceLocator.getTimesheetDocumentHeaderService().getDataByField(crit, new String[]{taf.getSearchField()});
        taf.setOutputString(JSONValue.toJSONString(results));

        return mapping.findForward("ws");

    }

}
