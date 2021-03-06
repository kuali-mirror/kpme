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
package org.kuali.kpme.tklm.leave.approval.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.kuali.kpme.tklm.api.leave.approval.ApprovalLeaveSummaryRowContract;
import org.kuali.kpme.tklm.common.CalendarApprovalForm;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.rice.core.api.config.property.ConfigContext;

public class LeaveApprovalActionForm extends CalendarApprovalForm {
	
	private static final long serialVersionUID = 1L;
	
	private List<ApprovalLeaveSummaryRowContract> leaveApprovalRows;
	private List<LocalDateTime> leaveCalendarDates = new ArrayList<LocalDateTime>();
	private Boolean displayYTDFMLA;
	
	public boolean isDisplayYTDFMLA() {
		if(displayYTDFMLA == null) {
			String displayColumn = ConfigContext.getCurrentContextConfig().getProperty(LMConstants.DISPLAY_YTD_FMLA_LEAVESUMMARY);
			if (StringUtils.equals(displayColumn, "Yes")) {
				displayYTDFMLA = Boolean.TRUE;
			} else {
				displayYTDFMLA = Boolean.FALSE;
			}
		}
		return displayYTDFMLA;
	}

	public void setDisplayYTDFMLA(boolean displayYTDFMLA) {
		this.displayYTDFMLA = displayYTDFMLA;
	}
	
	public List<ApprovalLeaveSummaryRowContract> getLeaveApprovalRows() {
		return leaveApprovalRows;
	}
	public void setLeaveApprovalRows(List<ApprovalLeaveSummaryRowContract> leaveApprovalRows) {
		this.leaveApprovalRows = leaveApprovalRows;
	}
	public List<LocalDateTime> getLeaveCalendarDates() {
		return leaveCalendarDates;
	}
	public void setLeaveCalendarDates(List<LocalDateTime> leaveCalendarDates) {
		this.leaveCalendarDates = leaveCalendarDates;
	}
	
	public boolean isAnyApprovalRowApprovable() {
		boolean isAnyApprovalRowApprovable = false;
		
		if (leaveApprovalRows != null) {
			for (ApprovalLeaveSummaryRowContract approvalRow : leaveApprovalRows) {
				if (approvalRow.isApprovable()) {
					isAnyApprovalRowApprovable = true;
					break;
				}
			}
		}
		
		return isAnyApprovalRowApprovable;
	}
	
	
}
