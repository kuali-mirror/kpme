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
package org.kuali.kpme.pm.positionResponsibilityOption;

import org.kuali.kpme.core.bo.HrBusinessObject;

public class PositionResponsibilityOption extends HrBusinessObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5054782543015429750L;
	
	private String prOptionId;
	private String prOptionName;
	private String prDescription;
	
	
	public String getPrOptionId() {
		return prOptionId;
	}

	public void setPrOptionId(String prOptionId) {
		this.prOptionId = prOptionId;
	}

	public String getPrOptionName() {
		return prOptionName;
	}

	public void setPrOptionName(String prOptionName) {
		this.prOptionName = prOptionName;
	}

	public String getPrDescription() {
		return prDescription;
	}

	public void setPrDescription(String prDescription) {
		this.prDescription = prDescription;
	}

	@Override
	public String getId() {
		return this.getPrOptionId();
	}

	@Override
	public void setId(String id) {
		this.setPrOptionId(id);
		
	}

	@Override
	protected String getUniqueKey() {
		return this.getPrOptionName();
	}

}
