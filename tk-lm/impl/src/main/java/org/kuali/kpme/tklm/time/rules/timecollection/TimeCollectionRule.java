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
import org.kuali.kpme.core.bo.HrKeyedBusinessObject;
import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.paytype.PayTypeBo;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.kpme.tklm.api.time.rules.timecollection.TimeCollectionRuleContract;
import org.kuali.kpme.tklm.api.common.TkConstants;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class TimeCollectionRule extends HrKeyedBusinessObject implements TimeCollectionRuleContract {

	static class KeyFields {
		private static final String PAY_TYPE = "payType";
		private static final String DEPT = "dept";
		private static final String WORK_AREA = "workArea";
		private static final String GROUP_KEY_CODE = "groupKeyCode";
	}
	
	private static final long serialVersionUID = 7892616560736184294L;

	public static final String CACHE_NAME = TkConstants.Namespace.NAMESPACE_PREFIX + "TimeCollectionRule";
	//KPME-2273/1965 Primary Business Keys List.	
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(KeyFields.WORK_AREA)
            .add(KeyFields.DEPT)
            .add(KeyFields.PAY_TYPE)
            .add(KeyFields.GROUP_KEY_CODE)
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

	private DepartmentBo departmentObj;
	private WorkAreaBo workAreaObj;
	
	// chen, 11/07/11, KPME-1152
	private String payType; 
	private String hrPayTypeId; 
	private PayTypeBo payTypeObj;
	// KPME-1152
	
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
			.put(KeyFields.WORK_AREA, this.getWorkArea())
			.put(KeyFields.DEPT, this.getDept())
			.put(KeyFields.PAY_TYPE, this.getPayType())
			.put(KeyFields.GROUP_KEY_CODE, this.getGroupKeyCode())
			.build();
	}

	
	public DepartmentBo getDepartmentObj() {
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

	public PayTypeBo getPayTypeObj() {
		return payTypeObj;
	}

	public void setPayTypeObj(PayTypeBo payTypeObj) {
		this.payTypeObj = payTypeObj;
	}

	public void setDepartmentObj(DepartmentBo departmentObj) {
		this.departmentObj = departmentObj;
	}

	public WorkAreaBo getWorkAreaObj() {
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkAreaBo workAreaObj) {
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
