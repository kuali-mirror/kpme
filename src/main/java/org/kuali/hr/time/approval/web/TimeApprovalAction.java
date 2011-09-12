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
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
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

        if (!roles.isAnyApproverActive() && !roles.isSystemAdmin() && !roles.isLocationAdmin() && !roles.isGlobalViewOnly() && !roles.isDeptViewOnly() && !roles.isDepartmentAdmin()) {
            throw new AuthorizationException(user.getPrincipalId(), "TimeApprovalAction", "");
        }
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ActionForward fwd = super.execute(mapping, form, request, response);
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        TKUser user = TKContext.getUser();
        Date currentDate;
        PayCalendarEntries selectedPayCalendarEntries = null;
        PayCalendar currentPayCalendar = null;
        
        taaf.setDepartments(user.getReportingApprovalDepartments());
        if(taaf.getDepartments().size() == 1){
        	taaf.setSelectedDept(taaf.getDepartments().get(0));
        }
        
        if(StringUtils.isBlank(taaf.getSelectedDept())){
        	return super.execute(mapping, form, request, response);
        }

        // Set current pay calendar entries if present.
        // Correct usage here is to use the beginPeriodDate or Current date -- use the non-specific search.
        // Set Current Date
        if (taaf.getHrPyCalendarEntriesId() != null) {
            selectedPayCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(taaf.getHrPyCalendarEntriesId());
            currentDate = selectedPayCalendarEntries.getEndPeriodDate();
        } else {
            currentDate = TKUtils.getTimelessDate(null);
        }

        // Set pay calendar + current pay calendar entries if present.
        if (taaf.getHrPyCalendarId() != null) {
            currentPayCalendar = TkServiceLocator.getPayCalendarSerivce().getPayCalendar(taaf.getHrPyCalendarId());
            if (selectedPayCalendarEntries == null) {
                // Use of non-specific pay calendar entry search.
                selectedPayCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntriesByPayCalendarId(taaf.getHrPyCalendarId(), currentDate);
            }
        }
        
        Map<String, PayCalendarEntries> currentPayCalendarEntries = TkServiceLocator.getTimeApproveService().getPayCalendarEntriesForDept( taaf.getSelectedDept(), currentDate);
        SortedSet<String> calGroups = new TreeSet<String>(currentPayCalendarEntries.keySet());

        if(calGroups.isEmpty()){
        	taaf.setSelectedPayCalendarGroup(null);
        	taaf.setPayCalendarGroups(null);
        	taaf.setApprovalRows(null);
        	return super.execute(mapping, form, request, response);
        }
        // Check pay Calendar Group
        if (StringUtils.isEmpty(taaf.getSelectedPayCalendarGroup())) {
            taaf.setSelectedPayCalendarGroup(calGroups.first());
        }
        
        if(selectedPayCalendarEntries == null){
        	currentPayCalendar = TkServiceLocator.getPayCalendarSerivce().getPayCalendarByGroup(taaf.getSelectedPayCalendarGroup());
        	selectedPayCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntriesByPayCalendarId(currentPayCalendar.getHrPyCalendarId(), currentDate);
        }
        
        if (selectedPayCalendarEntries != null) {
        	PayCalendarEntries tpce  = TkServiceLocator.getPayCalendarEntriesSerivce().getPreviousPayCalendarEntriesByPayCalendarId(currentPayCalendar.getHrPyCalendarId(), selectedPayCalendarEntries);
        	if(tpce != null && TkServiceLocator.getTimeApproveService().doesApproverHavePrincipalsForCalendarGroup(tpce.getEndPeriodDateTime(), tpce.getPyCalendarGroup())){
        		taaf.setPrevPayCalendarId(tpce.getHrPyCalendarEntriesId());
        	} else {
        		taaf.setPrevPayCalendarId(null);
        	}
        	tpce = TkServiceLocator.getPayCalendarEntriesSerivce().getNextPayCalendarEntriesByPayCalendarId(currentPayCalendar.getHrPyCalendarId(), selectedPayCalendarEntries);
        	if(tpce != null && TkServiceLocator.getTimeApproveService().doesApproverHavePrincipalsForCalendarGroup(tpce.getEndPeriodDateTime(), tpce.getPyCalendarGroup())){
        		taaf.setNextPayCalendarId(tpce.getHrPyCalendarEntriesId());
        	} else {
        		taaf.setNextPayCalendarId(null);
        	}
        }


        // Finally, set our entries, if not set already.
        if (selectedPayCalendarEntries == null) {
            selectedPayCalendarEntries = currentPayCalendarEntries.get(taaf.getSelectedPayCalendarGroup());
        }

        taaf.setHrPyCalendarId(selectedPayCalendarEntries.getHrPyCalendarId());
        taaf.setHrPyCalendarEntriesId(selectedPayCalendarEntries.getHrPyCalendarEntriesId());
        taaf.setPayBeginDate(selectedPayCalendarEntries.getBeginPeriodDateTime());
        taaf.setPayEndDate(selectedPayCalendarEntries.getEndPeriodDateTime());
        taaf.setName(user.getPrincipalName());

        taaf.setPayCalendarGroups(calGroups);
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeApproveService().getPayCalendarLabelsForApprovalTab(taaf.getPayBeginDate(), taaf.getPayEndDate()));
        taaf.setApprovalRows(getApprovalRows(taaf));
        return fwd;
    }
    
    public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
    	List<ApprovalTimeSummaryRow> lstApprovalRows = taaf.getApprovalRows();
    	for(ApprovalTimeSummaryRow ar: lstApprovalRows){
    		if(ar.isApprovable() && StringUtils.equals(ar.getSelected(), "on")){
    			String documentNumber = ar.getDocumentId();
    			TimesheetDocument tDoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentNumber);
    			TkServiceLocator.getTimesheetService().approveTimesheet(TKContext.getPrincipalId(), tDoc);
    		}
    	}
    	return mapping.findForward("basic");
    	
    }

    public ActionForward selectNewPayCalendarGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
    	taaf.setHrPyCalendarEntriesId(null);
    	taaf.setHrPyCalendarId(null);
    	taaf.setPayBeginDate(null);
    	taaf.setPayEndDate(null);
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
        taaf.setApprovalRows(getApprovalRows(taaf));
        List<String> results = new ArrayList<String>();

        for (ApprovalTimeSummaryRow row : taaf.getApprovalRows()) {
            if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_DOCID) && row.getDocumentId().contains(taaf.getSearchTerm())) {
                results.add(row.getDocumentId());
            } else if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_PRINCIPAL) && row.getName().toLowerCase().contains(taaf.getSearchTerm().toLowerCase())) {
                results.add(row.getName());
            } else if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_WORKAREA)) {
                for(String wa : row.getWorkAreas()) {
                    if(StringUtils.equals(wa, taaf.getSearchTerm())) {
                        results.add(wa);
                    }
                }
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
            List<WorkArea> workAreaObjs = TkServiceLocator.getWorkAreaService().getWorkAreas(taaf.getSelectedDept(), new java.sql.Date(taaf.getPayEndDate().getTime()));
            //TODO push this up
            List<Long> workAreas = new ArrayList<Long>();
            for(WorkArea wa : workAreaObjs){
            	workAreas.add(wa.getWorkArea());
            }
            rows = TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(taaf.getPayBeginDate(), taaf.getPayEndDate(), calGroup, workAreas);

            if (StringUtils.isNotBlank(taaf.getSearchField()) && StringUtils.isNotBlank(taaf.getSearchTerm())) {
                rows = filterApprovalRows(rows, taaf.getSearchField(), taaf.getSearchTerm());
            }

            sortApprovalRows(rows, taaf.getSortField(), taaf.isAscending());

            // Create a sublist view backed by the actual list.
            //int limit = (taaf.getRowsToShow() > rows.size()) ? rows.size() : taaf.getRowsToShow();

            //return rows.subList(0, limit);
            return rows;
    	}
    	return rows;
    }

    List<ApprovalTimeSummaryRow> filterApprovalRows(List<ApprovalTimeSummaryRow> rows, String searchField, String searchTerm) {
        List<ApprovalTimeSummaryRow> filteredRows = new ArrayList<ApprovalTimeSummaryRow>();

        for (ApprovalTimeSummaryRow row : rows) {
            if (StringUtils.equals(searchField, TimeApprovalActionForm.ORDER_BY_DOCID) && row.getDocumentId().contains(searchTerm)) {
                filteredRows.add(row);
            } else if (StringUtils.equals(searchField, TimeApprovalActionForm.ORDER_BY_PRINCIPAL) && row.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredRows.add(row);
            } else if (StringUtils.equals(searchField, TimeApprovalActionForm.ORDER_BY_WORKAREA)) {
                for(String wa : row.getWorkAreas()) {
                    if(StringUtils.equals(wa, searchTerm)) {
                        filteredRows.add(row);
                    }
                }
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

    private ApprovalTimeSummaryRow[] convertToArray(List<ApprovalTimeSummaryRow> rows) {
        return rows.toArray(new ApprovalTimeSummaryRow[rows.size()]);
    }

}
