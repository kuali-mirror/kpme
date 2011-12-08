package org.kuali.hr.time.approval.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.principal.calendar.PrincipalCalendar;
import org.kuali.hr.time.roles.UserRoles;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.exception.AuthorizationException;

import com.google.common.collect.Ordering;

public class TimeApprovalAction extends TkAction{
	
	public ActionForward searchResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeApprovalActionForm taaf = (TimeApprovalActionForm)form;
		String searchField = taaf.getSearchField();
		String searchTerm = taaf.getSearchTerm();
        String principalId = searchTerm;
        PayCalendarEntries selectedPayCalendarEntry = null;
        
        if(StringUtils.equals("documentId", searchField)){
        	TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(searchTerm);
        	principalId = tdh.getPrincipalId();
        	PrincipalCalendar principalCalendar = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(principalId, TKUtils.getCurrentDate());
        	PayCalendar payCal = TkServiceLocator.getPayCalendarSerivce().getPayCalendarByGroup(principalCalendar.getPyCalendarGroup());
        	selectedPayCalendarEntry = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntriesByIdAndPeriodEndDate(payCal.getHrPyCalendarId(), tdh.getPayEndDate());
        	taaf.setSearchField("principalId");
        	taaf.setSearchTerm(principalId);
        } else {
			PrincipalCalendar principalCalendar = TkServiceLocator.getPrincipalCalendarService().getPrincipalCalendar(searchTerm, TKUtils.getCurrentDate());
			PayCalendar payCal = TkServiceLocator.getPayCalendarSerivce().getPayCalendarByGroup(principalCalendar.getPyCalendarGroup());
			selectedPayCalendarEntry = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntriesByPayCalendarId(payCal.getHrPyCalendarId(), TKUtils.getCurrentDate());
		} 
		
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, selectedPayCalendarEntry.getEndPeriodDate());
        if(!assignments.isEmpty()){
        	taaf.setSelectedDept(assignments.get(0).getDept());
        	taaf.setSelectedWorkArea(assignments.get(0).getWorkArea().toString());
        }
        
		taaf.setSelectedPayCalendarGroup(selectedPayCalendarEntry.getPyCalendarGroup());
		
        taaf.setHrPyCalendarId(selectedPayCalendarEntry.getHrPyCalendarId());
        taaf.setHrPyCalendarEntriesId(selectedPayCalendarEntry.getHrPyCalendarEntriesId());
        taaf.setPayBeginDate(selectedPayCalendarEntry.getBeginPeriodDateTime());
        taaf.setPayEndDate(selectedPayCalendarEntry.getEndPeriodDateTime());
        taaf.setName(TKContext.getUser().getPrincipalName());
        taaf.setResultSize(1);
        
        List<String> principalIds = new ArrayList<String>();
        principalIds.add(principalId);
        taaf.setApprovalRows(getApprovalRows(taaf, principalIds, taaf.getSelectedPayCalendarGroup()));
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(selectedPayCalendarEntry, new ArrayList<Boolean>()));
		return mapping.findForward("basic");
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
    
	public ActionForward selectNewDept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeApprovalActionForm taaf = (TimeApprovalActionForm)form;
		Set<Long> workAreasForQuery = new HashSet<Long>();
		taaf.getDeptWorkareas().clear();
		taaf.setSearchField(null);
		taaf.setSearchTerm(null);
		
        if (StringUtils.isBlank(taaf.getSelectedWorkArea())) {
        	List<WorkArea> workAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(taaf.getSelectedDept(), new java.sql.Date(taaf.getPayBeginDate().getTime()));
            for(WorkArea wa : workAreas){
            	if (TKContext.getUser().getCurrentRoles().getApproverWorkAreas().contains(wa.getWorkArea())
            			|| TKContext.getUser().getCurrentRoles().getReviewerWorkAreas().contains(wa.getWorkArea())) {
            		taaf.getDeptWorkareas().add(wa.getWorkArea());
            	}
            }
        	workAreasForQuery = taaf.getDeptWorkareas();
        } else {
            workAreasForQuery.add(Long.parseLong(taaf.getSelectedWorkArea()));
        }
        
		Set<String> principalIds = TkServiceLocator.getTimeApproveService().getPrincipalIdsByWorkAreas(workAreasForQuery, new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()), taaf.getSelectedPayCalendarGroup());
        taaf.setResultSize(principalIds.size());
        taaf.setPrincipalIds(principalIds);
        Set<String> sortedPrincipalIds = getSortedPrincipalIdList(StringUtils.isEmpty(getSortField(request)) ? TimeApprovalActionForm.ORDER_BY_PRINCIPAL : getSortField(request), isAscending(request), new LinkedList<String>(principalIds),
                    new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()));
        taaf.setApprovalRows(getApprovalRows(taaf, getSubListPrincipalIds(request, new LinkedList<String>(sortedPrincipalIds)), taaf.getSelectedPayCalendarGroup()));
        PayCalendarEntries payCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(taaf.getHrPyCalendarEntriesId());
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(payCalendarEntries, new ArrayList<Boolean>()));
		
		return mapping.findForward("basic");
	}
	
	public ActionForward selectNewWorkArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeApprovalActionForm taaf = (TimeApprovalActionForm)form;
		taaf.setSearchField(null);
		taaf.setSearchTerm(null);
		Set<Long> workAreasForQuery = new HashSet<Long>();
		
        if (StringUtils.isBlank(taaf.getSelectedWorkArea())) {
        	List<WorkArea> workAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(taaf.getSelectedDept(), new java.sql.Date(taaf.getPayBeginDate().getTime()));
            for(WorkArea wa : workAreas){
            	if (TKContext.getUser().getCurrentRoles().getApproverWorkAreas().contains(wa.getWorkArea()) ||
            		TKContext.getUser().getCurrentRoles().getReviewerWorkAreas().contains(wa.getWorkArea())){
            		taaf.getDeptWorkareas().add(wa.getWorkArea());
            	}
            }
        	workAreasForQuery = taaf.getDeptWorkareas();
        } else {
            workAreasForQuery.add(Long.parseLong(taaf.getSelectedWorkArea()));
        }
        
		Set<String> principalIds = TkServiceLocator.getTimeApproveService().getPrincipalIdsByWorkAreas(workAreasForQuery, new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()), taaf.getSelectedPayCalendarGroup());
        taaf.setResultSize(principalIds.size());
        taaf.setPrincipalIds(principalIds);
        Set<String> sortedPrincipalIds = getSortedPrincipalIdList(StringUtils.isEmpty(getSortField(request)) ? TimeApprovalActionForm.ORDER_BY_PRINCIPAL : getSortField(request), isAscending(request), new LinkedList<String>(principalIds),
                    new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()));
        taaf.setApprovalRows(getApprovalRows(taaf, getSubListPrincipalIds(request, new LinkedList<String>(sortedPrincipalIds)), taaf.getSelectedPayCalendarGroup()));
        PayCalendarEntries payCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(taaf.getHrPyCalendarEntriesId());
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(payCalendarEntries, new ArrayList<Boolean>()));
		
		return mapping.findForward("basic");
	}
	
	public ActionForward loadApprovalTab(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		ActionForward fwd = mapping.findForward("basic");
		
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        TKUser user = TKContext.getUser();
        Date currentDate = null;
        PayCalendarEntries selectedPayCalendarEntries = null;
        PayCalendar currentPayCalendar = null;
        // Set calendar groups
        List<String> calGroups = TkServiceLocator.getTimeApproveService().getUniquePayGroups(user.getReportingWorkAreas());
        taaf.setPayCalendarGroups(calGroups);

        if (StringUtils.isBlank(taaf.getSelectedPayCalendarGroup())) {
            taaf.setSelectedPayCalendarGroup(calGroups.get(0));
        }
        List<String> depts = new ArrayList<String>(user.getReportingApprovalDepartments().keySet());
        if ( depts.isEmpty() ) {
        	return fwd;
        }
        Collections.sort(depts);
        taaf.setDepartments(depts);

        
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

        if(selectedPayCalendarEntries != null){
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

            taaf.setHrPyCalendarId(selectedPayCalendarEntries.getHrPyCalendarId());
            taaf.setHrPyCalendarEntriesId(selectedPayCalendarEntries.getHrPyCalendarEntriesId());
            taaf.setPayBeginDate(selectedPayCalendarEntries.getBeginPeriodDateTime());
            taaf.setPayEndDate(selectedPayCalendarEntries.getEndPeriodDateTime());
            taaf.setName(user.getPrincipalName());

            if (taaf.getDepartments().size() == 1) {
                taaf.setSelectedDept(taaf.getDepartments().get(0));
            	List<WorkArea> workAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(taaf.getSelectedDept(), new java.sql.Date(taaf.getPayBeginDate().getTime()));
                for(WorkArea wa : workAreas){
                	if (TKContext.getUser().getCurrentRoles().getApproverWorkAreas().contains(wa.getWorkArea())
                			|| TKContext.getUser().getCurrentRoles().getReviewerWorkAreas().contains(wa.getWorkArea())) {
                		taaf.getDeptWorkareas().add(wa.getWorkArea());
                	}
                }
            }
            
            Set<Long> workAreasForQuery = new HashSet<Long>();
            if (StringUtils.isBlank(taaf.getSelectedWorkArea())) {
                workAreasForQuery = taaf.getDeptWorkareas();
            } else {
                workAreasForQuery.add(Long.parseLong(taaf.getSelectedWorkArea()));
            }

            
            Set<String> principalIds = new HashSet<String>();
            if(StringUtils.isEmpty(taaf.getSearchTerm())){
            	principalIds = TkServiceLocator.getTimeApproveService().getPrincipalIdsByWorkAreas(workAreasForQuery, new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()), taaf.getSelectedPayCalendarGroup());
                taaf.setResultSize(principalIds.size());
                taaf.setPrincipalIds(principalIds);
            } else {
            	principalIds.add(taaf.getSearchTerm());
            }
            Set<String> sortedPrincipalIds = getSortedPrincipalIdList(StringUtils.isEmpty(getSortField(request)) ? TimeApprovalActionForm.ORDER_BY_PRINCIPAL : getSortField(request), isAscending(request), new LinkedList<String>(principalIds),
                        new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()));
            taaf.setApprovalRows(getApprovalRows(taaf, getSubListPrincipalIds(request, new LinkedList<String>(sortedPrincipalIds)), taaf.getSelectedPayCalendarGroup()));
            taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(selectedPayCalendarEntries, new ArrayList<Boolean>()));
        }
        
        return fwd;
	}
	
	public ActionForward selectNewPayCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeApprovalActionForm taaf = (TimeApprovalActionForm)form;
		taaf.setSearchField(null);
		taaf.setSearchTerm(null);
		taaf.setSelectedWorkArea(null);
		taaf.setSelectedDept(null);
		taaf.setPayBeginDate(null);
		taaf.setPayEndDate(null);
		taaf.setHrPyCalendarEntriesId(null);
		
		return loadApprovalTab(mapping, form, request, response);
	}
	
    /**
     * Helper method to modify / manage the list of records needed to display approval data to the user.
     *
     * @param taaf
     * @return
     */
    protected List<ApprovalTimeSummaryRow> getApprovalRows(TimeApprovalActionForm taaf, List<String> assignmentPrincipalIds, String calGroup) {

        if (assignmentPrincipalIds.size() == 0) {
            return new ArrayList<ApprovalTimeSummaryRow>();
        }

        if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_PRINCIPAL)) {
            assignmentPrincipalIds = new LinkedList<String>();
            assignmentPrincipalIds.add(taaf.getSearchTerm());
        } else if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_DOCID)) {

        }
        return TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(taaf.getPayBeginDate(), taaf.getPayEndDate(), calGroup, assignmentPrincipalIds);
    }

    // move this to the service layer
    protected Set<String> getSortedPrincipalIdList(String sortField, Boolean isAscending, List<String> assignmentPrincipalIds, java.sql.Date payBeginDate, java.sql.Date payEndDate) {

        Set<String> principalIds = new LinkedHashSet<String>();

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
                    TkServiceLocator.getTimeApproveService().getPrincipalDocumehtHeader(new LinkedList<String>(assignmentPrincipalIds), payBeginDate, payEndDate);

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

	
    @Override
    protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
        TKUser user = TKContext.getUser();
        UserRoles roles = user.getCurrentRoles();

        if (!roles.isTimesheetReviewer() && !roles.isAnyApproverActive() && !roles.isSystemAdmin() && !roles.isLocationAdmin() && !roles.isGlobalViewOnly() && !roles.isDeptViewOnly() && !roles.isDepartmentAdmin()) {
            throw new AuthorizationException(user.getPrincipalId(), "TimeApprovalAction", "");
        }
    }
    
    protected String getSortField(HttpServletRequest request) {
        return request.getParameter((new ParamEncoder(TkConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_SORT)));
    }

    protected Boolean isAscending(HttpServletRequest request) {
        // returned value 1 = ascending; 2 = descending
        String ascending = request.getParameter((new ParamEncoder(TkConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_ORDER)));
        return StringUtils.equals(ascending, "1") ? true : false;
    }

    // move this to the service layer
    protected List<String> getSubListPrincipalIds(HttpServletRequest request, List<String> assignmentPrincipalIds) {
        String page = request.getParameter((new ParamEncoder(TkConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
        // The paging index begins from 1, but the sublist index begins from 0.
        // So the logic below sets the sublist begin index to 0 if the page number is null or equals 1
        Integer beginIndex = StringUtils.isBlank(page) || StringUtils.equals(page, "1") ? 0 : Integer.parseInt(page);
        Integer endIndex = beginIndex + TkConstants.PAGE_SIZE > assignmentPrincipalIds.size() ? assignmentPrincipalIds.size() : beginIndex + TkConstants.PAGE_SIZE;

        return assignmentPrincipalIds.subList(beginIndex, endIndex);
    }   
}
