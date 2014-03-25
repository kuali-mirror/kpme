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
package org.kuali.kpme.pm.positionflag;

import org.kuali.kpme.pm.api.positionflag.PositionFlagContract;
import org.kuali.kpme.pm.position.PositionDerived;

import com.google.common.collect.ImmutableList;

public class PositionFlagBo extends PositionDerived implements PositionFlagContract {
	
	private static final String POSITION_FLAG_NAME = "positionFlagName";
	private static final String CATEGORY = "category";

	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
												    .add(CATEGORY)
												    .add(POSITION_FLAG_NAME)
												    .build();
	
	private static final long serialVersionUID = 1L;
	
	private String pmPositionFlagId;
	private String category;
	private String positionFlagName;
	
//	@Override
//	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
//		return new ImmutableMap.Builder<String, Object>()
//				.put(CATEGORY, this.getCategory())
//				.put(POSITION_FLAG_NAME, this.getPositionFlagName())
//				.build();
//	}
	
	public String getPmPositionFlagId() {
		return pmPositionFlagId;
	}
	public void setPmPositionFlagId(String pmPositionFlagId) {
		this.pmPositionFlagId = pmPositionFlagId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPositionFlagName() {
		return positionFlagName;
	}
	public void setPositionFlagName(String positionFlagName) {
		this.positionFlagName = positionFlagName;
	}
	
//	@Override
//	public String getId() {
//		return this.getPmPositionFlagId();
//	}
//	@Override
//	public void setId(String id) {
//		this.setPmPositionFlagId(id);
//		
//	}
//	@Override
//	protected String getUniqueKey() {
//		return this.getCategory() + "_" + this.getPositionFlagName();
//	}
	
}
