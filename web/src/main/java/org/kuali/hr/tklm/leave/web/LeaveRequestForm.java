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
package org.kuali.hr.tklm.leave.web;

import java.util.List;
import java.util.Map;

import org.kuali.hr.core.HrForm;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.block.LeaveBlockHistory;
import org.kuali.hr.tklm.leave.workflow.LeaveRequestDocument;

public class LeaveRequestForm extends HrForm {

    private int year;
    private String navString;
	private List<LeaveBlock> plannedLeaves;
	private List<LeaveBlock> pendingLeaves;
	private List<LeaveBlock> approvedLeaves;
	private List<LeaveBlockHistory> disapprovedLeaves;
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

	public List<LeaveBlockHistory> getDisapprovedLeaves() {
		return disapprovedLeaves;
	}

	public void setDisapprovedLeaves(List<LeaveBlockHistory> disapprovedLeaves) {
		this.disapprovedLeaves = disapprovedLeaves;
	}

    public Map<String, LeaveRequestDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(Map<String, LeaveRequestDocument> documents) {
        this.documents = documents;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNavString() {
        return navString;
    }

    public void setNavString(String navString) {
        this.navString = navString;
    }


}
