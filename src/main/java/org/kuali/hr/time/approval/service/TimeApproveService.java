package org.kuali.hr.time.approval.service;

import org.kuali.hr.time.approval.web.ApprovalTimeSummaryRow;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface TimeApproveService {

    /**
     * Obtains a Map of Lists of ApprovalTimeSummaryRows. The Key to the map is
     * the PayCalendar Group name.
     *
     * @param payBeginDate
     * @param payEndDate
     * @param calGroup Specify a calendar group to filter by, or 'null' if all
     * calendar groups are desired.
     * @param workArea Specify a work area to filter by, or 'null' if all
     * work areas are desired.
     *
     * @return A Map<String, List<ApprovalTimeSummaryRow>> container.
     */
    public Map<String, List<ApprovalTimeSummaryRow>> getApprovalSummaryRowsMap(Date payBeginDate, Date payEndDate, Long workArea);

    public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate, String calGroup, Long workArea);

    public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate, String calGroup);

    public List<ApprovalTimeSummaryRow> getApprovalSummaryRows(Date payBeginDate, Date payEndDate);

	public List<String> getPayCalendarLabelsForApprovalTab(Date payBeginDate, Date payEndDate);

    /**
     * Method to obtain all of the active Pay Calendar Group names for the current
     * user / approver.
     *
     * @param payBeginDate
     * @param payEndDate
     * @return
     */
    public Set<String> getApproverPayCalendarGroups(Date payBeginDate, Date payEndDate);
    
    /**
     * Used to determine if there are notes on a document
     * @param documentNumber
     * @return list of note objects
     */
    @SuppressWarnings("rawtypes")
	public List getNotesForDocument(String documentNumber);
}