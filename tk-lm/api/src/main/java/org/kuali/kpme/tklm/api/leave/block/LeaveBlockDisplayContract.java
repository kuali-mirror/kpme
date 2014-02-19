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
package org.kuali.kpme.tklm.api.leave.block;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;


/**
 * <p>LeaveBlockDisplayContract interface</p>
 *
 */
public interface LeaveBlockDisplayContract {
	
	/**
	 * The leave date associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * <p>
	 * 
	 * @return leaveBlock.getLeaveDate()
	 */	
	public Date getLeaveDate();
	
	/**
	 * The document id associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * <p>
	 * 
	 * @return leaveBlock.getDocumentId()
	 */	
	public String getDocumentId();
	
	/**
	 * The calendar id associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * <p>
	 * 
	 * @return leaveBlock.getCalendarId()
	 */	
	public String getCalendarId();
	
	/**
	 * The earn code associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * <p>
	 * 
	 * @return leaveBlock.getEarnCode()
	 */	
	public String getEarnCode();
	
	/**
	 * The description associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * If leaveBlock.getDescription() is null, call private retrieveDescriptionAccordingToLeaveType method
	 * <p>
	 * 
	 * @return leaveBlock.getDescription() if not null
	 */	
	public String getDescription();
	
	/**
	 * The request status associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * If leaveBlock.getRequestStatus() is not null, it returns HrConstants.REQUEST_STATUS_STRINGS for the status
	 * <p>
	 * 
	 * @return HrConstants.REQUEST_STATUS_STRINGS for the leave block's request status
	 */	
	public String getRequestStatus();
	
	/**
	 * The document status associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * If leaveBlock.getDocumentId() is not null, it gets leave calendar document header based on the document id and
	 * returns KewApiConstants.DOCUMENT_STATUSES for the header's document status
	 * <p>
	 * 
	 * @return KewApiConstants.DOCUMENT_STATUSES of the header document
	 */	
	public String getDocumentStatus();
	
	/**
	 * The leave amount associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * <p>
	 * 
	 * @return leaveBlock.getLeaveAmount()
	 */	
	public BigDecimal getLeaveAmount();
	
	/**
	 * The time stamp associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * <p>
	 * 
	 * @return leaveBlock.getTimestamp()
	 */	
	public Timestamp getTimestamp();
	
	/**
	 * The assignment title associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * <p>
	 * 
	 * @return leaveBlock.getAssignmentTitle()
	 */	
	public String getAssignmentTitle();
	
	/**
	 * The accrual category associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * <p>
	 * 
	 * @return leaveBlock.getAccrualCategory()
	 */	
	public String getAccrualCategory();
	
	/**
	 * The accrual balances associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * <p>
	 * 
	 * @return accrualBalances.values();
	 */	
	public Collection<BigDecimal> getAccrualBalances();

	/**
	 * The accrual balance associated with the LeaveBlockDisplay
	 * 
	 * <p>
	 * <p>
	 * 
	 * @param String to retrieve accrual balance from the accrual balances collection
	 * @return accrualBalances.get(accrualCategory)
	 */	
	public BigDecimal getAccrualBalance(String accrualCategory);

}
