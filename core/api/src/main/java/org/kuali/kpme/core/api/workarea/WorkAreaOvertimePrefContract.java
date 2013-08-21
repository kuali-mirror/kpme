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
package org.kuali.kpme.core.api.workarea;

import org.kuali.rice.krad.bo.PersistableBusinessObject;

/**
 * <p>WorkAreaOvertimePrefContract interface</p>
 *
 */
public interface WorkAreaOvertimePrefContract extends PersistableBusinessObject {

	/**
	 * The tkWorkAreaId the WorkAreaOvertimePref is associated with
	 * 
	 * <p>
	 * tkWorkAreaId of a WorkAreaOvertimePref
	 * </p>
	 * 
	 * @return tkWorkAreaId for WorkAreaOvertimePref
	 */
	public Long getTkWorkAreaId();

	/**
	 * The payType the WorkAreaOvertimePref is associated with
	 * 
	 * <p>
	 * payType of a WorkAreaOvertimePref
	 * </p>
	 * 
	 * @return payType for WorkAreaOvertimePref
	 */
	public String getPayType();
	
	/**
	 * The overtimePreference the WorkAreaOvertimePref is associated with
	 * 
	 * <p>
	 * overtimePreference of a WorkAreaOvertimePref
	 * </p>
	 * 
	 * @return overtimePreference for WorkAreaOvertimePref
	 */
	public String getOvertimePreference();

	/**
	 * The primary key of a WorkAreaOvertimePref entry saved in a database
	 * 
	 * <p>
	 * tkWorkAreaOvtPrefId of a WorkAreaOvertimePref
	 * <p>
	 * 
	 * @return tkWorkAreaOvtPrefId for WorkAreaOvertimePref
	 */
	public Long getTkWorkAreaOvtPrefId();
	
}
