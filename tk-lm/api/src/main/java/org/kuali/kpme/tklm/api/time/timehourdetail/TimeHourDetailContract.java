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
package org.kuali.kpme.tklm.api.time.timehourdetail;

import java.math.BigDecimal;

import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>TimeHourDetailContract interface</p>
 *
 */
public interface TimeHourDetailContract extends PersistableBusinessObject {

	/**
	 * The EarnCode name associated with the TimeHourDetail
	 * 
	 * <p>
	 * earnCode for a TimeHourDetail
	 * <p>
	 * 
	 * @return earnCode for TimeHourDetail
	 */
	public String getEarnCode();
	
	/**
	 * The hours associated with the TimeHourDetail
	 * 
	 * <p>
	 * hours for a TimeHourDetail
	 * <p>
	 * 
	 * @return hours for TimeHourDetail
	 */
	public BigDecimal getHours();
	
	/**
	 * The amount associated with the TimeHourDetail
	 * 
	 * <p>
	 * amount for a TimeHourDetail
	 * <p>
	 * 
	 * @return amount for TimeHourDetail
	 */
	public BigDecimal getAmount();

	/**
	 * The id of the TimeBlock object associated with the TimeHourDetail
	 * 
	 * <p>
	 * tkTimeBlockId for a TimeHourDetail
	 * <p>
	 * 
	 * @return tkTimeBlockId for TimeHourDetail
	 */
	public String getTkTimeBlockId();

	/**
	 * The primary key of a TimeHourDetail entry saved in a database
	 * 
	 * <p>
	 * tkTimeHourDetailId of a TimeHourDetail
	 * <p>
	 * 
	 * @return tkTimeHourDetailId for TimeHourDetail
	 */
	public String getTkTimeHourDetailId();

}
