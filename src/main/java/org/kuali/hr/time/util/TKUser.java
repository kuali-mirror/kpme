package org.kuali.hr.time.util;

import java.util.List;

import org.kuali.hr.job.Job;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.rice.kim.bo.Person;

public class TKUser {

    private Person actualPerson;
    private Person backdoorPerson;
    private List<Job> jobs;
    private List<Assignment> assignments;
//    private SecurityContext securityContext;

    public Person getActualPerson() {
        return actualPerson;
    }

    public void setActualPerson(Person person) {
        this.actualPerson = person;
    }

    public Person getBackdoorPerson() {
        return backdoorPerson;
    }

    public void setBackdoorPerson(Person backdoorPerson) {
        this.backdoorPerson = backdoorPerson;
    }

    public String getPrincipalId(){
	return actualPerson.getPrincipalId();
    }

    public String getPrincipalName(){
	return actualPerson.getPrincipalName();
    }

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

}
