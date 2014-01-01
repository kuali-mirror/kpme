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
package org.kuali.kpme.core.kfs.coa.businessobject.validation;

import java.util.HashMap;
import java.util.Map;

import org.kuali.kpme.core.kfs.coa.businessobject.Account;
import org.kuali.kpme.core.kfs.coa.businessobject.Chart;
import org.kuali.kpme.core.kfs.coa.businessobject.ObjectCode;
import org.kuali.kpme.core.kfs.coa.businessobject.SubObjectCode;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

@SuppressWarnings("deprecation")
public class SubObjectCodeValidation extends MaintenanceDocumentRuleBase {
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(
			MaintenanceDocument document) {
		SubObjectCode subObjectCode = (SubObjectCode) document.getNewMaintainableObject().getDataObject();
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);
		isValid &= validateChart(subObjectCode);
		isValid &= validateAccount(subObjectCode);
		isValid &= validateObjectCode(subObjectCode);
		return isValid;
	}

	@Override
	protected boolean validateMaintenanceDocument(
			MaintenanceDocument maintenanceDocument) {
		return super.validateMaintenanceDocument(maintenanceDocument);
	}
	
	private boolean validateChart(SubObjectCode subObjectCode) {
		boolean isValid = ValidationUtils.validateChart(subObjectCode.getChartOfAccountsCode());
		if(!isValid) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.chartOfAccountsCode", "exists.chart");
		}	
		return isValid;
	}
	
	private boolean validateObjectCode(SubObjectCode subObjectCode) {
		boolean isValid = ValidationUtils.validateObjectCode(subObjectCode.getFinancialObjectCode(),
															subObjectCode.getChartOfAccountsCode(),
															subObjectCode.getUniversityFiscalYear());
		if(!isValid) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.financialObjectCode", "exists.financialobjectcode", subObjectCode.getChartOfAccountsCode());
		}
		return isValid;
	}

	private boolean validateAccount(SubObjectCode subObjectCode) {
		boolean isValid = ValidationUtils.validateAccount(subObjectCode.getChartOfAccountsCode(), subObjectCode.getAccountNumber());
		if(!isValid) {
			GlobalVariables.getMessageMap().putError("document.newMaintainableObject.accountNumber", "exists.account", subObjectCode.getChartOfAccountsCode());
		}
		return isValid;
	}
	
}
