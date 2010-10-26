package org.kuali.hr.time.util;

import org.kuali.rice.kim.bo.Person;

/**
 * This class houses the concept of a user in the Timekeeping system.  It 
 * is essentially a lightweight wrapper around multiple KIM Person objects.
 * 
 * One for the actual logged in person
 * One for the user the logged in person is backdooring as.
 * 
 * TODO: Potentially one for the "target" user if we want to wrap security
 * in this object.
 */
public class TKUser {

	private Person actualPerson;
	private Person backdoorPerson;

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
	 * @return The principal ID of the User: Backdoor has precedence. 
	 */
	public String getPrincipalId() {
		return (backdoorPerson != null) ? backdoorPerson.getPrincipalId() : actualPerson.getPrincipalId();
	}

	/**
	 * @return The principal name of the User: Backdoor has precedence.
	 */
	public String getPrincipalName() {
		return (backdoorPerson != null) ? backdoorPerson.getPrincipalName() : actualPerson.getPrincipalName();
	}
	
	public void clearBackdoorUser() {
		this.backdoorPerson = null;
	}

}
