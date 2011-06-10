package org.kuali.hr.time.approval.service;

import org.kuali.hr.time.approval.web.ApprovalTimeSummaryRow;

import java.util.Date;
import java.util.List;


public interface TimeApproveService {
	public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate);
	public List<String> getPayCalendarLabelsForApprovalTab(Date payBeginDate, Date payEndDate);
}