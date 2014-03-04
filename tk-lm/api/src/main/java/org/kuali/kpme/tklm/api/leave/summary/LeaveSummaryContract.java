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
package org.kuali.kpme.tklm.api.leave.summary;

import java.util.List;



/**
 * <p>LeaveSummaryContract interface</p>
 *
 */
public interface LeaveSummaryContract {
	
	/**
	 * The list of LeaveSummaryRow objects associated with the LeaveSummary
	 * 
	 * <p>
	 * leaveSummaryRows of a LeaveSummary
	 * <p>
	 * 
	 * @return leaveSummaryRows for LeaveSummary
	 */
	public List<? extends LeaveSummaryRowContract> getLeaveSummaryRows();

	/**
	 * The LeaveSummaryRow object associated with the LeaveSummary
	 * 
	 * <p>
	 * leaveSummaryRow of a LeaveSummary
	 * <p>
	 * 
	 * @param accrualCategory name to retrieve LeaveSummaryRow objects from leaveSummaryRows
	 * @return leaveSummaryRow for LeaveSummary
	 */
    public LeaveSummaryRowContract getLeaveSummaryRowForAccrualCtgy(String accrualCategory);

    /**
     * TODO: Put a better comment
	 * The year to date dates string associated with the LeaveSummary
	 * 
	 * <p>
	 * ytdDatesString of a LeaveSummary
	 * <p>
	 * 
	 * @return ytdDatesString for LeaveSummary
	 */
	public String getYtdDatesString();

	/**
	 * TODO: Put a better comment
	 * The pending dates String associated with the LeaveSummary
	 * 
	 * <p>
	 * pendingDatesString of a LeaveSummary
	 * <p>
	 * 
	 * @return pendingDatesString for LeaveSummary
	 */
	public String getPendingDatesString();

	/**
	 * The list of approval headers associated with the LeaveSummary
	 * 
	 * <p>
	 * approvalHeaders of a LeaveSummary
	 * <p>
	 * 
	 * @return approvalHeaders for LeaveSummary
	 */
	public List<String> getApprovalHeaders();

	/**
	 * The LeaveSummaryRow object associated with the LeaveSummary
	 * 
	 * <p>
	 * leaveSummaryRow of a LeaveSummary
	 * <p>
	 * 
	 * @param accrualCategoryId id to retrieve LeaveSummaryRow object from leaveSummaryRows
	 * @return leaveSummaryRow for LeaveSummary
	 */
	public LeaveSummaryRowContract getLeaveSummaryRowForAccrualCategory(String accrualCategoryId);

	/**
	 * The note associated with the LeaveSummary
	 * 
	 * <p>
	 * note of a LeaveSummary
	 * <p>
	 * 
	 * @return note for LeaveSummary
	 */
    public String getNote();

}
