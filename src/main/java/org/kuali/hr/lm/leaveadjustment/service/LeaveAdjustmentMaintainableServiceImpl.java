package org.kuali.hr.lm.leaveadjustment.service;

import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leaveadjustment.LeaveAdjustment;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;

public class LeaveAdjustmentMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl{

	
	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void saveBusinessObject() {
		super.saveBusinessObject();
		
		// create leave block 
		LeaveAdjustment la = (LeaveAdjustment) this.getBusinessObject();
		LeaveBlock aLeaveBlock = new LeaveBlock();
		aLeaveBlock.setPrincipalId(la.getPrincipalId());
		aLeaveBlock.setLeaveDate(la.getEffectiveDate());
		aLeaveBlock.setEarnCode(la.getEarnCode());
		EarnCode ec = TkServiceLocator.getEarnCodeService().getEarnCode(la.getEarnCode(), la.getEffectiveDate());
		if(ec != null) {
			aLeaveBlock.setEarnCodeId(ec.getHrEarnCodeId());
		}
		AccrualCategory ac = TkServiceLocator.getAccrualCategoryService().getAccrualCategory(la.getAccrualCategory(), la.getEffectiveDate());
		if(ac != null) {
			aLeaveBlock.setAccrualCategoryId(ac.getLmAccrualCategoryId());
		}
		
		aLeaveBlock.setDescription("Leave Adjustment");
		aLeaveBlock.setLeaveAmount(la.getAdjustmentAmount());
		aLeaveBlock.setAccrualGenerated(false);
		aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_ADJUSTMENT_MAINT);
		aLeaveBlock.setBlockId(0L);
		
		TkServiceLocator.getLeaveBlockService().saveLeaveBlock(aLeaveBlock);		
	}

}
