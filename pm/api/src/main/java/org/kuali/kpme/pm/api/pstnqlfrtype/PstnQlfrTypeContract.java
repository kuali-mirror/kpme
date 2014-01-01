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
package org.kuali.kpme.pm.api.pstnqlfrtype;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

/**
 * <p>PstnQlfrTypeContract interface</p>
 *
 */
public interface PstnQlfrTypeContract extends HrBusinessObjectContract {

	/**
	 * The primary key for a PstnQlfrType entry saved in the database
	 * 
	 * <p>
	 * pmPstnQlfrTypeId of a PstnQlfrType
	 * <p>
	 * 
	 * @return PstnQlfrType for PstnQlfrType
	 */
	public String getPmPstnQlfrTypeId();

	/**
	 * The id of the PstnQlfrType
	 * 
	 * <p>
	 * code of a PstnQlfrType
	 * <p>
	 * 
	 * @return code for PstnQlfrType
	 */
	public String getCode();
	
	/**
	 * The shortened name for the qualifier type that will be used to select the qualifier
	 * 
	 * <p>
	 * type of a PstnQlfrType
	 * <p>
	 * 
	 * @return type for PstnQlfrType
	 */
	public String getType();
	
	/**
	 * The comma delimited list of qualification values that user can select 
	 * 
	 * <p>
	 * selectValues of a PstnQlfrType
	 * <p>
	 * 
	 * @return selectValues for PstnQlfrType
	 */
	public String getSelectValues();

	/**
	 * The type value associated with the PstnQlfrType
	 * 
	 * <p>
	 * typeValue of a PstnQlfrType
	 * <p>
	 * 
	 * @return typeValue for PstnQlfrType
	 */
	public String getTypeValue();
	
	/**
	 * The description of the PstnQlfrType
	 * 
	 * <p>
	 * descr of a PstnQlfrType
	 * <p>
	 * 
	 * @return descr for PstnQlfrType
	 */
	public String getDescr();

}
