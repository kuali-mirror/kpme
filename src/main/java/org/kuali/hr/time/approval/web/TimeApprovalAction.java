package org.kuali.hr.time.approval.web;

import com.google.common.collect.Ordering;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.exception.AuthorizationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        ActionForward fwd = super.execute(mapping, form, request, response);
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        TKUser user = TKContext.getUser();
        Date currentDate;
        CalendarEntries selectedCalendarEntries = null;
        Calendar currentCalendar = null;

        // Set calendar groups
        List<String> calGroups = TkServiceLocator.getTimeApproveService().getUniquePayGroups();
        taaf.setCalendarGroups(calGroups);

        // Set department
        taaf.setDepartments(user.getReportingApprovalDepartments());
        if (taaf.getDepartments().size() == 1) {
            taaf.setSelectedDept(taaf.getDepartments().get(0));
            taaf.setSelectedCalendarGroup(calGroups.get(0));
        }
        if (StringUtils.isBlank(taaf.getSelectedDept())) {
            return super.execute(mapping, form, request, response);
        }

        // Set work areas
        // If "show all" is selected, add all workareas to the list
        SortedSet<Long> workAreas = new TreeSet<Long>();
        if (StringUtils.isBlank(taaf.getSelectedWorkArea())) {
            workAreas = user.getWorkAreasFromUserRoles();
            taaf.setDeptWorkareas(workAreas);
        } else {
            workAreas.add(Long.parseLong(taaf.getSelectedWorkArea()));
        }

        // Set current pay calendar entries if present.
        // Decide if the current date should be today or the end period date
        if (taaf.getHrPyCalendarEntriesId() != null) {
            selectedCalendarEntries = TkServiceLocator.getCalendarEntriesSerivce().getCalendarEntries(taaf.getHrPyCalendarEntriesId());
            currentDate = selectedCalendarEntries.getEndPeriodDate();
        } else {
            currentDate = TKUtils.getTimelessDate(null);
        }

        if (taaf.getHrPyCalendarEntriesId() == null || selectedCalendarEntries == null) {
            currentCalendar = TkServiceLocator.getCalendarSerivce().getCalendarByGroup(taaf.getSelectedCalendarGroup());
            selectedCalendarEntries = TkServiceLocator.getCalendarEntriesSerivce().getCurrentCalendarEntriesByCalendarId(currentCalendar.getHrCalendarId(), currentDate);
        }

        if (selectedCalendarEntries != null) {
            CalendarEntries tpce = TkServiceLocator.getCalendarEntriesSerivce().getPreviousCalendarEntriesByCalendarId(selectedCalendarEntries.getHrCalendarId(), selectedCalendarEntries);
            if (tpce != null && TkServiceLocator.getTimeApproveService().doesApproverHavePrincipalsForCalendarGroup(tpce.getEndPeriodDateTime(), tpce.getCalendarName())) {
                taaf.setPrevCalendarId(tpce.getHrCalendarEntriesId());
            } else {
                taaf.setPrevCalendarId(null);
            }
            tpce = TkServiceLocator.getCalendarEntriesSerivce().getNextCalendarEntriesByCalendarId(selectedCalendarEntries.getHrCalendarId(), selectedCalendarEntries);
            if (tpce != null && TkServiceLocator.getTimeApproveService().doesApproverHavePrincipalsForCalendarGroup(tpce.getEndPeriodDateTime(), tpce.getCalendarName())) {
                taaf.setNextCalendarId(tpce.getHrCalendarEntriesId());
            } else {
                taaf.setNextCalendarId(null);
            }
        }


        taaf.setHrPyCalendarId(selectedCalendarEntries.getHrCalendarId());
        taaf.setHrPyCalendarEntriesId(selectedCalendarEntries.getHrCalendarEntriesId());
        taaf.setPayBeginDate(selectedCalendarEntries.getBeginPeriodDateTime());
        taaf.setPayEndDate(selectedCalendarEntries.getEndPeriodDateTime());
        taaf.setName(user.getPrincipalName());

        // TODO:
        // Getting principal ids from active assignments is a single sql query which runs pretty fast.
        // If the total number of work areas is large, some optimization needs to be done to reduce the db calls.

        List<String> principalIds = TkServiceLocator.getTimeApproveService().getPrincipalIdsByAssignment(workAreas, new java.sql.Date(taaf.getPayEndDate().getTime()), taaf.getSelectedCalendarGroup());
        taaf.setResultSize(principalIds.size());

        if (StringUtils.isNotBlank(getSortField(request))) {
            principalIds = getSortedPrincipalIdList(getSortField(request), isAscending(request), principalIds,
                    new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()));
        }
        taaf.setApprovalRows(getApprovalRows(taaf, getSubListPrincipalIds(request, principalIds), taaf.getSelectedCalendarGroup()));
        taaf.setCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(selectedCalendarEntries, new ArrayList<Boolean>()));



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

    public ActionForward selectNewCalendarGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        taaf.setHrPyCalendarEntriesId(null);
        taaf.setHrPyCalendarId(null);
        taaf.setPayBeginDate(null);
        taaf.setPayEndDate(null);
        return mapping.findForward("basic");
    }

    /**
     * Helper method to modify / manage the list of records needed to display approval data to the user.
     *
     * @param taaf
     * @return
     */
    List<ApprovalTimeSummaryRow> getApprovalRows(TimeApprovalActionForm taaf, List<String> assignmentPrincipalIds, String calGroup) {

        if(assignmentPrincipalIds.size() == 0) {
            return new ArrayList<ApprovalTimeSummaryRow>();
        }

        if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_PRINCIPAL)) {
            assignmentPrincipalIds = new ArrayList<String>();
            assignmentPrincipalIds.add(taaf.getSearchTerm());
        }
        else if(StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_DOCID)) {

        }
        return TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(taaf.getPayBeginDate(), taaf.getPayEndDate(), calGroup, assignmentPrincipalIds);
    }

    // move this to the service layer
    private List<String> getSortedPrincipalIdList(String sortField, Boolean isAscending, List<String> assignmentPrincipalIds, java.sql.Date payBeginDate, java.sql.Date payEndDate) {

        List<String> principalIds = new LinkedList<String>();

        // order by principal name
        if (StringUtils.equals(sortField, TimeApprovalActionForm.ORDER_BY_PRINCIPAL)) {
            List<Person> people = new LinkedList<Person>();
            for (String principalId : assignmentPrincipalIds) {
                Person person = KIMServiceLocator.getPersonService().getPerson(principalId);
                people.add(person);
            }

            Comparator<Person> nameComparator = new Comparator<Person>() {
                @Override
                public int compare(Person person, Person person1) {
                    return person.getName().compareToIgnoreCase(person1.getName());
                }
            };

            // Ordering is an abstract class which implements java Comparator from google's common lib - guava
            // More information could be found here: http://google-collections.googlecode.com/svn/trunk/javadoc/com/google/common/collect/Ordering.html
            // You can do fancy things, like chaining the ordering e.g.
            //
            // ordering.reverse().compound(firstNameComparator);
            //
            // the code above will sort by the lastname first and then the firstname

            // Order by name ascending
            Ordering<Person> ordering = Ordering.from(nameComparator);
            if (!isAscending) {
                // This is when guava comes in handy!
                ordering = ordering.reverse();
            }

            // nullsFirst: "Returns an ordering that treats null as less than all other values and uses this to compare non-null values."
            for (Person p : ordering.nullsFirst().sortedCopy(people)) {
                principalIds.add(p.getPrincipalId());
            }
            // order by document id
        } else if (StringUtils.equals(sortField, TimeApprovalActionForm.ORDER_BY_DOCID)) {
            Map<String, TimesheetDocumentHeader> principalDocumentHeaders =
                    TkServiceLocator.getTimeApproveService().getPrincipalDocumehtHeader(assignmentPrincipalIds, payBeginDate, payEndDate);

            Comparator<TimesheetDocumentHeader> docIdComparator = new Comparator<TimesheetDocumentHeader>() {
                @Override
                public int compare(TimesheetDocumentHeader tdh, TimesheetDocumentHeader tdh1) {
                    return Long.valueOf(tdh.getDocumentId()).compareTo(Long.valueOf(tdh1.getDocumentId()));
                }
            };

            Ordering<TimesheetDocumentHeader> ordering = Ordering.from(docIdComparator);
            if (!isAscending) {
                // This is when guava comes in handy!
                ordering = ordering.reverse();
            }

            for (TimesheetDocumentHeader tdh : ordering.nullsFirst().sortedCopy(principalDocumentHeaders.values())) {
                principalIds.add(tdh.getPrincipalId());
            }

            // add the principal who doesn't have a current timesheet document back to the list
            for (String id : assignmentPrincipalIds) {
                if (principalIds.contains(id)) {
                    continue;
                } else {
                    principalIds.add(id);
                }

            }
        }

        return principalIds;
    }

    private String getSortField(HttpServletRequest request) {
        return request.getParameter((new ParamEncoder(TkConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_SORT)));
    }

    private Boolean isAscending(HttpServletRequest request) {
        // returned value 1 = ascending; 2 = descending
        String ascending = request.getParameter((new ParamEncoder(TkConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_ORDER)));
        return StringUtils.equals(ascending, "1") ? true : false;
    }

    // move this to the service layer
    private List<String> getSubListPrincipalIds(HttpServletRequest request, List<String> assignmentPrincipalIds) {
        String page = request.getParameter((new ParamEncoder(TkConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
        // The paging index begins from 1, but the sublist index begins from 0.
        // So the logic below sets the sublist begin index to 0 if the page number is null or equals 1
        Integer beginIndex = StringUtils.isBlank(page) || StringUtils.equals(page, "1") ? 0 : Integer.parseInt(page);
        Integer endIndex = beginIndex + TkConstants.PAGE_SIZE > assignmentPrincipalIds.size() ? assignmentPrincipalIds.size() : beginIndex + TkConstants.PAGE_SIZE;

        return assignmentPrincipalIds.subList(beginIndex, endIndex);
    }

}
