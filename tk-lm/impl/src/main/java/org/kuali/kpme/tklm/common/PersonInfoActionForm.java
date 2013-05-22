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
package org.kuali.kpme.tklm.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.bo.assignment.Assignment;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.web.KPMEForm;
import org.kuali.rice.kim.api.identity.Person;

public class PersonInfoActionForm extends KPMEForm {

    private static final long serialVersionUID = 2258434545502362548L;
    
    private String principalId;
    private String principalName;
    private String name;
    //KPME-1441
    private String serviceDate;
    private String totalFTE;
    
    private List<AccrualCategory> accrualCategories = new ArrayList<AccrualCategory>();
    private Map<String, BigDecimal> accrualCategoryRates = new HashMap<String, BigDecimal>();
    private Map<String, String> accrualEarnIntervals = new HashMap<String, String>();
    private Map<String, String> unitOfTime = new HashMap<String, String>();
    
    //KPME-1441
    private List<Job> jobs = new ArrayList<Job>();
    private Map<Long,List<Assignment>> jobNumberToListAssignments = new HashMap<Long,List<Assignment>>();
	
    private List<Long> approverWorkAreas = new ArrayList<Long>();
    private List<Long> reviewerWorkAreas = new ArrayList<Long>();
    private List<String> deptAdminDepts = new ArrayList<String>();
    private List<String> locationAdminDepts = new ArrayList<String>();
    private Boolean systemAdmin = Boolean.FALSE;
    private Boolean globalViewOnlyRoles = Boolean.FALSE;
    private List<String> deptViewOnlyDepts = new ArrayList<String>();
    
	private Map<Long,List<Person>> workAreaToApproverPerson = new HashMap<Long, List<Person>>();
    private Map<String,List<Person>> deptToDeptAdminPerson = new HashMap<String, List<Person>>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}
	

	public Map<Long, List<Assignment>> getJobNumberToListAssignments() {
		return jobNumberToListAssignments;
	}

	public void setJobNumberToListAssignments(
			Map<Long, List<Assignment>> jobNumberToListAssignments) {
		this.jobNumberToListAssignments = jobNumberToListAssignments;
	}

	public Map<Long,List<Person>> getWorkAreaToApproverPerson() {
		return workAreaToApproverPerson;
	}

	public void setWorkAreaToApproverPerson(Map<Long,List<Person>> workAreaToApproverPerson) {
		this.workAreaToApproverPerson = workAreaToApproverPerson;
	}

	public List<AccrualCategory> getAccrualCategories() {
		return accrualCategories;
	}

	public void setAccrualCategories(List<AccrualCategory> accrualCategories) {
		this.accrualCategories = accrualCategories;
	}
	
	public Map<String, BigDecimal> getAccrualCategoryRates() {
		return accrualCategoryRates;
	}
	
	public void setAccrualCategoryRates(Map<String, BigDecimal> accrualCategoryRates) {
		this.accrualCategoryRates = accrualCategoryRates;
	}
	
	public Map<String, String> getAccrualEarnIntervals() {
		return accrualEarnIntervals;
	}

	public void setAccrualEarnIntervals(Map<String, String> accrualEarnIntervals) {
		this.accrualEarnIntervals = accrualEarnIntervals;
	}

	public Map<String, String> getUnitOfTime() {
		return unitOfTime;
	}

	public void setUnitOfTime(Map<String, String> unitOfTime) {
		this.unitOfTime = unitOfTime;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public String getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public List<Long> getApproverWorkAreas() {
		return approverWorkAreas;
	}

	public void setApproverWorkAreas(List<Long> approverWorkAreas) {
		this.approverWorkAreas = approverWorkAreas;
	}

	public List<Long> getReviewerWorkAreas() {
		return reviewerWorkAreas;
	}

	public void setReviewerWorkAreas(List<Long> reviewerWorkAreas) {
		this.reviewerWorkAreas = reviewerWorkAreas;
	}

	public List<String> getDeptAdminDepts() {
		return deptAdminDepts;
	}

	public void setDeptAdminDepts(List<String> deptAdminDepts) {
		this.deptAdminDepts = deptAdminDepts;
	}

	public List<String> getLocationAdminDepts() {
		return locationAdminDepts;
	}

	public void setLocationAdminDepts(List<String> locationAdminDepts) {
		this.locationAdminDepts = locationAdminDepts;
	}

	public Boolean getSystemAdmin() {
		return systemAdmin;
	}

	public void setSystemAdmin(Boolean systemAdmin) {
		this.systemAdmin = systemAdmin;
	}

	public Boolean getGlobalViewOnlyRoles() {
		return globalViewOnlyRoles;
	}

	public void setGlobalViewOnlyRoles(Boolean globalViewOnlyRoles) {
		this.globalViewOnlyRoles = globalViewOnlyRoles;
	}

	public List<String> getDeptViewOnlyDepts() {
		return deptViewOnlyDepts;
	}

	public void setDeptViewOnlyDepts(List<String> deptViewOnlyDepts) {
		this.deptViewOnlyDepts = deptViewOnlyDepts;
	}

    public Map<String, List<Person>> getDeptToDeptAdminPerson() {
        return deptToDeptAdminPerson;
    }

    public void setDeptToDeptAdminPerson(Map<String, List<Person>> deptToDeptAdminPerson) {
        this.deptToDeptAdminPerson = deptToDeptAdminPerson;
    }

	public String getTotalFTE() {
		return totalFTE;
	}

	public void setTotalFTE(String totalFTE) {
		this.totalFTE = totalFTE;
	} 
}
