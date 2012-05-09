package org.kuali.hr.lm.leavedonation;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.rice.kim.bo.Person;

public class LeaveDonation extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lmLeaveDonationId;
	private String donatedAccrualCategory;
	private String recipientsAccrualCategory;
	private BigDecimal amountDonated = new BigDecimal("0.0");
	private BigDecimal amountReceived = new BigDecimal("0.0");
	private String donorsPrincipalID;
	private String recipientsPrincipalID;
	private String description;
	
	private Boolean history;
	
	private AccrualCategory accrualCategoryObj;
	
	private Person personObj;

	
	private LeaveCode leaveCodeObj;
	public LeaveCode getLeaveCodeObj() {
		return leaveCodeObj;
	}

	public void setLeaveCodeObj(LeaveCode leaveCodeObj) {
		this.leaveCodeObj = leaveCodeObj;
	}

	private String donatedLeaveCode;
	private String recipientsLeaveCode;

	
	public String getDonatedLeaveCode() {
		return donatedLeaveCode;
	}

	public void setDonatedLeaveCode(String donatedLeaveCode) {
		this.donatedLeaveCode = donatedLeaveCode;
	}

	public String getRecipientsLeaveCode() {
		return recipientsLeaveCode;
	}

	public void setRecipientsLeaveCode(String recipientsLeaveCode) {
		this.recipientsLeaveCode = recipientsLeaveCode;
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
		String leaveCodeKey = getDonorsPrincipalID() +"_"+ getRecipientsPrincipalID() +"_"+ getDonatedAccrualCategory() +"_"+ getRecipientsAccrualCategory();
		return leaveCodeKey;
	}

	@Override
	public String getId() {
		return getLmLeaveDonationId();
	}

	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String id) {
		setLmLeaveDonationId(id);
	}

}
