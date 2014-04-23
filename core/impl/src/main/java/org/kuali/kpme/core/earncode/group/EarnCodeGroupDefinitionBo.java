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
package org.kuali.kpme.core.earncode.group;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.kpme.core.api.earncode.group.EarnCodeGroupDefinition;
import org.kuali.kpme.core.api.earncode.group.EarnCodeGroupDefinitionContract;
import org.kuali.kpme.core.earncode.EarnCodeBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class EarnCodeGroupDefinitionBo extends PersistableBusinessObjectBase implements EarnCodeGroupDefinitionContract {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8463674251885306591L;
	
	public static final ModelObjectUtils.Transformer<EarnCodeGroupDefinition, EarnCodeGroupDefinitionBo> toEarnCodeGroupDefinitionBo =
            new ModelObjectUtils.Transformer<EarnCodeGroupDefinition, EarnCodeGroupDefinitionBo>() {
                public EarnCodeGroupDefinitionBo transform(EarnCodeGroupDefinition input) {
                    return EarnCodeGroupDefinitionBo.from(input);
                };
            };

	private String hrEarnCodeGroupDefId;

	private String earnCode;

	private String hrEarnCodeGroupId;

	private String userPrincipalId;
	 
	private boolean active=true;
	
    private EarnCodeBo earnCodeObj;

	public String getEarnCode() {
		return earnCode;
	}

	public void setEarnCode(String earnCode) {
		this.earnCode = earnCode;
	}
	
	public String getHrEarnCodeGroupDefId() {
		return hrEarnCodeGroupDefId;
	}

	public void setHrEarnCodeGroupDefId(String hrEarnCodeGroupDefId) {
		this.hrEarnCodeGroupDefId = hrEarnCodeGroupDefId;
	}

	public String getHrEarnCodeGroupId() {
		return hrEarnCodeGroupId;
	}

	public void setHrEarnCodeGroupId(String hrEarnCodeGroupId) {
		this.hrEarnCodeGroupId = hrEarnCodeGroupId;
	}

	public EarnCodeBo getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCodeBo earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}
		
	// this is for the maintenance screen
	public String getEarnCodeDesc() {
		EarnCodeContract earnCode = HrServiceLocator.getEarnCodeService().getEarnCode(this.earnCode, LocalDate.now());
		
		if(earnCode != null && StringUtils.isNotBlank(earnCode.getDescription())) {
			return earnCode.getDescription();
		}
		return "";
	}

	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public String getId() {
		return hrEarnCodeGroupDefId;
	}

	public void setId(String id) {
		setHrEarnCodeGroupDefId(id);
	}
	
	public static EarnCodeGroupDefinitionBo from(EarnCodeGroupDefinition im) {
	    if (im == null) {
	        return null;
	    }
	    EarnCodeGroupDefinitionBo ecgd = new EarnCodeGroupDefinitionBo();
	    
	    ecgd.setHrEarnCodeGroupDefId(im.getHrEarnCodeGroupDefId());
	    ecgd.setEarnCode(im.getEarnCode());
	    ecgd.setHrEarnCodeGroupId(im.getHrEarnCodeGroupId());
	    ecgd.setActive(im.isActive());
	    ecgd.setUserPrincipalId(im.getUserPrincipalId());
	    ecgd.setVersionNumber(im.getVersionNumber());
	    ecgd.setObjectId(im.getObjectId());
	    return ecgd;
	} 
	
	public static EarnCodeGroupDefinition to(EarnCodeGroupDefinitionBo bo) {
	    if (bo == null) {
	        return null;
	    }
	    return EarnCodeGroupDefinition.Builder.create(bo).build();
	}

}