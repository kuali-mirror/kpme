/**
 * Copyright 2004-2014 The Kuali Foundation
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hsqldb.lib.StringUtil;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.calendar.entry.CalendarEntryContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.common.CalendarApprovalFormAction;
import org.kuali.kpme.tklm.time.approval.summaryrow.ApprovalTimeSummaryRow;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.time.missedpunch.MissedPunchDocument;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timeblock.TimeBlock;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.krad.util.ErrorMessage;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.springframework.util.CollectionUtils;

public class TimeApprovalAction extends CalendarApprovalFormAction {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward actionForward = super.execute(mapping, form, request, response);
        
        TimeApprovalActionForm timeApprovalActionForm = (TimeApprovalActionForm) form;
        String documentId = timeApprovalActionForm.getDocumentId();
        
        setSearchFields(timeApprovalActionForm);

        CalendarEntryContract calendarEntry = null;
        if (StringUtils.isNotBlank(documentId)) {
        	TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);

			if (timesheetDocument != null) {
				calendarEntry = timesheetDocument.getCalendarEntry();
				timeApprovalActionForm.setCalendarDocument(timesheetDocument);
			}
        } else if (StringUtils.isNotBlank(timeApprovalActionForm.getHrCalendarEntryId())) {
        	calendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCalendarEntry(timeApprovalActionForm.getHrCalendarEntryId());
        } else if (StringUtils.isNotBlank(timeApprovalActionForm.getSelectedPayPeriod())) {
        	calendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCalendarEntry(timeApprovalActionForm.getSelectedPayPeriod());
        } else {
        	Calendar calendar = (Calendar) HrServiceLocator.getCalendarService().getCalendarByGroup(timeApprovalActionForm.getSelectedPayCalendarGroup());
            if (calendar != null) {
                calendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(calendar.getHrCalendarId(), LocalDate.now().toDateTimeAtStartOfDay());
            }
        }

        if (calendarEntry != null) {
        	timeApprovalActionForm.setHrCalendarEntryId(calendarEntry.getHrCalendarEntryId());
        	timeApprovalActionForm.setCalendarEntry(calendarEntry);
        	timeApprovalActionForm.setBeginCalendarEntryDate(calendarEntry.getBeginPeriodDateTime());
        	timeApprovalActionForm.setEndCalendarEntryDate(DateUtils.addMilliseconds(calendarEntry.getEndPeriodDateTime(), -1));
		
			CalendarEntry prevCalendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getPreviousCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
			timeApprovalActionForm.setPrevHrCalendarEntryId(prevCalendarEntry != null ? prevCalendarEntry.getHrCalendarEntryId() : null);
			
			CalendarEntry nextCalendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getNextCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
			timeApprovalActionForm.setNextHrCalendarEntryId(nextCalendarEntry != null ? nextCalendarEntry.getHrCalendarEntryId() : null);
			
	        setCalendarFields(timeApprovalActionForm);
	        
			timeApprovalActionForm.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(timeApprovalActionForm.getCalendarEntry(), new ArrayList<Boolean>()));
			
			List<String> allPIdsList = getPrincipalIds(timeApprovalActionForm);
			List<String> pidList = new ArrayList<String>();
			pidList.addAll(allPIdsList);
			
			String docIdSearchTerm = "";
			if(StringUtils.equals(timeApprovalActionForm.getMethodToCall(), "searchResult") ) {
				if(StringUtils.equals(timeApprovalActionForm.getSearchField(), "principalName") ) {	            
		            if (StringUtils.isNotBlank(timeApprovalActionForm.getSearchTerm())) {
		            	String searchTerm = timeApprovalActionForm.getSearchTerm();
		            	pidList = new ArrayList<String>();
		            	for(String anId : allPIdsList) {
		            		if(anId.contains(searchTerm)) {
		            			pidList.add(anId);
		            		}
		            	}
		            }
			      }
				
				if(StringUtils.equals(timeApprovalActionForm.getSearchField(), "documentId") )	            
		            docIdSearchTerm = timeApprovalActionForm.getSearchTerm();
			}
				
	        setApprovalTables(timeApprovalActionForm, request, pidList, docIdSearchTerm);
        }
        setMessages(timeApprovalActionForm);
        return actionForward;
	}
	
	@Override
	protected List<String> getCalendars(List<String> principalIds) {
		return HrServiceLocator.getPrincipalHRAttributeService().getUniquePayCalendars(principalIds);
	}
	
	public ActionForward selectNewPayCalendar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeApprovalActionForm timeApprovalActionForm = (TimeApprovalActionForm) form;
		CalendarEntry calendarEntry = null;
		Calendar calendar = (Calendar) HrServiceLocator.getCalendarService().getCalendarByGroup(timeApprovalActionForm.getSelectedPayCalendarGroup());
        
		if (calendar != null) {
            calendarEntry = (CalendarEntry) HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(calendar.getHrCalendarId(), LocalDate.now().toDateTimeAtStartOfDay());
        }
        
        if (calendarEntry != null) {
        	timeApprovalActionForm.setHrCalendarEntryId(calendarEntry.getHrCalendarEntryId());
        	timeApprovalActionForm.setCalendarEntry(calendarEntry);
        	// change pay period map 
        	this.setCalendarFields(timeApprovalActionForm);
        }
		
		timeApprovalActionForm.setApprovalRows(new ArrayList<ApprovalTimeSummaryRow>());
		
		return mapping.findForward("basic");
	}
	
	public ActionForward selectNewDept(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeApprovalActionForm timeApprovalActionForm = (TimeApprovalActionForm) form;
		setApprovalTables(timeApprovalActionForm, request, getPrincipalIds(timeApprovalActionForm), "");
    	
		return mapping.findForward("basic");
	}
	
	public ActionForward selectNewWorkArea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeApprovalActionForm timeApprovalActionForm = (TimeApprovalActionForm) form;
		setApprovalTables(timeApprovalActionForm, request, getPrincipalIds(timeApprovalActionForm), "");
    	
		return mapping.findForward("basic");
	}
	

	public ActionForward searchResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeApprovalActionForm timeApprovalActionForm = (TimeApprovalActionForm) form;
		
		if(StringUtils.isBlank(timeApprovalActionForm.getSearchField()) 
				&& StringUtils.isNotBlank(request.getParameter("searchField"))) {
			timeApprovalActionForm.setSearchField(request.getParameter("searchField"));
		}
		if(StringUtils.isBlank(timeApprovalActionForm.getSearchTerm()) 
				&& StringUtils.isNotBlank(request.getParameter("searchValue"))) {
			timeApprovalActionForm.setSearchTerm(request.getParameter("searchValue"));
		}		
		if(StringUtils.isBlank(timeApprovalActionForm.getSelectedPayPeriod()) 
				&& StringUtils.isNotBlank(request.getParameter("selectedPayPeriod"))) {
			timeApprovalActionForm.setSelectedPayPeriod(request.getParameter("selectedPayPeriod"));
		}		
		return mapping.findForward("basic");
	}
	
	public ActionForward resetSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeApprovalActionForm timeApprovalActionForm = (TimeApprovalActionForm) form;
		timeApprovalActionForm.setSearchField("");
		timeApprovalActionForm.setSearchTerm("");
		return mapping.findForward("basic");
	}
	
	public ActionForward goToCurrentPeriod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeApprovalActionForm timeApprovalActionForm = (TimeApprovalActionForm) form;
		timeApprovalActionForm.setSearchField("");
		timeApprovalActionForm.setSearchTerm("");
		timeApprovalActionForm.setSelectedPayPeriod("");
		timeApprovalActionForm.setHrCalendarEntryId("");
		return mapping.findForward("basic");
	}
	
    private List<String> getPrincipalIds(TimeApprovalActionForm timeApprovalActionForm) {
        List<String> workAreas = new ArrayList<String>();
        if (StringUtil.isEmpty(timeApprovalActionForm.getSelectedWorkArea())) {
        	for (Long workAreaKey : timeApprovalActionForm.getWorkAreaDescr().keySet()) {
        		workAreas.add(workAreaKey.toString());
        	}
        } else {
        	workAreas.add(timeApprovalActionForm.getSelectedWorkArea());
        }
        String calendar = timeApprovalActionForm.getSelectedPayCalendarGroup();
        if (timeApprovalActionForm.getCalendarEntry() == null) {
            return Collections.emptyList();
        }
        LocalDate endDate = timeApprovalActionForm.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate().minusDays(1);
        LocalDate beginDate = timeApprovalActionForm.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate();

        return TkServiceLocator.getTimeApproveService().getTimePrincipalIdsWithSearchCriteria(workAreas, calendar, endDate, beginDate, endDate);      
	}
    
	protected void setApprovalTables(TimeApprovalActionForm timeApprovalActionForm, HttpServletRequest request, List<String> principalIds, String docIdSearchTerm) {
		if (principalIds.isEmpty()) {
			timeApprovalActionForm.setApprovalRows(new ArrayList<ApprovalTimeSummaryRow>());
			timeApprovalActionForm.setResultSize(0);
			timeApprovalActionForm.setOutputString(null);
		} else {
		    List<ApprovalTimeSummaryRow> approvalRows = getApprovalRows(timeApprovalActionForm, getSubListPrincipalIds(request, principalIds), docIdSearchTerm);
		    timeApprovalActionForm.setOutputString(!CollectionUtils.isEmpty(approvalRows) ? approvalRows.get(0).getOutputString() : null);
		    final String sortField = getSortField(request);
		    if (StringUtils.isEmpty(sortField) || StringUtils.equals(sortField, "name")) {
		    	final boolean sortNameAscending = getAscending(request);
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
		    } else if (StringUtils.equals(sortField, "documentID")) {
		    	final boolean sortDocumentIdAscending = getAscending(request);
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
		    } else if (StringUtils.equals(sortField, "status")) {
		    	final boolean sortStatusIdAscending = getAscending(request);;
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
		    
		    String page = request.getParameter((new ParamEncoder(HrConstants.APPROVAL_TABLE_ID).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		    Integer beginIndex = StringUtils.isBlank(page) || StringUtils.equals(page, "1") ? 0 : (Integer.parseInt(page) - 1)*HrConstants.PAGE_SIZE;
		    Integer endIndex = beginIndex + HrConstants.PAGE_SIZE > approvalRows.size() ? approvalRows.size() : beginIndex + HrConstants.PAGE_SIZE;
		    for (ApprovalTimeSummaryRow approvalTimeSummaryRow : approvalRows) {
 	 	 	 	approvalTimeSummaryRow.setMissedPunchList(getMissedPunches(approvalTimeSummaryRow.getDocumentId()));
		    }
            List<ApprovalTimeSummaryRow> sublist = new ArrayList<ApprovalTimeSummaryRow>();
            sublist.addAll(approvalRows.subList(beginIndex, endIndex));
		    timeApprovalActionForm.setApprovalRows(sublist);
		    timeApprovalActionForm.setResultSize(sublist.size());
		}		
	}
	
	private List<MissedPunch> getMissedPunches(String documentId) {
		List<MissedPunch> missedPunchList = new ArrayList<MissedPunch>();
		List<MissedPunchDocument> mpDoc = TkServiceLocator.getMissedPunchService().getMissedPunchDocumentsByTimesheetDocumentId(documentId);
		if(mpDoc!=null){
			for (MissedPunchDocument mpd : mpDoc) {
				missedPunchList.add(mpd.getMissedPunch());
			}
		}
		return missedPunchList;
	}
	
	protected List<ApprovalTimeSummaryRow> getApprovalRows(TimeApprovalActionForm timeApprovalActionForm, List<String> assignmentPrincipalIds, String docIdSearchTerm) {
    	return TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(timeApprovalActionForm.getSelectedPayCalendarGroup(), assignmentPrincipalIds, timeApprovalActionForm.getPayCalendarLabels(), timeApprovalActionForm.getCalendarEntry(), docIdSearchTerm);
    }
	
    public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeApprovalActionForm taaf = (TimeApprovalActionForm) form;
        List<ApprovalTimeSummaryRow> lstApprovalRows = taaf.getApprovalRows();
        List<String> errorList = new ArrayList<String>();
        for (ApprovalTimeSummaryRow ar : lstApprovalRows) {
            if (ar.isApprovable() && StringUtils.equals(ar.getSelected(), "on")) {
                String documentNumber = ar.getDocumentId();
                TimesheetDocument tDoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentNumber);
                if (tDoc != null) {
                    if(TkServiceLocator.getTimesheetService().isTimesheetValid(tDoc)) {
                        TkServiceLocator.getTimesheetService().approveTimesheet(HrContext.getPrincipalId(), tDoc);
                    } else {
                        errorList.add( "Timesheet " + tDoc.getDocumentId() + " could not be approved as it contains errors, see time detail for more info");
                    }
                }
            }
        }
        ActionRedirect redirect = new ActionRedirect(mapping.findForward("basicRedirect"));
        redirect.addParameter("selectedDept", taaf.getSelectedDept());
        redirect.addParameter("selectedPayCalendarGroup", taaf.getSelectedPayCalendarGroup());
        redirect.addParameter("selectedWorkArea", taaf.getSelectedWorkArea());
        redirect.addParameter("selectedPayPeriod", taaf.getSelectedPayPeriod());
        redirect.addParameter("errorMessageList", errorList);
        
        return redirect;
    }

    protected void setMessages(TimeApprovalActionForm taaf) {
        List<ApprovalTimeSummaryRow> lstApprovalRows = taaf.getApprovalRows();
        List<String> errorList = new ArrayList<String>();
        for (ApprovalTimeSummaryRow ar : lstApprovalRows) {
            String documentNumber = ar.getDocumentId();
            TimesheetDocument tDoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentNumber);
            if (tDoc != null && !TkServiceLocator.getTimesheetService().isTimesheetValid(tDoc)) {
                    errorList.add("Timesheet " + tDoc.getDocumentId() + " could not be approved as it contains errors, see time detail for more info");
            }
        }
        taaf.setErrorMessageList(errorList);
    }
}