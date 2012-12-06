package org.kuali.hr.lm.leaverequest.dao;

import org.kuali.hr.lm.workflow.LeaveRequestDocument;


public interface LeaveRequestDocumentDao {
    LeaveRequestDocument getLeaveRequestDocument(String documentNumber);
    LeaveRequestDocument getLeaveRequestDocumentByLeaveBlockId(String leaveBlockId);
}
