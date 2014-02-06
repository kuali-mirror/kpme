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
package org.kuali.kpme.core.api.position;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.util.HrApiConstants;

/**
 * <p>PositionBaseContract interface.</p>
 *
 */
public interface PositionBaseContract extends HrBusinessObjectContract {
	
	public static final String CACHE_NAME = HrApiConstants.CacheNamespace.NAMESPACE_PREFIX + "PositionBase";
	
	/**
	 * The Primary Key of a PositionBase entry saved in a database
	 * 
	 * <p>
	 * hrPositionId of a PositionBase
	 * <p>
	 * 
	 * @return hrPositionId for PositionBase
	 */
	public String getHrPositionId();

	/**
	 * The position number of a PositionBase
	 * 
	 * <p>
	 * positionNumber of a PositionBase
	 * <p>
	 * 
	 * @return positionNumber for PositionBase
	 */
	public String getPositionNumber();
	
	/**
	 * The description of a PositionBase
	 * 
	 * <p>
	 * description of a PositionBase
	 * <p>
	 * 
	 * @return description for PositionBase
	 */
	public String getDescription();
}
