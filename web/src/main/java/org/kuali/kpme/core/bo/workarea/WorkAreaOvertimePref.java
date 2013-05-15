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
package org.kuali.kpme.core.bo.workarea;

import org.kuali.rice.krad.bo.PersistableBusinessObjectBase;

public class WorkAreaOvertimePref extends PersistableBusinessObjectBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long tkWorkAreaOvtPrefId;
	private Long tkWorkAreaId;
	private String payType;
	private String overtimePreference;

	public Long getTkWorkAreaId() {
		return tkWorkAreaId;
	}

	public void setTkWorkAreaId(Long tkWorkAreaId) {
		this.tkWorkAreaId = tkWorkAreaId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOvertimePreference() {
		return overtimePreference;
	}

	public void setOvertimePreference(String overtimePreference) {
		this.overtimePreference = overtimePreference;
	}


	public Long getTkWorkAreaOvtPrefId() {
		return tkWorkAreaOvtPrefId;
	}


	public void setTkWorkAreaOvtPrefId(Long tkWorkAreaOvtPrefId) {
		this.tkWorkAreaOvtPrefId = tkWorkAreaOvtPrefId;
	}

}
