package org.kuali.hr.time.roles;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Transient;

import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class TkRoleGroup extends PersistableBusinessObjectBase {

    private static final long serialVersionUID = 1L;

    @Transient
    private List<TkRole> roles = new ArrayList<TkRole>();
    @Transient
    private List<TkRole> inactiveRoles = new ArrayList<TkRole>();
    @Transient
    private List<TkRole> positionRoles = new ArrayList<TkRole>();
    @Transient
    private List<TkRole> inactivePositionRoles = new ArrayList<TkRole>();
    @Transient
    private String principalId;
    @Transient
    private Person person;
    
    private WorkArea workAreaObj;
    private Department departmentObj;
    private TkRole tkRoleObj;
    
	private String roleName;
	private String principalName;
	private Long workArea;
	private String department;
	
	
    public WorkArea getWorkAreaObj() {
		return workAreaObj;
	}

	public void setWorkAreaObj(WorkArea workAreaObj) {
		this.workAreaObj = workAreaObj;
	}

	public Department getDepartmentObj() {
		return departmentObj;
	}

	public void setDepartmentObj(Department departmentObj) {
		this.departmentObj = departmentObj;
	}

	public TkRole getTkRoleObj() {
		return tkRoleObj;
	}

	public void setTkRoleObj(TkRole tkRoleObj) {
		this.tkRoleObj = tkRoleObj;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public Long getWorkArea() {
		return workArea;
	}

	public void setWorkArea(Long workArea) {
		this.workArea = workArea;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<TkRole> getRoles() {
        return roles;
    }

    public void setRoles(List<TkRole> roles) {
        this.roles = roles;
    }

    public List<TkRole> getInactiveRoles() {
		return inactiveRoles;
	}

	public void setInactiveRoles(List<TkRole> inactiveRoles) {
		this.inactiveRoles = inactiveRoles;
	}

	public List<TkRole> getPositionRoles() {
		return positionRoles;
	}

	public void setPositionRoles(List<TkRole> positionRoles) {
		this.positionRoles = positionRoles;
	}

	public List<TkRole> getInactivePositionRoles() {
		return inactivePositionRoles;
	}

	public void setInactivePositionRoles(List<TkRole> inactivePositionRoles) {
		this.inactivePositionRoles = inactivePositionRoles;
	}

	public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getUserName() {
        if (person == null) {
            person = KimApiServiceLocator.getPersonService().getPerson(this.principalId);
        }

        return (person != null) ? person.getName() : "";
    }
}
