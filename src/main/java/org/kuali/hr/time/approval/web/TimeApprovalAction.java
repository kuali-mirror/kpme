package org.kuali.hr.time.approval.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONValue;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.web.TimesheetAction;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeApprovalAction extends TimesheetAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.execute(mapping, form, request, response);
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        TKUser user = TKContext.getUser();

        // TODO: Obtain this via form?
        // Pay Begin/End needs to come from somewhere tangible, hard coded for now.
        TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(taaf.getDocumentId());
        taaf.setPayBeginDate(tdh.getPayBeginDate());
        taaf.setPayEndDate(tdh.getPayEndDate());

        taaf.setName(user.getPrincipalName());
        taaf.setPayCalendarGroups(TkServiceLocator.getTimeApproveService().getApproverPayCalendarGroups(taaf.getPayBeginDate(), taaf.getPayEndDate()));
        taaf.setApprovalRows(getApprovalRows(taaf));
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeApproveService().getPayCalendarLabelsForApprovalTab(taaf.getPayBeginDate(), taaf.getPayEndDate()));

        return forward;
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
     *
     * This search returns quick-results to the search box for the user to further
     * refine upon. The end value can then be form submitted.
     */
    public ActionForward searchApprovalRows(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        taaf.setPayBeginDate(taaf.getTimesheetDocument().getDocumentHeader().getPayBeginDate());
        taaf.setPayEndDate(taaf.getTimesheetDocument().getDocumentHeader().getPayEndDate());

        List<String> results = new ArrayList<String>();
        // TODO: create a field for the work area and figure out how to get the work area
        for (ApprovalTimeSummaryRow row : this.getApprovalRows(taaf)) {
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
     * @param taaf
     * @return
     */
    List<ApprovalTimeSummaryRow> getApprovalRows(TimeApprovalActionForm taaf) {

        String calGroup = StringUtils.isNotEmpty(taaf.getSelectedPayCalendarGroup()) ? taaf.getSelectedPayCalendarGroup() : taaf.getPayCalendarGroups().first();
        List<ApprovalTimeSummaryRow> rows = TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(taaf.getPayBeginDate(), taaf.getPayEndDate(), calGroup, null);

        if (!taaf.isAjaxCall() && StringUtils.isNotBlank(taaf.getSearchField()) && StringUtils.isNotBlank(taaf.getSearchTerm())) {
            rows = searchApprovalRows(rows, taaf.getSearchField(), taaf.getSearchTerm());
        }

        sortApprovalRows(rows, taaf.getSortField(), taaf.isAscending());

        // Create a sublist view backed by the actual list.
        int limit = (taaf.getRowsToShow() > rows.size()) ? rows.size() : taaf.getRowsToShow();

        return rows.subList(0, limit);
    }

    List<ApprovalTimeSummaryRow> searchApprovalRows(List<ApprovalTimeSummaryRow> rows, String searchField, String searchTerm) {
        List<ApprovalTimeSummaryRow> filteredRows = new ArrayList<ApprovalTimeSummaryRow>();

        for (ApprovalTimeSummaryRow row : rows) {
            if (StringUtils.equals(searchField, TimeApprovalActionForm.ORDER_BY_DOCID) && row.getDocumentId().contains(searchTerm)) {
                filteredRows.add(row);
            } else if (StringUtils.equals(searchField, TimeApprovalActionForm.ORDER_BY_PRINCIPAL) && row.getName().contains(searchTerm)) {
                filteredRows.add(row);
            }
        }

        return filteredRows;
    }

    void sortApprovalRows(List<ApprovalTimeSummaryRow> rows, String sortField, boolean ascending) {
        if (StringUtils.equals(sortField, TimeApprovalActionForm.ORDER_BY_PRINCIPAL)) {
            Collections.sort(rows, new ApprovalTimeSummaryRowPrincipalComparator(ascending));
        } else if (StringUtils.equals(sortField, TimeApprovalActionForm.ORDER_BY_DOCID)) {
            Collections.sort(rows, new ApprovalTimeSummaryRowDocIdComparator(ascending));
        } else if (StringUtils.equals(sortField, TimeApprovalActionForm.ORDER_BY_STATUS)) {
            Collections.sort(rows, new ApprovalTimeSummaryRowStatusComparator(ascending));
        }
    }

    List<ApprovalTimeSummaryRow> filterCalendarType(String calendarType) {
        return null;
    }

}
