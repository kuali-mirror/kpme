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
package org.kuali.kpme.core.kfs.coa.businessobject.validation;

import org.kuali.kpme.core.kfs.coa.businessobject.Account;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;

@SuppressWarnings("deprecation")
public class AccountValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		Account account = (Account) document.getNewMaintainableObject().getBusinessObject();
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);
		isValid &= validateChart(account);
		isValid &= validateOrganization(account);
		return isValid;
	}

	@Override
	protected boolean validateMaintenanceDocument(
			MaintenanceDocument maintenanceDocument) {
		return super.validateMaintenanceDocument(maintenanceDocument);
	}
	
	private boolean validateChart(Account account) {
		boolean isValid = ValidationUtils.validateChart(account.getChartOfAccountsCode());
		if(!isValid) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.chartOfAccountsCode", "exists.chart");
		}
		return isValid;
	}
	
	private boolean validateOrganization(Account account) {
		boolean isValid = ValidationUtils.validateOrganization(account.getOrganizationCode(), account.getChartOfAccountsCode());
		if(!isValid) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.organizationCode", "exists.organization", account.getChartOfAccountsCode());
		}
		return isValid;
	}
}
