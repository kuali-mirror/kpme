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
package org.kuali.kpme.core.api.earncode.group;

import java.util.List;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;

/**
 * <p>EarnCodeGroupContract interface</p>
 *
 */
public interface EarnCodeGroupContract extends KpmeEffectiveDataTransferObject {
		
	/**
	 * The list of EarnCodeGroupDefinition objects the EarnCodeGroup is associated with
	 * 
	 * <p>
	 * earnCodeGroups of a EarnCodeGroup
	 * </p>
	 * 
	 * @return earnCodeGroups for EarnCodeGroup
	 */
	public List<? extends EarnCodeGroupDefinitionContract> getEarnCodeGroups();

	/**
	 * The text which describes the purpose of this grouping of earn codes
	 * 
	 * <p>
	 * earnCodeGroup of an EarnCodeGroup
	 * <p>
	 * 
	 * @return earnCodeGroup for EarnCodeGroup
	 */
	public String getDescr();

	/**
	 * The warningText the EarnCodeGroup is associated with
	 * 
	 * <p>
	 * The warning text will appear on the top of the timesheet and/or leave calendar 
	 * when a time and/or leave block is created using an earn code from this Earn Code Group
	 * <p>
	 * 
	 * @return warningText for EarnCodeGroup
	 */
	public String getWarningText();

	/**
	 * The primary key of an EarnCodeGroup entry saved in a database
	 * 
	 * <p>
	 * hrEarnCodeGroupId of an EarnCodeGroup
	 * <p>
	 * 
	 * @return hrEarnCodeGroupId for EarnCodeGroup
	 */
	public String getHrEarnCodeGroupId();
	
	/**
	 * The text field used to identify the group
	 * 
	 * <p>
	 * earnCodeGroup of an EarnCodeGroup
	 * <p>
	 * 
	 * @return earnCodeGroup for EarnCodeGroup
	 */
	public String getEarnCodeGroup();

}
