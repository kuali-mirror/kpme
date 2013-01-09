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
package org.kuali.hr.lm.request.approval.web;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.workflow.LeaveRequestDocument;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.base.web.ApprovalAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionItem;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class LeaveRequestApprovalAction  extends ApprovalAction {
	
    private static final Logger LOG = Logger.getLogger(LeaveRequestApprovalAction.class);
    public static final String DOC_SEPARATOR = "----";	// separator for documents
    public static final String ID_SEPARATOR = "____";	// separator for documentId and reason string
    public static final String DOC_NOT_FOUND = "Leave request document not found with id ";
    public static final String LEAVE_BLOCK_NOT_FOUND = "Leave Block not found for Leave request document ";
    
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		
		Date currentDate = TKUtils.getCurrentDate();
		Set<Long> workAreas = TkServiceLocator.getTkRoleService().getWorkAreasForApprover(TKContext.getPrincipalId(), currentDate);
		List<String> principalIds = new ArrayList<String>();
        for (Long workArea : workAreas) {
            List<Assignment> assignments = TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(workArea, currentDate);
            for (Assignment a : assignments) {
                principalIds.add(a.getPrincipalId());
            }
        }

        // Set calendar groups
        List<String> calGroups =  new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(principalIds)) {
            calGroups = TkServiceLocator.getLeaveApprovalService().getUniqueLeavePayGroupsForPrincipalIds(principalIds);
        }
        lraaForm.setPayCalendarGroups(calGroups);		
		
		List<String> depts = new ArrayList<String>(TKContext.getUser().getReportingApprovalDepartments().keySet());
		Collections.sort(depts);
	    lraaForm.setDepartments(depts);
		
		// build employee rows to display on the page
	    List<ActionItem> items = filterActionsWithSeletedParameters(lraaForm.getSelectedPayCalendarGroup(),
				lraaForm.getSelectedDept(), lraaForm.getSelectedWorkArea());
		List<LeaveRequestApprovalEmployeeRow> rowList = this.getEmployeeRows(items);
		lraaForm.setEmployeeRows(rowList);
		return forward;
	}
	
	public ActionForward loadApprovalTab(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward fwd = mapping.findForward("basic");
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
        Date currentDate = TKUtils.getCurrentDate();
        
        //reset state
        if(StringUtils.isEmpty(lraaForm.getSelectedDept())) {
        	resetState(form, request);
        
	        Set<Long> workAreas = TkServiceLocator.getTkRoleService().getWorkAreasForApprover(TKContext.getPrincipalId(), currentDate);
	        List<String> principalIds = new ArrayList<String>();
	        for (Long workArea : workAreas) {
	            List<Assignment> assignments = TkServiceLocator.getAssignmentService().getActiveAssignmentsForWorkArea(workArea, currentDate);
	            for (Assignment a : assignments) {
	                principalIds.add(a.getPrincipalId());
	            }
	        }
	        // Set calendar groups
	        List<String> calGroups =  new ArrayList<String>();
	        if (CollectionUtils.isNotEmpty(principalIds)) {
	            calGroups = TkServiceLocator.getLeaveApprovalService().getUniqueLeavePayGroupsForPrincipalIds(principalIds);
	        }
	        lraaForm.setPayCalendarGroups(calGroups);
	        if (StringUtils.isBlank(lraaForm.getSelectedPayCalendarGroup())
	                && CollectionUtils.isNotEmpty(calGroups)) {
	        	lraaForm.setSelectedPayCalendarGroup(calGroups.get(0));
	        }
	        // set departments
			List<String> depts = new ArrayList<String>(TKContext.getUser().getReportingApprovalDepartments().keySet());
			Collections.sort(depts);
		    lraaForm.setDepartments(depts);
		    if (StringUtils.isBlank(lraaForm.getSelectedDept())
	                && lraaForm.getDepartments().size() == 1) {
	        	lraaForm.setSelectedDept(lraaForm.getDepartments().get(0));
	        	lraaForm.getWorkAreaDescr().clear();
	        	List<WorkArea> workAreaList = TkServiceLocator.getWorkAreaService().getWorkAreas(lraaForm.getSelectedDept(), currentDate);
	            for(WorkArea wa : workAreaList){
	            	if (TKContext.getUser().getApproverWorkAreas().contains(wa.getWorkArea())
	            			|| TKContext.getUser().getReviewerWorkAreas().contains(wa.getWorkArea())) {
	            		lraaForm.getWorkAreaDescr().put(wa.getWorkArea(),wa.getDescription()+"("+wa.getWorkArea()+")");
	            	}
	            }
	        }		    
        }
        
        // build employee rows to display on the page
        List<ActionItem> items = filterActionsWithSeletedParameters(lraaForm.getSelectedPayCalendarGroup(),
				lraaForm.getSelectedDept(), lraaForm.getSelectedWorkArea());
        List<LeaveRequestApprovalEmployeeRow> rowList = this.getEmployeeRows(items);
        lraaForm.setEmployeeRows(rowList);	     
        
        return fwd;
	}
	
	public ActionForward selectNewPayCalendar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// resets the common fields for approval pages
		super.resetMainFields(form);
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		lraaForm.setEmployeeRows(new ArrayList<LeaveRequestApprovalEmployeeRow>());
		
		return loadApprovalTab(mapping, form, request, response);
	}	
	
	public ActionForward selectNewDept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		
		Date currentDate = TKUtils.getCurrentDate();
		
		lraaForm.getWorkAreaDescr().clear();
    	List<WorkArea> workAreas = TkServiceLocator.getWorkAreaService().getWorkAreas(lraaForm.getSelectedDept(), currentDate);
        for(WorkArea wa : workAreas){
        	if (TKContext.getUser().getApproverWorkAreas().contains(wa.getWorkArea())
        			|| TKContext.getUser().getReviewerWorkAreas().contains(wa.getWorkArea())) {
        		lraaForm.getWorkAreaDescr().put(wa.getWorkArea(),wa.getDescription()+"("+wa.getWorkArea()+")");
        	}
        }
    	// filter actions with selected calendarGroup, Dept and workarea
    	List<ActionItem> items = filterActionsWithSeletedParameters(lraaForm.getSelectedPayCalendarGroup(),
    				lraaForm.getSelectedDept(), lraaForm.getSelectedWorkArea());
    	List<LeaveRequestApprovalEmployeeRow> rowList = this.getEmployeeRows(items);
		lraaForm.setEmployeeRows(rowList);	
 	
		return mapping.findForward("basic");
	}
	
	public ActionForward selectNewWorkArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		    
		// filter actions with selected calendarGroup, Dept and workarea
    	List<ActionItem> items = filterActionsWithSeletedParameters(lraaForm.getSelectedPayCalendarGroup(),
    				lraaForm.getSelectedDept(), lraaForm.getSelectedWorkArea());
    	List<LeaveRequestApprovalEmployeeRow> rowList = this.getEmployeeRows(items);
		lraaForm.setEmployeeRows(rowList);
        
		return mapping.findForward("basic");
	}
	
	private List<ActionItem> filterActionsWithSeletedParameters(String calGroup, String dept, String workArea) {
		String principalId = TKUser.getCurrentTargetPerson().getPrincipalId();
		List<ActionItem> actionList = KewApiServiceLocator.getActionListService().getActionItemsForPrincipal(principalId);
		List<ActionItem> resultsList = new ArrayList<ActionItem>();

		Date currentDate = TKUtils.getCurrentDate();
		List<String> principalIds = TkServiceLocator.getLeaveApprovalService()
			.getPrincipalIdsByDeptWorkAreaRolename(null, dept, workArea, currentDate, currentDate, calGroup);
		
		if(CollectionUtils.isNotEmpty(principalIds)) {
			for(ActionItem anAction : actionList) {
				String docId = anAction.getDocumentId();
				if(anAction.getDocName().equals(LeaveRequestDocument.LEAVE_REQUEST_DOCUMENT_TYPE)) {
					LeaveRequestDocument lrd = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
					if(lrd != null) {
						LeaveBlock lb = lrd.getLeaveBlock();
						if(lb != null) {
							if(principalIds.contains(lb.getPrincipalId())) {
								resultsList.add(anAction);
							}
						}
					}
				}
			}
		}
        
        return resultsList;
	}
	
	public void resetState(ActionForm form, HttpServletRequest request) {
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
    	lraaForm.getDepartments().clear();
    	lraaForm.getWorkAreaDescr().clear();
    	lraaForm.setEmployeeRows(new ArrayList<LeaveRequestApprovalEmployeeRow>());
    	lraaForm.setSelectedDept(null);
    	lraaForm.setSearchField(null);
    	lraaForm.setSearchTerm(null);
	}
	
	public ActionForward takeActionOnEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		
		if(StringUtils.isNotEmpty(lraaForm.getApproveList())) {
			String[] approveList = lraaForm.getApproveList().split(DOC_SEPARATOR);
			for(String eachAction : approveList){
				String[] fields = eachAction.split(ID_SEPARATOR);
				String docId = fields[0];	// leave request document id
				String reasonString = fields.length > 1 ? fields[1] : ""; 	// approve reason text, could be empty
				TkServiceLocator.getLeaveRequestDocumentService().approveLeave(docId, TKContext.getPrincipalId(), reasonString);
				// leave block's status is changed to "approved" in postProcessor of LeaveRequestDocument
			}
		}
		if(StringUtils.isNotEmpty(lraaForm.getDisapproveList())) {
			String[] disapproveList = lraaForm.getDisapproveList().split(DOC_SEPARATOR);
			for(String eachAction : disapproveList){
				String[] fields = eachAction.split(ID_SEPARATOR);
				String docId = fields[0];	// leave request document id
				String reasonString = fields.length > 1 ? fields[1] : ""; 	// disapprove reason
				TkServiceLocator.getLeaveRequestDocumentService().disapproveLeave(docId, TKContext.getPrincipalId(), reasonString);
				// leave block's status is changed to "disapproved" in postProcessor of LeaveRequestDocument	
			}
		}
		if(StringUtils.isNotEmpty(lraaForm.getDeferList())) {
			String[] deferList = lraaForm.getDeferList().split(DOC_SEPARATOR);
			for(String eachAction : deferList){
				String[] fields = eachAction.split(ID_SEPARATOR);
				String docId = fields[0];	// leave request document id
				String reasonString =  fields.length > 1 ? fields[1] : ""; 	// defer reason
				TkServiceLocator.getLeaveRequestDocumentService().deferLeave(docId, TKContext.getPrincipalId(), reasonString);
				// leave block's status is changed to "deferred" in postProcessor of LeaveRequestDocument	
			}
		}
		return mapping.findForward("basic");
	}

	private List<LeaveRequestApprovalEmployeeRow> getEmployeeRows(List<ActionItem> actionList) {
		List<LeaveRequestApprovalEmployeeRow> empRowList = new ArrayList<LeaveRequestApprovalEmployeeRow>();
		Map<String, List<LeaveRequestDocument>> docMap = new HashMap<String, List<LeaveRequestDocument>>();
		for(ActionItem action : actionList) {
			if(action.getDocName().equals(LeaveRequestDocument.LEAVE_REQUEST_DOCUMENT_TYPE)) {
				String docId = action.getDocumentId();
				LeaveRequestDocument lrd = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
				if(lrd != null) {
					LeaveBlock lb = lrd.getLeaveBlock();
					if(lb != null) {
						String lbPrincipalId = lb.getPrincipalId();
						List<LeaveRequestDocument> docList = docMap.get(lbPrincipalId) == null ? new ArrayList<LeaveRequestDocument>() : docMap.get(lbPrincipalId);
						docList.add(lrd);
						docMap.put(lbPrincipalId, docList);
					}
				}
				
			}
		}
		for (Map.Entry<String, List<LeaveRequestDocument>> entry : docMap.entrySet()) {
			LeaveRequestApprovalEmployeeRow aRow = this.getAnEmployeeRow(entry.getKey(), entry.getValue());
			if(aRow != null) {
				empRowList.add(aRow);
			}
		}
		// sort list by employee name
		Collections.sort(empRowList, new Comparator<LeaveRequestApprovalEmployeeRow>() {
			@Override
			public int compare(LeaveRequestApprovalEmployeeRow row1, LeaveRequestApprovalEmployeeRow row2) {
				return ObjectUtils.compare(row1.getEmployeeName(), row2.getEmployeeName());
			}
    	});		
		
		return empRowList;
	}
	
	private LeaveRequestApprovalEmployeeRow getAnEmployeeRow(String principalId, List<LeaveRequestDocument> docList) {
		if(CollectionUtils.isEmpty(docList) || StringUtils.isEmpty(principalId)) {
			return null;
		}
		Person principal = KimApiServiceLocator.getPersonService().getPerson(principalId);
		if(principal == null) {
			return null;
		}
		LeaveRequestApprovalEmployeeRow empRow = new LeaveRequestApprovalEmployeeRow();
		empRow.setPrincipalId(principalId);
		empRow.setEmployeeName(principal.getName());
		List<LeaveRequestApprovalRow> rowList = new ArrayList<LeaveRequestApprovalRow>();
		for(LeaveRequestDocument lrd : docList) {
			if(lrd == null) {
				return null;
			}
			LeaveBlock lb = lrd.getLeaveBlock();
			if(lb == null) {
				return null;
			}
			LeaveRequestApprovalRow aRow = new LeaveRequestApprovalRow();
			aRow.setLeaveRequestDocId(lrd.getDocumentNumber());
			aRow.setLeaveCode(lb.getEarnCode());
			aRow.setRequestedDate(TKUtils.formatDate(lb.getLeaveDate()));
			aRow.setRequestedHours(lb.getLeaveAmount().toString());
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			aRow.setSubmittedTime(df.format( new Date(lrd.getDocumentHeader().getWorkflowDocument().getDateCreated().getMillis())));
			rowList.add(aRow);
		}
		// sort list by date
		if(CollectionUtils.isNotEmpty(rowList)) {
			Collections.sort(rowList, new Comparator<LeaveRequestApprovalRow>() {
				@Override
				public int compare(LeaveRequestApprovalRow row1, LeaveRequestApprovalRow row2) {
					return ObjectUtils.compare(row1.getRequestedDate(), row2.getRequestedDate());
				}
	    	});
			empRow.setLeaveRequestList(rowList);
		}
		return empRow;
	}

	@SuppressWarnings("unchecked")
	public ActionForward validateActions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		JSONArray errorMsgList = new JSONArray();
        List<String> errors = new ArrayList<String>();
        if(StringUtils.isEmpty(lraaForm.getApproveList()) 
	        	&& StringUtils.isEmpty(lraaForm.getDisapproveList())
	        	&& StringUtils.isEmpty(lraaForm.getDeferList())) {
        	errors.add("No Actions selected. Please try again.");
        } else {
			if(StringUtils.isNotEmpty(lraaForm.getApproveList())) {
				String[] approveList = lraaForm.getApproveList().split(DOC_SEPARATOR);
				for(String eachAction : approveList){
					String[] fields = eachAction.split(ID_SEPARATOR);
					String docId = fields[0];	// leave request document id
					LeaveRequestDocument lrd = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
					if(lrd == null) {
						errors.add(DOC_NOT_FOUND + docId);
						break;
					} else {
						LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lrd.getLmLeaveBlockId());
						if(lb == null) {
							errors.add(LEAVE_BLOCK_NOT_FOUND + docId);
							break;
						}
					}
				}
			}
			if(StringUtils.isNotEmpty(lraaForm.getDisapproveList())) {
				String[] disapproveList = lraaForm.getDisapproveList().split(DOC_SEPARATOR);
				for(String eachAction : disapproveList){
					String[] fields = eachAction.split(ID_SEPARATOR);
					String docId = fields[0];	// leave request document id
					String reasonString = fields.length > 1 ? fields[1] : ""; 	// disapprove reason
					if(StringUtils.isEmpty(reasonString)) {
						errors.add("Reason is required for Disapprove action");
						break;
					} else {
						LeaveRequestDocument lrd = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
						if(lrd == null) {
							errors.add(DOC_NOT_FOUND + docId);
							break;
						}else {
							LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lrd.getLmLeaveBlockId());
							if(lb == null) {
								errors.add(LEAVE_BLOCK_NOT_FOUND + docId);
								break;
							}
						}
					}
				}
			}
			if(StringUtils.isNotEmpty(lraaForm.getDeferList())) {
				String[] deferList = lraaForm.getDeferList().split(DOC_SEPARATOR);
				for(String eachAction : deferList){
					String[] fields = eachAction.split(ID_SEPARATOR);
					String docId = fields[0];	// leave request document id
					String reasonString =  fields.length > 1 ? fields[1] : ""; 	// defer reason
					if(StringUtils.isEmpty(reasonString)) {
						errors.add("Reason is required for Defer action");
						break;
					} else {
						LeaveRequestDocument lrd = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
						if(lrd == null) {
							errors.add(DOC_NOT_FOUND + docId);
							break;
						}else {
							LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lrd.getLmLeaveBlockId());
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
}
