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
package org.kuali.hr.time.roles;

import java.sql.Date;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.location.Location;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.kfs.coa.businessobject.Chart;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;

public class TkRole extends HrBusinessObject {

	private static final long serialVersionUID = -2123815189941339343L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "TkRole";

	private String hrRolesId;
	private String principalId;
	private String roleName;
	private String userPrincipalId;
	private Long workArea;
	private String department;
    private String chart;
	private Long hrDeptId;
	private String positionNumber;
	private Date expirationDate;

    /**
     * These objects are used by Lookups to provide links on the maintenance
     * page. They are not necessarily going to be populated.
     */
	private Person person;
    private Department departmentObj;
    private WorkArea workAreaObj;
    private Chart chartObj;
    private Position positionObj;
    
    private Location locationObj;

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
        if (departmentObj != null) {
            this.department = departmentObj.getDept();
            this.workAreaObj= null;
            this.workArea = null;
        }
    }

    public WorkArea getWorkAreaObj() {
        return workAreaObj;
    }

    public void setWorkAreaObj(WorkArea workAreaObj) {
        this.workAreaObj = workAreaObj;
        if (workAreaObj != null) {
            this.workArea = workAreaObj.getWorkArea();
            this.departmentObj = TkServiceLocator.getDepartmentService().getDepartment(workAreaObj.getDept(), TKUtils.getCurrentDate());
            this.department = workAreaObj.getDept();
        }
    }

    public String getHrRolesId() {
		return hrRolesId;
	}
    
	public void setHrRolesId(String hrRolesId) {
		this.hrRolesId = hrRolesId;
	}
	
	public String getPrincipalId() {
		return principalId;
	}
	
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
        setPerson(KimApiServiceLocator.getPersonService().getPerson(this.principalId));
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

	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public Long getHrDeptId() {
		return hrDeptId;
	}
	
	public void setHrDeptId(Long hrDeptId) {
		this.hrDeptId = hrDeptId;
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
            person = KimApiServiceLocator.getPersonService().getPerson(this.principalId);
        }

        return (person != null) ? person.getName() : "";
    }

	public void setPositionNumber(String positionNumber) {
		this.positionNumber = positionNumber;
	}

	public String getPositionNumber() {
		return positionNumber;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setPositionObj(Position positionObj) {
		this.positionObj = positionObj;
	}

	public Position getPositionObj() {
		return positionObj;
	}

	@Override
	public String getUniqueKey() {
		return principalId + "_" + positionNumber != null ? positionNumber.toString() : "" +"_"+
				roleName + "_" + workArea != null ? workArea.toString() : "" + "_" +
				department + "_" + chart;
	}

	@Override
	public String getId() {
		return getHrRolesId();
	}

	@Override
	public void setId(String id) {
		setHrRolesId(id);
	}

	public Location getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TkRole tkRole = (TkRole) o;

        if (chart != null ? !chart.equals(tkRole.chart) : tkRole.chart != null) return false;
        if (chartObj != null ? !chartObj.equals(tkRole.chartObj) : tkRole.chartObj != null) return false;
        if (department != null ? !department.equals(tkRole.department) : tkRole.department != null) return false;
        if (departmentObj != null ? !departmentObj.equals(tkRole.departmentObj) : tkRole.departmentObj != null)
            return false;
        if (expirationDate != null ? !expirationDate.equals(tkRole.expirationDate) : tkRole.expirationDate != null)
            return false;
        if (hrDeptId != null ? !hrDeptId.equals(tkRole.hrDeptId) : tkRole.hrDeptId != null) return false;
        if (!hrRolesId.equals(tkRole.hrRolesId)) return false;
        if (locationObj != null ? !locationObj.equals(tkRole.locationObj) : tkRole.locationObj != null) return false;
        if (person != null ? !person.equals(tkRole.person) : tkRole.person != null) return false;
        if (positionNumber != null ? !positionNumber.equals(tkRole.positionNumber) : tkRole.positionNumber != null)
            return false;
        if (positionObj != null ? !positionObj.equals(tkRole.positionObj) : tkRole.positionObj != null) return false;
        if (principalId != null ? !principalId.equals(tkRole.principalId) : tkRole.principalId != null) return false;
        if (roleName != null ? !roleName.equals(tkRole.roleName) : tkRole.roleName != null) return false;
        if (userPrincipalId != null ? !userPrincipalId.equals(tkRole.userPrincipalId) : tkRole.userPrincipalId != null)
            return false;
        if (workArea != null ? !workArea.equals(tkRole.workArea) : tkRole.workArea != null) return false;
        if (workAreaObj != null ? !workAreaObj.equals(tkRole.workAreaObj) : tkRole.workAreaObj != null) return false;

        return true;
    }
}