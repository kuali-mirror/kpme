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
package org.kuali.kpme.tklm.leave.request.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.kpme.core.assignment.Assignment;
import org.kuali.kpme.core.calendar.entry.CalendarEntry;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.request.LeaveRequestActionValue;
import org.kuali.kpme.tklm.leave.request.dao.LeaveRequestDocumentDao;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.kpme.tklm.leave.workflow.LeaveRequestDocument;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.action.ActionTaken;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.action.DocumentActionParameters;
import org.kuali.rice.kew.api.action.ValidActions;
import org.kuali.rice.kew.api.action.WorkflowDocumentActionsService;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.exception.UnknownDocumentIdException;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;

public class LeaveRequestDocumentServiceImpl implements LeaveRequestDocumentService {
    private static final Logger LOG = Logger.getLogger(LeaveRequestDocumentServiceImpl.class);
    private LeaveRequestDocumentDao leaveRequestDocumentDao;

    @Override
    public LeaveRequestDocument getLeaveRequestDocument(String documentId) {
        LeaveRequestDocument document = null;
        try {
            document = (LeaveRequestDocument)KRADServiceLocatorWeb.getDocumentService().getByDocumentHeaderId(documentId);
        } catch (WorkflowException e) {
            LOG.error("Document with documentId: " + documentId + " does not exist");
        } catch (UnknownDocumentIdException e) {
        	LOG.error("Document with documentId: " + documentId + " does not exist");
            return document;
        }
        return document;
    }
    
    @Override
    public List<LeaveRequestDocument> getLeaveRequestDocumentsByLeaveBlockId(String leaveBlockId) {
    	List<LeaveRequestDocument> docList = getLeaveRequestDocumentDao().getLeaveRequestDocumentsByLeaveBlockId(leaveBlockId);
    	List<LeaveRequestDocument> results = new ArrayList<LeaveRequestDocument>();
    	for(LeaveRequestDocument aDoc : docList) {
    		LeaveRequestDocument lrd = this.getLeaveRequestDocument(aDoc.getDocumentNumber());    		
    		if(lrd != null){
    			results.add(lrd);
    		}
    	}
    	return results;
    }

    @Override
    public LeaveRequestDocument saveLeaveRequestDocument(LeaveRequestDocument document) {
        LeaveRequestDocument lrd = null;
        try {
            lrd = (LeaveRequestDocument)KRADServiceLocatorWeb.getDocumentService().saveDocument(document);
        } catch (WorkflowException e) {
            LOG.error(e.getStackTrace());
            return null;
        }
        return lrd;
    }

    @Override
    public LeaveRequestDocument createLeaveRequestDocument(String leaveBlockId) {
        LeaveRequestDocument lrd = initiateLeaveRequestDocument(HrContext.getTargetPrincipalId(), leaveBlockId);

        return saveLeaveRequestDocument(lrd);
    }

    @Override
    public void requestLeave(String documentId) {
        LeaveRequestDocument doc = getLeaveRequestDocument(documentId);
        doc.getDocumentHeader().getWorkflowDocument().route("");

    }

    @Override
    public void approveLeave(String documentId, String principalId, String reason) {
        //verify principal has an action item to approve...
        //KewApiServiceLocator.
        LeaveRequestDocument doc = getLeaveRequestDocument(documentId);
        
        //do we need to switch ids?
        doc.getDocumentHeader().getWorkflowDocument().switchPrincipal(principalId);
        ValidActions validActions = doc.getDocumentHeader().getWorkflowDocument().getValidActions();
        if (validActions.getValidActions().contains(ActionType.APPROVE)) {
        	if(StringUtils.isNotEmpty(reason)) {
    	        doc.setDescription(reason);
    	        saveLeaveRequestDocument(doc);
            }
            doc.getDocumentHeader().getWorkflowDocument().approve("");
        }
    }

    @Override
    public void disapproveLeave(String documentId, String principalId, String reason) {
        LeaveRequestDocument doc = getLeaveRequestDocument(documentId);        
        doc.getDocumentHeader().getWorkflowDocument().switchPrincipal(principalId);
        ValidActions validActions = doc.getDocumentHeader().getWorkflowDocument().getValidActions();        
        if (validActions.getValidActions().contains(ActionType.DISAPPROVE)) {
        	if(StringUtils.isNotEmpty(reason)) {
    	        doc.setDescription(reason);
    	        saveLeaveRequestDocument(doc);
            }
            doc.getDocumentHeader().getWorkflowDocument().disapprove("");
        }
    }

