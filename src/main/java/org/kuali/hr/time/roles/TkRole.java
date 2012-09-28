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
package org.kuali.hr.time.roles;

import java.sql.Date;
import java.sql.Timestamp;

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
    public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "TkRole";
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String hrRolesId;
	private String principalId;
	private String roleName;
	private String userPrincipalId;
	private Long workArea;
	private String department;
    private String chart;
	private Long hrDeptId;
	private String tkWorkAreaId;
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
	public Long getHrDeptId() {
		return hrDeptId;
	}
	public void setHrDeptId(Long hrDeptId) {
		this.hrDeptId = hrDeptId;
	}
	public String getTkWorkAreaId() {
		return tkWorkAreaId;
	}
	public void setTkWorkAreaId(String tkWorkAreaId) {
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
}