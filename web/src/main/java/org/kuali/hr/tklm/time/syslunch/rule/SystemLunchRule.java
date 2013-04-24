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
package org.kuali.hr.tklm.time.syslunch.rule;

import org.kuali.hr.core.KPMEConstants;
import org.kuali.hr.tklm.time.rule.TkRule;

public class SystemLunchRule extends TkRule {
    public static final String CACHE_NAME = KPMEConstants.APPLICATION_NAMESPACE_CODE + "/" + "SystemLunchRule";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tkSystemLunchRuleId;
	private Boolean showLunchButton = false;
	private boolean history;
	private String userPrincipalId;


	public String getTkSystemLunchRuleId() {
		return tkSystemLunchRuleId;
	}

	public void setTkSystemLunchRuleId(String tkSystemLunchRuleId) {
		this.tkSystemLunchRuleId = tkSystemLunchRuleId;
	}

	public boolean isHistory() {
		return history;
	}


	public void setHistory(boolean history) {
		this.history = history;
	}

	public String getUserPrincipalId() {
		return userPrincipalId;
	}

	public void setUserPrincipalId(String userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}


	public Boolean getShowLunchButton() {
		return showLunchButton;
	}


	public void setShowLunchButton(Boolean showLunchButton) {
		this.showLunchButton = showLunchButton;
	}


	@Override
	public String getUniqueKey() {
		return "";
	}

	@Override
	public String getId() {
		return getTkSystemLunchRuleId();
	}

	@Override
	public void setId(String id) {
		setTkSystemLunchRuleId(id);
	}

}
