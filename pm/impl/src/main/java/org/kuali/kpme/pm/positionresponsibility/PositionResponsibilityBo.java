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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.kuali.kpme.pm.api.positionresponsibility.PositionResponsibilityContract;
import org.kuali.kpme.pm.position.PositionDerived;
import org.kuali.rice.location.api.campus.Campus;

import java.math.BigDecimal;

public class PositionResponsibilityBo extends PositionDerived implements PositionResponsibilityContract {

	private static final long serialVersionUID = -1631206606795253956L;
	
	private String positionResponsibilityId;
	private String positionResponsibilityOption;
	private BigDecimal percentTime;
	private Campus campusObj;

	public String getPositionResponsibilityId() {
		return positionResponsibilityId;
	}

	public void setPositionResponsibilityId(String positionResponsibilityId) {
		this.positionResponsibilityId = positionResponsibilityId;
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
	
	public Campus getCampusObj() {
		return campusObj;
	}

	public void setCampusObj(Campus campusObj) {
		this.campusObj = campusObj;
	}

	public String getId() {
		return this.getPositionResponsibilityId();
	}

	public void setId(String id) {
		this.setPositionResponsibilityId(id);
		
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        PositionResponsibilityBo rhs = (PositionResponsibilityBo)obj;
        return new EqualsBuilder()
                .append(positionResponsibilityId, rhs.getPositionResponsibilityId())
                .append(positionResponsibilityOption, rhs.getPositionResponsibilityOption())
                .append(percentTime, rhs.getPercentTime())
                .append(hrPositionId, rhs.getHrPositionId())
                .isEquals();

    }
	
}
