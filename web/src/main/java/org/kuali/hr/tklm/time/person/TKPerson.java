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
package org.kuali.hr.tklm.time.person;

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
