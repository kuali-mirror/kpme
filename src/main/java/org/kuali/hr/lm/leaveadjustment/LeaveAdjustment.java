package org.kuali.hr.lm.leaveadjustment;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class LeaveAdjustment extends HrBusinessObject {
	private static final long serialVersionUID = 1L;
	private String lmLeaveAdjustmentId;
	private String principalId;
	private String leavePlan;
	private String accrualCategory;
	private String earnCode;
	private String description;
	private BigDecimal adjustmentAmount = new BigDecimal("0.0");
	private Person principal;
	private AccrualCategory accrualCategoryObj;
	private EarnCode earnCodeObj;
	private PrincipalHRAttributes principalHRAttrObj;
	
	public String getEarnCode() {
		return earnCode;
	}
	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}
	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}
	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public String getName() {
		if (principal == null) {
        principal = KimApiServiceLocator.getPersonService().getPerson(this.principalId);
		}
		return (principal != null) ? principal.getName() : "";
	}
	
	public String getLeavePlan() {
		if (!StringUtils.isEmpty(this.principalId)) {
			principalHRAttrObj = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, this.getEffectiveDate());
		}
		return (principalHRAttrObj != null) ? principalHRAttrObj.getLeavePlan() : "";
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}
	public String getAccrualCategory() {
		return accrualCategory;
	}
	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}
	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}
	public AccrualCategory getAccrualCategoryObj() {
		return accrualCategoryObj;
	}
	public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.accrualCategoryObj = accrualCategoryObj;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getLmLeaveAdjustmentId() {
		return lmLeaveAdjustmentId;
	}
	public void setLmLeaveAdjustmentId(String lmLeaveAdjustmentId) {
		this.lmLeaveAdjustmentId = lmLeaveAdjustmentId;
	}
	
	@Override
	protected String getUniqueKey() {
		return getLmLeaveAdjustmentId();
	}
	
	@Override
	public String getId() {
		return getLmLeaveAdjustmentId();
	}

	@Override
	public void setId(String id) {
		setLmLeaveAdjustmentId(id);
	}

	public PrincipalHRAttributes getPrincipalHRAttrObj() {
		return principalHRAttrObj;
	}
	public void setPrincipalHRAttrObj(PrincipalHRAttributes principalHRAttrObj) {
		this.principalHRAttrObj = principalHRAttrObj;
	}
}
