package org.kuali.hr.time.approval.web;

import com.google.common.collect.Ordering;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.*;

public class TimeApprovalAction extends TkAction{
	
	public ActionForward searchResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeApprovalActionForm taaf = (TimeApprovalActionForm)form;
		String searchField = taaf.getSearchField();
		String searchTerm = taaf.getSearchTerm();
        String principalId = searchTerm;
        
        if(StringUtils.equals("documentId", searchField)){
        	TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(searchTerm);
        	principalId = tdh.getPrincipalId();
        	taaf.setSearchField("principalId");
        	taaf.setSearchTerm(principalId);
        } 
        
        PayCalendarEntries payCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(taaf.getHrPyCalendarEntriesId());
        taaf.setPayCalendarEntries(payCalendarEntries);
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(payCalendarEntries, new ArrayList<Boolean>()));
        taaf.setName(TKContext.getUser().getPrincipalName());
        taaf.setResultSize(1);
        
        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, payCalendarEntries.getEndPeriodDate());
        if(!assignments.isEmpty()){
        	taaf.setSelectedDept(assignments.get(0).getDept());
        	taaf.setSelectedWorkArea(assignments.get(0).getWorkArea().toString());
        }
        
        List<String> principalIds = new ArrayList<String>();
        principalIds.add(principalId);
        taaf.setApprovalRows(getApprovalRows(taaf, principalIds));
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(payCalendarEntries, new ArrayList<Boolean>()));
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
		taaf.getDeptWorkareas().clear();
		taaf.setSearchField(null);
		taaf.setSearchTerm(null);
		  
    	List<WorkArea> workAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(taaf.getSelectedDept(), new java.sql.Date(taaf.getPayBeginDate().getTime()));
        for(WorkArea wa : workAreas){
        	if (TKContext.getUser().getCurrentRoles().getApproverWorkAreas().contains(wa.getWorkArea())
        			|| TKContext.getUser().getCurrentRoles().getReviewerWorkAreas().contains(wa.getWorkArea())) {
        		taaf.getDeptWorkareas().add(wa.getWorkArea());
        		taaf.getWorkAreaDescr().put(wa.getWorkArea(),wa.getDescription()+"("+wa.getWorkArea()+")");
        	}
        }
        
		Set<String> principalIds = TkServiceLocator.getTimeApproveService().getPrincipalIdsByDeptAndWorkArea(taaf.getSelectedDept(), null, new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()), taaf.getSelectedPayCalendarGroup());
        taaf.setResultSize(principalIds.size());

       // Set<String> sortedPrincipalIds = getSortedPrincipalIdList(StringUtils.isEmpty(getSortField(request)) ? TimeApprovalActionForm.ORDER_BY_PRINCIPAL : getSortField(request), isAscending(request), new LinkedList<String>(principalIds),
               //     new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()));
        PayCalendarEntries payCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(taaf.getHrPyCalendarEntriesId());
        taaf.setPayCalendarEntries(payCalendarEntries);
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(payCalendarEntries, new ArrayList<Boolean>()));
        taaf.setApprovalRows(getApprovalRows(taaf, getSubListPrincipalIds(request, new LinkedList<String>(principalIds))));

		return mapping.findForward("basic");
	}
	
	public ActionForward selectNewWorkArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeApprovalActionForm taaf = (TimeApprovalActionForm)form;
		taaf.setSearchField(null);
		taaf.setSearchTerm(null);
		String workArea = null;
		
        if (StringUtils.isBlank(taaf.getSelectedWorkArea())) {
        	List<WorkArea> workAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(taaf.getSelectedDept(), new java.sql.Date(taaf.getPayBeginDate().getTime()));
            for(WorkArea wa : workAreas){
            	if (TKContext.getUser().getCurrentRoles().getApproverWorkAreas().contains(wa.getWorkArea()) ||
            		TKContext.getUser().getCurrentRoles().getReviewerWorkAreas().contains(wa.getWorkArea())){
            		taaf.getWorkAreaDescr().put(wa.getWorkArea(),wa.getDescription()+"("+wa.getWorkArea()+")");
            		taaf.getDeptWorkareas().add(wa.getWorkArea());
            	}
            }
        } else {
            workArea = taaf.getSelectedWorkArea();
        }
        
		Set<String> principalIds = TkServiceLocator.getTimeApproveService().getPrincipalIdsByDeptAndWorkArea(taaf.getSelectedDept(), workArea, new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()), taaf.getSelectedPayCalendarGroup());
        taaf.setResultSize(principalIds.size());

        //Set<String> sortedPrincipalIds = getSortedPrincipalIdList(StringUtils.isEmpty(getSortField(request)) ? TimeApprovalActionForm.ORDER_BY_PRINCIPAL : getSortField(request), isAscending(request), new LinkedList<String>(principalIds),
                    //new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()));
        PayCalendarEntries payCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(taaf.getHrPyCalendarEntriesId());
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(payCalendarEntries, new ArrayList<Boolean>()));
        taaf.setApprovalRows(getApprovalRows(taaf, getSubListPrincipalIds(request, new LinkedList<String>(principalIds))));
		
		return mapping.findForward("basic");
	}
	
	public ActionForward loadApprovalTab(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		ActionForward fwd = mapping.findForward("basic");
		
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        TKUser user = TKContext.getUser();
        Date currentDate = null;
        PayCalendarEntries payCalendarEntries = null;
        PayCalendar currentPayCalendar = null;
        // Set calendar groups
        List<String> calGroups = TkServiceLocator.getTimeApproveService().getUniquePayGroups();
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
        	payCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(taaf.getHrPyCalendarEntriesId());
            currentDate = payCalendarEntries.getEndPeriodDate();
        } else {
            currentDate = TKUtils.getTimelessDate(null);
            currentPayCalendar = TkServiceLocator.getPayCalendarSerivce().getPayCalendarByGroup(taaf.getSelectedPayCalendarGroup());
            payCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getCurrentPayCalendarEntriesByPayCalendarId(currentPayCalendar.getHrPyCalendarId(), currentDate);
        }

        if(payCalendarEntries != null){
            taaf.setHrPyCalendarId(payCalendarEntries.getHrPyCalendarId());
            taaf.setHrPyCalendarEntriesId(payCalendarEntries.getHrPyCalendarEntriesId());
            taaf.setPayBeginDate(payCalendarEntries.getBeginPeriodDateTime());
            taaf.setPayEndDate(payCalendarEntries.getEndPeriodDateTime());
            taaf.setName(user.getPrincipalName());
            
            PayCalendarEntries prevPayCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getPreviousPayCalendarEntriesByPayCalendarId(taaf.getHrPyCalendarId(), payCalendarEntries);
            if (prevPayCalendarEntries != null) {
                taaf.setPrevPayCalendarId(prevPayCalendarEntries.getHrPyCalendarEntriesId());
            } else {
                taaf.setPrevPayCalendarId(null);
            }
            
            PayCalendarEntries nextPayCalendarEntries = TkServiceLocator.getPayCalendarEntriesSerivce().getNextPayCalendarEntriesByPayCalendarId(taaf.getHrPyCalendarId(), payCalendarEntries);
            if (nextPayCalendarEntries != null) {
                taaf.setNextPayCalendarId(nextPayCalendarEntries.getHrPyCalendarEntriesId());
            } else {
                taaf.setNextPayCalendarId(null);
            }

            if (taaf.getDepartments().size() == 1 || !StringUtils.isEmpty(taaf.getSelectedDept())) {
            	
            	if (StringUtils.isEmpty(taaf.getSelectedDept()))
            		taaf.setSelectedDept(taaf.getDepartments().get(0));
            	
            	List<WorkArea> workAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(taaf.getSelectedDept(), new java.sql.Date(taaf.getPayBeginDate().getTime()));
                for(WorkArea wa : workAreas){
                	if (TKContext.getUser().getCurrentRoles().getApproverWorkAreas().contains(wa.getWorkArea())
                			|| TKContext.getUser().getCurrentRoles().getReviewerWorkAreas().contains(wa.getWorkArea())) {
                		taaf.getDeptWorkareas().add(wa.getWorkArea());
                		taaf.getWorkAreaDescr().put(wa.getWorkArea(),wa.getDescription()+"("+wa.getWorkArea()+")");
                	}
                }
                
                Set<String> principalIds = new HashSet<String>();
            	principalIds = TkServiceLocator.getTimeApproveService().getPrincipalIdsByDeptAndWorkArea(taaf.getSelectedDept(), null, new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()), taaf.getSelectedPayCalendarGroup());
            	
                taaf.setResultSize(principalIds.size());
                //taaf.setPrincipalIds(principalIds);
                
                //Set<String> sortedPrincipalIds = getSortedPrincipalIdList(StringUtils.isEmpty(getSortField(request)) ? TimeApprovalActionForm.ORDER_BY_PRINCIPAL : getSortField(request), isAscending(request), new LinkedList<String>(principalIds),
                            //new java.sql.Date(taaf.getPayBeginDate().getTime()), new java.sql.Date(taaf.getPayEndDate().getTime()));
                taaf.setPayCalendarEntries(payCalendarEntries);
                taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(payCalendarEntries, new ArrayList<Boolean>()));
                taaf.setApprovalRows(getApprovalRows(taaf, getSubListPrincipalIds(request, new LinkedList<String>(principalIds))));
            }
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
        // KPME-909
        taaf.setApprovalRows(new ArrayList<ApprovalTimeSummaryRow>());
		
		return loadApprovalTab(mapping, form, request, response);
	}
	
    /**
     * Helper method to modify / manage the list of records needed to display approval data to the user.
     *
     * @param taaf
     * @return
     */
    protected List<ApprovalTimeSummaryRow> getApprovalRows(TimeApprovalActionForm taaf, List<String> assignmentPrincipalIds) {

        if (assignmentPrincipalIds.size() == 0) {
            return new ArrayList<ApprovalTimeSummaryRow>();
        }

        if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_PRINCIPAL) && !StringUtils.isBlank(taaf.getSearchTerm())) {
            assignmentPrincipalIds = new LinkedList<String>();
            assignmentPrincipalIds.add(taaf.getSearchTerm());
        } else if (StringUtils.equals(taaf.getSearchField(), TimeApprovalActionForm.ORDER_BY_DOCID)) {

        }
        return TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(taaf.getPayBeginDate(), taaf.getPayEndDate(), taaf.getSelectedPayCalendarGroup(), assignmentPrincipalIds, taaf.getPayCalendarLabels(), taaf.getPayCalendarEntries());
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
        Integer beginIndex = StringUtils.isBlank(page) || StringUtils.equals(page, "1") ? 0 : Integer.parseInt(page)*TkConstants.PAGE_SIZE;
        Integer endIndex = beginIndex + TkConstants.PAGE_SIZE > assignmentPrincipalIds.size() ? assignmentPrincipalIds.size() : beginIndex + TkConstants.PAGE_SIZE;

        return assignmentPrincipalIds.subList(beginIndex, endIndex);
    }   
}
