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
		// TODO Auto-generated method stub
		SubObjectCode subObjectCode = (SubObjectCode) document.getNewMaintainableObject().getDataObject();
		boolean isValid = super.processCustomRouteDocumentBusinessRules(document);
		isValid &= validateChart(subObjectCode);
		isValid &= validateAccount(subObjectCode);
		isValid &= validateObjectCode(subObjectCode);
		isValid &= validateChartConsistency(subObjectCode);
		return isValid;
	}

	@Override
	protected boolean validateMaintenanceDocument(
			MaintenanceDocument maintenanceDocument) {
		return super.validateMaintenanceDocument(maintenanceDocument);
	}
	
	private boolean validateChart(SubObjectCode subObjectCode) {
		return ValidationUtils.validateChart(subObjectCode.getChartOfAccountsCode());
	}
	
	private boolean validateObjectCode(SubObjectCode subObjectCode) {
		if(subObjectCode.getFinancialObjectCode() != null) {
			return ValidationUtils.validateObjectCode(subObjectCode.getFinancialObjectCode());
		}
		else
			return false;
	}

	private boolean validateAccount(SubObjectCode subObjectCode) {
		return ValidationUtils.validateAccount(subObjectCode.getAccountNumber());
	}
	
	private boolean validateChartConsistency(SubObjectCode subObjectCode) {
		Chart chartOfAccounts = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(Chart.class, subObjectCode.getChartOfAccountsCode());

		if(chartOfAccounts != null) {
			
			String accountNumber = subObjectCode.getAccountNumber();
			Map<String, String> keys = new HashMap<String,String>();
			keys.put("chartOfAccountsCode", chartOfAccounts.getCode());
			keys.put("accountNumber", accountNumber);
			Account account = (Account) KRADServiceLocator.getBusinessObjectService().findByPrimaryKey(Account.class, keys);
			Chart accountChart = null;
			
			Integer fiscalYear = subObjectCode.getUniversityFiscalYear();
			String financialObjectCode = subObjectCode.getFinancialObjectCode();
			keys.clear();
			keys.put("universityFiscalYear", fiscalYear.toString());
			keys.put("chartOfAccountsCode", chartOfAccounts.getCode());
			keys.put("financialObjectCode", financialObjectCode);
			
			ObjectCode objectCode = KRADServiceLocator.getBusinessObjectService().findByPrimaryKey(ObjectCode.class, keys);
			Chart objectCodeChart = null;
			
			if(account != null) {
				accountChart = account.getChartOfAccounts();
			}
			if(objectCode != null) {
				objectCodeChart = objectCode.getChartOfAccounts();
			}
			
			if(accountChart != null && objectCodeChart != null) {
				
				boolean valid = true;
				
				if(!accountChart.equals(chartOfAccounts)) {
					GlobalVariables.getMessageMap().putError("document.newMaintainableObject.accountNumber",
																"subobjectcode.account.chart.inconsistent",
																accountChart.getChartOfAccountsCode(),
																chartOfAccounts.getCode());
					valid = false;
				}
				if(!objectCodeChart.equals(chartOfAccounts)) {
					GlobalVariables.getMessageMap().putError("document.newMaintainableObject.financialObjectCode",
																"subobjectcode.objectcode.chart.inconsistent",
																objectCodeChart.getChartOfAccountsCode(),
																chartOfAccounts.getCode());
					valid = false;
				}
				
				return valid;
			} else {
				if(accountChart == null) {
					GlobalVariables.getMessageMap().putError("document.newMaintainableObject.chartOfAccounts", "subobjectcode.account.chart.exists", chartOfAccounts.getChartOfAccountsCode());
				}
				if(objectCodeChart == null) {
					GlobalVariables.getMessageMap().putError("document.newMaintainableObject.financialObjectCode", "subobjectcode.objectcode.chart.exists", fiscalYear.toString(), chartOfAccounts.getChartOfAccountsCode());
				}
			}

		}
		
		return false;
	}
	
}
