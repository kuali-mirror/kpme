package org.kuali.hr.time.approval.web;

import java.util.Comparator;

public class ApprovalTimeSummaryRowPrincipalComparator implements Comparator<ApprovalTimeSummaryRow> {

    int multiplier = 1;

    public ApprovalTimeSummaryRowPrincipalComparator() {
    }

    public ApprovalTimeSummaryRowPrincipalComparator(boolean ascending) {
        multiplier = (ascending) ? 1 : -1;
    }

    @Override
    public int compare(ApprovalTimeSummaryRow approvalTimeSummaryRow, ApprovalTimeSummaryRow approvalTimeSummaryRow1) {
        return multiplier * approvalTimeSummaryRow.getName().compareTo(approvalTimeSummaryRow1.getName());
    }
}
