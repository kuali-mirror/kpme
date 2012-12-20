/**
 * Copyright 2004-2012 The Kuali Foundation
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
		// TODO Auto-generated method stub
		return TkServiceLocator.getBalanceTransferService().getBalanceTransferById(id);
	}

	/**
	 * Used to 
	 */
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		// TODO Auto-generated method stub
		if(StringUtils.equals(getMaintenanceAction(), "New")) {

			if(fieldValues.containsKey("principalId")
					&& StringUtils.isNotBlank(fieldValues.get("principalId"))
					&& fieldValues.containsKey("effectiveDate")
					&& StringUtils.isNotBlank(fieldValues.get("effectiveDate"))) {
				//Once principal and effective date have been gathered
				BalanceTransfer bt = (BalanceTransfer) this.getBusinessObject();
				
				Date effectiveDate = TKUtils.formatDateString(fieldValues.get("effectiveDate"));
				String principalId = fieldValues.get("principalId");
				if(fieldValues.containsKey("fromAccrualCategory") && StringUtils.isNotBlank(fieldValues.get("fromAccrualCategory"))) {
					//if the "from" accrual category has been entered
					String fromAccrualCategory = fieldValues.get("fromAccrualCategory");

					AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
	
					//attempt to set the related object.
					//bt.setDebitedAccrualCategory(ac);
					System.out.println("*******************************************************************");
					System.out.println(" Populated Business Object With : " + ac.getAccrualCategory());
					System.out.println("*******************************************************************");
					
					PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, effectiveDate);
					AccrualCategoryRule acr = null;
					if(ObjectUtils.isNotNull(pha))
						acr = TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(ac, effectiveDate, pha.getServiceDate());
					if(acr!=null) {
						bt.setAccrualCategoryRule(acr.getLmAccrualCategoryRuleId());
						System.out.println("***************************");
						System.out.println(" Populate Business Object Set ACR to : " + acr.getLmAccrualCategoryRuleId());
						System.out.println("***************************");
					}
				}
				if(fieldValues.containsKey("toAccrualCategory")	&& StringUtils.isNotBlank(fieldValues.get("toAccrualCategory"))) {
					//once "to" accrual category has been entered
					String toAccrualCategory = fieldValues.get("toAccrualCategory");
					
					AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(toAccrualCategory, effectiveDate);
					//attempt to set the related object.
					//bt.setCreditedAccrualCategory(ac);
				}
			}
		}
		
		System.out.println("***************************");
		System.out.println(" Populate Business Object: " + getMaintenanceAction());
		System.out.println("***************************");
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}

}
