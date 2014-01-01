/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.pm.positionresponsibility;

import java.math.BigDecimal;

import org.kuali.kpme.pm.api.positionresponsibility.PositionResponsibilityContract;
import org.kuali.rice.location.impl.campus.CampusBo;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class PositionResponsibility extends PersistableBusinessObjectBase implements PositionResponsibilityContract {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1631206606795253956L;
	
	private String positionResponsibilityId;
	private String institution;
	private String location;
	private String positionResponsibilityOption;
	private BigDecimal percentTime;
	private String hrPositionId;
	private CampusBo campusObj;

	public String getPositionResponsibilityId() {
		return positionResponsibilityId;
	}

	public void setPositionResponsibilityId(String positionResponsibilityId) {
		this.positionResponsibilityId = positionResponsibilityId;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getPositionResponsibilityOption() {
		return positionResponsibilityOption;
	}

	public void setPositionResponsibilityOption(String positionResponsibilityOption) {
		this.positionResponsibilityOption = positionResponsibilityOption;
	}

	
	public BigDecimal getPercentTime() {
		return percentTime;
	}

	public void setPercentTime(BigDecimal percentTime) {
		this.percentTime = percentTime;
	}
	
	public CampusBo getCampusObj() {
		return campusObj;
	}

	public void setCampusObj(CampusBo campusObj) {
		this.campusObj = campusObj;
	}

	public String getHrPositionId() {
		return hrPositionId;
	}

	public void setHrPositionId(String hrPositionId) {
		this.hrPositionId = hrPositionId;
	}

	public String getId() {
		return this.getPositionResponsibilityId();
	}

	public void setId(String id) {
		this.setPositionResponsibilityId(id);
		
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
}
