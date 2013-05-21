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
package org.kuali.kpme.pm.positionresponsibility;

import java.math.BigDecimal;

import org.kuali.kpme.core.bo.HrBusinessObject;

public class PositionResponsibilty extends HrBusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1631206606795253956L;
	
	private String positionResponsibilityId;
	private String institution;
	private String campus;
	private String positionResponsibility;
	private BigDecimal percentage;
	
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

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getPositionResponsibility() {
		return positionResponsibility;
	}

	public void setPositionResponsibility(String positionResponsibility) {
		this.positionResponsibility = positionResponsibility;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	@Override
	public String getId() {
		return this.getPositionResponsibilityId();
	}
	@Override
	public void setId(String id) {
		this.setPositionResponsibilityId(id);
		
	}

	@Override
	protected String getUniqueKey() {
		return this.getInstitution() + "_" + this.getCampus();
	}

}
