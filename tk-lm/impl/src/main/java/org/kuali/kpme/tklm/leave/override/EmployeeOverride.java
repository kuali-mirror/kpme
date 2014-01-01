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
package org.kuali.kpme.tklm.leave.override;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.api.leave.override.EmployeeOverrideContract;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class EmployeeOverride extends HrBusinessObject implements EmployeeOverrideContract {

	private static final String OVERRIDE_TYPE = "overrideType";
	private static final String ACCRUAL_CATEGORY = "accrualCategory";
	private static final String LEAVE_PLAN = "leavePlan";
	private static final String PRINCIPAL_ID = "principalId";
	
	private static final long serialVersionUID = 1L;
    public static final String CACHE_NAME = TkConstants.CacheNamespace.NAMESPACE_PREFIX + "EmployeeOverride";
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(PRINCIPAL_ID)
            .add(LEAVE_PLAN)
            .add(ACCRUAL_CATEGORY)
            .add(OVERRIDE_TYPE)
            .build();
	
	private String lmEmployeeOverrideId;
	private String principalId;
	private String accrualCategory;
	private String leavePlan;
	private transient Person principal;
	private transient PrincipalHRAttributes principalHRAttrObj;
	private transient AccrualCategory accrualCategoryObj;
	private String overrideType;
	private Long overrideValue;
	private String description;

	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
    		.put(PRINCIPAL_ID, this.getPrincipalId())
			.put(LEAVE_PLAN, this.getLeavePlan())
			.put(ACCRUAL_CATEGORY, this.getAccrualCategory())
			.put(OVERRIDE_TYPE, this.getOverrideType())
			.build();
	}
	
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
			principalHRAttrObj = (PrincipalHRAttributes) HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, this.getEffectiveLocalDate());
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
