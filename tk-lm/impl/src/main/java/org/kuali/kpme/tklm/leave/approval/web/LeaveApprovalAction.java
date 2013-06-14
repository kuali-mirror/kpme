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
package org.kuali.kpme.tklm.leave.approval.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.json.simple.JSONValue;
import org.kuali.kpme.core.calendar.Calendar;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.CalendarApprovalFormAction;
import org.kuali.kpme.tklm.leave.calendar.LeaveCalendarDocument;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveCalendarDocumentHeader;

public class LeaveApprovalAction extends CalendarApprovalFormAction {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveApprovalActionForm leaveApprovalActionForm = (LeaveApprovalActionForm) form;
        String documentId = leaveApprovalActionForm.getDocumentId();
        
        setSearchFields(leaveApprovalActionForm);
        
        CalendarEntry calendarEntry = null;
        if (StringUtils.isNotBlank(documentId)) {
        	LeaveCalendarDocument leaveCalendarDocument = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentId);

			if (leaveCalendarDocument != null) {
				calendarEntry = leaveCalendarDocument.getCalendarEntry();
				leaveApprovalActionForm.setCalendarDocument(leaveCalendarDocument);
			}
        } else if (leaveApprovalActionForm.getHrCalendarEntryId() != null) {
        	calendarEntry = HrServiceLocator.getCalendarEntryService().getCalendarEntry(leaveApprovalActionForm.getHrCalendarEntryId());
        } else {
        	Calendar calendar = HrServiceLocator.getCalendarService().getCalendarByGroup(leaveApprovalActionForm.getSelectedPayCalendarGroup());
            if (calendar != null) {
                calendarEntry = HrServiceLocator.getCalendarEntryService().getCurrentCalendarEntryByCalendarId(calendar.getHrCalendarId(), LocalDate.now().toDateTimeAtStartOfDay());
            }
        }
        
        if (calendarEntry != null) {
        	leaveApprovalActionForm.setHrCalendarEntryId(calendarEntry.getHrCalendarEntryId());
        	leaveApprovalActionForm.setCalendarEntry(calendarEntry);
        	leaveApprovalActionForm.setBeginCalendarEntryDate(calendarEntry.getBeginPeriodDateTime());
        	leaveApprovalActionForm.setEndCalendarEntryDate(DateUtils.addMilliseconds(calendarEntry.getEndPeriodDateTime(), -1));
		
			CalendarEntry prevCalendarEntry = HrServiceLocator.getCalendarEntryService().getPreviousCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
			leaveApprovalActionForm.setPrevHrCalendarEntryId(prevCalendarEntry != null ? prevCalendarEntry.getHrCalendarEntryId() : null);
			
			CalendarEntry nextCalendarEntry = HrServiceLocator.getCalendarEntryService().getNextCalendarEntryByCalendarId(calendarEntry.getHrCalendarId(), calendarEntry);
			leaveApprovalActionForm.setNextHrCalendarEntryId(nextCalendarEntry != null ? nextCalendarEntry.getHrCalendarEntryId() : null);
			
	        setCalendarFields(leaveApprovalActionForm);
        }
        
        ActionForward actionForward = super.execute(mapping, form, request, response);

        if (calendarEntry != null) {
			leaveApprovalActionForm.setLeaveCalendarDates(LmServiceLocator.getLeaveSummaryService().getLeaveSummaryDates(calendarEntry));
			setApprovalTables(leaveApprovalActionForm, request, getPrincipalIds(leaveApprovalActionForm));
		}
		
		return actionForward;
	}
	
	@Override
	protected List<String> getCalendars(List<String> principalIds) {
		return HrServiceLocator.getPrincipalHRAttributeService().getUniqueLeaveCalendars(principalIds);
	}
	
	public ActionForward selectNewPayCalendar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveApprovalActionForm leaveApprovalActionForm = (LeaveApprovalActionForm) form;

        leaveApprovalActionForm.setLeaveApprovalRows(new ArrayList<ApprovalLeaveSummaryRow>());
		
		return mapping.findForward("basic");
	}
	
	public ActionForward selectNewDept(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveApprovalActionForm leaveApprovalActionForm = (LeaveApprovalActionForm) form;
	
        setApprovalTables(leaveApprovalActionForm, request, getPrincipalIds(leaveApprovalActionForm));
    	
        return mapping.findForward("basic");
	}
	
	public ActionForward selectNewWorkArea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveApprovalActionForm leaveApprovalActionForm = (LeaveApprovalActionForm) form;

		setApprovalTables(leaveApprovalActionForm, request, getPrincipalIds(leaveApprovalActionForm));
        
		return mapping.findForward("basic");
	}	
	
	public ActionForward searchResult(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveApprovalActionForm leaveApprovalActionForm = (LeaveApprovalActionForm) form;
		
        if (StringUtils.equals("documentId", leaveApprovalActionForm.getSearchField())) {
        	LeaveCalendarDocumentHeader leaveCalendarDocumentHeader = LmServiceLocator.getLeaveCalendarDocumentHeaderService().getDocumentHeader(leaveApprovalActionForm.getSearchTerm());
        	leaveApprovalActionForm.setSearchTerm(leaveCalendarDocumentHeader != null ? leaveCalendarDocumentHeader.getPrincipalId() : StringUtils.EMPTY);
        }
        
    	leaveApprovalActionForm.setSearchField("principalId");
        List<String> principalIds = new ArrayList<String>();
        principalIds.add(leaveApprovalActionForm.getSearchTerm());
        
        if (principalIds.isEmpty()) {
        	leaveApprovalActionForm.setLeaveApprovalRows(new ArrayList<ApprovalLeaveSummaryRow>());
        	leaveApprovalActionForm.setResultSize(0);
        } else {
        	setApprovalTables(leaveApprovalActionForm, request, principalIds);
        	leaveApprovalActionForm.setResultSize(principalIds.size());
        }
 
		return mapping.findForward("basic");
	}
	
	protected List<String> getPrincipalIds(LeaveApprovalActionForm leaveApprovalActionForm) {
        List<String> workAreas = new ArrayList<String>();
        if (StringUtil.isEmpty(leaveApprovalActionForm.getSelectedWorkArea())) {
        	for (Long workAreaKey : leaveApprovalActionForm.getWorkAreaDescr().keySet()) {
        		workAreas.add(workAreaKey.toString());
        	}
        } else {
        	workAreas.add(leaveApprovalActionForm.getSelectedWorkArea());
        }
        String calendar = leaveApprovalActionForm.getSelectedPayCalendarGroup();
        LocalDate endDate = leaveApprovalActionForm.getCalendarEntry().getEndPeriodFullDateTime().toLocalDate().minusDays(1);
        LocalDate beginDate = leaveApprovalActionForm.getCalendarEntry().getBeginPeriodFullDateTime().toLocalDate();

        return LmServiceLocator.getLeaveApprovalService().getLeavePrincipalIdsWithSearchCriteria(workAreas, calendar, endDate, beginDate, endDate);
	}	
	
	private void setApprovalTables(LeaveApprovalActionForm leaveApprovalActionForm, HttpServletRequest request, List<String> principalIds) {
		if (principalIds.isEmpty()) {
			leaveApprovalActionForm.setLeaveApprovalRows(new ArrayList<ApprovalLeaveSummaryRow>());
			leaveApprovalActionForm.setResultSize(0);
		} else {
			List<ApprovalLeaveSummaryRow> approvalRows = getApprovalLeaveRows(leaveApprovalActionForm, principalIds); 
			String sortField = getSortField(request);
			if (StringUtils.isEmpty(sortField) || StringUtils.equals(sortField, "name")) {
				final boolean sortNameAscending = getAscending(request);
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
			} else if (StringUtils.equals(sortField, "documentID")) {
				final boolean sortDocumentIdAscending = getAscending(request);
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
			} else if (StringUtils.equals(sortField, "status")) {
				final boolean sortStatusIdAscending = getAscending(request);
		    	Collections.sort(approvalRows, new Comparator<ApprovalLeaveSummaryRow>() {
					@Override
					public int compare(ApprovalLeaveSummaryRow row1, ApprovalLeaveSummaryRow row2) {
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

			leaveApprovalActionForm.setLeaveApprovalRows(approvalRows.subList(beginIndex, endIndex));
		    leaveApprovalActionForm.setResultSize(principalIds.size());
		    
		    Map<String, String> userColorMap = new HashMap<String, String>();
	        Set<String> randomColors = new HashSet<String>();
		    List<Map<String, String>> approvalRowsMap = new ArrayList<Map<String, String>>();
		    if(approvalRows != null && !approvalRows.isEmpty()) {
		    	for (ApprovalLeaveSummaryRow row : approvalRows) {
		    		for (Date date : leaveApprovalActionForm.getLeaveCalendarDates()) {
		    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						String dateString = formatter.format(date);
		    			Map<String, BigDecimal> earnCodeMap = row.getEarnCodeLeaveHours().get(date);
		    			if(earnCodeMap != null && !earnCodeMap.isEmpty()) {
			    			for (String key : earnCodeMap.keySet()){
			    				String color = null;
			    				
			    				Map<String, String> event = new HashMap<String, String>();
			    				event.put("title", (key.split("[|]"))[0] + "-" +earnCodeMap.get(key).toString());
			    			    event.put("start", dateString);
			    			    
			    			    if(!userColorMap.containsKey(row.getPrincipalId())) {
			    	        		color = TKUtils.getRandomColor(randomColors);
			    	        		randomColors.add(color);
			    	        		userColorMap.put(row.getPrincipalId(), color);
			    	        	}
			    	        	row.setColor(userColorMap.get(row.getPrincipalId()));
			            		event.put("color", userColorMap.get(row.getPrincipalId()));
			            		event.put("className", "event-approval");
			    				approvalRowsMap.add(event);
			    			}
		    			}
		    		}
		    	}
		    }
		    
		    leaveApprovalActionForm.setOutputString(JSONValue.toJSONString(approvalRowsMap));
		}
	}
	   
    protected List<ApprovalLeaveSummaryRow> getApprovalLeaveRows(LeaveApprovalActionForm leaveApprovalActionForm, List<String> assignmentPrincipalIds) {
        return LmServiceLocator.getLeaveApprovalService().getLeaveApprovalSummaryRows(assignmentPrincipalIds, leaveApprovalActionForm.getCalendarEntry(), leaveApprovalActionForm.getLeaveCalendarDates());
    }
	
    public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveApprovalActionForm laaf = (LeaveApprovalActionForm) form;
       
        List<ApprovalLeaveSummaryRow> lstLeaveRows = laaf.getLeaveApprovalRows();
        for (ApprovalLeaveSummaryRow ar : lstLeaveRows) {
            if (ar.isApprovable() && StringUtils.equals(ar.getSelected(), "on")) {
                String documentNumber = ar.getDocumentId();
                LeaveCalendarDocument lcd = LmServiceLocator.getLeaveCalendarService().getLeaveCalendarDocument(documentNumber);
                LmServiceLocator.getLeaveCalendarService().approveLeaveCalendar(HrContext.getPrincipalId(), lcd);
            }
        }  
        
        return mapping.findForward("basic");
    }

}