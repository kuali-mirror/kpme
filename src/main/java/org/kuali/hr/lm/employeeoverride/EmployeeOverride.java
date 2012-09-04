package org.kuali.hr.lm.employeeoverride;

import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class EmployeeOverride extends HrBusinessObject {

	private static final long serialVersionUID = 1L;
	
	private String lmEmployeeOverrideId;
	private String principalId;
	private String accrualCategory;
	private String leavePlan;
	private Person principal;
	private PrincipalHRAttributes principalHRAttrObj;
	private AccrualCategory accrualCategoryObj;
	private String overrideType;
	private Long overrideValue;
	private String description;

	@Override
	public String getId() {
		return getLmEmployeeOverrideId();
	}

	@Override
	protected String getUniqueKey() {
		return getLmEmployeeOverrideId();
	}

	@Override
	public void setId(String id) {
		setLmEmployeeOverrideId(id);		
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	protected LinkedHashMap toStringMapper() {
//		LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
//		toStringMap.put("lmEmployeeOverrideId", lmEmployeeOverrideId);
//		toStringMap.put("principalId", principalId);
//		toStringMap.put("overrideType", overrideType);
//		toStringMap.put("accrualCategory", accrualCategory);
//	
//		return toStringMap;
//	}

	public String getLmEmployeeOverrideId() {
		return lmEmployeeOverrideId;
	}

	public void setLmEmployeeOverrideId(String lmEmployeeOverrideId) {
		this.lmEmployeeOverrideId = lmEmployeeOverrideId;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
		this.setPrincipal(KimApiServiceLocator.getPersonService().getPerson(this.principalId));
	}
	
	public String getName() {
		if (principal == null) {
        principal = KimApiServiceLocator.getPersonService().getPerson(this.principalId);
		}
		return (principal != null) ? principal.getName() : "";
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
        if (leavePlan != null) {
            return leavePlan;
        }
		if (this.principalHRAttrObj == null && !StringUtils.isEmpty(this.principalId)) {
			principalHRAttrObj = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, this.getEffectiveDate());
		}
        leavePlan = principalHRAttrObj == null ? null : principalHRAttrObj.getLeavePlan();
		return leavePlan;
	}

	public void setLeavePlan(String leavePlan) {
		this.leavePlan = leavePlan;
	}

	public Long getOverrideValue() {
		return overrideValue;
	}

	public void setOverrideValue(Long overrideValue) {
		this.overrideValue = overrideValue;
	}

	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	public PrincipalHRAttributes getPrincipalHRAttrObj() {
		return principalHRAttrObj;
	}

	public void setPrincipalHRAttrObj(PrincipalHRAttributes principalHRAttrObj) {
		this.principalHRAttrObj = principalHRAttrObj;
	}

}
