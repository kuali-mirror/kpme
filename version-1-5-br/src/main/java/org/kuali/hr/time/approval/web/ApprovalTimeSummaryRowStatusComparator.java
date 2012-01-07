package org.kuali.hr.time.approval.web;

import java.util.Comparator;

public class ApprovalTimeSummaryRowStatusComparator implements Comparator<ApprovalTimeSummaryRow> {

    int multiplier = 1;

    public ApprovalTimeSummaryRowStatusComparator() {
    }

    public ApprovalTimeSummaryRowStatusComparator(boolean ascending) {
        multiplier = (ascending) ? 1 : -1;
    }

    @Override
    public int compare(ApprovalTimeSummaryRow approvalTimeSummaryRow, ApprovalTimeSummaryRow approvalTimeSummaryRow1) {
        return multiplier * approvalTimeSummaryRow.getApprovalStatus().compareTo(approvalTimeSummaryRow1.getApprovalStatus());
    }

}