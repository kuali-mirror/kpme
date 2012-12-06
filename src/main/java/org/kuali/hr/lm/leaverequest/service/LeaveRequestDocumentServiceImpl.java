package org.kuali.hr.lm.leaverequest.service;


import org.apache.log4j.Logger;
import org.kuali.hr.job.Job;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaverequest.dao.LeaveRequestDocumentDao;
import org.kuali.hr.lm.workflow.LeaveRequestDocument;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
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
        return getLeaveRequestDocumentDao().getLeaveRequestDocument(documentId);
    }

    @Override
    public LeaveRequestDocument getLeaveRequestDocumentByLeaveBlockId(String leaveBlockId) {
        return getLeaveRequestDocumentDao().getLeaveRequestDocumentByLeaveBlockId(leaveBlockId);
    }

    @Override
    public LeaveRequestDocument createLeaveRequestDocument(String leaveBlockId) {
        LeaveRequestDocument lrd = initiateLeaveRequestDocument(TKContext.getTargetPrincipalId(), leaveBlockId);
        //anything we need to do here?
        // ...

        try {
            KRADServiceLocatorWeb.getDocumentService().saveDocument(lrd);
        } catch (WorkflowException e) {
            LOG.error(e.getStackTrace());
            return null;
        }
        return lrd;
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
        leaveRequestDocument.setActionCode("");
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
