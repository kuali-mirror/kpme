/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.approval.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hsqldb.lib.StringUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.common.CalendarApprovalFormAction;
import org.kuali.kpme.tklm.common.CalendarApprovalForm;
import org.kuali.kpme.tklm.time.approval.summaryrow.ApprovalTimeSummaryRow;
import org.kuali.kpme.tklm.time.detail.web.ActionFormUtils;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.krad.util.GlobalVariables;

public class TimeApprovalAction extends CalendarApprovalFormAction{
	
	public ActionForward searchResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeApprovalActionForm taaf = (TimeApprovalActionForm)form;
		
        if (StringUtils.equals("documentId", taaf.getSearchField())) {
        	TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(taaf.getSearchTerm());
        	taaf.setSearchTerm(tdh != null ? tdh.getPrincipalId() : StringUtils.EMPTY);
        }
        
    	taaf.setSearchField("principalId");
        List<String> principalIds = new ArrayList<String>();
        principalIds.add(taaf.getSearchTerm());
        
        if (principalIds.isEmpty()) {
        	taaf.setApprovalRows(new ArrayList<ApprovalTimeSummaryRow>());
        	taaf.setResultSize(0);
        } else {
        	CalendarEntry payCalendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(taaf.getHrCalendarEntryId());
   	        taaf.setCalendarEntry(payCalendarEntry);
   	        taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(payCalendarEntry, new ArrayList<Boolean>()));
        	
        	taaf.setResultSize(principalIds.size());	
	        taaf.setApprovalRows(getApprovalRows(taaf, principalIds));

