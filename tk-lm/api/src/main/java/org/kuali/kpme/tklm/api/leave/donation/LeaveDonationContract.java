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
package org.kuali.kpme.tklm.api.leave.donation;

import java.math.BigDecimal;

import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.rice.kim.api.identity.Person;

/**
 * <p>LeaveDonationContract interface</p>
 *
 */
public interface LeaveDonationContract extends HrBusinessObjectContract {
	
	/**
	 * The EarnCode object associated with the LeaveDonation
	 * 
	 * <p>
	 * earnCodeObject of a LeaveDonation
	 * <p>
	 * 
	 * @return earnCodeObject for LeaveDonation
	 */
	public EarnCodeContract getEarnCodeObj();
	
	/**
	 * The EarnCode name that a leave block indicating the amount donated will be recorded with 
	 * 
	 * <p>
	 * donatedEarnCode of a LeaveDonation
	 * </p>
	 * 
	 * @return donatedEarnCode for LeaveDonation
	 */
	public String getDonatedEarnCode();	

	/**
	 * The EarnCode name that donation accrual leave block will be recorded with
	 * 
	 * <p>
	 * recipientsEarnCode of a LeaveDonation
	 * </p>
	 * 
	 * @return recipientsEarnCode for LeaveDonation
	 */
	public String getRecipientsEarnCode();	

	/**
	 * The primary key of a LeaveDonation entry saved in a database
	 * 
	 * <p>
	 * lmLeaveDonationId of a LeaveDonation
	 * <p>
	 * 
	 * @return lmLeaveDonationId for LeaveDonation
	 */
	public String getLmLeaveDonationId();

	/**
	 * The AccrualCategory name associated with the LeaveDonation
	 * 
	 * <p>
	 * donatedAccrualCategory of a LeaveDonation
	 * </p>
	 * 
	 * @return donatedAccrualCategory for LeaveDonation
	 */
	public String getDonatedAccrualCategory();	

	/**
	 * The AccrualCategory name the donated Leave will be accrued to
	 * 
	 * <p>
	 * recipientsAccrualCategory of a LeaveDonation
	 * </p>
	 * 
	 * @return recipientsAccrualCategory for LeaveDonation
	 */
	public String getRecipientsAccrualCategory();	

	/**
	 * The amount of accrued leave to be donated
	 * 
	 * <p>
	 * amountDonated of a LeaveDonation
	 * </p>
	 * 
	 * @return amountDonated for LeaveDonation
	 */
	public BigDecimal getAmountDonated();
	
	/**
	 * The amount of accrued leave to be received
	 * 
	 * <p>
	 * amountReceived of a LeaveDonation
	 * </p>
	 * 
	 * @return amountReceived for LeaveDonation
	 */
	public BigDecimal getAmountReceived();
	
	/**
	 * The principalID of the employee who is donating leave
	 * 
	 * <p>
	 * donorsPrincipalID of a LeaveDonation
	 * </p>
	 * 
	 * @return donorsPrincipalID for LeaveDonation
	 */
	public String getDonorsPrincipalID();	

	/**
	 * The principalID of the employee who is receiving the donated leave
	 * 
	 * <p>
	 * recipientsPrincipalID of a LeaveDonation
	 * </p>
	 * 
	 * @return recipientsPrincipalID for LeaveDonation
	 */
	public String getRecipientsPrincipalID();

	/**
	 * The descripton of the LeaveDonation
	 * 
	 * <p>
	 * description of a LeaveDonation
	 * </p>
	 * 
	 * @return description for LeaveDonation
	 */
	public String getDescription();	

	/**
	 * The history flag of the LeaveDonation
	 * 
	 * <p>
	 * history flag of a LeaveDonation
	 * <p>
	 * 
	 * @return Y if on, N if not
	 */
	public Boolean getHistory();

	/**
	 * The Person object associated with the LeaveDonation
	 * 
	 * <p>
	 * personObj of a LeaveDonation
	 * <p>
	 * 
	 * @return personObj for LeaveDonation
	 */	
	public Person getPersonObj();

	/**
	 * The AccuralCategory object associated with the LeaveDonation
	 * 
	 * <p>
	 * accrualCategoryObj of a LeaveDonation
	 * <p>
	 * 
	 * @return accrualCategoryObj for LeaveDonation
	 */	
	public AccrualCategoryContract getAccrualCategoryObj();

}
