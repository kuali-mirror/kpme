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
import org.kuali.kpme.core.kfs.coa.businessobject.Chart;
import org.kuali.kpme.core.kfs.coa.businessobject.ObjectCode;
import org.kuali.kpme.core.kfs.coa.businessobject.SubObjectCode;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;

@SuppressWarnings("deprecation")
public class SubObjectCodeValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		// TODO Auto-generated method stub
		SubObjectCode subObjectCode = (SubObjectCode) document.getNewMaintainableObject().getBusinessObject();
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);
		isValid &= validateChart(subObjectCode);
		isValid &= validateAccount(subObjectCode);
		isValid &= validateObjectCode(subObjectCode);
		isValid &= validateConsistency(subObjectCode);
		return isValid;
	}

	@Override
	protected boolean validateMaintenanceDocument(
			MaintenanceDocument maintenanceDocument) {
		// TODO Auto-generated method stub
		return super.validateMaintenanceDocument(maintenanceDocument);
	}
	
	private boolean validateChart(SubObjectCode subObjectCode) {
		// TODO Auto-generated method stub
		return ValidationUtils.validateChart(subObjectCode.getChartOfAccountsCode());
	}
	
	private boolean validateObjectCode(SubObjectCode subObjectCode) {
		// TODO Auto-generated method stub
		return ValidationUtils.validateObjectCode(subObjectCode.getFinancialObject().getCode());
	}

	private boolean validateAccount(SubObjectCode subObjectCode) {
		// TODO Auto-generated method stub
		return ValidationUtils.validateAccount(subObjectCode.getAccountNumber());
	}
	
	private boolean validateConsistency(SubObjectCode subObjectCode) {
		// TODO Auto-generated method stub
		Chart chart = subObjectCode.getChartOfAccounts();
		
		Account account = subObjectCode.getAccount();
		Chart accountChart = account.getChartOfAccounts();

		ObjectCode objectCode = subObjectCode.getFinancialObject();
		Chart objectCodeChart = objectCode.getChartOfAccounts();
		
		return accountChart.equals(chart) && objectCodeChart.equals(chart) ? true : false;
	}
	
}
