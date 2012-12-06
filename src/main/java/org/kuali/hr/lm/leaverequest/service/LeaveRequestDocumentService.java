package org.kuali.hr.lm.leaverequest.service;


import org.kuali.hr.lm.workflow.LeaveRequestDocument;

public interface LeaveRequestDocumentService {
    LeaveRequestDocument getLeaveRequestDocument(String documentId);

    LeaveRequestDocument getLeaveRequestDocumentByLeaveBlockId(String leaveBlockId);

    LeaveRequestDocument createLeaveRequestDocument(String leaveBlockId);

}
