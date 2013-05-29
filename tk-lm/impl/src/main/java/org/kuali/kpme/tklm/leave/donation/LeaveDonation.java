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
package org.kuali.kpme.tklm.leave.donation;

import java.math.BigDecimal;

import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.earncode.EarnCode;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.rice.kim.api.identity.Person;

import com.google.common.collect.ImmutableList;

public class LeaveDonation extends HrBusinessObject {
    public static final String CACHE_NAME = TkConstants.CacheNamespace.NAMESPACE_PREFIX + "LeaveDonation";
    private static final long serialVersionUID = 1L;
    //KPME-2273/1965 Primary Business Keys List.	
    public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("donorsPrincipalID")
            .add("donatedAccrualCategory")
            .add("amountDonated")
            .add("recipientsPrincipalID")
            .add("recipientsAccrualCategory")
            .add("amountReceived")
            .build();    
    
    private String lmLeaveDonationId;
	private String donatedAccrualCategory;
	private String recipientsAccrualCategory;
	private BigDecimal amountDonated = new BigDecimal("0.0");
	private BigDecimal amountReceived = new BigDecimal("0.0");
	private String donorsPrincipalID;
	private String recipientsPrincipalID;
	private String description;
	
	private Boolean history;
	
	private transient AccrualCategory accrualCategoryObj;
	private transient Person personObj;
	private transient EarnCode earnCodeObj;
	
	private String donatedEarnCode;
	private String recipientsEarnCode;

	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}

	public String getDonatedEarnCode() {
		return donatedEarnCode;
	}

	public void setDonatedEarnCode(String donatedEarnCode) {
		this.donatedEarnCode = donatedEarnCode;
	}

	public String getRecipientsEarnCode() {
		return recipientsEarnCode;
	}

	public void setRecipientsEarnCode(String recipientsEarnCode) {
		this.recipientsEarnCode = recipientsEarnCode;
	}

	public String getLmLeaveDonationId() {
		return lmLeaveDonationId;
	}

	public void setLmLeaveDonationId(String lmLeaveDonationId) {
		this.lmLeaveDonationId = lmLeaveDonationId;
	}

	public String getDonatedAccrualCategory() {
		return donatedAccrualCategory;
	}

	public void setDonatedAccrualCategory(String donatedAccrualCategory) {
		this.donatedAccrualCategory = donatedAccrualCategory;
	}

	public String getRecipientsAccrualCategory() {
		return recipientsAccrualCategory;
	}

	public void setRecipientsAccrualCategory(String recipientsAccrualCategory) {
		this.recipientsAccrualCategory = recipientsAccrualCategory;
	}

	public BigDecimal getAmountDonated() {
		return amountDonated;
	}

	public void setAmountDonated(BigDecimal amountDonated) {
		this.amountDonated = amountDonated;
	}

	public BigDecimal getAmountReceived() {
		return amountReceived;
	}

	public void setAmountReceived(BigDecimal amountReceived) {
		this.amountReceived = amountReceived;
	}

	public String getDonorsPrincipalID() {
		return donorsPrincipalID;
	}

	public void setDonorsPrincipalID(String donorsPrincipalID) {
		this.donorsPrincipalID = donorsPrincipalID;
	}

	public String getRecipientsPrincipalID() {
		return recipientsPrincipalID;
	}

	public void setRecipientsPrincipalID(String recipientsPrincipalID) {
		this.recipientsPrincipalID = recipientsPrincipalID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}

	public Person getPersonObj() {
		return personObj;
	}

	public void setPersonObj(Person personObj) {
		this.personObj = personObj;
	}

	public AccrualCategory getAccrualCategoryObj() {
		return accrualCategoryObj;
	}

	public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.accrualCategoryObj = accrualCategoryObj;
	}

	@Override
	protected String getUniqueKey() {
		return getDonorsPrincipalID() +"_"+ getRecipientsPrincipalID() +"_"+ getDonatedAccrualCategory() +"_"+ getRecipientsAccrualCategory();
	}

	@Override
	public String getId() {
		return getLmLeaveDonationId();
	}

	@Override
	public void setId(String id) {
		setLmLeaveDonationId(id);
	}

}
