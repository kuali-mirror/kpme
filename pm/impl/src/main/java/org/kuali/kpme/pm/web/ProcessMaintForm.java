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
package org.kuali.kpme.pm.web;

import org.kuali.kpme.core.web.KPMEForm;

import com.google.common.collect.ImmutableMap;

public class ProcessMaintForm extends KPMEForm {
	private static final long serialVersionUID = 1L;
	
	private String category;
	private String subCategory;
	private String reason;
	private String positionId;
	
	
	private ImmutableMap<String, String> categoryMap = new ImmutableMap.Builder<String, String>()
            .put("new", "New Position")
            .put("reorg", "Reorganization")
            .put("reclass", "Reclassification")
            .put("update", "Data Update")
            .put("status", "Change Status")
            .build();

	public ImmutableMap<String, String> getCategoryMap() {
		return categoryMap;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	

}
