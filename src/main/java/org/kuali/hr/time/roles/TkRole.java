package org.kuali.hr.time.roles;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.kfs.coa.businessobject.Chart;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;

public class TkRole extends HrBusinessObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long hrRolesId;
	private String principalId;
	private String roleName;
	private String userPrincipalId;
	private Long workArea;
	private String department;
    private String chart;
	private Long hrDeptId;
	private Long tkWorkAreaId;
	private Long positionNumber;

    /**
     * These objects are used by Lookups to provide links on the maintenance
     * page. They are not necessarily going to be populated.
     */
	private Person person;
    private Department departmentObj;
    private WorkArea workAreaObj;
    private Chart chartObj;
    private Position positionObj;

    public Chart getChartObj() {
        return chartObj;
    }

    public void setChartObj(Chart chartObj) {
        this.chartObj = chartObj;
    }

    public Department getDepartmentObj() {
        return departmentObj;
    }

    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }

    public WorkArea getWorkAreaObj() {
        return workAreaObj;
    }

    public void setWorkAreaObj(WorkArea workAreaObj) {
        this.workAreaObj = workAreaObj;
    }

    public Long getHrRolesId() {
		return hrRolesId;
	}
	public void setHrRolesId(Long hrRolesId) {
		this.hrRolesId = hrRolesId;
	}
	public String getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
        setPerson(KIMServiceLocator.getPersonService().getPerson(this.principalId));
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserPrincipalId() {
		return userPrincipalId;
	}
	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
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
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	@SuppressWarnings("rawtypes")
	@Override
	protected LinkedHashMap toStringMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getHrDeptId() {
		return hrDeptId;
	}
	public void setHrDeptId(Long hrDeptId) {
		this.hrDeptId = hrDeptId;
	}
	public Long getTkWorkAreaId() {
		return tkWorkAreaId;
	}
	public void setTkWorkAreaId(Long tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    /**
     * This method supports maintenance and lookup pages.
     */
    public String getUserName() {
        if (person == null) {
            person = KIMServiceLocator.getPersonService().getPerson(this.principalId);
        }

        return (person != null) ? person.getName() : "";
    }

	public void setPositionNumber(Long positionNumber) {
		this.positionNumber = positionNumber;
	}

	public Long getPositionNumber() {
		return positionNumber;
	}

	public void setPositionObj(Position positionObj) {
		this.positionObj = positionObj;
	}

	public Position getPositionObj() {
		return positionObj;
	}

	@Override
	protected String getUniqueKey() {
		return principalId + "_" + positionNumber != null ? positionNumber.toString() : "" +"_"+
				roleName + "_" + workArea != null ? workArea.toString() : "" + "_" +
				department + "_" + chart;
	}

	@Override
	public Long getId() {
		return getHrRolesId();
	}

	@Override
	public void setId(Long id) {
		setHrRolesId(id);
	}
}