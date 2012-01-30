package org.kuali.hr.lm.employeeoverride;

import java.util.LinkedHashMap;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

public class EmployeeOverride extends HrBusinessObject {

	private static final long serialVersionUID = 1L;
	
	private String tkEmployeeOverrideId;
	private String principalId;
	private String accrualCategory;
	private String leavePlan;
	private Person principal;
	private LeavePlan leavePlanObj;
	private AccrualCategory accrualCategoryObj;
	private String overrideType;
	private int overrideValue;
	private String description;

	@Override
	public String getId() {
		return getTkEmployeeOverrideId();
	}

	@Override
	protected String getUniqueKey() {
		return getTkEmployeeOverrideId();
	}

	@Override
	public void setId(String id) {
		setTkEmployeeOverrideId(id);		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
		toStringMap.put("tkEmployeeOverrideId", tkEmployeeOverrideId);
		toStringMap.put("principalId", principalId);
		toStringMap.put("overrideType", overrideType);
		toStringMap.put("accrualCategory", accrualCategory);
	
		return toStringMap;
	}

	public String getTkEmployeeOverrideId() {
		return tkEmployeeOverrideId;
	}

	public void setTkEmployeeOverrideId(String tkEmployeeOverrideId) {
		this.tkEmployeeOverrideId = tkEmployeeOverrideId;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
		this.setPrincipal(KIMServiceLocator.getPersonService().getPerson(this.principalId));
	}
	
	public String getName() {
		if (principal == null) {
        principal = KIMServiceLocator.getPersonService().getPerson(this.principalId);
		}
		return (principal != null) ? principal.getName() : "";
	}

	public LeavePlan getLeavePlanObj() {
		return leavePlanObj;
	}

	public void setLeavePlanObj(LeavePlan leavePlanObj) {
		this.leavePlanObj = leavePlanObj;
	}

	public AccrualCategory getAccrualCategoryObj() {
		return accrualCategoryObj;
	}

	public void setAccrualCategoryObj(AccrualCategory accrualCategoryObj) {
		this.accrualCategoryObj = accrualCategoryObj;
	}

	public String getOverrideType() {
		return overrideType;
	}

	public void setOverrideType(String overrideType) {
		this.overrideType = overrideType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccrualCategory() {
		return accrualCategory;
	}

	public void setAccrualCategory(String accrualCategory) {
		this.accrualCategory = accrualCategory;
	}

	public String getLeavePlan() {
		return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public int getOverrideValue() {
		return overrideValue;
	}

	public void setOverrideValue(int overrideValue) {
		this.overrideValue = overrideValue;
	}

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

}
