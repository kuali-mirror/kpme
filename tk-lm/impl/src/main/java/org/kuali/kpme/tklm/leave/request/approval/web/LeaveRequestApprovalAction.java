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
package org.kuali.kpme.tklm.leave.request.approval.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.common.ApprovalFormAction;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.block.LeaveBlockBo;
import org.kuali.kpme.tklm.leave.calendar.LeaveRequestCalendar;
import org.kuali.kpme.tklm.leave.calendar.web.LeaveActionFormUtils;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveRequestDocument;
import org.kuali.kpme.tklm.time.util.TkContext;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionItem;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.util.GlobalVariables;

public class LeaveRequestApprovalAction extends ApprovalFormAction {
	
    public static final String DOC_SEPARATOR = "----";	// separator for documents
    public static final String ID_SEPARATOR = "____";	// separator for documentId and reason string
    public static final String DOC_NOT_FOUND = "Leave request document not found with id ";
    public static final String LEAVE_BLOCK_NOT_FOUND = "Leave Block not found for Leave request document ";
    public static final String APPROVE_ACTION = "Approve";	
    public static final String DISAPPROVE_ACTION = "Disapprove";
    public static final String DEFER_ACTION = "Defer";
    
    @Override
	protected void checkTKAuthorization(ActionForm form, String methodToCall) throws AuthorizationException {
		if (!HrContext.isReviewer() && !HrContext.isAnyApprover() && !HrContext.isSystemAdmin() && !TkContext.isLocationAdmin() 
				&& !HrContext.isGlobalViewOnly() && !TkContext.isDepartmentViewOnly() && !TkContext.isDepartmentAdmin()) {
			throw new AuthorizationException(GlobalVariables.getUserSession().getPrincipalId(), "ApprovalFormAction", "");
		}
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.execute(mapping, form, request, response);
        LeaveRequestApprovalActionForm leaveRequestApprovalActionForm = (LeaveRequestApprovalActionForm) form;
        
        setSearchFields(leaveRequestApprovalActionForm);
        if(leaveRequestApprovalActionForm.getSelectedCalendarType() == null) {
        	leaveRequestApprovalActionForm.setSelectedCalendarType("M");
        }
        setUpLeaveRequestApprovalFieldsForm(leaveRequestApprovalActionForm, false);
		return forward;
	}
	
    protected List<LeaveBlock> getLeaveBlocks(String principalId, LocalDate beginDate, LocalDate endDate) {
        return LmServiceLocator.getLeaveBlockService().getLeaveBlocksForLeaveCalendar(principalId, beginDate, endDate, Collections.<String>emptyList());
    }
	
	public ActionForward selectNewDept(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveRequestApprovalActionForm leaveRequestApprovalActionForm = (LeaveRequestApprovalActionForm) form;
        setUpLeaveRequestApprovalFieldsForm(leaveRequestApprovalActionForm, false);
		return mapping.findForward("basic");
	}
	
