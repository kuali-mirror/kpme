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
package org.kuali.kpme.tklm.leave.workflow;

import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.krad.document.TransactionalDocumentBase;

public class LeaveRequestDocument extends TransactionalDocumentBase {

    public static final String LEAVE_REQUEST_DOCUMENT_TYPE = "LeaveRequestDocument";
    private String lmLeaveBlockId;
    private String actionCode;
    private String description;

    public LeaveRequestDocument() { }

    public LeaveRequestDocument(String leaveBlockId) {
		this.lmLeaveBlockId = leaveBlockId;
	}

    @Override
    public boolean getAllowsCopy() {
        return false;
    }

    public String getLmLeaveBlockId() {
        return lmLeaveBlockId;
    }

    public void setLmLeaveBlockId(String lmLeaveBlockId) {
        this.lmLeaveBlockId = lmLeaveBlockId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LeaveBlock getLeaveBlock() {
        return getLmLeaveBlockId() == null ? null : LmServiceLocator.getLeaveBlockService().getLeaveBlock(getLmLeaveBlockId());
    }
}
