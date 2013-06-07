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
package org.kuali.kpme.core.department;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.kfs.coa.businessobject.Chart;
import org.kuali.kpme.core.kfs.coa.businessobject.Organization;
import org.kuali.kpme.core.location.Location;
import org.kuali.kpme.core.role.department.DepartmentPrincipalRoleMemberBo;
import org.kuali.kpme.core.util.HrConstants;

import com.google.common.collect.ImmutableList;

public class Department extends HrBusinessObject {

	private static final long serialVersionUID = 5476378484272246487L;

	public static final String CACHE_NAME = HrConstants.CacheNamespace.NAMESPACE_PREFIX + "Department";
	//KPME-2273/1965 Primary Business Keys List.		
	public static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("dept")
            .add("location")
            .build();

    private String hrDeptId;
    private String dept;
    private String description;
    private String location;
    private String chart;
    private String org;
    private String history;

    private Location locationObj;
    private Chart chartObj;
    private Organization orgObj;
    
    @Transient
    private List<DepartmentPrincipalRoleMemberBo> roleMembers = new ArrayList<DepartmentPrincipalRoleMemberBo>();
    
    @Transient
    private List<DepartmentPrincipalRoleMemberBo> inactiveRoleMembers = new ArrayList<DepartmentPrincipalRoleMemberBo>();
    
	@Override
	public String getUniqueKey() {
		return getDept() + "_" + getOrg() + "_" + getChart();
	}
    
	@Override
	public String getId() {
		return getHrDeptId();
	}

	@Override
	public void setId(String id) {
		setHrDeptId(id);
	}
	
	public String getHrDeptId() {
		return hrDeptId;
	}

	public void setHrDeptId(String hrDeptId) {
		this.hrDeptId = hrDeptId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
    
	public Location getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}

	public Chart getChartObj() {
		return chartObj;
	}

	public void setChartObj(Chart chartObj) {
		this.chartObj = chartObj;
	}

	public Organization getOrgObj() {
		return orgObj;
	}

	public void setOrgObj(Organization orgObj) {
		this.orgObj = orgObj;
	}

	public List<DepartmentPrincipalRoleMemberBo> getRoleMembers() {
		return roleMembers;
	}
	
	public void addRoleMember(DepartmentPrincipalRoleMemberBo roleMemberBo) {
		roleMembers.add(roleMemberBo);
	}
	
	public void removeRoleMember(DepartmentPrincipalRoleMemberBo roleMemberBo) {
		roleMembers.remove(roleMemberBo);
	}
	
	public void setRoleMembers(List<DepartmentPrincipalRoleMemberBo> roleMembers) {
		this.roleMembers = roleMembers;
	}

	public List<DepartmentPrincipalRoleMemberBo> getInactiveRoleMembers() {
		return inactiveRoleMembers;
	}
	
	public void addInactiveRoleMember(DepartmentPrincipalRoleMemberBo inactiveRoleMemberBo) {
		inactiveRoleMembers.add(inactiveRoleMemberBo);
	}
	
	public void removeInactiveRoleMember(DepartmentPrincipalRoleMemberBo inactiveRoleMemberBo) {
		inactiveRoleMembers.remove(inactiveRoleMemberBo);
	}

	public void setInactiveRoleMembers(List<DepartmentPrincipalRoleMemberBo> inactiveRoleMembers) {
		this.inactiveRoleMembers = inactiveRoleMembers;
	}
	
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}