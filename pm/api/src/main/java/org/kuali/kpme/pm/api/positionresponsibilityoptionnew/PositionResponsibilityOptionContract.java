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
package org.kuali.kpme.pm.api.positionresponsibilityoptionnew;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;

/**
 * <p>PositionResponsibilityOptionContract interface</p>
 *
 */
public interface PositionResponsibilityOptionContract extends HrBusinessObjectContract {

    /**
     * The primary key for a PositionResponsibilityOption entry saved in the database
     *
     * <p>
     * prOptionId of a PositionResponsibilityOption.
     * <p>
     *
     * @return prOptionId for PositionResponsibilityOption
     */
	public String getPrOptionId();

    /**
     * The short text descriptive of the responsibility 
     *
     * <p>
     * prOptionName of a PositionResponsibilityOption.
     * <p>
     *
     * @return prOptionName for PositionResponsibilityOption
     */
	public String getPrOptionName();

    /**
     * The long description of the responsibility
     *
     * <p>
     * prDescription of a PositionResponsibilityOption.
     * <p>
     *
     * @return prDescription for PositionResponsibilityOption
     */
	public String getPrDescription();

}
