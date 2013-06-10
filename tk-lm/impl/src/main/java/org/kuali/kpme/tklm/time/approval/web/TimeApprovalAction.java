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

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hsqldb.lib.StringUtil;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.common.CalendarApprovalFormAction;
import org.kuali.kpme.tklm.time.approval.summaryrow.ApprovalTimeSummaryRow;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimeApprovalAction extends CalendarApprovalFormAction {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward actionForward = super.execute(mapping, form, request, response);
        
        TimeApprovalActionForm timeApprovalActionForm = (TimeApprovalActionForm) form;
        String documentId = timeApprovalActionForm.getDocumentId();
        
        setSearchFields(timeApprovalActionForm);
        
        CalendarEntry calendarEntry = null;
        if (StringUtils.isNotBlank(documentId)) {
        	TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(documentId);

			if (timesheetDocument != null) {
				calendarEntry = timesheetDocument.getCalendarEntry();
				timeApprovalActionForm.setCalendarDocument(timesheetDocument);
			}
        } else if (timeApprovalActionForm.getHrCalendarEntryId() != null) {
        	calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(timeApprovalActionForm.getHrCalendarEntryId());
        } else {
        	Calendar calendar = HrServiceLocator.getCalendarService().getCalendarByGroup(timeApprovalActionForm.getSelectedPayCalendarGroup());
            if (calendar != null) {
                calendarEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(calendar.getHrCalendarId(), LocalDate.now().toDateTimeAtStartOfDay());
            }
        }

        if (calendarEntry != null) {
        	timeApprovalActionForm.setHrCalendarEntryId(calendarEntry.getHrCalendarEntryId());
        	timeApprovalActionForm.setCalendarEntry(calendarEntry);
        	timeApprovalActionForm.setBeginCalendarEntryDate(calendarEntry.getBeginPeriodDateTime());
        	timeApprovalActionForm.setEndCalendarEntryDate(DateUtils.addMilliseconds(calendarEntry.getEndPeriodDateTime(), -1));
		
			CalendarEntry prevCalendarEntry = HrServiceLocator.getCalendarEntryService().getPreviousCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
			timeApprovalActionForm.setPrevHrCalendarEntryId(prevCalendarEntry != null ? prevCalendarEntry.getHrCalendarEntryId() : null);
			
			CalendarEntry nextCalendarEntry = HrServiceLocator.getCalendarEntryService().getNextCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
			timeApprovalActionForm.setNextHrCalendarEntryId(nextCalendarEntry != null ? nextCalendarEntry.getHrCalendarEntryId() : null);
			
	        setCalendarFields(timeApprovalActionForm);
	        
			timeApprovalActionForm.setPayCalendarLabels(TkServiceLocator.getTimeSummaryService().getHeaderForSummary(timeApprovalActionForm.getCalendarEntry(), new ArrayList<Boolean>()));
	        setApprovalTables(timeApprovalActionForm, request, getPrincipalIds(timeApprovalActionForm));
	        
	        if (timeApprovalActionForm.getApprovalRows() != null && !timeApprovalActionForm.getApprovalRows().isEmpty()) {
	        	timeApprovalActionForm.setOutputString(timeApprovalActionForm.getApprovalRows().get(0).getOutputString());
	        }
        }

        return actionForward;
	}
	
	@Override
	protected List<String> getCalendars(List<String> principalIds) {
		return HrServiceLocator.getPrincipalHRAttributeService().getUniquePayCalendars(principalIds);
	}
	
	public ActionForward selectNewPayCalendar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeApprovalActionForm timeApprovalActionForm = (TimeApprovalActionForm) form;
		
		timeApprovalActionForm.setApprovalRows(new ArrayList<ApprovalTimeSummaryRow>());
		
		return mapping.findForward("basic");
	}
	
	public ActionForward selectNewDept(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeApprovalActionForm timeApprovalActionForm = (TimeApprovalActionForm) form;
		
		setApprovalTables(timeApprovalActionForm, request, getPrincipalIds(timeApprovalActionForm));
    	
		return mapping.findForward("basic");
	}
	
	public ActionForward selectNewWorkArea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeApprovalActionForm timeApprovalActionForm = (TimeApprovalActionForm) form;

		setApprovalTables(timeApprovalActionForm, request, getPrincipalIds(timeApprovalActionForm));
    	
		return mapping.findForward("basic");
	}
	
	public ActionForward searchResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeApprovalActionForm timeApprovalActionForm = (TimeApprovalActionForm) form;
		
        if (StringUtils.equals("documentId", timeApprovalActionForm.getSearchField())) {
        	TimesheetDocumentHeader timesheetDocumentHeader = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(timeApprovalActionForm.getSearchTerm());
        	timeApprovalActionForm.setSearchTerm(timesheetDocumentHeader != null ? timesheetDocumentHeader.getPrincipalId() : StringUtils.EMPTY);
        }
        
        timeApprovalActionForm.setSearchField("principalId");
        List<String> principalIds = new ArrayList<String>();
        principalIds.add(timeApprovalActionForm.getSearchTerm());
        
        if (principalIds.isEmpty()) {
        	timeApprovalActionForm.setApprovalRows(new ArrayList<ApprovalTimeSummaryRow>());
        	timeApprovalActionForm.setResultSize(0);
        } else {
   	        setApprovalTables(timeApprovalActionForm, request, getPrincipalIds(timeApprovalActionForm));
   	        timeApprovalActionForm.setResultSize(principalIds.size());
        }
 
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
    
	protected void setApprovalTables(TimeApprovalActionForm timeApprovalActionForm, HttpServletRequest request, List<String> principalIds) {
		if (principalIds.isEmpty()) {
			timeApprovalActionForm.setApprovalRows(new ArrayList<ApprovalTimeSummaryRow>());
			timeApprovalActionForm.setResultSize(0);
		} else {
		    List<ApprovalTimeSummaryRow> approvalRows = getApprovalRows(timeApprovalActionForm, getSubListPrincipalIds(request, principalIds));
		    final String sortField = getSortField(request);
		    if (StringUtils.isEmpty(sortField) || StringUtils.equals(sortField, "name")) {
		    	final boolean sortNameAscending = isAscending(request);
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
		    	final boolean sortDocumentIdAscending = isAscending(request);
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
		    	final boolean sortStatusIdAscending = isAscending(request);;
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
		    
		    timeApprovalActionForm.setApprovalRows(approvalRows.subList(beginIndex, endIndex)); 	
		    timeApprovalActionForm.setResultSize(principalIds.size());
		    
		    timeApprovalActionForm.setOutputString(!CollectionUtils.isEmpty(timeApprovalActionForm.getApprovalRows()) ? timeApprovalActionForm.getApprovalRows().get(0).getOutputString() : null);
		}		
	}

    protected List<ApprovalTimeSummaryRow> getApprovalRows(TimeApprovalActionForm timeApprovalActionForm, List<String> assignmentPrincipalIds) {
    	return TkServiceLocator.getTimeApproveService().getApprovalSummaryRows(timeApprovalActionForm.getSelectedPayCalendarGroup(), assignmentPrincipalIds, timeApprovalActionForm.getPayCalendarLabels(), timeApprovalActionForm.getCalendarEntry());
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
	
}