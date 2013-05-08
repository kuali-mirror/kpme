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
package org.kuali.hr.time.department;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.location.Location;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.kfs.coa.businessobject.Chart;
import org.kuali.kfs.coa.businessobject.Organization;

public class Department extends HrBusinessObject {

	private static final long serialVersionUID = -7856741688212725536L;

	public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "Department";

    private String hrDeptId;
    private String dept;
    private String description;
    private String chart;
    private String org;
    private String location;
    private String history;

    private Chart chartObj;
    private Organization orgObj;
    private Location locationObj;
    
    private List<TkRole> roles = new LinkedList<TkRole>();
    private List<TkRole> inactiveRoles = new ArrayList<TkRole>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


	@Override
	public String getUniqueKey() {
		return getDept() + "_" + getOrg() + "_" + getChart() + getRoles().size();
	}

	@Override
	public String getId() {
		return getHrDeptId();
	}

	@Override
	public void setId(String id) {
		setHrDeptId(id);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Location getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}
	
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
