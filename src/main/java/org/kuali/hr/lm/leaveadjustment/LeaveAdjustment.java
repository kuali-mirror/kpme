package org.kuali.hr.lm.leaveadjustment;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.rice.kim.bo.Person;

public class LeaveAdjustment extends HrBusinessObject {
	private static final long serialVersionUID = 1L;
	private String lmLeaveAdjustmentId;
	private String principalId;
	private String leavePlan;
	private String accrualCategory;
	private String leaveCode;
	private String description;
	private BigDecimal adjustmentAmount = new BigDecimal("0.0");
	private Person principal;
	private AccrualCategory accrualCategoryObj;
	private LeavePlan leavePlanObj;
	private LeaveCode leaveCodeObj;
	
	public LeaveCode getLeaveCodeObj() {
		return leaveCodeObj;
	}
	public void setLeaveCodeObj(LeaveCode leaveCodeObj) {
		this.leaveCodeObj = leaveCodeObj;
	}
	
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}
	public String getLeavePlan() {
		return leavePlan;
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
	public String getLeaveCode() {
		return leaveCode;
	}
	public void setLeaveCode(String leaveCode) {
		this.leaveCode = leaveCode;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Person getPrincipal() {
		return principal;
	}
	public void setPrincipal(Person principal) {
		this.principal = principal;
	}
	public LeavePlan getLeavePlanObj() {
		return leavePlanObj;
	}
	public void setLeavePlanObj(LeavePlan leavePlanObj) {
		this.leavePlanObj = leavePlanObj;
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
		return leavePlan;
	}
	
	@Override
	public String getId() {
		return getLmLeaveAdjustmentId();
	}

	@Override
	public void setId(String id) {
		setLmLeaveAdjustmentId(id);
	}

	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}
}
