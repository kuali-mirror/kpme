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
package org.kuali.kpme.tklm.time.rules.overtime.daily;

import java.math.BigDecimal;

import org.kuali.kpme.core.department.DepartmentBo;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.earncode.group.EarnCodeGroupBo;
import org.kuali.kpme.core.location.LocationBo;
import org.kuali.kpme.core.paytype.PayTypeBo;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.kpme.tklm.api.time.rules.overtime.daily.DailyOvertimeRuleContract;
import org.kuali.kpme.tklm.api.common.TkConstants;
import org.kuali.kpme.tklm.time.rules.TkRule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class DailyOvertimeRule extends TkRule implements DailyOvertimeRuleContract {

	static class KeyFields {
		private static final String WORK_AREA = "workArea";
		private static final String DEPT = "dept";
		private static final String PAY_TYPE = "payType";
		//private static final String LOCATION = "location";
		private static final String GROUP_KEY_CODE = "groupKeyCode";
	}
	private static final long serialVersionUID = 2064326101630818390L;
	
	//KPME-2273/1965 Primary Business Keys List.
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
	            //.add(KeyFields.LOCATION)
				.add(KeyFields.GROUP_KEY_CODE)
	            .add(KeyFields.PAY_TYPE)
	            .add(KeyFields.DEPT)
	            .add(KeyFields.WORK_AREA)
	            .build();

		
	public static final String CACHE_NAME = TkConstants.Namespace.NAMESPACE_PREFIX + "DailyOvertimeRule";

	private String tkDailyOvertimeRuleId;

	private String fromEarnGroup;
	private String earnCode;

	//private String location;
	private String paytype;
	private String dept;
	private Long workArea;

	private BigDecimal maxGap;
	private BigDecimal minHours;
	private boolean history;
	private boolean ovtEarnCode;

	private String tkWorkAreaId;
	private String hrDeptId;
	private String hrLocationId;
	
	private TaskBo taskObj;
	private WorkAreaBo workAreaObj;
	private DepartmentBo departmentObj;
	private PayTypeBo payTypeObj;

	private EarnCodeGroupBo fromEarnGroupObj;
	private EarnCodeBo earnCodeObj;
	private LocationBo locationObj;
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
    	return  new ImmutableMap.Builder<String, Object>()
    		//.put(KeyFields.LOCATION, this.getLocation())
    		.put(KeyFields.GROUP_KEY_CODE, this.getGroupKeyCode())
    		.put(KeyFields.PAY_TYPE, this.getPaytype())
			.put(KeyFields.DEPT, this.getDept())
			.put(KeyFields.WORK_AREA, this.getWorkArea())
			.build();
	}

	public String getTkDailyOvertimeRuleId() {
		return tkDailyOvertimeRuleId;
	}

	public void setTkDailyOvertimeRuleId(String tkDailyOvertimeRuleId) {
		this.tkDailyOvertimeRuleId = tkDailyOvertimeRuleId;
	}

	/*
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}*/

	public BigDecimal getMaxGap() {
		return maxGap;
	}

	public void setMaxGap(BigDecimal maxGap) {
		this.maxGap = maxGap;
	}

	public DepartmentBo getDepartmentObj() {
		return departmentObj;
	}

	public void setDepartmentObj(DepartmentBo departmentObj) {
		this.departmentObj = departmentObj;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public TaskBo getTaskObj() {
		return taskObj;
	}

	public void setTaskObj(TaskBo taskObj) {
		this.taskObj = taskObj;
	}

	public WorkAreaBo getWorkAreaObj() {
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkAreaBo workAreaObj) {
		this.workAreaObj = workAreaObj;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

	public Long getWorkArea() {
		return workArea;
	}

	public PayTypeBo getPayTypeObj() {
		return payTypeObj;
	}

	public void setPayTypeObj(PayTypeBo payTypeObj) {
		this.payTypeObj = payTypeObj;
	}

	public String getFromEarnGroup() {
		return fromEarnGroup;
	}

	public void setFromEarnGroup(String fromEarnGroup) {
		this.fromEarnGroup = fromEarnGroup;
	}

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}

	public BigDecimal getMinHours() {
		return minHours;
	}

	public void setMinHours(BigDecimal minHours) {
		this.minHours = minHours;
	}

	public EarnCodeGroupBo getFromEarnGroupObj() {
		return fromEarnGroupObj;
	}

	public void setFromEarnGroupObj(EarnCodeGroupBo fromEarnGroupObj) {
		this.fromEarnGroupObj = fromEarnGroupObj;
	}

	public EarnCodeBo getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCodeBo earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}

	public LocationBo getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(LocationBo locationObj) {
		this.locationObj = locationObj;
	}

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	public boolean isOvtEarnCode() {
		return ovtEarnCode;
	}

	public void setOvtEarnCode(boolean ovtEarnCode) {
		this.ovtEarnCode = ovtEarnCode;
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

	public String getHrLocationId() {
		return hrLocationId;
	}

	public void setHrLocationId(String hrLocationId) {
		this.hrLocationId = hrLocationId;
	}

	@Override
	public String getUniqueKey() {
		return getGroupKeyCode() + "_" + dept + "_" + workArea + "_" + paytype;
	}

	@Override
	public String getId() {
		return getTkDailyOvertimeRuleId();
	}

	@Override
	public void setId(String id) {
		setTkDailyOvertimeRuleId(id);
	}

}
