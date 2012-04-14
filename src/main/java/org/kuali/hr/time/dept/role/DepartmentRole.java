package org.kuali.hr.time.dept.role;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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