	public ActionForward selectNewWorkArea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LeaveRequestApprovalActionForm leaveRequestApprovalActionForm = (LeaveRequestApprovalActionForm) form;
        setUpLeaveRequestApprovalFieldsForm(leaveRequestApprovalActionForm, false);
		return mapping.findForward("basic");
	}
	
	public ActionForward selectNewCalendarType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveRequestApprovalActionForm leaveRequestApprovalActionForm = (LeaveRequestApprovalActionForm) form;
		setUpLeaveRequestApprovalFieldsForm(leaveRequestApprovalActionForm, true);
		return mapping.findForward("basic");
	}
	
	private Map<String, List<LeaveRequestDocument>> getLeaveRequestDocsMap(List<String> principalIds, String dept, List<String> workAreaList, LocalDate beginDate, LocalDate endDate) {
		String principalId = HrContext.getTargetPrincipalId();
		List<ActionItem> actionList = KewApiServiceLocator.getActionListService().getActionItemsForPrincipal(principalId);
		Map<String, List<LeaveRequestDocument>> resultsMap = new HashMap<String, List<LeaveRequestDocument>>();

		List<LeaveRequestDocument> leaveReqDocs = new ArrayList<LeaveRequestDocument>();
		if(CollectionUtils.isNotEmpty(principalIds)) {
			for(ActionItem anAction : actionList) {
				String docId = anAction.getDocumentId();
				if(anAction.getDocName().equals(LeaveRequestDocument.LEAVE_REQUEST_DOCUMENT_TYPE)) {
					LeaveRequestDocument lrd = LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
					if(lrd != null) {
						LeaveBlock lb = lrd.getLeaveBlock();
						if(lb != null) {
							if(principalIds.contains(lb.getPrincipalId())) {
								String key = lb.getLeaveLocalDate().toString();
								if(resultsMap.containsKey(key)) {
									leaveReqDocs = resultsMap.get(key);
								} else {
									leaveReqDocs = new ArrayList<LeaveRequestDocument>();
								}
								leaveReqDocs.add(lrd);
								resultsMap.put(key, leaveReqDocs);
							}
						}
					}
				}
			}
		}
        return resultsMap;
	}
	
	private Map<String,List<LeaveBlock>> getLeaveBlocksForDisplay(List<String> principalIds, String dept, List<String> workAreaList, LocalDate beginDate, LocalDate endDate) {
		Map<String, List<LeaveBlock>> leaveBlockMaps = new HashMap<String, List<LeaveBlock>>();
		List<LeaveBlock> leaveBlocksForDisplay = new ArrayList<LeaveBlock>();
		List<LeaveBlock> leaveBlocks = new ArrayList<LeaveBlock>();
		if(CollectionUtils.isNotEmpty(principalIds)) {
			for(String userId : principalIds) {
				leaveBlocksForDisplay = getLeaveBlocks(userId, beginDate, endDate);
				for(LeaveBlock lb : leaveBlocksForDisplay) {
					if(lb.getLeaveBlockType().equalsIgnoreCase(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_CALENDAR) && 
					   (lb.getRequestStatus().equalsIgnoreCase(HrConstants.REQUEST_STATUS.REQUESTED) || 
					   lb.getRequestStatus().equalsIgnoreCase(HrConstants.REQUEST_STATUS.APPROVED) ||
					   lb.getRequestStatus().equalsIgnoreCase(HrConstants.REQUEST_STATUS.USAGE))) {
							String key = lb.getLeaveLocalDate().toString();
							if(leaveBlockMaps.containsKey(key)) {
								leaveBlocks = leaveBlockMaps.get(key);
							} else {
								leaveBlocks = new ArrayList<LeaveBlock>();
							}
							leaveBlocks.add(lb);
							leaveBlockMaps.put(key, leaveBlocks);
					}
				}
			}
		}
        return leaveBlockMaps;
	}

	

	public ActionForward takeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		String action =  lraaForm.getAction();
		if(StringUtils.isNotEmpty(action) && StringUtils.isNotEmpty(lraaForm.getActionList())) {
			String[] actionList = lraaForm.getActionList().split(DOC_SEPARATOR);
			for(String docId : actionList){
				if(StringUtils.isNotEmpty(docId) && !StringUtils.equals(docId, DOC_SEPARATOR)) {
					String reasonString = lraaForm.getReason(); 	// approve reason text, could be empty
					if(StringUtils.equals(action, APPROVE_ACTION)) {
						LmServiceLocator.getLeaveRequestDocumentService().approveLeave(docId, HrContext.getPrincipalId(), reasonString);
						// leave block's status is changed to "approved" in postProcessor of LeaveRequestDocument
					} else if (StringUtils.equals(action, DISAPPROVE_ACTION)) {
						LmServiceLocator.getLeaveRequestDocumentService().disapproveLeave(docId, HrContext.getPrincipalId(), reasonString);
						// leave block's status is changed to "disapproved" in postProcessor of LeaveRequestDocument
					} else if (StringUtils.equals(action, DEFER_ACTION)) {
						LmServiceLocator.getLeaveRequestDocumentService().deferLeave(docId, HrContext.getPrincipalId(), reasonString);
						// leave block's status is changed to "deferred" in postProcessor of LeaveRequestDocument
					}
				}
			}
		}
		lraaForm.setDocumentId(null);
		return mapping.findForward("basic");
	}
	
	@Override
	protected List<String> getCalendars(List<String> principalIds) {
		return HrServiceLocator.getPrincipalHRAttributeService().getUniqueLeaveCalendars(principalIds);
	}

	public ActionForward validateNewActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		JSONArray errorMsgList = new JSONArray();
        List<String> errors = new ArrayList<String>();
        if(StringUtils.isEmpty(lraaForm.getActionList())){ 
        	errors.add("No Actions selected. Please try again.");
        } else {
			if(StringUtils.isNotEmpty(lraaForm.getActionList())) {
				String[] actionList = lraaForm.getActionList().split(DOC_SEPARATOR);
				for(String docId : actionList){
					if(StringUtils.isNotEmpty(docId) && !StringUtils.equals(docId, DOC_SEPARATOR)) {
						LeaveRequestDocument lrd = LmServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
						if(lrd == null) {
							errors.add(DOC_NOT_FOUND + docId);
							break;
						} else {
							LeaveBlock lb = LmServiceLocator.getLeaveBlockService().getLeaveBlock(lrd.getLmLeaveBlockId());
							if(lb == null) {
								errors.add(LEAVE_BLOCK_NOT_FOUND + docId);
								break;
							}
						}
					}
				}
			}
        }
        errorMsgList.addAll(errors);
        lraaForm.setOutputString(JSONValue.toJSONString(errorMsgList));
        return mapping.findForward("ws");
    }
	
	
	private void setUpLeaveRequestApprovalFieldsForm(LeaveRequestApprovalActionForm leaveRequestApprovalActionForm, boolean isCalendarTypeChanged) {
        String calendarType = leaveRequestApprovalActionForm.getSelectedCalendarType();
        String bString = leaveRequestApprovalActionForm.getBeginDateString();
        String eString = leaveRequestApprovalActionForm.getEndDateString();
        String navigationString = leaveRequestApprovalActionForm.getNavigationAction();
        DateTime beginDateTime = null;
        DateTime endDateTime = null;
        String selectedPrincipal = leaveRequestApprovalActionForm.getSelectedPrincipal();
        List<String> principalIds = new ArrayList<String>();
        if(bString != null && eString != null) {
        	beginDateTime = TKUtils.formatDateTimeStringNoTimezone(leaveRequestApprovalActionForm.getBeginDateString());
        	endDateTime = TKUtils.formatDateTimeStringNoTimezone(leaveRequestApprovalActionForm.getEndDateString());
        } else {
        	DateMidnight dtFirst = new DateMidnight().withDayOfMonth(1);
        	dtFirst = dtFirst.plusMonths(1);
        	beginDateTime = new DateTime(dtFirst.getMillis());
        	endDateTime = beginDateTime.plusMonths(1);
        }
    	if(isCalendarTypeChanged) {
    		beginDateTime = beginDateTime.withDayOfMonth(1);
    		if(calendarType.equalsIgnoreCase("W")) {
    			if(beginDateTime.getDayOfWeek() != DateTimeConstants.SUNDAY) {
    				endDateTime = beginDateTime.withDayOfWeek(7);
    				beginDateTime = beginDateTime.withDayOfWeek(1).minus(1);
    			} else {
    				endDateTime = beginDateTime.plusDays(1).withDayOfWeek(7);
    				beginDateTime = beginDateTime.withDayOfWeek(1).minusDays(1);    				
    			}
    		} else {
    			endDateTime = beginDateTime.plusMonths(1);
    		}
    	} 
    	// check navigation String
    	if(navigationString != null) {
        	if(navigationString.equalsIgnoreCase("PREV")) {
        		if(calendarType.equalsIgnoreCase("W")) {
        			beginDateTime = beginDateTime.minusWeeks(1);
        			if(beginDateTime.getDayOfWeek() != DateTimeConstants.SUNDAY) {
        				endDateTime = beginDateTime.withDayOfWeek(7);
        			} else {
        				endDateTime = beginDateTime.plusDays(1).withDayOfWeek(7);
        			}
        		} else {
        			beginDateTime = beginDateTime.minusMonths(1);
        			endDateTime = beginDateTime.plusMonths(1);
        		}
        	} else if(navigationString.equalsIgnoreCase("NEXT")) {
        		if(calendarType.equalsIgnoreCase("W")) {
        			beginDateTime = beginDateTime.plusWeeks(1);
        			if(beginDateTime.getDayOfWeek() != DateTimeConstants.SUNDAY) {
        				endDateTime = beginDateTime.withDayOfWeek(7);
        			} else {
        				endDateTime = beginDateTime.plusDays(1).withDayOfWeek(7);
        			}
        		} else {
        			beginDateTime = beginDateTime.plusMonths(1);
        			endDateTime = beginDateTime.plusMonths(1);
        		}
        	}
        	leaveRequestApprovalActionForm.setNavigationAction(null);
    	}

    	List<String> principalIdsToSearch = new ArrayList<String>();
    	
//        DateTime beginDateTime =  currentCE.getBeginPeriodLocalDateTime().toDateTime(HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
//        DateTime endDateTime = currentCE.getEndPeriodLocalDateTime().toDateTime(HrServiceLocator.getTimezoneService().getUserTimezoneWithFallback());
        if(selectedPrincipal == null || StringUtils.isEmpty(selectedPrincipal)) {
        	List<String> leaveCalendars = leaveRequestApprovalActionForm.getPayCalendarGroups();
        	for(String calendar : leaveCalendars) {
            	principalIds.addAll(LmServiceLocator.getLeaveApprovalService()
        	 			.getLeavePrincipalIdsWithSearchCriteria(getWorkAreas(leaveRequestApprovalActionForm), calendar, LocalDate.now(), beginDateTime.toLocalDate(), endDateTime.toLocalDate()));
            	Collections.sort(principalIds);
            	leaveRequestApprovalActionForm.setPrincipalIds(principalIds);
        	}
        	principalIdsToSearch = new ArrayList<String>(principalIds);
        } else {
        	principalIdsToSearch = Collections.singletonList(selectedPrincipal);
        }
        
		// set LeaveCalendar
		Map<String, List<LeaveRequestDocument>> leaveReqDocsMap = getLeaveRequestDocsMap(principalIdsToSearch, leaveRequestApprovalActionForm.getSelectedDept(), getWorkAreas(leaveRequestApprovalActionForm), beginDateTime.toLocalDate(), endDateTime.plusDays(1).toLocalDate());
		Map<String, List<LeaveBlock>> leaveBlocksMap = getLeaveBlocksForDisplay(principalIdsToSearch, leaveRequestApprovalActionForm.getSelectedDept(), getWorkAreas(leaveRequestApprovalActionForm), beginDateTime.toLocalDate(), endDateTime.plusDays(1).toLocalDate());
		leaveRequestApprovalActionForm.setLeaveRequestCalendar(new LeaveRequestCalendar(beginDateTime, endDateTime, leaveReqDocsMap, leaveBlocksMap, calendarType));

		// generate json
		leaveRequestApprovalActionForm.setLeaveRequestString(LeaveActionFormUtils.getLeaveRequestsJson(leaveRequestApprovalActionForm.getLeaveRequestCalendar().getRequestList()));
		
		// set begin and end date time string
		leaveRequestApprovalActionForm.setBeginDateString(TKUtils.formatDateTimeLong(leaveRequestApprovalActionForm.getLeaveRequestCalendar().getBeginDateTime()));
		leaveRequestApprovalActionForm.setEndDateString(TKUtils.formatDateTimeLong(leaveRequestApprovalActionForm.getLeaveRequestCalendar().getEndDateTime()));		
    	
	}

}
