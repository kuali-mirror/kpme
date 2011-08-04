package org.kuali.hr.time.approval.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONValue;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.exception.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

public class TimeApprovalAction extends TkAction {

    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TKUser user = TKContext.getUser();
        UserRoles roles = user.getCurrentRoles();

        if (!roles.isAnyApproverActive() && !roles.isSystemAdmin()) {
            throw new AuthorizationException(user.getPrincipalId(), "TimeApprovalAction", "");
        }
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //ActionForward fwd = super.execute(mapping, form, request, response);
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        TKUser user = TKContext.getUser();
        Date currentDate;
        PayCalendarEntries selectedPayCalendarEntries = null;
        PayCalendar currentPayCalendar = null;

        // Set current pay calendar entries if present.
        // Correct usage here is to use the beginPeriodDate or Current date -- use the non-specific search.
        // Set Current Date
        if (taaf.getPayCalendarEntriesId() != null) {
            selectedPayCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(taaf.getPayCalendarEntriesId());
            currentDate = selectedPayCalendarEntries.getBeginPeriodDate();
        } else {
            currentDate = TKUtils.getTimelessDate(null);
        }

        // Set pay calendar + current pay calendar entries if present.
        if (taaf.getPayCalendarId() != null) {
            currentPayCalendar = TkServiceLocator.getPayCalendarSerivce().getPayCalendar(taaf.getPayCalendarId());
            if (selectedPayCalendarEntries == null) {
                // Use of non-specific pay calendar entry search.
                selectedPayCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntriesByPayCalendarId(taaf.getPayCalendarId(), currentDate);
            }
        }

        if (currentPayCalendar != null && selectedPayCalendarEntries != null) {
            if (StringUtils.equals(taaf.getCalNav(), "next")) {
                PayCalendarEntries tpce = TkServiceLocator.getPayCalendarEntriesSerivce().getNextPayCalendarEntriesByPayCalendarId(currentPayCalendar.getPayCalendarId(), selectedPayCalendarEntries);
                if (tpce != null) {
                    selectedPayCalendarEntries = tpce;
                }
            } else if (StringUtils.equals(taaf.getCalNav(), "prev")) {
                PayCalendarEntries tpce  = TkServiceLocator.getPayCalendarEntriesSerivce().getPreviousPayCalendarEntriesByPayCalendarId(currentPayCalendar.getPayCalendarId(), selectedPayCalendarEntries);
                if (tpce != null) {
                    selectedPayCalendarEntries = tpce;
                }
            }
        }

        Map<String, PayCalendarEntries> currentPayCalendarEntries = TkServiceLocator.getTimeApproveService().getPayCalendarEntriesForApprover(TKContext.getPrincipalId(), currentDate);
        SortedSet<String> calGroups = new TreeSet<String>(currentPayCalendarEntries.keySet());

        if(calGroups.isEmpty()){
        	return super.execute(mapping, form, request, response);
        }
        // Check pay Calendar Group
        if (StringUtils.isEmpty(taaf.getSelectedPayCalendarGroup())) {
            taaf.setSelectedPayCalendarGroup(calGroups.first());
        }

        // Finally, set our entries, if not set already.
        if (selectedPayCalendarEntries == null) {
            selectedPayCalendarEntries = currentPayCalendarEntries.get(taaf.getSelectedPayCalendarGroup());
        }

        taaf.setPayCalendarId(selectedPayCalendarEntries.getPayCalendarId());
        taaf.setPayCalendarEntriesId(selectedPayCalendarEntries.getPayCalendarEntriesId());
        taaf.setPayBeginDate(selectedPayCalendarEntries.getBeginPeriodDateTime());
        taaf.setPayEndDate(selectedPayCalendarEntries.getEndPeriodDateTime());
        taaf.setName(user.getPrincipalName());

