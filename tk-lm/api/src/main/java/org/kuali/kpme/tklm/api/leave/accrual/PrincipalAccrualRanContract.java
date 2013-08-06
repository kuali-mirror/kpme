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
package org.kuali.kpme.tklm.api.leave.accrual;

import java.util.Date;

import org.joda.time.DateTime;
import org.kuali.rice.core.api.mo.common.GloballyUnique;
import org.kuali.rice.core.api.mo.common.Versioned;

/**
 * <p>PrincipalAccrualRanContract interface</p>
 *
 */
public interface PrincipalAccrualRanContract extends Versioned, GloballyUnique {

	/**
	 * The principalId that a last accrual service ran for
	 * 
	 * <p>
	 * principalId of a PrincipalAccrualRan
	 * <p>
	 * 
	 * @return principalId for PrincipalAccrualRan
	 */
	public String getPrincipalId();
	
	/**
	 * The last time (Date) an accrual service ran
	 *  
	 * <p>
	 * lastRanTs of a PrincipalAccrualRan
	 * <p>
	 * 
	 * @return lastRanTs (Date) for PrincipalAccrualRan
	 */
	public Date getLastRanTs();
	
	/**
	 * The last time (DateTime) an accrual service ran
	 * 
	 * * <p>
	 * lastRanTs of a PrincipalAccrualRan
	 * <p>
	 * 
	 * @return lastRanTs (DateTime) for PrincipalAccrualRan
	 */
	public DateTime getLastRanDateTime();

}
