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
package org.kuali.hr.lm.leavedonation.service;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavedonation.LeaveDonation;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;

public class LeaveDonationMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = -2969252116840795858L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getLeaveDonationService().getLeaveDonation(id);
	}
	
	@Override
	public void saveBusinessObject() {
		super.saveBusinessObject();
		//leave donation saved, clear cache before grabbing saved object
        CacheUtils.flushCache(LeaveDonation.CACHE_NAME);

		// create leave blocks for donor and recipient
		LeaveDonation ld = (LeaveDonation) this.getBusinessObject();
		List<LeaveBlock> lbList = new ArrayList<LeaveBlock>();
		// donor leave block
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setPrincipalId(ld.getDonorsPrincipalID());
		aLeaveBlock.setLeaveDate(ld.getEffectiveDate());
		aLeaveBlock.setEarnCode(ld.getDonatedEarnCode());
		aLeaveBlock.setAccrualCategory(ld.getDonatedAccrualCategory());
		aLeaveBlock.setDescription(ld.getDescription());
		aLeaveBlock.setLeaveAmount(ld.getAmountDonated().negate());	// usage
		aLeaveBlock.setAccrualGenerated(false);
		aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.DONATION_MAINT);
		aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
		aLeaveBlock.setBlockId(0L);
		lbList.add(aLeaveBlock);
		
		// recipient leave block
		aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setPrincipalId(ld.getRecipientsPrincipalID());
		aLeaveBlock.setLeaveDate(ld.getEffectiveDate());
		aLeaveBlock.setEarnCode(ld.getRecipientsEarnCode());
		aLeaveBlock.setAccrualCategory(ld.getRecipientsAccrualCategory());
		aLeaveBlock.setDescription(ld.getDescription());
		aLeaveBlock.setLeaveAmount(ld.getAmountReceived());
		aLeaveBlock.setAccrualGenerated(false);
		aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.DONATION_MAINT);
		aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
		aLeaveBlock.setBlockId(0L);
		lbList.add(aLeaveBlock);
		
		TkServiceLocator.getLeaveBlockService().saveLeaveBlocks(lbList);
	}
}
