/**
 * Copyright 2004-2012 The Kuali Foundation
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


import org.kuali.hr.lm.workflow.LeaveRequestDocument;
import java.util.List;

public interface LeaveRequestDocumentService {
    LeaveRequestDocument getLeaveRequestDocument(String documentId);

    List<LeaveRequestDocument> getLeaveRequestDocumentsByLeaveBlockId(String leaveBlockId);
    LeaveRequestDocument saveLeaveRequestDocument(LeaveRequestDocument leaveRequestDocument);
    LeaveRequestDocument createLeaveRequestDocument(String leaveBlockId);

    void requestLeave(String documentId);
    void approveLeave(String documentId, String principalId, String reason);
    void disapproveLeave(String documentId, String principalId, String reason);
    void deferLeave(String documentId, String principalId, String reason);
    void suBlanketApproveLeave(String documentId, String principalId);    
    void recallAndCancelLeave(String documentId, String principalId, String reason);    
}
