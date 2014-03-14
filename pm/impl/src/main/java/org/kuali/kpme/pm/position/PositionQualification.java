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
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.kuali.kpme.pm.api.position.PositionQualificationContract;
import org.kuali.kpme.pm.api.pstnqlfrtype.PstnQlfrTypeContract;
import org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class PositionQualification extends PersistableBusinessObjectBase implements PositionQualificationContract {
	private static final long serialVersionUID = 1L;
	
	private String pmQualificationId;
	private String qualificationType;
	private String typeValue;		// for GUI only
	private String qualifier;
	private String qualificationValue;
	private String hrPositionId;
		
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

	public String getHrPositionId() {
		return hrPositionId;
	}

	public void setHrPositionId(String hrPositionId) {
		this.hrPositionId = hrPositionId;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        PositionQualification rhs = (PositionQualification)obj;
        return new EqualsBuilder()
                .append(pmQualificationId, rhs.getPmQualificationId())
                .append(qualificationType, rhs.getQualificationType())
                .append(qualifier, rhs.getQualifier())
                .append(qualificationValue, rhs.getQualificationValue())
                .append(hrPositionId, rhs.getHrPositionId())
                .isEquals();

    }

}
