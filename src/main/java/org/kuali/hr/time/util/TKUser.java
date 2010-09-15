package org.kuali.hr.time.util;

import org.kuali.rice.kim.bo.Person;

public class TKUser {

	private Person actualPerson;
	private Person backdoorPerson;


	// private SecurityContext securityContext;

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

	public String getPrincipalId() {
		return actualPerson.getPrincipalId();
	}

	public String getPrincipalName() {
		return actualPerson.getPrincipalName();
	}

}
