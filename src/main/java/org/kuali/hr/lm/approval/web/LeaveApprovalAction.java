/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.lm.approval.web;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.approval.web.ApprovalLeaveSummaryRow;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.ApprovalAction;
import org.kuali.hr.time.base.web.ApprovalForm;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.detail.web.ActionFormUtils;
import org.kuali.hr.time.person.TKPerson;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workarea.WorkArea;

public class LeaveApprovalAction extends ApprovalAction{
	
	public ActionForward searchResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LeaveApprovalActionForm laaf = (LeaveApprovalActionForm)form;
		String searchField = laaf.getSearchField();
		String searchTerm = laaf.getSearchTerm();
		String principalId;
		
        if(StringUtils.equals("documentId", searchField)){
        	LeaveCalendarDocumentHeader lcd = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(searchTerm);
        	principalId = lcd.getPrincipalId();
        } else {
        	principalId = searchTerm;
        }
    	laaf.setSearchField("principalId");
    	laaf.setSearchTerm(principalId);
       
        List<String> principalIds = new ArrayList<String>();
        principalIds.add(principalId);
        List<TKPerson> persons = TkServiceLocator.getPersonService().getPersonCollection(principalIds);
        CalendarEntries payCalendarEntries = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(laaf.getHrPyCalendarEntriesId());
        if (persons.isEmpty()) {
        	laaf.setLeaveApprovalRows(new ArrayList<ApprovalLeaveSummaryRow>());
        	laaf.setResultSize(0);
        } else {
        	this.setApprovalTables(laaf, principalIds, request, payCalendarEntries);
        	
   	        laaf.setPayCalendarEntries(payCalendarEntries);
   	        laaf.setLeaveCalendarLabels(TkServiceLocator.getLeaveSummaryService().getHeaderForSummary(payCalendarEntries));
        	
	        List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignments(principalId, payCalendarEntries.getEndPeriodDate());
	        if(!assignments.isEmpty()){
	        	 for(Long wa : laaf.getWorkAreaDescr().keySet()){
	        		for (Assignment assign : assignments) {
		             	if (assign.getWorkArea().toString().equals(wa.toString())) {
		             		laaf.setSelectedWorkArea(wa.toString());
		             		break;
		             	}
	        		}
	             }
	        }
        }
 
