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
package org.kuali.kpme.pm.api.classification.flag;

import org.kuali.kpme.pm.api.flag.FlagContract;

/**
 * <p>ClassificationFlagContract interface</p>
 *
 */
public interface ClassificationFlagContract extends FlagContract {
	
	/**
	 * THe Position class that the ClassificationFlag is associated with
	 * 
	 * <p>
	 * pmPositionClassId of a ClassificationFlag.
	 * <p>
	 * 
	 * @return pmPositionClassId for ClassificationFlag
	 */
	public String getPmPositionClassId();

}
