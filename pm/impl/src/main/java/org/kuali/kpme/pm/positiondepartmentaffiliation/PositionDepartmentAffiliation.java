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
package org.kuali.kpme.pm.positiondepartmentaffiliation;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.pm.api.positiondepartmentaffiliation.PositionDepartmentAffiliationContract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
public class PositionDepartmentAffiliation extends HrBusinessObject implements PositionDepartmentAffiliationContract {
	
	private static final String POSITION_DEPT_AFFL_TYPE = "positionDeptAfflType";

	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
		    .add(POSITION_DEPT_AFFL_TYPE)
		    .build();
	
	private static final long serialVersionUID = 1L;
	
	private String pmPositionDeptAfflId;
	private String positionDeptAfflType;
	private boolean primaryIndicator;
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(POSITION_DEPT_AFFL_TYPE, this.getPositionDeptAfflType())
				.build();
	}
	

	@Override
	public String getId() {
		return this.getPmPositionDeptAfflId();
	}

	@Override
	public void setId(String id) {
		setPmPositionDeptAfflId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getPositionDeptAfflType();
	}

	/**
	 * @return the pmPositionDeptAfflId
	 */
	public String getPmPositionDeptAfflId() {
		return pmPositionDeptAfflId;
	}

	/**
	 * @param pmPositionDeptAfflId the pmPositionDeptAfflId to set
	 */
	public void setPmPositionDeptAfflId(String pmPositionDeptAfflId) {
		this.pmPositionDeptAfflId = pmPositionDeptAfflId;
	}

	/**
	 * @return the positionDeptAfflType
	 */
	public String getPositionDeptAfflType() {
		return positionDeptAfflType;
	}

	/**
	 * @param positionDeptAfflType the positionDeptAfflType to set
	 */
	public void setPositionDeptAfflType(String positionDeptAfflType) {
		this.positionDeptAfflType = positionDeptAfflType;
	}

	/**
	 * @return the primaryIndicator
	 */
	public boolean isPrimaryIndicator() {
		return primaryIndicator;
	}

	/**
	 * @param primaryIndicator the primaryIndicator to set
	 */
	public void setPrimaryIndicator(boolean primaryIndicator) {
		this.primaryIndicator = primaryIndicator;
	}



}