    @Override
    public void deferLeave(String documentId, String principalId, String reason) {
        LeaveRequestDocument doc = getLeaveRequestDocument(documentId);
        doc.getDocumentHeader().getWorkflowDocument().switchPrincipal(principalId);
        ValidActions validActions = doc.getDocumentHeader().getWorkflowDocument().getValidActions();       
        if (validActions.getValidActions().contains(ActionType.CANCEL)) {
        	 if(StringUtils.isNotEmpty(reason)) {
     	        doc.setDescription(reason);
     	        saveLeaveRequestDocument(doc);
             }
        	doc.getDocumentHeader().getWorkflowDocument().cancel("");
        }
    }
    
    @Override
    public void recallAndCancelLeave(String documentId, String principalId, String reason) {
        LeaveRequestDocument doc = getLeaveRequestDocument(documentId);
        if (principalId.equals(doc.getDocumentHeader().getWorkflowDocument().getInitiatorPrincipalId())) {
            doc.getDocumentHeader().getWorkflowDocument().switchPrincipal(principalId);
            if(StringUtils.isNotEmpty(reason)) {
     	        doc.setDescription(reason);
     	        saveLeaveRequestDocument(doc);
             }
            doc.getDocumentHeader().getWorkflowDocument().recall("", true);
        }
    }

    @Override
    public void suBlanketApproveLeave(String documentId, String principalId) {
        WorkflowDocumentActionsService docActionService = KewApiServiceLocator.getWorkflowDocumentActionsService();
        DocumentActionParameters parameters = DocumentActionParameters.create(documentId, principalId);
        docActionService.superUserBlanketApprove(parameters, true);
    }
   
    public LeaveRequestDocumentDao getLeaveRequestDocumentDao() {
        return leaveRequestDocumentDao;
    }

    public void setLeaveRequestDocumentDao(LeaveRequestDocumentDao leaveRequestDocumentDao) {
        this.leaveRequestDocumentDao = leaveRequestDocumentDao;
    }

    private LeaveRequestDocument initiateLeaveRequestDocument(String principalId, String leaveBlockId) {
        //LeaveRequestDocument leaveRequestDocument = new LeaveRequestDocument(leaveBlockId);
        LeaveRequestDocument leaveRequestDocument = null;
        try {
            leaveRequestDocument = (LeaveRequestDocument) KRADServiceLocatorWeb.getDocumentService().getNewDocument(LeaveRequestDocument.LEAVE_REQUEST_DOCUMENT_TYPE);
        } catch (WorkflowException e) {
            LOG.error(e.getMessage());
            return null;
        }
        
        EntityNamePrincipalName person = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(principalId);
        LeaveBlock leaveBlock = LmServiceLocator.getLeaveBlockService().getLeaveBlock(leaveBlockId);

        String principalName = person != null && person.getDefaultName() != null ? person.getDefaultName().getCompositeName() : StringUtils.EMPTY;
        String leaveRequestDateString = leaveBlock != null ? TKUtils.formatDate(leaveBlock.getLeaveLocalDate()) : StringUtils.EMPTY;
        String leaveRequestDocumentTitle = principalName + " (" + principalId + ") - " + leaveRequestDateString;
        
        leaveRequestDocument.setLmLeaveBlockId(leaveBlockId);
        leaveRequestDocument.getDocumentHeader().setDocumentDescription(leaveRequestDocumentTitle);
        leaveRequestDocument.setDescription(leaveBlock != null ? leaveBlock.getDescription() : StringUtils.EMPTY);
        leaveRequestDocument.setActionCode(LeaveRequestActionValue.NO_ACTION.getCode());
        initiateSearchableAttributes(leaveRequestDocument);

        return leaveRequestDocument;
    }

