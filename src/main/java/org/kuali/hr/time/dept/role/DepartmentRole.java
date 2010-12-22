package org.kuali.hr.time.dept.role;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.hr.time.roles.TkRole;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class DepartmentRole extends PersistableBusinessObjectBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String department;
	private List<TkRole> lstDeptRoles = new ArrayList<TkRole>();

	@SuppressWarnings("unchecked")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartment() {
		return department;
	}

	public void setLstDeptRoles(List<TkRole> lstDeptRoles) {
		this.lstDeptRoles = lstDeptRoles;
	}

	public List<TkRole> getLstDeptRoles() {
		return lstDeptRoles;
	}

}
