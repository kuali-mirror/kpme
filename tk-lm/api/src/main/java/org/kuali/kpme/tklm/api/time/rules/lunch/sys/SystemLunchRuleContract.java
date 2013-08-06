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
package org.kuali.kpme.tklm.api.time.rules.lunch.sys;

import org.kuali.kpme.tklm.api.time.rules.TkRuleContract;

/**
 * <p>SystemLunchRuleContract interface</p>
 *
 */
public interface SystemLunchRuleContract extends TkRuleContract {
	
	/**
	 * The primary key of a SystemLunchRule entry saved in a database
	 * 
	 * <p>
	 * tkSystemLunchRuleId of a SystemLunchRule
	 * <p>
	 * 
	 * @return tkSystemLunchRuleId for SystemLunchRule
	 */
	public String getTkSystemLunchRuleId();
	
	/**
	 * The history flag of the SystemLunchRule
	 * 
	 * <p>
	 * history flag of a SystemLunchRule
	 * <p>
	 * 
	 * @return Y if on, N if not
	 */
	public boolean isHistory();
	
	/**
	 * The principal id of the user who set up the SystemLunchRule
	 * 
	 * <p>
	 * userPrincipalId of a SystemLunchRule
	 * <p>
	 * 
	 * @return userPrincipalId for SystemLunchRule
	 */
	public String getUserPrincipalId();
	
	/**
	 * The flag to indicate if the lunch buttons will be presented or not
	 * 
	 * <p>
	 * showLunchButton of a SystemLunchRule
	 * <p>
	 * 
	 * @return Y if presented, N if not
	 */
	public Boolean getShowLunchButton();

}
