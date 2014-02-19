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
package org.kuali.kpme.tklm.time.rules.timecollection;

import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.paytype.PayType;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.tklm.api.time.rules.timecollection.TimeCollectionRuleContract;
import org.kuali.kpme.tklm.api.common.TkConstants;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class TimeCollectionRule extends HrBusinessObject implements TimeCollectionRuleContract {

	private static final String PAY_TYPE = "payType";
	private static final String DEPT = "dept";
	private static final String WORK_AREA = "workArea";

	private static final long serialVersionUID = 7892616560736184294L;

	public static final String CACHE_NAME = TkConstants.CacheNamespace.NAMESPACE_PREFIX + "TimeCollectionRule";
	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(WORK_AREA)
            .add(DEPT)
            .add(PAY_TYPE)
            .build();
    public static final ImmutableList<String> CACHE_FLUSH = new ImmutableList.Builder<String>()
            .add(TimeCollectionRule.CACHE_NAME)
            .add(CalendarBlockPermissions.CACHE_NAME)
            .build();

	private String tkTimeCollectionRuleId;
	private String dept;
	private Long workArea;
	private boolean clockUserFl;

	private String tkWorkAreaId;
	private String hrDeptId;

	private Department departmentObj;
	private WorkArea workAreaObj;
	
	// chen, 11/07/11, KPME-1152
	private String payType; 
	private String hrPayTypeId; 
	private PayType payTypeObj;
	// KPME-1152
	
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(WORK_AREA, this.getWorkArea())
			.put(DEPT, this.getDept())
			.put(PAY_TYPE, this.getPayType())
			.build();
	}

	
	public Department getDepartmentObj() {
		return departmentObj;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getHrPayTypeId() {
		return hrPayTypeId;
	}

	public void setHrPayTypeId(String hrPayTypeId) {
		this.hrPayTypeId = hrPayTypeId;
	}

	public PayType getPayTypeObj() {
		return payTypeObj;
	}

	public void setPayTypeObj(PayType payTypeObj) {
		this.payTypeObj = payTypeObj;
	}

	public void setDepartmentObj(Department departmentObj) {
		this.departmentObj = departmentObj;
	}

	public WorkArea getWorkAreaObj() {
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkArea workAreaObj) {
		this.workAreaObj = workAreaObj;
	}

	public Long getWorkArea() {
		return workArea;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

	public boolean isClockUserFl() {
		return clockUserFl;
	}

	public void setClockUserFl(boolean clockUserFl) {
		this.clockUserFl = clockUserFl;
	}

	public String getTkTimeCollectionRuleId() {
		return tkTimeCollectionRuleId;
	}

	public void setTkTimeCollectionRuleId(String tkTimeCollectionRuleId) {
		this.tkTimeCollectionRuleId = tkTimeCollectionRuleId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(String tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

	public String getHrDeptId() {
		return hrDeptId;
	}

	public void setHrDeptId(String hrDeptId) {
		this.hrDeptId = hrDeptId;
	}

	@Override
	public String getUniqueKey() {
		String timeCollKey = getDept()+"_"+isClockUserFl()+"_"+
		(getWorkArea() !=null ? getWorkArea().toString() : "");

		return timeCollKey;
	}

	@Override
	public String getId() {
		return getTkTimeCollectionRuleId();
	}

	@Override
	public void setId(String id) {
		setTkTimeCollectionRuleId(id);
	}

}
