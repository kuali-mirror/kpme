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
package org.kuali.kpme.core.api.earncode.group;

import org.kuali.kpme.core.api.earncode.EarnCodeContract;
import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>EarnCodeGroupDefinitionContract interface</p>
 *
 */
public interface EarnCodeGroupDefinitionContract extends PersistableBusinessObject {

	/**
	 * The EarnCode name the EarnCodeGroupDefinition is associated with
	 * 
	 * <p>
	 * earnCode of an EarnCodeGroupDefinition
	 * </p>
	 * 
	 * @return earnCode for EarnCodeGroupDefinition
	 */
	public String getEarnCode();

	/**
	 * The primary key of an EarnCodeGroupDefinition entry saved in a database
	 * 
	 * <p>
	 * hrEarnCodeGroupDefId of an EarnCodeGroupDefinition
	 * <p>
	 * 
	 * @return hrEarnCodeGroupDefId for EarnCodeGroupDefinition
	 */
	public String getHrEarnCodeGroupDefId();
	
	/**
	 * The hrEarnCodeGroupId the EarnCodeGroupDefinition is associated with
	 * 
	 * <p>
	 * hrEarnCodeGroupId of an EarnCodeGroupDefinition
	 * </p>
	 * 
	 * @return hrEarnCodeGroupId for EarnCodeGroupDefinition
	 */
	public String getHrEarnCodeGroupId();

	/**
	 * The EarnCode object the EarnCodeGroupDefinition is associated with
	 * 
	 * <p>
	 * earnCodeObj of an EarnCodeGroupDefinition
	 * </p>
	 * 
	 * @return earnCodeObj for EarnCodeGroupDefinition
	 */
	public EarnCodeContract getEarnCodeObj();

	/**
	 * The EarnCode description the EarnCodeGroupDefinition is associated with
	 * 
	 * <p>
	 * earnCode.getDescription() of an EarnCodeGroupDefinition
	 * </p>
	 * 
	 * @return earnCode.getDescription() for EarnCodeGroupDefinition
	 */
	public String getEarnCodeDesc();

}
