/**
 * Copyright 2004-2013 The Kuali Foundation
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

import org.kuali.kpme.core.KPMEConstants;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.bo.earncode.EarnCode;
import org.kuali.kpme.core.bo.earncode.group.EarnCodeGroup;
import org.kuali.kpme.core.bo.location.Location;
import org.kuali.kpme.core.bo.paytype.PayType;
import org.kuali.kpme.core.bo.task.Task;
import org.kuali.kpme.core.bo.workarea.WorkArea;
import org.kuali.kpme.tklm.time.rules.TkRule;

public class DailyOvertimeRule extends TkRule {

	private static final long serialVersionUID = 2064326101630818390L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "DailyOvertimeRule";

	private String tkDailyOvertimeRuleId;

	private String fromEarnGroup;
	private String earnCode;

	private String location;
	private String paytype;
	private String dept;
	private Long workArea;

	private BigDecimal maxGap;
	private BigDecimal minHours;
	private String userPrincipalId;
	private boolean history;
	private Boolean ovtEarnCode;

	private String tkWorkAreaId;
	private String hrDeptId;
	private String hrLocationId;
	
	private Task taskObj;
	private WorkArea workAreaObj;
	private Department departmentObj;
	private PayType payTypeObj;

	private EarnCodeGroup fromEarnGroupObj;
	private EarnCode earnCodeObj;
	private Location locationObj;

	public String getTkDailyOvertimeRuleId() {
		return tkDailyOvertimeRuleId;
	}

	public void setTkDailyOvertimeRuleId(String tkDailyOvertimeRuleId) {
		this.tkDailyOvertimeRuleId = tkDailyOvertimeRuleId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getMaxGap() {
		return maxGap;
	}

	public void setMaxGap(BigDecimal maxGap) {
		this.maxGap = maxGap;
	}

	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}

	public Department getDepartmentObj() {
		return departmentObj;
	}

	public void setDepartmentObj(Department departmentObj) {
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

	public Task getTaskObj() {
		return taskObj;
	}

	public void setTaskObj(Task taskObj) {
		this.taskObj = taskObj;
	}

	public WorkArea getWorkAreaObj() {
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkArea workAreaObj) {
		this.workAreaObj = workAreaObj;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

	public Long getWorkArea() {
		return workArea;
	}

	public PayType getPayTypeObj() {
		return payTypeObj;
	}

	public void setPayTypeObj(PayType payTypeObj) {
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

	public EarnCodeGroup getFromEarnGroupObj() {
		return fromEarnGroupObj;
	}

	public void setFromEarnGroupObj(EarnCodeGroup fromEarnGroupObj) {
		this.fromEarnGroupObj = fromEarnGroupObj;
	}

	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}

	public Location getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	public Boolean getOvtEarnCode() {
		return ovtEarnCode;
	}

	public void setOvtEarnCode(Boolean ovtEarnCode) {
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
		return location + "_" + dept + "_" + workArea + "_" + paytype;
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
