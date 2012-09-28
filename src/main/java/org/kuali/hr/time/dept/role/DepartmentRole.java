/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.time.dept.role;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.time.roles.TkRole;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class DepartmentRole extends PersistableBusinessObjectBase{

	private static final long serialVersionUID = -1778963026651724761L;
	
	private String department;
	private List<TkRole> roles = new ArrayList<TkRole>();

	public List<TkRole> getRoles() {
		return roles;
	}

	public void setRoles(List<TkRole> roles) {
		this.roles = roles;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}