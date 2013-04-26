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
package org.kuali.kpme.tklm.time.approval.summaryrow.comparator;

import java.io.Serializable;
import java.util.Comparator;

import org.kuali.kpme.tklm.time.approval.summaryrow.ApprovalTimeSummaryRow;

public class ApprovalTimeSummaryRowDocIdComparator implements Comparator<ApprovalTimeSummaryRow>, Serializable {

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
