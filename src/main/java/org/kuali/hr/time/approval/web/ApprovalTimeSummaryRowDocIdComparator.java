package org.kuali.hr.time.approval.web;

import java.util.Comparator;

public class ApprovalTimeSummaryRowDocIdComparator implements Comparator<ApprovalTimeSummaryRow> {

    int multiplier = 1;

    public ApprovalTimeSummaryRowDocIdComparator() {
    }

    public ApprovalTimeSummaryRowDocIdComparator(boolean ascending) {
        multiplier = (ascending) ? 1 : -1;
    }

    @Override
    public int compare(ApprovalTimeSummaryRow approvalTimeSummaryRow, ApprovalTimeSummaryRow approvalTimeSummaryRow1) {
        return multiplier * approvalTimeSummaryRow.getDocumentId().compareTo(approvalTimeSummaryRow1.getDocumentId());
    }
}
