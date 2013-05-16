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
package org.kuali.kpme.pm.pstnqlfrtype;

import org.kuali.kpme.core.bo.HrBusinessObject;

public class PstnQlfrType extends HrBusinessObject {
	private static final long serialVersionUID = 1L;

	private String pmPstnQlfrTypeId;
	private String code;
	private String type;
	private String descr;
	private String typeValue;
//	private PriorityQueue<String> selectValues;
	private String selectValues;
	
	@Override
	public String getId() {
		return this.getPmPstnQlfrTypeId();
	}

	@Override
	public void setId(String id) {
		this.setPmPstnQlfrTypeId(id);
	}

	@Override
	protected String getUniqueKey() {
		return this.getCode() + "_" + this.getType();
	}

	public String getPmPstnQlfrTypeId() {
		return pmPstnQlfrTypeId;
	}

	public void setPmPstnQlfrTypeId(String pmPstnQlfrTypeId) {
		this.pmPstnQlfrTypeId = pmPstnQlfrTypeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSelectValues() {
		return selectValues;
	}

	public void setSelectValues(String selectValues) {
		this.selectValues = selectValues;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	
}
