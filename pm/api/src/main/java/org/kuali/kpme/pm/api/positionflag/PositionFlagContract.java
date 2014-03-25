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
package org.kuali.kpme.pm.api.positionflag;

import org.kuali.kpme.pm.api.position.PositionDerivedContract;

/**
 * <p>PositionFlagContract interface</p>
 *
 */
public interface PositionFlagContract extends PositionDerivedContract {

	/**
	 * THe Primary Key that a PositionFlag record will be saved to a database with
	 * 
	 * <p>
	 * pmPositionFlagId of a Flag.
	 * <p>
	 * 
	 * @return pmPositionFlagId for Flag
	 */
	public String getPmPositionFlagId();

	/**
	 * A grouping of PositionFlags
	 * 
	 * <p>
	 * category of a PositionFlag, user will select a category to display all the PositionFlags for that category
	 * <p>
	 * 
	 * @return category for PositionFlag
	 */
	public String getCategory();

	/**
	 * The name of the PositionFlag
	 * 
	 * <p>
	 * A descriptive name for the PositionFlag.
	 * <p>
	 * 
	 * @return positionFlagName for PositionFlag
	 */
	public String getPositionFlagName();

}
