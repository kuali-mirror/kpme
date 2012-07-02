package org.kuali.hr.time.earncode;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.util.TkConstants;

public class EarnCode extends HrBusinessObject {

	/**
     *
     */
	private static final long serialVersionUID = 1L;

	private String hrEarnCodeId;
	private String earnCode;
	private String description;
	
    private Boolean ovtEarnCode;
	private String accrualCategory;
	private BigDecimal inflateMinHours;
	private BigDecimal inflateFactor;

	private boolean history;

	private AccrualCategory accrualCategoryObj;
	private EarnCode rollupToEarnCodeObj;
	
	private String leavePlan;
	private String accrualBalanceAction;
	private String fractionalTimeAllowed;
	private String roundingOption;
	private String eligibleForAccrual;
	private String affectPay;
	private String allowScheduledLeave;
	private String fmla;
	private String workmansComp;
	private Long defaultAmountofTime;
	private String allowNegativeAccrualBalance;
	private String rollupToEarnCode;
	private String recordMethod;
	
	public String getRecordMethod() {
		return recordMethod;
	}

	public void setRecordMethod(String recordMethod) {
		this.recordMethod = recordMethod;
	}

	public String getRollupToEarnCode() {
		return rollupToEarnCode;
	}

	public void setRollupToEarnCode(String rollupToEarnCode) {
		this.rollupToEarnCode = rollupToEarnCode;
	}

	public EarnCode getRollupToEarnCodeObj() {
		return rollupToEarnCodeObj;
	}

	public void setRollupToEarnCodeObj(EarnCode rollupToEarnCodeObj) {
		this.rollupToEarnCodeObj = rollupToEarnCodeObj;
	}

	public String getLeavePlan() {
		AccrualCategory myAccrualCategoryObj = new AccrualCategory();
		if(this.accrualCategory != null) {
			myAccrualCategoryObj =  TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, this.effectiveDate);
	    }
		this.leavePlan =(myAccrualCategoryObj != null) ? myAccrualCategoryObj.getLeavePlan() : ""; 
	    return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public String getAccrualBalanceAction() {
		return accrualBalanceAction;
	}

	public void setAccrualBalanceAction(String accrualBalanceAction) {
		this.accrualBalanceAction = accrualBalanceAction;
	}

	public String getFractionalTimeAllowed() {
		return fractionalTimeAllowed;
	}

	public void setFractionalTimeAllowed(String fractionalTimeAllowed) {
		this.fractionalTimeAllowed = fractionalTimeAllowed;
	}

	public String getRoundingOption() {
		return roundingOption;
	}

	public void setRoundingOption(String roundingOption) {
		this.roundingOption = roundingOption;
	}

	public String getEligibleForAccrual() {
		return eligibleForAccrual;
	}

	public void setEligibleForAccrual(String eligibleForAccrual) {
		this.eligibleForAccrual = eligibleForAccrual;
	}

	public String getAffectPay() {
		return affectPay;
	}

	public void setAffectPay(String affectPay) {
		this.affectPay = affectPay;
	}

	public String getAllowScheduledLeave() {
		return allowScheduledLeave;
	}

	public void setAllowScheduledLeave(String allowScheduledLeave) {
		this.allowScheduledLeave = allowScheduledLeave;
	}

	public String getFmla() {
		return fmla;
	}

	public void setFmla(String fmla) {
		this.fmla = fmla;
	}

	public String getWorkmansComp() {
		return workmansComp;
	}

	public void setWorkmansComp(String workmansComp) {
		this.workmansComp = workmansComp;
	}

	public Long getDefaultAmountofTime() {
		return defaultAmountofTime;
	}

	public void setDefaultAmountofTime(Long defaultAmountofTime) {
		this.defaultAmountofTime = defaultAmountofTime;
	}

	public String getAllowNegativeAccrualBalance() {
		return allowNegativeAccrualBalance;
	}

	public void setAllowNegativeAccrualBalance(String allowNegativeAccrualBalance) {
		this.allowNegativeAccrualBalance = allowNegativeAccrualBalance;
	}


	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getHrEarnCodeId() {
		return hrEarnCodeId;
	}

	public void setHrEarnCodeId(String hrEarnCodeId) {
		this.hrEarnCodeId = hrEarnCodeId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getAccrualCategory() {
		return accrualCategory;
	}

	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}

	public AccrualCategory getAccrualCategoryObj() {
		if(accrualCategoryObj == null && !this.getAccrualCategory().isEmpty()) {
			this.assingAccrualCategoryObj();
		}
		return accrualCategoryObj;
	}
	public void assingAccrualCategoryObj() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("accrualCategory", getAccrualCategory());
		Collection c = KRADServiceLocator.getBusinessObjectService().findMatching(AccrualCategory.class, parameters);
		if(!c.isEmpty()) {
			this.setAccrualCategoryObj((AccrualCategory)c.toArray()[0]);
		}
	}

	public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.accrualCategoryObj = accrualCategoryObj;
	}

	public BigDecimal getInflateMinHours() {
		return inflateMinHours;
	}

	public void setInflateMinHours(BigDecimal inflateMinHours) {
		this.inflateMinHours = inflateMinHours;
	}

	public BigDecimal getInflateFactor() {
		return inflateFactor;
	}

	public void setInflateFactor(BigDecimal inflateFactor) {
		this.inflateFactor = inflateFactor;
	}

    public Boolean getOvtEarnCode() {
        return ovtEarnCode;
    }

    public void setOvtEarnCode(Boolean ovtEarnCode) {
        this.ovtEarnCode = ovtEarnCode;
    }

    /**
	 * This is used by the timeblock json object.
	 * The purpose of this function is to create a string based on the record_* fields which can be used to render hour / begin(end) time input box
	 * @return String fieldType
	 */
	public String getEarnCodeType() {
//		if(getRecordHours()) {
//			return TkConstants.EARN_CODE_HOUR;
//		}
//		else if(getRecordTime()) {
//			return TkConstants.EARN_CODE_TIME;
//		}
//		else if(getRecordAmount()) {
//			return TkConstants.EARN_CODE_AMOUNT;
//		}
//		else {
//			return "";
//		}
		return this.recordMethod;
	}

	@Override
	public String getUniqueKey() {
		return earnCode;
	}

	@Override
	public String getId() {
		return getHrEarnCodeId();
	}

	@Override
	public void setId(String id) {
		setHrEarnCodeId(id);
	}
	
    public String getEarnCodeKeyForDisplay() {
//    	String unitTime = null;
//    	AccrualCategory acObj = null;
//    	if(this.accrualCategory != null) {
//    		acObj = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(accrualCategory, this.effectiveDate);
//    	}
//    	unitTime = (acObj!= null ? acObj.getUnitOfTime() : this.recordMethod) ;
//        return hrEarnCodeId + ":" + unitTime;
    	return hrEarnCodeId;
    }
    
    public String getEarnCodeValueForDisplay() {
        return earnCode + " : " + description;
    }
}
