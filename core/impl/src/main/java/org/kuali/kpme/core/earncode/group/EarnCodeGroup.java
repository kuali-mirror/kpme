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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.api.earncode.group.EarnCodeGroupContract;
import org.kuali.kpme.core.bo.HrBusinessObject;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class EarnCodeGroup extends HrBusinessObject implements EarnCodeGroupContract {

	private static final String EARN_CODE_GROUP = "earnCodeGroup";

	//KPME-2273/1965 Primary Business Keys List.
	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
            .add(EARN_CODE_GROUP)
            .build();

	private static final long serialVersionUID = -3034933572755800531L;

	private String hrEarnCodeGroupId;

	private String earnCodeGroup;

	private String descr;

	private Boolean history;
	
	private Boolean showSummary;

	private List<EarnCodeGroupDefinition> earnCodeGroups = new ArrayList<EarnCodeGroupDefinition>();
	
	private String warningText;

	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(EARN_CODE_GROUP, this.getEarnCodeGroup())		
				.build();
	}
	

	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}

	public List<EarnCodeGroupDefinition> getEarnCodeGroups() {
		return earnCodeGroups;
	}

	public void setEarnCodeGroups(List<EarnCodeGroupDefinition> earnCodeGroups) {
		this.earnCodeGroups = earnCodeGroups;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDescr() {
		return descr;
	}

	
	public Boolean getShowSummary() {
		return showSummary;
	}

	public void setShowSummary(Boolean showSummary) {
		this.showSummary = showSummary;
	}

	@Override
	public String getUniqueKey() {
		return earnCodeGroup;
	}

	@Override
	public String getId() {
		return getHrEarnCodeGroupId();
	}

	@Override
	public void setId(String id) {
		setHrEarnCodeGroupId(id);
	}

	public String getWarningText() {
		return warningText;
	}

	public void setWarningText(String warningText) {
		this.warningText = warningText;
	}

	public String getHrEarnCodeGroupId() {
		return hrEarnCodeGroupId;
	}

	public void setHrEarnCodeGroupId(String hrEarnCodeGroupId) {
		this.hrEarnCodeGroupId = hrEarnCodeGroupId;
	}

	public String getEarnCodeGroup() {
		return earnCodeGroup;
	}

	public void setEarnCodeGroup(String earnCodeGroup) {
		this.earnCodeGroup = earnCodeGroup;
	}

}