    private void initiateSearchableAttributes(LeaveRequestDocument leaveRequestDocument) {
        DocumentHeader dh = leaveRequestDocument.getDocumentHeader();
        WorkflowDocument workflowDocument = dh.getWorkflowDocument();
        if (!DocumentStatus.FINAL.equals(workflowDocument.getStatus())) {
            try {
                workflowDocument.setApplicationContent(createSearchableAttributeXml(leaveRequestDocument, leaveRequestDocument.getLeaveBlock()));
                workflowDocument.saveDocument("");
                if (!"I".equals(workflowDocument.getStatus().getCode())) {
                    if (GlobalVariables.getUserSession() != null && workflowDocument.getInitiatorPrincipalId().equals(GlobalVariables.getUserSession().getPrincipalId())) {
                        workflowDocument.saveDocument("");
                    } else{
                        workflowDocument.saveDocumentData();
                    }
                } else{
                    workflowDocument.saveDocument("");
                }


            } catch (Exception e) {
                LOG.warn("Exception during searchable attribute update.");
                throw new RuntimeException(e);
            }
        }
    }

    private String createSearchableAttributeXml(LeaveRequestDocument document, LeaveBlock leaveBlock) {
        List<Long> workAreas = new ArrayList<Long>();
        Map<String,List<Long>> deptToListOfWorkAreas = new HashMap<String,List<Long>>();
        List<String> salGroups = new ArrayList<String>();
        CalendarEntry ce = getCalendarEntry(leaveBlock);
        if (ce != null) {
            List<Assignment> assignments = HrServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(leaveBlock.getPrincipalId(), ce);

            for(Assignment assign: assignments){
                if(!workAreas.contains(assign.getWorkArea())){
                    workAreas.add(assign.getWorkArea());
                }
                Job job = HrServiceLocator.getJobService().getJob(assign.getPrincipalId(), assign.getJobNumber(), leaveBlock.getLeaveLocalDate());

                if(!salGroups.contains(job.getHrSalGroup())){
                    salGroups.add(job.getHrSalGroup());
                }
            }
        }
        for(Long workArea : workAreas){
            WorkArea workAreaObj = HrServiceLocator.getWorkAreaService().getWorkArea(workArea, leaveBlock.getLeaveLocalDate());
            if(deptToListOfWorkAreas.containsKey(workAreaObj.getDept())){
                List<Long> deptWorkAreas = deptToListOfWorkAreas.get(workAreaObj.getDept());
                deptWorkAreas.add(workArea);
            } else {
                List<Long> deptWorkAreas = new ArrayList<Long>();
                deptWorkAreas.add(workArea);
                deptToListOfWorkAreas.put(workAreaObj.getDept(), deptWorkAreas);
            }
        }
        StringBuilder sb = new StringBuilder();
        String className = document.getClass().getSimpleName();
        sb.append("<documentContext><applicationContent><").append(className).append(">");
        sb.append("<DEPARTMENTS>");
        for(Map.Entry<String, List<Long>> dept : deptToListOfWorkAreas.entrySet()){
            sb.append("<DEPARTMENT value=\""+dept.getKey()+"\">");
            for(Long workArea : dept.getValue()){
                sb.append("<WORKAREA value=\""+workArea+"\"/>");
            }
            sb.append("</DEPARTMENT>");
        }
        sb.append("</DEPARTMENTS>");
        for(String salGroup : salGroups){
            sb.append("<SALGROUP value=\""+salGroup+"\"/>");
        }

        sb.append("<PAYENDDATE value=\""+leaveBlock.getLeaveDate()+"\"/>");
        sb.append("</").append(className).append("></applicationContent></documentContext>");

        return sb.toString();
    }

    private CalendarEntry getCalendarEntry(LeaveBlock leaveBlock) {
        return HrServiceLocator.getCalendarEntryService().getCalendarEntry(leaveBlock.getCalendarId());
    }
    
    public List<String> getApproverIdList(String documentId) {
    	List<String> idList = new ArrayList<String>();
    	List<ActionTaken> actions = KewApiServiceLocator.getWorkflowDocumentService().getActionsTaken(documentId);
    	for(ActionTaken anAction : actions) {
    		if(anAction.getActionTaken().getCode().equals(ActionType.APPROVE.getCode())
    				&& StringUtils.isNotEmpty(anAction.getPrincipalId()) ) {
    			idList.add(anAction.getPrincipalId());
    		}
    	}
        return idList;
    }
    
}