        taaf.setPayCalendarGroups(calGroups);
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeApproveService().getPayCalendarLabelsForApprovalTab(taaf.getPayBeginDate(), taaf.getPayEndDate()));
        taaf.setApprovalRows(getApprovalRows(taaf));

        return super.execute(mapping, form, request, response);
    }

    public ActionForward selectNewPayCalendarGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        taaf.setPayCalendarId(null);
        
    	return mapping.findForward("basic");
    	
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

        List<String> results = new ArrayList<String>();
        for (ApprovalTimeSummaryRow row : taaf.getApprovalRows()) {
            if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_DOCID) && row.getDocumentId().contains(taaf.getSearchTerm())) {
                results.add(row.getDocumentId());
            } else if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_PRINCIPAL) && row.getName().toLowerCase().contains(taaf.getSearchTerm())) {
                results.add(row.getName());
            }

        }

        taaf.setOutputString(JSONValue.toJSONString(results));

        return mapping.findForward("ws");
    }

    /**
     * Action called via AJAX.
     *
     * This is used to get the hours by work area
     */
    public ActionForward getApprovalRowsByWorkArea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;

        List<String> labels = TkServiceLocator.getTimeApproveService().getPayCalendarLabelsForApprovalTab(taaf.getPayBeginDate(), taaf.getPayEndDate());
        List<TimeBlock> lstTimeBlocks = TkServiceLocator.getTimeBlockService().getTimeBlocks(Long.parseLong(taaf.getDocumentId()));
        // work area(s) is a hidden comma-separated string which is generated when the approval table is rendered.
        List<String> workAreas = Arrays.asList(StringUtils.split(taaf.getEmployeeWorkArea(), ","));
        StringBuilder outputHtml = new StringBuilder();

        for (String workArea : workAreas) {
            Map<String, BigDecimal> hourstoPayDapMap = TkServiceLocator.getTimeApproveService().getHoursToPayDayMap(taaf.getPrincipalId(), taaf.getPayBeginDate(), labels, lstTimeBlocks, Long.parseLong(workArea));
            outputHtml.append("<tr class='hours-by-workArea'>");
            outputHtml.append("<td colspan='3'>Work Area: ").append(workArea).append("</td>");
            for (Map.Entry<String, BigDecimal> entry : hourstoPayDapMap.entrySet()) {
                BigDecimal value = entry.getValue();
                outputHtml.append("<td>").append(value.toString()).append("</td>");
            }
            outputHtml.append("<td colspan='2'></td>");
            outputHtml.append("</tr>");
        }

        taaf.setOutputString(outputHtml.toString());

        return mapping.findForward("ws");
    }

    /**
     * Helper method to modify / manage the list of records needed to display approval data to the user.
     *
     * @param taaf
     * @return
     */
    List<ApprovalTimeSummaryRow> getApprovalRows(TimeApprovalActionForm taaf) {
    	List<ApprovalTimeSummaryRow> rows = new ArrayList<ApprovalTimeSummaryRow>();

    	if(taaf.getPayCalendarGroups().size() > 0){
            String calGroup = StringUtils.isNotEmpty(taaf.getSelectedPayCalendarGroup()) ? taaf.getSelectedPayCalendarGroup() : taaf.getPayCalendarGroups().first();
            rows = TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(taaf.getPayBeginDate(), taaf.getPayEndDate(), calGroup, taaf.getWorkArea());

            if (!taaf.isAjaxCall() && StringUtils.isNotBlank(taaf.getSearchField()) && StringUtils.isNotBlank(taaf.getSearchTerm())) {
                rows = searchApprovalRows(rows, taaf.getSearchField(), taaf.getSearchTerm());
            }

            sortApprovalRows(rows, taaf.getSortField(), taaf.isAscending());

            // Create a sublist view backed by the actual list.
            int limit = (taaf.getRowsToShow() > rows.size()) ? rows.size() : taaf.getRowsToShow();

            return rows.subList(0, limit);
    	}
    	return rows;
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
