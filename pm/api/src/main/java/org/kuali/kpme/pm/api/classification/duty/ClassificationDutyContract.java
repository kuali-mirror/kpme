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
package org.kuali.kpme.pm.api.classification.duty;

import java.math.BigDecimal;

import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>ClassificationDutyContract interface</p>
 *
 */
public interface ClassificationDutyContract extends PersistableBusinessObject {

	/**
	 * The Primary Key that a ClassificationDuty record will be saved to a database with
	 * 
	 * <p>
	 * pmDutyId of a ClassificationDuty.
	 * <p>
	 * 
	 * @return pmDutyId for ClassificationDuty
	 */
	public String getPmDutyId();

	/**
	 * The short name of the duty associated with the ClassificationDuty
	 * 
	 * <p>
	 * name of a ClassificationDuty.
	 * <p>
	 * 
	 * @return name for ClassificationDuty
	 */
	public String getName();
	
	/**
	 * The text area used for detailed description of the Duty
	 * 
	 * <p>
	 * description of a ClassificationDuty.
	 * <p>
	 * 
	 * @return description for ClassificationDuty
	 */
	public String getDescription();
	
	/**
	 * The percent of time performing the specified duty
	 * 
	 * <p>
	 * percentage of a ClassificationDuty.
	 * <p>
	 * 
	 * @return percentage for ClassificationDuty
	 */
	public BigDecimal getPercentage();
	
	/**
	 * The position class id associated with the ClassificationDuty
	 * 
	 * <p>
	 * pmPositionClassId of a ClassificationDuty.
	 * <p>
	 * 
	 * @return pmPositionClassId for ClassificationDuty
	 */
	public String getPmPositionClassId();

}
