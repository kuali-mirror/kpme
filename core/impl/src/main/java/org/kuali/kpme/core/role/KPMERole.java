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
	LEAVE_SYSTEM_ADMINISTRATOR ("Leave System Administrator"),
	PAYROLL_PROCESSOR ("Payroll Processor"),
	PAYROLL_PROCESSOR_DELEGATE ("Payroll Processor Delegate"),
	
	// derived roles
	DERIVED_ROLE_POSITION ("Derived Role : Position"),
	
	// PROXY roles
	KPME_PROXY_ROLE("Derived Role : KPME Role Proxy"),
	APPROVER_PROXY_ROLE("Derived Role: Approver Proxy"),
	APPROVER_DELEGATE_PROXY_ROLE("Derived Role: Approver Delegate Proxy"),
	
	// KPME-3198
	POSITION_SYSTEM_ADMINISTRATOR ("Position System Administrator"), 
	POSITION_SYSTEM_VIEW_ONLY ("Position System View Only"),
	KOHR_INSTITUTION_ADMINISTRATOR ("KOHR Institution Administrator"),
	KOHR_ACADEMIC_HR_ADMINISTRATOR ("KOHR Academic HR Administrator"),
	KOHR_INSTITUTION_VIEW_ONLY ("KOHR Institution View Only"),
	KOHR_LOCATION_ADMINISTRATOR ("KOHR Location Administrator"),
	KOHR_LOCATION_VIEW_ONLY ("KOHR Location View Only"),
	KOHR_ORG_ADMINISTRATOR ("KOHR Org Administrator"),
	KOHR_ORG_VIEW_ONLY ("KOHR Org View Only"),
	HR_DEPARTMENT_ADMINISTRATOR ("HR Department Administrator"),
	HR_DEPARTMENT_VIEW_ONLY ("HR Department View Only"),
	HR_INSTITUTION_APPROVER ("HR Institution Approver"),
	ACADEMIC_HR_INSTITUTION_APPROVER ("Academic HR Institution Approver"),
	BUDGET_APPROVER ("Budget Approver"),
	PAYROLL_APPROVER ("Payroll Approver"),
	HR_LOCATION_APPROVER ("HR Location Approver"),
	ACADEMIC_HR_LOCATION_APPROVER ("Academic HR Location Approver"), 
	FISCAL_LOCATION_APPROVER ("Fiscal Location Approver"), 
	HR_ORG_APPROVER ("HR Org Approver"),
	FISCAL_ORG_APPROVER ("Fiscal Org Approver"),
	DEPARTMENT_APPROVER ("Department Approver"),
	FISCAL_DEPARTMENT_APPROVER ("Fiscal Department Approver");
	
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