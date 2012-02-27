package org.kuali.hr.time.person;

import java.io.Serializable;

public class TKPerson implements Comparable<TKPerson>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String principalId;
	private String firstName;
    private String lastName;
    private String principalName;
    
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
    
    public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	 public int compareTo(TKPerson person) {
	        return principalName.compareToIgnoreCase(person.getPrincipalName());
	    }
}
