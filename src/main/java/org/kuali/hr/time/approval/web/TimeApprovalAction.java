package org.kuali.hr.time.approval.web;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.json.simple.JSONValue;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
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

        if (!roles.isTimesheetReviewer() && !roles.isAnyApproverActive() && !roles.isSystemAdmin() && !roles.isLocationAdmin() && !roles.isGlobalViewOnly() && !roles.isDeptViewOnly() && !roles.isDepartmentAdmin()) {
            throw new AuthorizationException(user.getPrincipalId(), "TimeApprovalAction", "");
        }
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        long beginTs = System.currentTimeMillis();
        ActionForward fwd = super.execute(mapping, form, request, response);
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        TKUser user = TKContext.getUser();
        Date currentDate;
        PayCalendarEntries selectedPayCalendarEntries = null;
        PayCalendar currentPayCalendar = null;

        // Set calendar groups
        List<String> calGroups = TkServiceLocator.getTimeApproveService().getUniquePayGroups();
        taaf.setPayCalendarGroups(calGroups);

        // Set department
        taaf.setDepartments(user.getReportingApprovalDepartments());
        if (taaf.getDepartments().size() == 1) {
            taaf.setSelectedDept(taaf.getDepartments().get(0));
        }
        if (StringUtils.isBlank(taaf.getSelectedDept())) {
            return super.execute(mapping, form, request, response);
        }

        // Set work areas
        SortedSet<Long> workAreas = new TreeSet<Long>();
        if (StringUtils.isBlank(taaf.getSelectedWorkArea())) {
            workAreas = getWorkAreasFromUserRoles(user.getCurrentRoles());
            taaf.setDeptWorkareas(workAreas);
        } else {
            workAreas.add(Long.parseLong(taaf.getSelectedWorkArea()));
        }

        // Set current pay calendar entries if present.
        // Decide if the current date should be today or the end period date
        if (taaf.getHrPyCalendarEntriesId() != null) {
            selectedPayCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(taaf.getHrPyCalendarEntriesId());
            currentDate = selectedPayCalendarEntries.getEndPeriodDate();
        } else {
            currentDate = TKUtils.getTimelessDate(null);
        }

        if (taaf.getHrPyCalendarEntriesId() == null || selectedPayCalendarEntries == null) {
            currentPayCalendar = TkServiceLocator.getPayCalendarSerivce().getPayCalendarByGroup(taaf.getSelectedPayCalendarGroup());
            selectedPayCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntriesByPayCalendarId(currentPayCalendar.getHrPyCalendarId(), currentDate);
        }

        if (selectedPayCalendarEntries != null) {
            PayCalendarEntries tpce = TkServiceLocator.getPayCalendarEntriesSerivce().getPreviousPayCalendarEntriesByPayCalendarId(selectedPayCalendarEntries.getHrPyCalendarId(), selectedPayCalendarEntries);
            if (tpce != null && TkServiceLocator.getTimeApproveService().doesApproverHavePrincipalsForCalendarGroup(tpce.getEndPeriodDateTime(), tpce.getPyCalendarGroup())) {
                taaf.setPrevPayCalendarId(tpce.getHrPyCalendarEntriesId());
            } else {
                taaf.setPrevPayCalendarId(null);
            }
            tpce = TkServiceLocator.getPayCalendarEntriesSerivce().getNextPayCalendarEntriesByPayCalendarId(selectedPayCalendarEntries.getHrPyCalendarId(), selectedPayCalendarEntries);
            if (tpce != null && TkServiceLocator.getTimeApproveService().doesApproverHavePrincipalsForCalendarGroup(tpce.getEndPeriodDateTime(), tpce.getPyCalendarGroup())) {
                taaf.setNextPayCalendarId(tpce.getHrPyCalendarEntriesId());
            } else {
                taaf.setNextPayCalendarId(null);
            }
        }

        taaf.setHrPyCalendarId(selectedPayCalendarEntries.getHrPyCalendarId());
        taaf.setHrPyCalendarEntriesId(selectedPayCalendarEntries.getHrPyCalendarEntriesId());
        taaf.setPayBeginDate(selectedPayCalendarEntries.getBeginPeriodDateTime());
        taaf.setPayEndDate(selectedPayCalendarEntries.getEndPeriodDateTime());
        taaf.setName(user.getPrincipalName());
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(selectedPayCalendarEntries, new ArrayList<Boolean>()));

        // TODO:
        // Getting principal ids from active assignments are a single sql query, which should run pretty fast.
        // If the total number of work areas is large, some optimization needs to be done to reduce the db calls.

        List<String> principalIds = TkServiceLocator.getTimeApproveService().getPrincipalIdsByAssignment(workAreas, new java.sql.Date(taaf.getPayEndDate().getTime()), taaf.getSelectedPayCalendarGroup());
        taaf.setAssignmentPrincipalIds(principalIds);
        taaf.setResultSize(taaf.getAssignmentPrincipalIds().size());

        long endTs = System.currentTimeMillis();
        BigDecimal diff = new BigDecimal((endTs - beginTs) / 1000.0).setScale(TkConstants.BIG_DECIMAL_SCALE, TkConstants.BIG_DECIMAL_SCALE_ROUNDING);
        System.out.println("Processing time before approval rows : " + diff + " secs");

        taaf.setApprovalRows(getApprovalRows(taaf, getSubListPrincipalIds(request, taaf.getAssignmentPrincipalIds()), taaf.getSelectedPayCalendarGroup()));


        return fwd;
    }

    public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        List<ApprovalTimeSummaryRow> lstApprovalRows = taaf.getApprovalRows();
        for (ApprovalTimeSummaryRow ar : lstApprovalRows) {
            if (ar.isApprovable() && StringUtils.equals(ar.getSelected(), "on")) {
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

    /**
     * Action called via AJAX. (ajaj really...)
     * <p/>
     * This search returns quick-results to the search box for the user to further
     * refine upon. The end value can then be form submitted.
     */
    public ActionForward searchApprovalRows(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
//        taaf.setApprovalRows(getApprovalRows(taaf));
        List<String> results = new ArrayList<String>();

        for (ApprovalTimeSummaryRow row : taaf.getApprovalRows()) {
            if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_DOCID) && row.getDocumentId().contains(taaf.getSearchTerm())) {
                results.add(row.getDocumentId());
            } else if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_PRINCIPAL) && row.getName().toLowerCase().contains(taaf.getSearchTerm().toLowerCase())) {
                results.add(row.getName());
//            } else if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_WORKAREA)) {
//                for(String wa : row.getWorkAreas()) {
//                    if(StringUtils.equals(wa, taaf.getSearchTerm())) {
//                        results.add(wa);
//                    }
//                }
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
    List<ApprovalTimeSummaryRow> getApprovalRows(TimeApprovalActionForm taaf, List<String> assignmentPrincipalIds, String calGroup) {
        List<ApprovalTimeSummaryRow> rows = new ArrayList<ApprovalTimeSummaryRow>();

        if (taaf.getPayCalendarGroups().size() > 0) {
//            String calGroup = StringUtils.isNotEmpty(taaf.getSelectedPayCalendarGroup()) ? taaf.getSelectedPayCalendarGroup() : taaf.getPayCalendarGroups().get(0);
//            List<WorkArea> workAreaObjs = TkServiceLocator.getWorkAreaService().getWorkAreas(taaf.getSelectedDept(), new java.sql.Date(taaf.getPayEndDate().getTime()));
//            //TODO push this up
//            List<Long> workAreas = new ArrayList<Long>();
//            for (WorkArea wa : workAreaObjs) {
//                workAreas.add(wa.getWorkArea());
//            }
            rows = TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(taaf.getPayBeginDate(), taaf.getPayEndDate(), calGroup, assignmentPrincipalIds);

//            if (StringUtils.isNotBlank(taaf.getSearchField()) && StringUtils.isNotBlank(taaf.getSearchTerm())) {
//                rows = filterApprovalRows(rows, taaf.getSearchField(), taaf.getSearchTerm());
//            }

//            sortApprovalRows(rows, taaf.getSortField(), taaf.isAscending());

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
//            } else if (StringUtils.equals(searchField, TimeApprovalActionForm.ORDER_BY_WORKAREA)) {
//                for(String wa : row.getWorkAreas()) {
//                    if(StringUtils.equals(wa, searchTerm)) {
//                        filteredRows.add(row);
//                    }
//                }
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

    private SortedSet<Long> getWorkAreasFromUserRoles(UserRoles userRoles) {
        SortedSet<Long> workAreas = new TreeSet<Long>();
        workAreas.addAll(userRoles.getApproverWorkAreas());
        workAreas.addAll(userRoles.getReviewerWorkAreas());

        return workAreas;
    }

//    private List<String> sortedPrincipalIdList(HttpServletRequest request, List<String> assignmentPrincipalIds) {
//        String sortField = request.getParameter((new ParamEncoder(TkConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_SORT)));
//        Boolean ascending = Boolean.parseBoolean(request.getParameter((new ParamEncoder(TkConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_SORTUSINGNAME))));
//
//        if (StringUtils.equals(sortField, TimeApprovalActionForm.ORDER_BY_PRINCIPAL)) {
//            for(String principalId : assignmentPrincipalIds) {
//
//            }
//        } else if (StringUtils.equals(sortField, TimeApprovalActionForm.ORDER_BY_DOCID)) {
//
//        }
//    }

    private List<String> getSubListPrincipalIds(HttpServletRequest request, List<String> assignmentPrincipalIds) {
        String page = request.getParameter((new ParamEncoder(TkConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
        Integer beginIndex = StringUtils.isBlank(page) ? 0 : Integer.parseInt(page);
        Integer endIndex = beginIndex + TkConstants.PAGE_SIZE > assignmentPrincipalIds.size() ? assignmentPrincipalIds.size() : beginIndex + TkConstants.PAGE_SIZE;

        return assignmentPrincipalIds.subList(beginIndex, endIndex);
    }

}
