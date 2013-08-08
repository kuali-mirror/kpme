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
package org.kuali.kpme.tklm.time.rules.lunch.department;

import java.math.BigDecimal;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.department.Department;
import org.kuali.kpme.core.job.Job;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.tklm.api.time.rules.lunch.department.DeptLunchRuleContract;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.rice.kim.api.identity.Person;

import com.google.common.collect.ImmutableList;

public class DeptLunchRule extends HrBusinessObject implements DeptLunchRuleContract {
    public static final String CACHE_NAME = TkConstants.CacheNamespace.NAMESPACE_PREFIX + "DeptLunchRule";
    //KPME-2273/1965 Primary Business Keys List.
    public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("dept")
            .add("workArea")
            .add("principalId")
            .add("jobNumber")
            .build();

	
    private static final long serialVersionUID = 1L;

    private String tkDeptLunchRuleId;
    private String dept;
    private Long workArea;
    private String principalId;
    private Long jobNumber;    
    private BigDecimal deductionMins;
    private BigDecimal shiftHours;
    private String userPrincipalId;

    private String tkWorkAreaId;
    private String hrDeptId;
    private String hrJobId;

    private transient WorkArea workAreaObj;
    private transient Department departmentObj;
    private transient Job job;
	private transient Person principal;
	
	//for lookups
	private boolean history;
	
	public Person getPrincipal() {
		return principal;
	}

	public void setPrincipal(Person principal) {
		this.principal = principal;
	}

	public Job getJob() {
		return job;
	}


	public void setJob(Job job) {
		this.job = job;
	}


	public WorkArea getWorkAreaObj() {
		return workAreaObj;
	}


	public void setWorkAreaObj(WorkArea workAreaObj) {
		this.workAreaObj = workAreaObj;
	}


	public Department getDepartmentObj() {
		return departmentObj;
	}


	public void setDepartmentObj(Department departmentObj) {
		this.departmentObj = departmentObj;
	}

    public Long getWorkArea() {
		return workArea;
	}


	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

    public String getPrincipalId() {
        return principalId;
    }


    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }


    public Long getJobNumber() {
		return jobNumber;
	}


	public void setJobNumber(Long jobNumber) {
		this.jobNumber = jobNumber;
	}


	public String getUserPrincipalId() {
        return userPrincipalId;
    }


    public void setUserPrincipalId(String userPrincipalId) {
        this.userPrincipalId = userPrincipalId;
    }

	public String getTkDeptLunchRuleId() {
		return tkDeptLunchRuleId;
	}


	public void setTkDeptLunchRuleId(String tkDeptLunchRuleId) {
		this.tkDeptLunchRuleId = tkDeptLunchRuleId;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public BigDecimal getDeductionMins() {
		return deductionMins;
	}


	public void setDeductionMins(BigDecimal deductionMins) {
		this.deductionMins = deductionMins;
	}


	public BigDecimal getShiftHours() {
		return shiftHours;
	}


	public void setShiftHours(BigDecimal shiftHours) {
		this.shiftHours = shiftHours;
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

	public String getHrJobId() {
		return hrJobId;
	}

	public void setHrJobId(String hrJobId) {
		this.hrJobId = hrJobId;
	}

	@Override
	public String getUniqueKey() {
		return getDept() + "_" + getWorkArea() != null ? getWorkArea().toString() : "" + "_" + 
				getPrincipalId() + "_" + getJobNumber() != null ? getJobNumber().toString() : "";
	}

	@Override
	public String getId() {
		return getTkDeptLunchRuleId();
	}

	@Override
	public void setId(String id) {
		setTkDeptLunchRuleId(id);
	}

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

}
