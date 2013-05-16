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
package org.kuali.kpme.tklm.leave.adjustment.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.leave.adjustment.LeaveAdjustment;
import org.kuali.kpme.tklm.leave.block.LeaveBlock;
import org.kuali.kpme.tklm.leave.service.LmServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;

public class LeaveAdjustmentMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl{

	private static final long serialVersionUID = 2500485054776792764L;

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
		aLeaveBlock.setAccrualCategory(la.getAccrualCategory());
		aLeaveBlock.setDescription("Leave Adjustment");
		aLeaveBlock.setLeaveAmount(la.getAdjustmentAmount());	// can be negative or positive.  leave as is.
		aLeaveBlock.setAccrualGenerated(false);
		aLeaveBlock.setLeaveBlockType(LMConstants.LEAVE_BLOCK_TYPE.LEAVE_ADJUSTMENT_MAINT);
		aLeaveBlock.setRequestStatus(HrConstants.REQUEST_STATUS.APPROVED);
		aLeaveBlock.setBlockId(0L);
		
		LmServiceLocator.getLeaveBlockService().saveLeaveBlock(aLeaveBlock, HrContext.getPrincipalId());		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("principalId")
				&& StringUtils.isNotEmpty(fieldValues.get("principalId"))
				&& fieldValues.containsKey("effectiveDate")
				&& StringUtils.isNotEmpty(fieldValues.get("effectiveDate"))) {
			LocalDate effDate = TKUtils.formatDateString(fieldValues.get("effectiveDate"));
			PrincipalHRAttributes principalHRAttrObj = HrServiceLocator.getPrincipalHRAttributeService()
						.getPrincipalCalendar(fieldValues.get("principalId"), effDate);
			String lpString = (principalHRAttrObj != null) ? principalHRAttrObj.getLeavePlan() : "";
			fieldValues.put("leavePlan", lpString);
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall); 
	}


}
