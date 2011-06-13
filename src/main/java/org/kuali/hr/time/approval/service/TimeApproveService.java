package org.kuali.hr.time.approval.service;

import org.kuali.hr.time.approval.web.ApprovalTimeSummaryRow;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface TimeApproveService {

    /**
     * Obtains a Map of Lists of ApprovalTimeSummaryRows. The Key to the map is
     * the PayCalendar Group name.
     *
     * @param payBeginDate
     * @param payEndDate
     * @param calGroup Specify a calendar group to filter by, or 'null' if all
     * calendar groups are desired.
     *
     * @return A Map<String, List<ApprovalTimeSummaryRow>> container.
     */
    public Map<String, List<ApprovalTimeSummaryRow>> getApprovalSummaryRowsByCalendarGroup(Date payBeginDate, Date payEndDate, String calGroup);

	public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate);
	public List<String> getPayCalendarLabelsForApprovalTab(Date payBeginDate, Date payEndDate);
}