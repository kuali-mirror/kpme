package org.kuali.hr.time.roles;


import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
    
	private String roleName;
	private Long workArea;
	private String department;
	
    public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
            person = KIMServiceLocator.getPersonService().getPerson(this.principalId);
        }

        return (person != null) ? person.getName() : "";
    }

    @Override
    protected LinkedHashMap toStringMapper() {
        return null;
    }
}
