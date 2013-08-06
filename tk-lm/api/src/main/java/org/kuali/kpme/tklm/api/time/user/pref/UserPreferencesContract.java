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
package org.kuali.kpme.tklm.api.time.user.pref;

import org.kuali.rice.core.api.mo.common.GloballyUnique;
import org.kuali.rice.core.api.mo.common.Versioned;

/**
 * <p>UserPreferencesContract interface</p>
 *
 */
public interface UserPreferencesContract extends Versioned, GloballyUnique {
	
	/**
	 * The system(server) timezone
	 * 
	 * <p>
	 * timezone of an UserPreferences
	 * </p>
	 * 
	 * @return system timezone if timezone is null
	 */
	public String getTimezone();

	/**
	 * The principalId associated with the UserPreferences
	 * 
	 * <p>
	 * principalId of an UserPreferences
	 * </p>
	 * 
	 * @return principalId for UserPreferences
	 */
	public String getPrincipalId(); 
}