	        List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignments(taaf.getSearchTerm(), payCalendarEntry.getEndPeriodFullDateTime().toLocalDate());
	        if(!assignments.isEmpty()){
	        	 for(Long wa : taaf.getWorkAreaDescr().keySet()){
	        		for (Assignment assign : assignments) {
		             	if (assign.getWorkArea().toString().equals(wa.toString())) {
		             		taaf.setSelectedWorkArea(wa.toString());
		             		break;
		             	}
	        		}
	             }
	        }
        }
 
		return mapping.findForward("basic");
	}
	
    public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        List<ApprovalTimeSummaryRow> lstApprovalRows = taaf.getApprovalRows();
        for (ApprovalTimeSummaryRow ar : lstApprovalRows) {
            if (ar.isApprovable() && StringUtils.equals(ar.getSelected(), "on")) {
                String documentNumber = ar.getDocumentId();
                TimesheetDocument tDoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentNumber);
                TkServiceLocator.getTimesheetService().approveTimesheet(HrContext.getTargetPrincipalId(), tDoc);
            }
        }
        return mapping.findForward("basic");
    }
    
	public ActionForward selectNewDept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeApprovalActionForm taaf = (TimeApprovalActionForm)form;
		taaf.setSearchField(null);
		taaf.setSearchTerm(null);
		taaf.setOutputString(null);
		taaf.getWorkAreaDescr().clear();
		
        CalendarEntry payCalendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(taaf.getHrCalendarEntryId());
        taaf.setCalendarEntry(payCalendarEntry);
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(payCalendarEntry, new ArrayList<Boolean>()));

		String principalId = GlobalVariables.getUserSession().getPrincipalId();
    	List<WorkArea> workAreaObjs = HrServiceLocator.getWorkAreaService().getWorkAreas(taaf.getSelectedDept(), payCalendarEntry.getBeginPeriodFullDateTime().toLocalDate());
        for (WorkArea workAreaObj : workAreaObjs) {
        	Long workArea = workAreaObj.getWorkArea();
        	String description = workAreaObj.getDescription();
        	
        	if (HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.REVIEWER.getRoleName(), workArea, new DateTime())
        			|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER_DELEGATE.getRoleName(), workArea, new DateTime())
        			|| HrServiceLocator.getHRRoleService().principalHasRoleInWorkArea(principalId, KPMERole.APPROVER.getRoleName(), workArea, new DateTime())) {
        		taaf.getWorkAreaDescr().put(workArea, description + "(" + workArea + ")");
        	}
        }

        List<String> principalIds = this.getPrincipalIdsToPopulateTable(taaf); 
    	if (principalIds.isEmpty()) {
    		taaf.setApprovalRows(new ArrayList<ApprovalTimeSummaryRow>());
    		taaf.setResultSize(0);
    	}
    	else {
    		Collections.sort(principalIds);
	        taaf.setApprovalRows(getApprovalRows(taaf, getSubListPrincipalIds(request, principalIds)));
	        
	        if (taaf.getApprovalRows() != null && !taaf.getApprovalRows().isEmpty()) {
	        	taaf.setOutputString(taaf.getApprovalRows().get(0).getOutputString());
	        }
	        
	        taaf.setResultSize(principalIds.size());
    	}
    	
    	this.populateCalendarAndPayPeriodLists(request, taaf);
		return mapping.findForward("basic");
	}
	
	public ActionForward selectNewWorkArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeApprovalActionForm taaf = (TimeApprovalActionForm)form;
		taaf.setSearchField(null);
		taaf.setSearchTerm(null);
		taaf.setOutputString(null);

	    CalendarEntry payCalendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(taaf.getHrCalendarEntryId());
        taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(payCalendarEntry, new ArrayList<Boolean>()));
        
        List<String> principalIds = this.getPrincipalIdsToPopulateTable(taaf); 
		if (principalIds.isEmpty()) {
			taaf.setApprovalRows(new ArrayList<ApprovalTimeSummaryRow>());
			taaf.setResultSize(0);
		}
		else {
	    		Collections.sort(principalIds);
		        taaf.setApprovalRows(getApprovalRows(taaf, getSubListPrincipalIds(request, principalIds)));
		        taaf.setResultSize(principalIds.size());
	    	}
		return mapping.findForward("basic");
	}
	
	@Override
	public ActionForward loadApprovalTab(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		ActionForward fwd = mapping.findForward("basic");
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        taaf.setOutputString(null);
        DateTime currentDate = null;
        CalendarEntry payCalendarEntry = null;
        Calendar currentPayCalendar = null;
        String page = request.getParameter((new ParamEncoder(HrConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
        
        //reset state
        if(StringUtils.isBlank(taaf.getSelectedDept())){
        	resetState(form, request);
        }
        // Set calendar groups
        List<String> calGroups = HrServiceLocator.getPrincipalHRAttributeService().getUniqueTimePayGroups();
        taaf.setPayCalendarGroups(calGroups);

        if (StringUtils.isBlank(taaf.getSelectedPayCalendarGroup())) {
            taaf.setSelectedPayCalendarGroup(calGroups.get(0));
        }
        
        // Set current pay calendar entries if present. Decide if the current date should be today or the end period date
        if (taaf.getHrCalendarEntryId() != null) {
        	payCalendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(taaf.getHrCalendarEntryId());
        } else {
            currentDate = new LocalDate().toDateTimeAtStartOfDay();
            currentPayCalendar = HrServiceLocator.getCalendarService().getCalendarByGroup(taaf.getSelectedPayCalendarGroup());
            payCalendarEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(currentPayCalendar.getHrCalendarId(), currentDate);
        }
        taaf.setCalendarEntry(payCalendarEntry);
        
        
        if(taaf.getCalendarEntry() != null) {
	        populateCalendarAndPayPeriodLists(request, taaf);
        }
        setupDocumentOnFormContext(request,taaf, payCalendarEntry, page);

        if (taaf.getApprovalRows() != null && !taaf.getApprovalRows().isEmpty()) {
        	taaf.setOutputString(taaf.getApprovalRows().get(0).getOutputString());
        }
        return fwd;
	}

	@Override
	protected void setupDocumentOnFormContext(HttpServletRequest request,CalendarApprovalForm form, CalendarEntry payCalendarEntry, String page) {
		super.setupDocumentOnFormContext(request, form, payCalendarEntry, page);
		TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
		taaf.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(payCalendarEntry, new ArrayList<Boolean>()));

		List<String> principalIds = this.getPrincipalIdsToPopulateTable(taaf);
		if (principalIds.isEmpty()) {
			taaf.setApprovalRows(new ArrayList<ApprovalTimeSummaryRow>());
			taaf.setResultSize(0);
		} else {
		    List<ApprovalTimeSummaryRow> approvalRows = getApprovalRows(taaf, getSubListPrincipalIds(request, principalIds));
		    final String sortField = request.getParameter("sortField");
		    if (StringUtils.equals(sortField, "Name")) {
			    final boolean sortNameAscending = Boolean.parseBoolean(request.getParameter("sortNameAscending"));
		    	Collections.sort(approvalRows, new Comparator<ApprovalTimeSummaryRow>() {
					@Override
					public int compare(ApprovalTimeSummaryRow row1, ApprovalTimeSummaryRow row2) {
						if (sortNameAscending) {
							return ObjectUtils.compare(StringUtils.lowerCase(row1.getName()), StringUtils.lowerCase(row2.getName()));
						} else {
							return ObjectUtils.compare(StringUtils.lowerCase(row2.getName()), StringUtils.lowerCase(row1.getName()));
						}
					}
		    	});
		    } else if (StringUtils.equals(sortField, "DocumentID")) {
			    final boolean sortDocumentIdAscending = Boolean.parseBoolean(request.getParameter("sortDocumentIDAscending"));
		    	Collections.sort(approvalRows, new Comparator<ApprovalTimeSummaryRow>() {
					@Override
					public int compare(ApprovalTimeSummaryRow row1, ApprovalTimeSummaryRow row2) {
						if (sortDocumentIdAscending) {
							return ObjectUtils.compare(NumberUtils.toInt(row1.getDocumentId()), NumberUtils.toInt(row2.getDocumentId()));
						} else {
							return ObjectUtils.compare(NumberUtils.toInt(row2.getDocumentId()), NumberUtils.toInt(row1.getDocumentId()));
						}
					}
		    	});
		    } else if (StringUtils.equals(sortField, "Status")) {
			    final boolean sortStatusIdAscending = Boolean.parseBoolean(request.getParameter("sortStatusAscending"));
		    	Collections.sort(approvalRows, new Comparator<ApprovalTimeSummaryRow>() {
					@Override
					public int compare(ApprovalTimeSummaryRow row1, ApprovalTimeSummaryRow row2) {
						if (sortStatusIdAscending) {
							return ObjectUtils.compare(StringUtils.lowerCase(row1.getApprovalStatus()), StringUtils.lowerCase(row2.getApprovalStatus()));
						} else {
							return ObjectUtils.compare(StringUtils.lowerCase(row2.getApprovalStatus()), StringUtils.lowerCase(row1.getApprovalStatus()));
						}
					}
		    	});
		    }
		    
		    taaf.setApprovalRows(approvalRows);
		    taaf.setResultSize(principalIds.size());
		}		
	}
	
	public ActionForward selectNewPayCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// resets the common fields for approval pages
		super.resetMainFields(form);
		TimeApprovalActionForm taaf = (TimeApprovalActionForm)form;
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
        DateTime beginDate = taaf.getCalendarEntry().getBeginPeriodFullDateTime();
        DateTime endDate = taaf.getCalendarEntry().getEndPeriodFullDateTime();
    	return TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(beginDate, endDate, taaf.getSelectedPayCalendarGroup(), assignmentPrincipalIds, taaf.getPayCalendarLabels(), taaf.getCalendarEntry());
    }
	
    public void resetState(ActionForm form, HttpServletRequest request) {
    	  TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
 	      String page = request.getParameter((new ParamEncoder(HrConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
 	      
 	      if (StringUtils.isBlank(page)) {
 			  taaf.getDepartments().clear();
 			  taaf.getWorkAreaDescr().clear();
 			  taaf.setApprovalRows(new ArrayList<ApprovalTimeSummaryRow>());
 			  taaf.setSelectedDept(null);
 			  taaf.setSearchField(null);
 			  taaf.setSearchTerm(null);
 	      }
	}
	
    @Override
    protected void populateCalendarAndPayPeriodLists(HttpServletRequest request, CalendarApprovalForm taf) {
    	TimeApprovalActionForm taaf = (TimeApprovalActionForm)taf;
		// set calendar year list
		Set<String> yearSet = new HashSet<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		// if selected calendar year is passed in
		if(!StringUtils.isEmpty(request.getParameter("selectedCY"))) {
			taaf.setSelectedCalendarYear(request.getParameter("selectedCY").toString());
		} else {
			taaf.setSelectedCalendarYear(sdf.format(taaf.getCalendarEntry().getBeginPeriodDate()));
		}
		
		List<CalendarEntry> pcListForYear = new ArrayList<CalendarEntry>();
		List<CalendarEntry> pceList = TkServiceLocator.getTimeApproveService()
			.getAllPayCalendarEntriesForApprover(HrContext.getTargetPrincipalId(), LocalDate.now());
	    for(CalendarEntry pce : pceList) {
	    	yearSet.add(sdf.format(pce.getBeginPeriodDate()));
	    	if(sdf.format(pce.getBeginPeriodDate()).equals(taaf.getSelectedCalendarYear())) {
	    		pcListForYear.add(pce);
	    	}
	    }
	    List<String> yearList = new ArrayList<String>(yearSet);
	    Collections.sort(yearList);
	    Collections.reverse(yearList);	// newest on top
	    taaf.setCalendarYears(yearList);
		
		// set pay period list contents
		if(!StringUtils.isEmpty(request.getParameter("selectedPP"))) {
			taaf.setSelectedPayPeriod(request.getParameter("selectedPP").toString());
		} else {
			taaf.setSelectedPayPeriod(taaf.getCalendarEntry().getHrCalendarEntryId());
			taaf.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(pcListForYear, null));
		}
		if(taaf.getPayPeriodsMap().isEmpty()) {
		    taaf.setPayPeriodsMap(ActionFormUtils.getPayPeriodsMap(pcListForYear, null));
		}
	}
    
    private List<String> getPrincipalIdsToPopulateTable(TimeApprovalActionForm taf) {
        List<String> workAreas = new ArrayList<String>();
        if (StringUtil.isEmpty(taf.getSelectedWorkArea())) {
        	for (Long workAreaKey : taf.getWorkAreaDescr().keySet()) {
        		workAreas.add(workAreaKey.toString());
        	}
        } else {
        	workAreas.add(taf.getSelectedWorkArea());
        }
        String calendar = taf.getSelectedPayCalendarGroup();
        LocalDate endDate = taf.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate().minusDays(1);
        LocalDate beginDate = taf.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate();

        return TkServiceLocator.getTimeApproveService().getTimePrincipalIdsWithSearchCriteria(workAreas, calendar, endDate, beginDate, endDate);      
	}	
}
