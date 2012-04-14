package org.kuali.hr.time.base.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.rice.kim.api.identity.Person;



public class PersonInfoActionForm extends TkForm {

    private static final long serialVersionUID = 2258434545502362548L;
    
    private String principalId;
    private String principalName;
    private String name;
    private List<Job> jobs = new ArrayList<Job>();
    private Map<Long,List<Assignment>> jobNumberToListAssignments = new HashMap<Long,List<Assignment>>();
	
    private List<Long> approverWorkAreas = new ArrayList<Long>();
    private List<Long> reviewerWorkAreas = new ArrayList<Long>();
    private List<String> deptAdminDepts = new ArrayList<String>();
    private List<String> locationAdminDepts = new ArrayList<String>();
    private Boolean systemAdmin = Boolean.FALSE;
    private Boolean globalViewOnlyRoles = Boolean.FALSE;
    private List<String> deptViewOnlyDepts = new ArrayList<String>();
    
    private Map<Long,List<TkRole>> workAreaToApprover = new HashMap<Long,List<TkRole>>();
	private Map<String,List<TkRole>> deptToOrgAdmin = new HashMap<String,List<TkRole>>();
	private Map<String,Person> principalIdToPerson = new HashMap<String,Person>();
	private Map<Long,List<Person>> workAreaToApproverPerson = new HashMap<Long, List<Person>>();
    private Map<String,List<Person>> deptToDeptAdminPerson = new HashMap<String, List<Person>>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Long, List<Assignment>> getJobNumberToListAssignments() {
		return jobNumberToListAssignments;
	}

	public void setJobNumberToListAssignments(
			Map<Long, List<Assignment>> jobNumberToListAssignments) {
		this.jobNumberToListAssignments = jobNumberToListAssignments;
	}

	public Map<Long, List<TkRole>> getWorkAreaToApprover() {
		return workAreaToApprover;
	}

	public void setWorkAreaToApprover(Map<Long, List<TkRole>> workAreaToApprover) {
		this.workAreaToApprover = workAreaToApprover;
	}

	public Map<String, List<TkRole>> getDeptToOrgAdmin() {
		return deptToOrgAdmin;
	}

	public void setDeptToOrgAdmin(Map<String, List<TkRole>> deptToOrgAdmin) {
		this.deptToOrgAdmin = deptToOrgAdmin;
	}

	public Map<String, Person> getPrincipalIdToPerson() {
		return principalIdToPerson;
	}

	public void setPrincipalIdToPerson(Map<String, Person> principalIdToPerson) {
		this.principalIdToPerson = principalIdToPerson;
	}

	public Map<Long,List<Person>> getWorkAreaToApproverPerson() {
		return workAreaToApproverPerson;
	}

	public void setWorkAreaToApproverPerson(Map<Long,List<Person>> workAreaToApproverPerson) {
		this.workAreaToApproverPerson = workAreaToApproverPerson;
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
}
