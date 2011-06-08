package org.kuali.hr.time.approval.web;

import java.util.Comparator;

public class ApprovalTimeSummaryRowDocIdComparator implements Comparator<ApprovalTimeSummaryRow> {
    @Override
    public int compare(ApprovalTimeSummaryRow approvalTimeSummaryRow, ApprovalTimeSummaryRow approvalTimeSummaryRow1) {
        return approvalTimeSummaryRow.getDocumentId().compareTo(approvalTimeSummaryRow1.getDocumentId());
    }
}
