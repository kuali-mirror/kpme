package org.kuali.hr.lm.request.approval.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.kuali.hr.time.base.web.TkAction;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.action.ActionItem;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class LeaveRequestApprovalAction  extends TkAction {
	
    private static final Logger LOG = Logger.getLogger(LeaveRequestApprovalAction.class);
    public static final String DOC_SEPARATOR = "----";	// separator for documents
    public static final String ID_SEPARATOR = "____";	// separator for documentId and reason string
    
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.execute(mapping, form, request, response);
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		
		// build employee rows to display on the page
		List<LeaveRequestApprovalEmployeeRow> rowList = this.getEmployeeRows();
		lraaForm.setEmployeeRows(rowList);		
		return forward;
	}
	
//	public ActionForward loadApprovalTab(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ActionForward fwd = mapping.findForward("basic");
//		TKUser user = TKContext.getUser();
//		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
//        Date currentDate = null;
//        
//        //reset state
//        if(StringUtils.isBlank(lraaForm.getSelectedDept())){
//        	resetState(form, request);
//        }
//      
//        setupDocumentOnFormContext(request,taaf,payCalendarEntries, page);
//        return fwd;
//	}
	
	public ActionForward takeActionOnEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		
		String principalId = lraaForm.getPrincipalId();	// may not be needed
		if(StringUtils.isNotEmpty(lraaForm.getApproveList())) {
			String[] approveList = lraaForm.getApproveList().split(DOC_SEPARATOR);
			for(String eachAction : approveList){
				String[] fields = eachAction.split(ID_SEPARATOR);
				String docId = fields[0];	// leave request document id
				String reasonString = fields.length > 1 ? fields[1] : ""; 	// approve reason text, could be empty
				TkServiceLocator.getLeaveRequestDocumentService().approveLeave(docId, principalId, reasonString);
				// leave block's status is changed to "approved" in postProcessor of LeaveRequestDocument
			}
		}
		if(StringUtils.isNotEmpty(lraaForm.getDisapproveList())) {
			String[] disapproveList = lraaForm.getDisapproveList().split(DOC_SEPARATOR);
			for(String eachAction : disapproveList){
				String[] fields = eachAction.split(ID_SEPARATOR);
				String docId = fields[0];	// leave request document id
				String reasonString = fields.length > 1 ? fields[1] : ""; 	// disapprove reason
				TkServiceLocator.getLeaveRequestDocumentService().disapproveLeave(docId, principalId, reasonString);
				// leave block's status is changed to "disapproved" in postProcessor of LeaveRequestDocument	
			}
		}
		if(StringUtils.isNotEmpty(lraaForm.getDisapproveList())) {
			String[] deferList = lraaForm.getDeferList().split(DOC_SEPARATOR);
			for(String eachAction : deferList){
				String[] fields = eachAction.split(ID_SEPARATOR);
				String docId = fields[0];	// leave request document id
				String reasonString =  fields.length > 1 ? fields[1] : ""; 	// defer reason
				TkServiceLocator.getLeaveRequestDocumentService().disapproveLeave(docId, principalId, reasonString);
				// leave block's status is changed to "planning" in postProcessor of LeaveRequestDocument	
			}
		}
		
		return mapping.findForward("basic");
	}
	
	public ActionForward approveAllForEmployee(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LeaveRequestApprovalActionForm lraaForm = (LeaveRequestApprovalActionForm) form;
		String principalId = lraaForm.getPrincipalId();
//		JSONArray errorMsgList = new JSONArray();
//		List<String> errors = new ArrayList<String>();
		
		if(StringUtils.isNotEmpty(lraaForm.getApproveList())) {
			String[] approveList = lraaForm.getApproveList().split(DOC_SEPARATOR);
			for(String eachAction : approveList){
				String[] fields = eachAction.split(ID_SEPARATOR);
				String docId = fields[0];	// leave request document id
				String reasonString = fields.length > 1 ? fields[1] : ""; 	// approve reason text, can be empty
				TkServiceLocator.getLeaveRequestDocumentService().approveLeave(docId, principalId, reasonString);
			}
		}
		
//		for(LeaveRequestApprovalEmployeeRow employeeRow :lraaForm.getEmployeeRows()) {
//			if(employeeRow.getPrincipalId().equals(principalId)) {
//				for(LeaveRequestApprovalRow requestRow : employeeRow.getLeaveRequestList()) {
//					String docId = requestRow.getLeaveRequestDocId();
//					LeaveRequestDocument lrd = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
//					try {
//						// who should be receiving the email notification???? argument 3 is the the adhoc receipient list
//			            KRADServiceLocatorWeb.getDocumentService().approveDocument(lrd, "", null);
//			         // change status of leave block to "approved"
//						LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lrd.getLmLeaveBlockId());
//						lb.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
//			        } catch (WorkflowException e) {
//			            LOG.error(e.getStackTrace());
//			            errors.add("Error occurred when approving leave request. Please try again.");
//			            errorMsgList.addAll(errors);
//			            lraaForm.setOutputString(JSONValue.toJSONString(errorMsgList));
//			            return mapping.findForward("basic");
//			        }
//				}
//			}
//		}
		
		return mapping.findForward("basic");
	}
	
	private List<LeaveRequestApprovalEmployeeRow> getEmployeeRows() {
		
		List<LeaveRequestApprovalEmployeeRow> empRowList = new ArrayList<LeaveRequestApprovalEmployeeRow>();
		// currently just use the principalId of current employee
		String principalId = TKUser.getCurrentTargetPerson().getPrincipalId();

//		Map<String, List<String>> docMap = new HashMap<String, List<String>>();
		Map<String, List<LeaveRequestDocument>> docMap = new HashMap<String, List<LeaveRequestDocument>>();
		List<ActionItem> actionList = KewApiServiceLocator.getActionListService().getActionItemsForPrincipal(principalId);
		for(ActionItem action : actionList) {
			if(action.getDocName().equals("LeaveRequestDocument")) {
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
			aRow.setLeaveCode(lb.getEarnCode());
			aRow.setLeaveBlockId(lb.getLmLeaveBlockId());
			aRow.setRequestedDate(TKUtils.formatDate(lb.getLeaveDate()));
			aRow.setRequestedHours(lb.getLeaveAmount().toString());
			aRow.setSubmittedTime("test");
//			aRow.setSubmittedTime(lrd.getDocumentHeader().getWorkflowDocument().getDateCreated().toString());
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
						errors.add("Leave request document not founded with id " + docId);
					}else {
						LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lrd.getLmLeaveBlockId());
						if(lb == null) {
							errors.add("Leave Block not founded for Leave request document " + docId);
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
						errors.add("Reason is required for disapprove action");
						break;
					} else {
						LeaveRequestDocument lrd = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
						if(lrd == null) {
							errors.add("Leave request document not found with id " + docId);
							break;
						}else {
							LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lrd.getLmLeaveBlockId());
							if(lb == null) {
								errors.add("Leave Block not found for Leave request document " + docId);
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
						errors.add("Reason is required for defer action");
						break;
					} else {
						LeaveRequestDocument lrd = TkServiceLocator.getLeaveRequestDocumentService().getLeaveRequestDocument(docId);
						if(lrd == null) {
							errors.add("Leave request document not found with id " + docId);
							break;
						}else {
							LeaveBlock lb = TkServiceLocator.getLeaveBlockService().getLeaveBlock(lrd.getLmLeaveBlockId());
							if(lb == null) {
								errors.add("Leave Block not found for Leave request document " + docId);
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
