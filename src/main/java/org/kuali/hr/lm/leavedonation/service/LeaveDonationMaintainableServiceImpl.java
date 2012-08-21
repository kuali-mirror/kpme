package org.kuali.hr.lm.leavedonation.service;

import java.util.ArrayList;
import java.util.List;

import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leavedonation.LeaveDonation;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;

public class LeaveDonationMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(ld.getDonatedEarnCode(), ld.getEffectiveDate());
		if(ec != null) {
			aLeaveBlock.setEarnCodeId(ec.getHrEarnCodeId());
		}
		AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(ld.getDonatedAccrualCategory(), ld.getEffectiveDate());
		if(ac != null) {
			aLeaveBlock.setAccrualCategoryId(ac.getLmAccrualCategoryId());
		}
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
		ec = TkServiceLocator.getEarnCodeService().getEarnCode(ld.getRecipientsEarnCode(), ld.getEffectiveDate());
		if(ec != null) {
			aLeaveBlock.setEarnCodeId(ec.getHrEarnCodeId());
		}
		ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(ld.getRecipientsAccrualCategory(), ld.getEffectiveDate());
		if(ac != null) {
			aLeaveBlock.setAccrualCategoryId(ac.getLmAccrualCategoryId());
		}
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
