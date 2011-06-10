package org.kuali.hr.time.approval.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONValue;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TimeApprovalAction extends TkAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        TKUser user = TKContext.getUser();

        // TODO: Obtain this via form?
        // Pay Begin/End needs to come from somewhere tangible, hard coded for now.
        taaf.setPayBeginDate(TKUtils.createDate(5, 29, 2011, 0, 0, 0));
        taaf.setPayEndDate(TKUtils.createDate(6, 12, 2011, 0, 0, 0));

        taaf.setName(user.getPrincipalName());
        taaf.setApprovalRows(getApprovalRows(taaf.isAjaxCall(), taaf.getSearchField(), taaf.getSearchTerm(), taaf.getSortField(),
                taaf.isAscending(), taaf.getRows(), taaf.getPayBeginDate(), taaf.getPayEndDate()));
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeApproveService().getPayCalendarLabelsForApprovalTab(taaf.getPayBeginDate(), taaf.getPayEndDate()));

        return super.execute(mapping, form, request, response);
    }


    public ActionForward getMoreDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;

        StringBuilder outputHtml = new StringBuilder();
        for (ApprovalTimeSummaryRow row : taaf.getApprovalRows()) {
            outputHtml.append("<tr>");
            outputHtml.append("<td><span id='").append(row.getDocumentId()).append("' class='document'>").append(row.getDocumentId());
            outputHtml.append("</span></td>");
            outputHtml.append("<td>").append(row.getName()).append("</td>");
            outputHtml.append("<td><label for='checkbox'><input type='checkbox'/></label></tr>");
        }
        taaf.setOutputString(outputHtml.toString());

        return mapping.findForward("ws");
    }

    /**
     * Action called via AJAX. (ajaj really...)
     */
    public ActionForward searchApprovalRows(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;

        List<String> results = new ArrayList<String>();
        for (ApprovalTimeSummaryRow row : taaf.getApprovalRows()) {
            if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_DOCID) &&
                    row.getDocumentId().contains(taaf.getSearchTerm())) {

                results.add(row.getDocumentId());
            } else if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_PRINCIPAL) &&
                    row.getName().toLowerCase().contains(taaf.getSearchTerm())) {

                results.add(row.getName());
            }

        }

        taaf.setOutputString(JSONValue.toJSONString(results));

        return mapping.findForward("ws");
    }

    /**
     * Helper method to modify / manage the list of records needed to display approval data to the user.
     *
     * @param isAjaxCall this is to determine if the request is coming from an ajax call or not
     * @param searchField
     * @param searchTerm
     * @param sortField
     * @param ascending
     * @param rowsToReturn
     * @param beginDate
     * @param endDate
     * @return
     */
    List<ApprovalTimeSummaryRow> getApprovalRows(boolean isAjaxCall, String searchField, String searchTerm, String sortField,
                                                 boolean ascending, int rowsToReturn, Date beginDate, Date endDate) {
        List<ApprovalTimeSummaryRow> rows = new ArrayList<ApprovalTimeSummaryRow>();

        // Relies on TkContext.getUser(), will work with backdoor users / etc.
        List<ApprovalTimeSummaryRow> allRows = TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(beginDate, endDate);

        List<ApprovalTimeSummaryRow> filteredRows = new ArrayList<ApprovalTimeSummaryRow>();

        if (!isAjaxCall && StringUtils.isNotBlank(searchField) && StringUtils.isNotBlank(searchTerm)) {
            for (ApprovalTimeSummaryRow row : allRows) {
                if (StringUtils.equals(searchField, TimeApprovalActionForm.ORDER_BY_DOCID) &&
                        row.getDocumentId().contains(searchTerm)) {
                    filteredRows.add(row);
                } else if (StringUtils.equals(searchField, TimeApprovalActionForm.ORDER_BY_PRINCIPAL) &&
                        row.getName().contains(searchTerm)) {
                    filteredRows.add(row);
                }
            }
            allRows = filteredRows;
        }

        if (StringUtils.equals(sortField, TimeApprovalActionForm.ORDER_BY_PRINCIPAL)) {
            Collections.sort(allRows, new ApprovalTimeSummaryRowPrincipalComparator(ascending));
        } else if (StringUtils.equals(sortField, TimeApprovalActionForm.ORDER_BY_DOCID)) {
            Collections.sort(allRows, new ApprovalTimeSummaryRowDocIdComparator(ascending));
        } else if (StringUtils.equals(sortField, TimeApprovalActionForm.ORDER_BY_STATUS)) {
            Collections.sort(allRows, new ApprovalTimeSummaryRowStatusComparator(ascending));
        } else {
            // unsorted?
        }

        // TODO : Investigate more efficient row limiting/filtering. We're not really doing much here worthwhile.
        rows = allRows;

        return rows;
    }

}
