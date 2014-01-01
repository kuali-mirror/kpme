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
package org.kuali.kpme.tklm.leave.request.service;


import java.util.List;

import org.kuali.kpme.tklm.leave.workflow.LeaveRequestDocument;

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
    void suCancelLeave(String documentId, String principalId);
    void recallAndCancelLeave(String documentId, String principalId, String reason); 
   /**
    * Get a list of principal ids of approvers that have taken "approve" action on the given document
    * @param documentId
    * @return
    */
    public List<String> getApproverIdList(String documentId);
}
