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
package org.kuali.kpme.core.departmentaffiliation;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.api.departmentaffiliation.DepartmentAffiliationContract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
public class DepartmentAffiliation extends HrBusinessObject implements DepartmentAffiliationContract {
	
	private static final String DEPT_AFFL_TYPE = "deptAfflType";

	public static final ImmutableList<String> BUSINESS_KEYS = new ImmutableList.Builder<String>()
		    .add(DEPT_AFFL_TYPE)
		    .build();
	
	private static final long serialVersionUID = 1L;
	
	private String hrDeptAfflId;
	private String deptAfflType;
	private boolean primaryIndicator;
	
	@Override
	public ImmutableMap<String, Object> getBusinessKeyValuesMap() {
		return new ImmutableMap.Builder<String, Object>()
				.put(DEPT_AFFL_TYPE, this.getDeptAfflType())
				.build();
	}
	

	@Override
	public String getId() {
		return this.getHrDeptAfflId();
	}

	@Override
	public void setId(String id) {
		setHrDeptAfflId(id);
	}

	@Override
	protected String getUniqueKey() {
		return getDeptAfflType();
	}

	/**
	 * @return the hrDeptAfflId
	 */
	public String getHrDeptAfflId() {
		return hrDeptAfflId;
	}

	/**
	 * @param hrDeptAfflId the hrDeptAfflId to set
	 */
	public void setHrDeptAfflId(String hrDeptAfflId) {
		this.hrDeptAfflId = hrDeptAfflId;
	}

	/**
	 * @return the deptAfflType
	 */
	public String getDeptAfflType() {
		return deptAfflType;
	}

	/**
	 * @param deptAfflType the deptAfflType to set
	 */
	public void setDeptAfflType(String deptAfflType) {
		this.deptAfflType = deptAfflType;
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