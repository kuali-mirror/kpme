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
package org.kuali.kpme.tklm.time.approval.web;

import java.util.ArrayList;
import java.util.List;

import org.kuali.kpme.core.web.ApprovalForm;
import org.kuali.kpme.tklm.time.approval.summaryrow.ApprovalTimeSummaryRow;

public class TimeApprovalActionForm extends ApprovalForm {

	private static final long serialVersionUID = 339670908525224389L;
	
	private List<String> payCalendarLabels = new ArrayList<String>();
	private List<ApprovalTimeSummaryRow> approvalRows;
	
	public List<String> getPayCalendarLabels() {
		return payCalendarLabels;
	}
	public void setPayCalendarLabels(List<String> payCalendarLabels) {
		this.payCalendarLabels = payCalendarLabels;
	}
	public List<ApprovalTimeSummaryRow> getApprovalRows() {
		return approvalRows;
	}
	public void setApprovalRows(List<ApprovalTimeSummaryRow> approvalRows) {
		this.approvalRows = approvalRows;
	}
	
	public boolean isAnyApprovalRowApprovable() {
		boolean isAnyApprovalRowApprovable = false;
		
		if (approvalRows != null) {
			for (ApprovalTimeSummaryRow approvalRow : approvalRows) {
				if (approvalRow.isApprovable()) {
					isAnyApprovalRowApprovable = true;
					break;
				}
			}
		}
		
		return isAnyApprovalRowApprovable;
	}
	

}
