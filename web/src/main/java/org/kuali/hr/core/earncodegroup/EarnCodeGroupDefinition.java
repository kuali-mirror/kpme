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
package org.kuali.hr.core.earncodegroup;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.hr.core.earncode.EarnCode;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;
import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class EarnCodeGroupDefinition extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8463674251885306591L;

	private String hrEarnCodeGroupDefId;

	private String earnCode;

	private String hrEarnCodeGroupId;

    private EarnCode earnCodeObj;

    // this is for the maintenance screen
    private String earnCodeDesc;

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

	public void setEarnCodeDesc(String earnCodeDesc) {
		this.earnCodeDesc = earnCodeDesc;
	}

	public EarnCode getEarnCodeObj() {
		return earnCodeObj;
	}

	public void setEarnCodeObj(EarnCode earnCodeObj) {
		this.earnCodeObj = earnCodeObj;
	}
	
	// this is for the maintenance screen
	public String getEarnCodeDesc() {
		EarnCode earnCode = TkServiceLocator.getEarnCodeService().getEarnCode(this.earnCode, LocalDate.now());
		
		if(earnCode != null && StringUtils.isNotBlank(earnCode.getDescription())) {
			return earnCode.getDescription();
		}
		return "";
	}
}