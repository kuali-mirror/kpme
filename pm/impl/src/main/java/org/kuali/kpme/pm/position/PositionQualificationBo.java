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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.kuali.kpme.pm.api.position.PositionQualification;
import org.kuali.kpme.pm.api.position.PositionQualificationContract;
import org.kuali.kpme.pm.api.pstnqlfrtype.PstnQlfrTypeContract;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class PositionQualificationBo extends PositionDerived implements PositionQualificationContract {
	private static final long serialVersionUID = 1L;
	
	private String pmQualificationId;
	private String qualificationType;
	@SuppressWarnings("unused")
	private String typeValue;		// for GUI only
	private String qualifier;
	private String qualificationValue;
		
	public String getQualificationType() {
		return qualificationType;
	}

	public void setQualificationType(String qualificationType) {
		this.qualificationType = qualificationType;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public String getQualificationValue() {
		return qualificationValue;
	}

	public void setQualificationValue(String qualificationValue) {
		this.qualificationValue = qualificationValue;
	}

	public String getTypeValue() {
		if(StringUtils.isNotEmpty(this.getQualificationType())) {
			PstnQlfrTypeContract aTypeObj = PmServiceLocator.getPstnQlfrTypeService().getPstnQlfrTypeById(this.getQualificationType());
			if(aTypeObj != null) {
				return aTypeObj.getTypeValue();
			}
		}
		return "";
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getPmQualificationId() {
		return pmQualificationId;
	}

	public void setPmQualificationId(String pmQualificationId) {
		this.pmQualificationId = pmQualificationId;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        PositionQualificationBo rhs = (PositionQualificationBo)obj;
        return new EqualsBuilder()
                .append(pmQualificationId, rhs.getPmQualificationId())
                .append(qualificationType, rhs.getQualificationType())
                .append(qualifier, rhs.getQualifier())
                .append(qualificationValue, rhs.getQualificationValue())
                .append(hrPositionId, rhs.getHrPositionId())
                .isEquals();

    }
    

	public static PositionQualificationBo from(PositionQualification im) {

		if (im == null) {
			return null;
		}

		PositionQualificationBo positionQualificationBo = new PositionQualificationBo();

		positionQualificationBo.setQualifier(im.getQualifier());
		positionQualificationBo.setHrPositionId(im.getHrPositionId());
		positionQualificationBo.setObjectId(im.getObjectId());
		positionQualificationBo.setPmQualificationId(im.getPmQualificationId());
		positionQualificationBo.setQualificationType(im.getQualificationType());
		positionQualificationBo.setQualificationValue(im.getQualificationValue());
		positionQualificationBo.setTypeValue(im.getTypeValue());
		positionQualificationBo.setVersionNumber(im.getVersionNumber());
		
		
		return positionQualificationBo;

	}

	public static PositionQualification to(PositionQualificationBo bo) {
		if (bo == null) {
			return null;
		}
		return PositionQualification.Builder.create(bo).build();
	}
	
	public static final ModelObjectUtils.Transformer<PositionQualificationBo, PositionQualification> toImmutable = new ModelObjectUtils.Transformer<PositionQualificationBo, PositionQualification>() {
		public PositionQualification transform(PositionQualificationBo input) {
			return PositionQualificationBo.to(input);
		};
	};

	public static final ModelObjectUtils.Transformer<PositionQualification, PositionQualificationBo> toBo = new ModelObjectUtils.Transformer<PositionQualification, PositionQualificationBo>() {
		public PositionQualificationBo transform(PositionQualification input) {
			return PositionQualificationBo.from(input);
		};
	};

	@Override
	public String getId() {
		return this.getPmQualificationId();
	}

	@Override
	public void setId(String id) {
		this.setPmQualificationId(id);
	}

}
