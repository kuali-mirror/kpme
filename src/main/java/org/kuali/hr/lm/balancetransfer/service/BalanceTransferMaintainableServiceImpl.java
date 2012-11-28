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
import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavedonation.LeaveDonation;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.util.KRADConstants;
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
		// create leave block 
		// Needs to be moved to another location. Balance transfer document should not
		// create leave blocks upon save, but rather, check max carry-over.
		// Leave blocks should be created when balance transfer document reaches
		// an "Approved" or "Final" status. System created balance transfer documents
		// should be pushed to status "Final" or "Approved". Balance transfer documents
		// created by Department Admins routed for approval by system admins will have EnRoute
		// status. Do system admins auto-stamp "Approved"/"Final" - or is interaction required.
		// Payout selected for Action at Max Balance transaction initiated via Leave Calendar Display
		// Does this action require approval by department admins?
		BalanceTransfer btd = (BalanceTransfer) this.getBusinessObject();
		AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(btd.getToAccrualCategory(),
				btd.getEffectiveDate());
		btd.setCreditedAccrualCategory(ac);
		//Generate a positive leave block for the credited accrual category
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setPrincipalId(btd.getPrincipalId());
		aLeaveBlock.setLeaveDate(btd.getEffectiveDate());
		aLeaveBlock.setEarnCode(ac.getEarnCode());
		aLeaveBlock.setAccrualCategory(btd.getToAccrualCategory());
		aLeaveBlock.setDescription("Balance Transfer");
		aLeaveBlock.setLeaveAmount(btd.getTransferAmount());	// can be negative or positive.  leave as is.
		aLeaveBlock.setAccrualGenerated(true);
		aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
		aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
		aLeaveBlock.setBlockId(0L);
		
		TkServiceLocator.getLeaveBlockService().saveLeaveBlock(aLeaveBlock);
		
		//member debitedAccrualCategory in class BalanceTransfer, at this time, is not being instantiated/populated with
		//the relevant object. Perhaps it's a caching issue, or a bean property that needs to be defined.
		//Until then, retrieve the relevant accrual category object, making the earn code available to set on the leave block.
		AccrualCategory debitedAcc = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(btd.getFromAccrualCategory(),btd.getEffectiveDate());
		//Generate a negative leave block for the debited accrual category
		aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setPrincipalId(btd.getPrincipalId());
		aLeaveBlock.setLeaveDate(btd.getEffectiveDate());
		aLeaveBlock.setEarnCode(debitedAcc.getEarnCode());
		aLeaveBlock.setAccrualCategory(btd.getFromAccrualCategory());
		aLeaveBlock.setDescription("Balance Transfer");
		aLeaveBlock.setLeaveAmount(btd.getTransferAmount().negate());	// can be negative or positive.  leave as is.
		aLeaveBlock.setAccrualGenerated(true);
		aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
		aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
		aLeaveBlock.setBlockId(0L);
		
		TkServiceLocator.getLeaveBlockService().saveLeaveBlock(aLeaveBlock);
		
		if(btd.getForfeitedAmount()!=null) {
			if(btd.getForfeitedAmount().compareTo(BigDecimal.ZERO) > 0) {
				aLeaveBlock = new LeaveBlock();
				aLeaveBlock.setPrincipalId(btd.getPrincipalId());
				aLeaveBlock.setLeaveDate(btd.getEffectiveDate());
				//Which earn code will be forfeiture?
				aLeaveBlock.setEarnCode("FEC");
				//Which accrual category will be the forfeiture bucket.
				aLeaveBlock.setAccrualCategory(btd.getFromAccrualCategory());
				aLeaveBlock.setDescription("Balance Transfer Forfeiture");
				aLeaveBlock.setLeaveAmount(btd.getForfeitedAmount());
				aLeaveBlock.setAccrualGenerated(true);
				aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.BALANCE_TRANSFER);
				aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.REQUESTED);
				aLeaveBlock.setBlockId(0L);
				
				TkServiceLocator.getLeaveBlockService().saveLeaveBlock(aLeaveBlock);
			}
		}
		
		super.saveBusinessObject();
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
			//What other maintenance actions are available?
			System.out.println("***********************");
			System.out.println("*   New BT Document   *");
			System.out.println("***********************");
			String principalId = null;
			String fromAccrualCategory = null;
			Date effectiveDate = null;
			if(fieldValues.containsKey("principalId")
					&& StringUtils.isNotBlank(fieldValues.get("principalId"))
					&& fieldValues.containsKey("fromAccrualCategory")
					&& StringUtils.isNotBlank(fieldValues.get("fromAccrualCategory"))
					&& fieldValues.containsKey("effectiveDate")
					&& StringUtils.isNotBlank(fieldValues.get("effectiveDate"))) {
				System.out.println("***************************");
				System.out.println("*  Contains Nesc. Fields  *");
				System.out.println("***************************");				
				BalanceTransfer bt = (BalanceTransfer) this.getBusinessObject();
				effectiveDate = TKUtils.formatDateString(fieldValues.get("effectiveDate"));
				principalId = fieldValues.get("principalId");
				fromAccrualCategory = fieldValues.get("fromAccrualCategory");
				PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, effectiveDate);
				AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fromAccrualCategory, effectiveDate);
				if(ObjectUtils.isNotNull(bt.getCreditedAccrualCategory())) {
					System.out.println("credited accrual category has been saved");
				}
				bt.setDebitedAccrualCategory(ac);
				AccrualCategory creditedAccrualCategory = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(fieldValues.get("toAccrualCategory"),effectiveDate);
				bt.setCreditedAccrualCategory(creditedAccrualCategory);
				if(pha!=null) {
					//fieldValues.put("accrualCategoryRule", TkServiceLocator.getAccrualCategoryRuleService().getAccrualCategoryRuleForDate(ac,
					//		TKUtils.getCurrentDate(), pha.getServiceDate()).getLmAccrualCategoryRuleId());
				}
			}
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}

}
