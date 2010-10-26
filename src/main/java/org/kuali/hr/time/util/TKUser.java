package org.kuali.hr.time.util;

import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.rice.kim.bo.Person;

/**
 * This class houses the concept of a user in the Timekeeping system.  It 
 * is essentially a lightweight wrapper around multiple KIM Person objects.
 * 
 * One for the actual ACTUAL person
 * One for the user the ACTUAL person is backdooring as.
 * One for the user the ACTUAL person is targeting.
 * 
 */
public class TKUser {
	private Person actualPerson = null;
	private Person backdoorPerson = null;
	private Person targetPerson = null;
	
	private TkUserRoles actualPersonRoles = null;
	private TkUserRoles backdoorPersonRoles = null;

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

	/**
	 * @return The principal ID of the User.  Precedence order:
	 * 
	 *  Target User > Back Door User > Actual User
	 */
	public String getPrincipalId() {
		if (targetPerson != null)
			return targetPerson.getPrincipalId();
		
		if (backdoorPerson != null)
			return backdoorPerson.getPrincipalId();
		
		return actualPerson.getPrincipalId();
	}

	/**
	 * @return The principal name of the User.
	 * 
	 * Target User > Back Door User > Actual User
	 */
	public String getPrincipalName() {
		if (targetPerson != null)
			return targetPerson.getPrincipalName();
		
		if (backdoorPerson != null)
			return backdoorPerson.getPrincipalName();
		
		return actualPerson.getPrincipalName();		
	}
	
	public void clearBackdoorUser() {
		this.backdoorPerson = null;
		this.backdoorPersonRoles = null;
	}
	
	public void clearTargetUser() {
		this.targetPerson = null;
	}

	public Person getTargetPerson() {
		return targetPerson;
	}

	public void setTargetPerson(Person targetPerson) {
		this.targetPerson = targetPerson;
	}

	public TkUserRoles getActualPersonRoles() {
		return actualPersonRoles;
	}

	public void setActualPersonRoles(TkUserRoles actualPersonRoles) {
		this.actualPersonRoles = actualPersonRoles;
	}

	public TkUserRoles getBackdoorPersonRoles() {
		return backdoorPersonRoles;
	}

	public void setBackdoorPersonRoles(TkUserRoles backdoorPersonRoles) {
		this.backdoorPersonRoles = backdoorPersonRoles;
	}

	public boolean isSystemAdmin() {
		boolean admin = false;

		if (getBackdoorPersonRoles() != null)
			admin = getBackdoorPersonRoles().isSystemAdmin();
		else if (getActualPersonRoles() != null)
			admin = getActualPersonRoles().isSystemAdmin();
		
		return admin;
	}
	
	/**
	 * TODO: This should probably consider the TARGET employee.
	 * @param assignment
	 * @return
	 */
	public boolean isTkEmployee(Long assignmentId) {
		boolean employee = false;

		if (getBackdoorPersonRoles() != null)
			employee = getBackdoorPersonRoles().isTkEmployee(assignmentId);
		else if (getActualPersonRoles() != null)
			employee = getActualPersonRoles().isTkEmployee(assignmentId);
		
		return employee;		
	}
	
	public boolean isTkApprover(Long workArea) {
		boolean approver = false;

		if (getBackdoorPersonRoles() != null)
			approver = getBackdoorPersonRoles().isApprover(workArea);
		else if (getActualPersonRoles() != null)
			approver = getActualPersonRoles().isApprover(workArea);
		
		return approver;			
	}
	
	public boolean isOrgAdmin(String department) {
		boolean orgadmin = false;

		if (getBackdoorPersonRoles() != null)
			orgadmin = getBackdoorPersonRoles().isOrgAdmin(department);
		else if (getActualPersonRoles() != null)
			orgadmin = getActualPersonRoles().isOrgAdmin(department);
		
		return orgadmin;					
	}
}
