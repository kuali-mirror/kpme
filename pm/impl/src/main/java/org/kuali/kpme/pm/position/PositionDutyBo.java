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
package org.kuali.kpme.pm.position;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.kuali.kpme.pm.api.position.PositionDuty;
import org.kuali.kpme.pm.api.position.PositionDutyContract;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class PositionDutyBo extends PositionDerived implements PositionDutyContract {
private static final long serialVersionUID = 1L;
	
	private String pmDutyId;
	private String name;
	private String description;
	private BigDecimal percentage;
	
	public String getPmDutyId() {
		return pmDutyId;
	}
	public void setPmDutyId(String pmDutyId) {
		this.pmDutyId = pmDutyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        PositionDutyBo rhs = (PositionDutyBo)obj;
        return new EqualsBuilder()
                .append(pmDutyId, rhs.getPmDutyId())
                .append(name, rhs.getName())
                .append(description, rhs.getDescription())
                .append(percentage, rhs.getPercentage())
                .append(hrPositionId, rhs.getHrPositionId())
                .isEquals();

    }
	@Override
	public String getId() {
		return this.getPmDutyId();
	}
	@Override
	public void setId(String id) {
		this.setPmDutyId(id);
	}
	
	    public static PositionDutyBo from(PositionDuty im) {
		
				if (im == null) {
					return null;
				}
		
				PositionDutyBo positionDutyBo = new PositionDutyBo();
		
				positionDutyBo.setDescription(im.getDescription());
				positionDutyBo.setHrPositionId(im.getHrPositionId());
				positionDutyBo.setName(im.getName());
				positionDutyBo.setObjectId(im.getObjectId());
				positionDutyBo.setPercentage(im.getPercentage());
				positionDutyBo.setPmDutyId(im.getPmDutyId());
				positionDutyBo.setVersionNumber(im.getVersionNumber());
				
				return positionDutyBo;
		
			}
		
			public static PositionDuty to(PositionDutyBo bo) {
				if (bo == null) {
					return null;
				}
				return PositionDuty.Builder.create(bo).build();
			}
			
			public static final ModelObjectUtils.Transformer<PositionDutyBo, PositionDuty> toImmutable = new ModelObjectUtils.Transformer<PositionDutyBo, PositionDuty>() {
				public PositionDuty transform(PositionDutyBo input) {
					return PositionDutyBo.to(input);
				};
			};
		
			public static final ModelObjectUtils.Transformer<PositionDuty, PositionDutyBo> toBo = new ModelObjectUtils.Transformer<PositionDuty, PositionDutyBo>() {
				public PositionDutyBo transform(PositionDuty input) {
					return PositionDutyBo.from(input);
				};
			};
		 
	
}
