package org.kuali.hr.time.base.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.rice.kim.bo.Person;



public class PersonInfoActionForm extends TkForm {

    private static final long serialVersionUID = 2258434545502362548L;

    private Map<Long,List<Assignment>> jobNumberToListAssignments = new HashMap<Long,List<Assignment>>();
	private Map<Long,List<TkRole>> workAreaToApprover = new HashMap<Long,List<TkRole>>();
	private Map<String,List<TkRole>> deptToOrgAdmin = new HashMap<String,List<TkRole>>();
	private Map<String,Person> principalIdToPerson = new HashMap<String,Person>();
	private Map<Long,List<Person>> workAreaToApproverPerson = new HashMap<Long, List<Person>>();

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
   
}
