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
package org.kuali.hr.lm.balancetransfer.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.ObjectUtils;

public class BalanceTransferMaintainableServiceImpl extends
		HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -789218061798169466L;

	@Override
	public void saveBusinessObject() {
		//CacheUtils.flushCache(BalanceTransfer.CACHE_NAME);

		super.saveBusinessObject();
		PersistableBusinessObject pbo = this.getBusinessObject();
		if(pbo instanceof BalanceTransfer) {
			BalanceTransfer balanceTransfer = (BalanceTransfer) pbo;
			TkServiceLocator.getBalanceTransferService().transfer(balanceTransfer);
		}
	}
	
	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getBalanceTransferService().getBalanceTransferById(id);
	}

	/**
	 * Used to 
	 */
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		BalanceTransfer bt = (BalanceTransfer) this.getBusinessObject();
		if(StringUtils.equals(getMaintenanceAction(), "New")) {
			if(fieldValues.containsKey("effectiveDate")	&& StringUtils.isBlank(fieldValues.get("effectiveDate")))
				bt.setEffectiveDate(TKUtils.getCurrentDate());
			if(fieldValues.containsKey("principalId")
					&& StringUtils.isNotBlank(fieldValues.get("principalId"))) {
				//Once principal and effective date have been gathered

				
				Date effectiveDate = TKUtils.formatDateString(fieldValues.get("effectiveDate"));
				String principalId = fieldValues.get("principalId");
				if(fieldValues.containsKey("fromAccrualCategory") && StringUtils.isNotBlank(fieldValues.get("fromAccrualCategory"))) {
					//if the "from" accrual category has been entered
					String fromAccrualCategory = fieldValues.get("fromAccrualCategory");

					AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
					
					PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, effectiveDate);
					AccrualCategoryRule acr = null;
					if(ObjectUtils.isNotNull(pha))
						acr = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(ac, effectiveDate, pha.getServiceDate());
					if(acr!=null) {
						//bt.setAccrualCategoryRule(acr.getLmAccrualCategoryRuleId());
						//Don't set the rule, and transfer conversion factor issue will dissolve.
						//n.t.s. that balance transfers initiated via maintenance tab are for specific use cases
						//that do not conform to rules set forth by accrual category. i.e. 1-1 transfers between leave plans
						//when employee shifts positions.
						//If payout were still part of balance transfer, rule would be needed for conversion factor.
					}
				}
			}
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}

}
