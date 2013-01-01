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
package org.kuali.hr.lm.leaverequest.service;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaverequest.LeaveRequestActionValue;
import org.kuali.hr.lm.leaverequest.dao.LeaveRequestDocumentDao;
import org.kuali.hr.lm.workflow.LeaveRequestDocument;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kew.api.KewApiServiceLocator;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.action.DocumentActionParameters;
import org.kuali.rice.kew.api.action.ValidActions;
import org.kuali.rice.kew.api.action.WorkflowDocumentActionsService;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.krad.bo.DocumentHeader;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        }
        return document;
    }

    @Override
    public List<LeaveRequestDocument> getLeaveRequestDocumentsByLeaveBlockId(String leaveBlockId) {
    	List<LeaveRequestDocument> docList = getLeaveRequestDocumentDao().getLeaveRequestDocumentsByLeaveBlockId(leaveBlockId);
    	List<LeaveRequestDocument> results = new ArrayList<LeaveRequestDocument>();
    	for(LeaveRequestDocument aDoc : docList) {
    		results.add(this.getLeaveRequestDocument(aDoc.getDocumentNumber()));
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
        LeaveRequestDocument lrd = initiateLeaveRequestDocument(TKContext.getTargetPrincipalId(), leaveBlockId);

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
        leaveRequestDocument.setLmLeaveBlockId(leaveBlockId);
        leaveRequestDocument.getDocumentHeader().setDocumentDescription("Leave Request for LeaveBlock " + leaveBlockId);
        leaveRequestDocument.setDescription("");
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
        CalendarEntries ce = getCalendarEntry(leaveBlock);
        if (ce != null) {
            List<Assignment> assignments = TkServiceLocator.getAssignmentService().getAssignmentsByCalEntryForLeaveCalendar(leaveBlock.getPrincipalId(), ce);

            for(Assignment assign: assignments){
                if(!workAreas.contains(assign.getWorkArea())){
                    workAreas.add(assign.getWorkArea());
                }
                Job job = TkServiceLocator.getJobService().getJob(assign.getPrincipalId(), assign.getJobNumber(), leaveBlock.getLeaveDate());

                if(!salGroups.contains(job.getHrSalGroup())){
                    salGroups.add(job.getHrSalGroup());
                }
            }
        }
        for(Long workArea : workAreas){
            WorkArea workAreaObj = TkServiceLocator.getWorkAreaService().getWorkArea(workArea, TKUtils.getTimelessDate(leaveBlock.getLeaveDate()));
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
        for(String dept : deptToListOfWorkAreas.keySet()){
            sb.append("<DEPARTMENT value=\""+dept+"\">");
            List<Long> deptWorkAreas = deptToListOfWorkAreas.get(dept);
            for(Long workArea : deptWorkAreas){
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

    private CalendarEntries getCalendarEntry(LeaveBlock leaveBlock) {
        return TkServiceLocator.getCalendarEntriesService().getCalendarEntries(leaveBlock.getCalendarId());
    }
}
