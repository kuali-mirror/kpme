package org.kuali.hr.time.util;

import org.kuali.hr.time.roles.TkUserRoles;
import org.kuali.hr.time.user.pref.UserPreferences;
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
	
	private UserPreferences userPreference;

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
		return getCurrentPerson().getPrincipalId();
	}

	/**
	 * @return The principal name of the User.
	 * 
	 * Target User > Back Door User > Actual User
	 */
	public String getPrincipalName() {
		return getCurrentPerson().getPrincipalName();
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
		return getCurrentUserRoles().isSystemAdmin();
	}
	
	public boolean isSynchronousAspect() {
		return getCurrentUserRoles().hasSynchronousAspect();
	}
	
	/**
	 * TODO: This should probably consider the TARGET employee.
	 * @param assignment
	 * @return
	 */
	public boolean isTkEmployee() {
		return getCurrentUserRoles().isTkEmployee();
	}
	
	public boolean isTkApprover() {
		return getCurrentUserRoles().isApprover();
	}
	
	public boolean isOrgAdmin() {
		return getCurrentUserRoles().isOrgAdmin();
	}
	
	private TkUserRoles getCurrentUserRoles() {
		if (getBackdoorPersonRoles() != null) {
			return getBackdoorPersonRoles();
		} else {
			return getActualPersonRoles();	
		}
	}
	
	private Person getCurrentPerson() {
		if (targetPerson != null)
			return targetPerson;
		
		if (backdoorPerson != null)
			return backdoorPerson;
		
		return actualPerson;		
	}

	public UserPreferences getUserPreference() {
		return userPreference;
	}

	public void setUserPreference(UserPreferences userPreference) {
		this.userPreference = userPreference;
	}
}
