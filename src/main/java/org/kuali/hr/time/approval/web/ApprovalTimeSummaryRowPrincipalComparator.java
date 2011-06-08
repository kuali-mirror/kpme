package org.kuali.hr.time.approval.web;

import java.util.Comparator;

public class ApprovalTimeSummaryRowPrincipalComparator implements Comparator<ApprovalTimeSummaryRow> {
    @Override
    public int compare(ApprovalTimeSummaryRow approvalTimeSummaryRow, ApprovalTimeSummaryRow approvalTimeSummaryRow1) {
        return approvalTimeSummaryRow.getName().compareTo(approvalTimeSummaryRow1.getName());
    }
}
