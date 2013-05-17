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
package org.kuali.kpme.core.role;

public enum KPMERole {
	
	REVIEWER ("Reviewer"),
	APPROVER ("Approver"),
	APPROVER_DELEGATE ("Approver Delegate"),
	TIME_DEPARTMENT_VIEW_ONLY ("Time Department View Only"),
	TIME_DEPARTMENT_ADMINISTRATOR ("Time Department Administrator"),
	LEAVE_DEPARTMENT_VIEW_ONLY ("Leave Department View Only"),
	LEAVE_DEPARTMENT_ADMINISTRATOR ("Leave Department Administrator"),
	TIME_LOCATION_VIEW_ONLY ("Time Location View Only"),
	TIME_LOCATION_ADMINISTRATOR ("Time Location Administrator"),
	LEAVE_LOCATION_VIEW_ONLY ("Leave Location View Only"),
	LEAVE_LOCATION_ADMINISTRATOR ("Leave Location Administrator"),
	TIME_SYSTEM_VIEW_ONLY ("Time System View Only"),
	TIME_SYSTEM_ADMINISTRATOR ("Time System Administrator"),
	LEAVE_SYSTEM_VIEW_ONLY ("Leave System View Only"),
	LEAVE_SYSTEM_ADMINISTRATOR ("Leave System Administrator");
	
	private String roleName;
	
	private KPMERole(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}