		return mapping.findForward("basic");
	}
	
    public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveApprovalActionForm laaf = (LeaveApprovalActionForm) form;
       
        List<ApprovalLeaveSummaryRow> lstLeaveRows = laaf.getLeaveApprovalRows();
        for (ApprovalLeaveSummaryRow ar : lstLeaveRows) {
            if (ar.isApprovable() && StringUtils.equals(ar.getSelected(), "on")) {
                String documentNumber = ar.getDocumentId();
                LeaveCalendarDocumentHeader lcd = TkServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(documentNumber);
                //TODO: approve the selected leave calendar documents, logic does not exist in LeaveCalendarDocumentHeaderService yet
            }
        }  
        
        return mapping.findForward("basic");
    }
        
	public ActionForward selectNewDept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LeaveApprovalActionForm laaf = (LeaveApprovalActionForm)form;
		laaf.setSearchField(null);
		laaf.setSearchTerm(null);

        CalendarEntries payCalendarEntries = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(laaf.getHrPyCalendarEntriesId());
        laaf.setPayCalendarEntries(payCalendarEntries);
        laaf.setLeaveCalendarLabels(TkServiceLocator.getLeaveSummaryService().getHeaderForSummary(payCalendarEntries));

		laaf.getWorkAreaDescr().clear();
    	List<WorkArea> workAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(laaf.getSelectedDept(), new java.sql.Date(laaf.getPayBeginDate().getTime()));
        for(WorkArea wa : workAreas){
        	if (TKContext.getUser().getApproverWorkAreas().contains(wa.getWorkArea())
        			|| TKContext.getUser().getReviewerWorkAreas().contains(wa.getWorkArea())) {
        		laaf.getWorkAreaDescr().put(wa.getWorkArea(),wa.getDescription()+"("+wa.getWorkArea()+")");
        	}
        }
	
    	List<String> principalIds = TkServiceLocator.getLeaveApprovalService().getPrincipalIdsByDeptWorkAreaRolename(laaf.getRoleName(), laaf.getSelectedDept(), laaf.getSelectedWorkArea(), new java.sql.Date(laaf.getPayBeginDate().getTime()), new java.sql.Date(laaf.getPayEndDate().getTime()), laaf.getSelectedPayCalendarGroup());
    	this.setApprovalTables(laaf, principalIds, request, payCalendarEntries);
    	
    	this.populateCalendarAndPayPeriodLists(request, laaf);
		return mapping.findForward("basic");
	}
	
	public ActionForward selectNewWorkArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LeaveApprovalActionForm laaf = (LeaveApprovalActionForm)form;
		laaf.setSearchField(null);
		laaf.setSearchTerm(null);

	    CalendarEntries payCalendarEntries = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(laaf.getHrPyCalendarEntriesId());
        laaf.setLeaveCalendarLabels(TkServiceLocator.getLeaveSummaryService().getHeaderForSummary(payCalendarEntries));
        
        List<String> principalIds = TkServiceLocator.getLeaveApprovalService().getPrincipalIdsByDeptWorkAreaRolename(laaf.getRoleName(), laaf.getSelectedDept(), laaf.getSelectedWorkArea(), new java.sql.Date(laaf.getPayBeginDate().getTime()), new java.sql.Date(laaf.getPayEndDate().getTime()), laaf.getSelectedPayCalendarGroup());
        this.setApprovalTables(laaf, principalIds, request, payCalendarEntries);
        
		return mapping.findForward("basic");
	}
		
	private void setApprovalTables(LeaveApprovalActionForm laaf, List<String> principalIds, HttpServletRequest request, CalendarEntries payCalendarEntries) {
		laaf.setLeaveCalendarLabels(TkServiceLocator.getLeaveSummaryService().getHeaderForSummary(payCalendarEntries));
		
		if (principalIds.isEmpty()) {
			laaf.setLeaveApprovalRows(new ArrayList<ApprovalLeaveSummaryRow>());
			laaf.setResultSize(0);
		} else {
			List<TKPerson> persons = TkServiceLocator.getPersonService().getPersonCollection(principalIds);
			List<ApprovalLeaveSummaryRow> approvalRows = getApprovalLeaveRows(laaf, getSubListPrincipalIds(request, persons)); 
		    
			final String sortField = request.getParameter("sortField");		    
		    if (StringUtils.equals(sortField, "Name")) {
			    final boolean sortNameAscending = Boolean.parseBoolean(request.getParameter("sortNameAscending"));
		    	Collections.sort(approvalRows, new Comparator<ApprovalLeaveSummaryRow>() {
					@Override
					public int compare(ApprovalLeaveSummaryRow row1, ApprovalLeaveSummaryRow row2) {
						if (sortNameAscending) {
							return ObjectUtils.compare(StringUtils.lowerCase(row1.getName()), StringUtils.lowerCase(row2.getName()));
						} else {
							return ObjectUtils.compare(StringUtils.lowerCase(row2.getName()), StringUtils.lowerCase(row1.getName()));
						}
					}
		    	});
		    } else if (StringUtils.equals(sortField, "DocumentID")) {
			    final boolean sortDocumentIdAscending = Boolean.parseBoolean(request.getParameter("sortDocumentIDAscending"));
		    	Collections.sort(approvalRows, new Comparator<ApprovalLeaveSummaryRow>() {
					@Override
					public int compare(ApprovalLeaveSummaryRow row1, ApprovalLeaveSummaryRow row2) {
						if (sortDocumentIdAscending) {
							return ObjectUtils.compare(NumberUtils.toInt(row1.getDocumentId()), NumberUtils.toInt(row2.getDocumentId()));
						} else {
							return ObjectUtils.compare(NumberUtils.toInt(row2.getDocumentId()), NumberUtils.toInt(row1.getDocumentId()));
						}
					}
		    	});
		    }
			laaf.setLeaveApprovalRows(approvalRows);
		    laaf.setResultSize(persons.size());
		}
	}
	
	@Override
	public ActionForward loadApprovalTab(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		ActionForward fwd = mapping.findForward("basic");
		TKUser user = TKContext.getUser();
        LeaveApprovalActionForm laaf = (LeaveApprovalActionForm) form;
        Date currentDate = null;
        CalendarEntries payCalendarEntries = null;
        Calendar currentPayCalendar = null;
        String page = request.getParameter((new ParamEncoder(TkConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));


        //reset state
        if(StringUtils.isBlank(laaf.getSelectedDept())){
        	resetState(form, request);
        }

        // Set current pay calendar entries if present. Decide if the current date should be today or the end period date
        if (laaf.getHrPyCalendarEntriesId() != null) {
            currentDate = payCalendarEntries.getEndPeriodDate();
        } else {
            currentDate = TKUtils.getTimelessDate(null);
        }
        Set<Long> workAreas = TkServiceLocator.getTkRoleService().getWorkAreasForApprover(TKContext.getPrincipalId(), currentDate);
        List<String> principalIds = new ArrayList<String>();
        for (Long workArea : workAreas) {
            List<Assignment> assignments = TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(workArea, currentDate);
            for (Assignment a : assignments) {
                principalIds.add(a.getPrincipalId());
            }
        }

        // Set calendar groups
        List<String> calGroups = TkServiceLocator.getLeaveApprovalService().getUniqueLeavePayGroupsForPrincipalIds(principalIds);
        laaf.setPayCalendarGroups(calGroups);

        if (StringUtils.isBlank(laaf.getSelectedPayCalendarGroup())
                && CollectionUtils.isNotEmpty(calGroups)) {
            laaf.setSelectedPayCalendarGroup(calGroups.get(0));

        }
        
        // Set current pay calendar entries if present. Decide if the current date should be today or the end period date
        if (laaf.getHrPyCalendarEntriesId() != null) {
            payCalendarEntries = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(laaf.getHrPyCalendarEntriesId());
        } else {
            currentPayCalendar = TkServiceLocator.getCalendarService().getCalendarByGroup(laaf.getSelectedPayCalendarGroup());
            if (currentPayCalendar != null) {
                payCalendarEntries = TkServiceLocator.getCalendarEntriesService().getCurrentCalendarEntriesByCalendarId(currentPayCalendar.getHrCalendarId(), currentDate);
            }
        }
        laaf.setPayCalendarEntries(payCalendarEntries);
        
        
        if(laaf.getPayCalendarEntries() != null) {
	        populateCalendarAndPayPeriodLists(request, laaf);
        }
        setupDocumentOnFormContext(request,laaf,payCalendarEntries, page);
        return fwd;
	}

	@Override
	protected void setupDocumentOnFormContext(HttpServletRequest request,ApprovalForm form, CalendarEntries payCalendarEntries, String page) {
		super.setupDocumentOnFormContext(request, form, payCalendarEntries, page);
		LeaveApprovalActionForm laaf = (LeaveApprovalActionForm)form;
		laaf.setLeaveCalendarLabels(TkServiceLocator.getLeaveSummaryService().getHeaderForSummary(payCalendarEntries));
		
		List<String> principalIds = TkServiceLocator.getLeaveApprovalService().getPrincipalIdsByDeptWorkAreaRolename(laaf.getRoleName(), laaf.getSelectedDept(), laaf.getSelectedWorkArea(), new java.sql.Date(laaf.getPayBeginDate().getTime()), new java.sql.Date(laaf.getPayEndDate().getTime()), laaf.getSelectedPayCalendarGroup());
		TkServiceLocator.getLeaveApprovalService().removeNonLeaveEmployees(principalIds);
     	
		this.setApprovalTables(laaf, principalIds, request, payCalendarEntries);
		laaf.setOnCurrentPeriod(ActionFormUtils.getOnCurrentPeriodFlag(laaf.getPayCalendarEntries()));
	}
	
	public ActionForward selectNewPayCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// resets the common fields for approval pages
		super.resetMainFields(form);
		LeaveApprovalActionForm laaf = (LeaveApprovalActionForm)form;
        // KPME-909
        laaf.setLeaveApprovalRows(new ArrayList<ApprovalLeaveSummaryRow>());
		
		return loadApprovalTab(mapping, form, request, response);
	}
	   
    protected List<ApprovalLeaveSummaryRow> getApprovalLeaveRows(LeaveApprovalActionForm laaf, List<TKPerson> assignmentPrincipalIds) {
        return TkServiceLocator.getLeaveApprovalService().getLeaveApprovalSummaryRows
        	(assignmentPrincipalIds, laaf.getPayCalendarEntries(), laaf.getLeaveCalendarLabels());
    }
	
    public void resetState(ActionForm form, HttpServletRequest request) {
    	  LeaveApprovalActionForm laaf = (LeaveApprovalActionForm) form;
 	      String page = request.getParameter((new ParamEncoder(TkConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
 	      
 	      if (StringUtils.isBlank(page)) {
 			  laaf.getDepartments().clear();
 			  laaf.getWorkAreaDescr().clear();
 			  laaf.setLeaveApprovalRows(new ArrayList<ApprovalLeaveSummaryRow>());
 			  laaf.setSelectedDept(null);
 			  laaf.setSearchField(null);
 			  laaf.setSearchTerm(null);
 	      }
	}
    
    @Override
    protected void populateCalendarAndPayPeriodLists(HttpServletRequest request, ApprovalForm taf) {
    	 LeaveApprovalActionForm laaf = (LeaveApprovalActionForm) taf;
		// set calendar year list
		Set<String> yearSet = new HashSet<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		// if selected calendar year is passed in
		if(!StringUtils.isEmpty(request.getParameter("selectedCY"))) {
			laaf.setSelectedCalendarYear(request.getParameter("selectedCY").toString());
		} else {
			laaf.setSelectedCalendarYear(sdf.format(laaf.getPayCalendarEntries().getBeginPeriodDate()));
		}
		
		List<CalendarEntries> pcListForYear = new ArrayList<CalendarEntries>();
		List<CalendarEntries> pceList =  new ArrayList<CalendarEntries>();
		pceList.addAll(TkServiceLocator.getLeaveApprovalService()
			.getAllLeavePayCalendarEntriesForApprover(TKContext.getPrincipalId(), TKUtils.getTimelessDate(null)));
		
	    for(CalendarEntries pce : pceList) {
	    	yearSet.add(sdf.format(pce.getBeginPeriodDate()));
	    	if(sdf.format(pce.getBeginPeriodDate()).equals(laaf.getSelectedCalendarYear())) {
	    		pcListForYear.add(pce);
	    	}
	    }
	    List<String> yearList = new ArrayList<String>(yearSet);
	    Collections.sort(yearList);
	    Collections.reverse(yearList);	// newest on top
	    laaf.setCalendarYears(yearList);
		
		// set pay period list contents
		if(!StringUtils.isEmpty(request.getParameter("selectedPP"))) {
			laaf.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
		} else {
			laaf.setSelectedPayPeriod(laaf.getPayCalendarEntries().getHrCalendarEntriesId());
			laaf.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(pcListForYear));
		}
		if(laaf.getPayPeriodsMap().isEmpty()) {
		    laaf.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(pcListForYear));
		}
	}

}
