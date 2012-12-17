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
package org.kuali.hr.lm.leave.web;

import java.util.List;
import java.util.Map;

import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveblock.LeaveBlockHistory;
import org.kuali.hr.lm.workflow.LeaveRequestDocument;
import org.kuali.hr.time.base.web.TkForm;

public class LeaveRequestForm extends TkForm {

	private List<LeaveBlock> plannedLeaves;
	private List<LeaveBlock> pendingLeaves;
	private List<LeaveBlock> approvedLeaves;
	private List<LeaveBlock> disapprovedLeaves;
    private Map<String, LeaveRequestDocument> documents;

	public List<LeaveBlock> getPlannedLeaves() {
		return plannedLeaves;
	}

	public void setPlannedLeaves(List<LeaveBlock> plannedLeaves) {
		this.plannedLeaves = plannedLeaves;
	}

	public List<LeaveBlock> getPendingLeaves() {
		return pendingLeaves;
	}

	public void setPendingLeaves(List<LeaveBlock> pendingLeaves) {
		this.pendingLeaves = pendingLeaves;
	}

	public List<LeaveBlock> getApprovedLeaves() {
		return approvedLeaves;
	}

	public void setApprovedLeaves(List<LeaveBlock> approvedLeaves) {
		this.approvedLeaves = approvedLeaves;
	}

	public List<LeaveBlock> getDisapprovedLeaves() {
		return disapprovedLeaves;
	}

	public void setDisapprovedLeaves(List<LeaveBlock> disapprovedLeaves) {
		this.disapprovedLeaves = disapprovedLeaves;
	}

    public Map<String, LeaveRequestDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(Map<String, LeaveRequestDocument> documents) {
        this.documents = documents;
    }

}
