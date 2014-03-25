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
package org.kuali.kpme.pm.classification.flag;

import java.util.List;

import org.kuali.kpme.pm.api.classification.flag.ClassificationFlagContract;

public class ClassificationFlag implements ClassificationFlagContract {
	
	
	private String pmPositionClassId;
	private String pmFlagId;
	private String category;
	//private String names;
	private List<String> names; // KPME-2360/2958
	
	public String getPmFlagId() {
		return pmFlagId;
	}
	public void setPmFlagId(String pmFlagId) {
		this.pmFlagId = pmFlagId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	
	public String getPmPositionClassId() {
		return pmPositionClassId;
	}
	public void setPmPositionClassId(String pmPositionClassId) {
		this.pmPositionClassId = pmPositionClassId;
	}
}
