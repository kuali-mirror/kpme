package org.kuali.hr.lm.leaveadjustment.service;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.leaveadjustment.LeaveAdjustment;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.earncode.EarnCode;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kns.document.MaintenanceDocument;

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
		aLeaveBlock.setRequestStatus(LMConstants.REQUEST_STATUS.APPROVED);
		aLeaveBlock.setBlockId(0L);
		
		TkServiceLocator.getLeaveBlockService().saveLeaveBlock(aLeaveBlock);		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("principalId")
				&& StringUtils.isNotEmpty(fieldValues.get("principalId"))
				&& fieldValues.containsKey("effectiveDate")
				&& StringUtils.isNotEmpty(fieldValues.get("effectiveDate"))) {
			Date effDate = new Date(fieldValues.get("effectiveDate"));
			PrincipalHRAttributes principalHRAttrObj = TkServiceLocator.getPrincipalHRAttributeService()
						.getPrincipalCalendar(fieldValues.get("principalId"), effDate);
			String lpString = (principalHRAttrObj != null) ? principalHRAttrObj.getLeavePlan() : "";
			fieldValues.put("leavePlan", lpString);
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall); 
	}


}
