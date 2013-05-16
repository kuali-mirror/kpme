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
package org.kuali.kpme.tklm.leave.service;

import org.kuali.kpme.tklm.leave.accrual.service.AccrualCategoryMaxBalanceService;
import org.kuali.kpme.tklm.leave.accrual.service.AccrualCategoryMaxCarryOverService;
import org.kuali.kpme.tklm.leave.accrual.service.AccrualService;
import org.kuali.kpme.tklm.leave.accrual.service.PrincipalAccrualRanService;
import org.kuali.kpme.tklm.leave.adjustment.service.LeaveAdjustmentService;
import org.kuali.kpme.tklm.leave.approval.service.LeaveApprovalService;
import org.kuali.kpme.tklm.leave.block.service.LeaveBlockHistoryService;
import org.kuali.kpme.tklm.leave.block.service.LeaveBlockService;
import org.kuali.kpme.tklm.leave.calendar.service.LeaveCalendarService;
import org.kuali.kpme.tklm.leave.donation.service.LeaveDonationService;
import org.kuali.kpme.tklm.leave.override.service.EmployeeOverrideService;
import org.kuali.kpme.tklm.leave.payout.service.LeavePayoutService;
import org.kuali.kpme.tklm.leave.request.service.LeaveRequestDocumentService;
import org.kuali.kpme.tklm.leave.service.permission.LMPermissionService;
import org.kuali.kpme.tklm.leave.service.role.LMRoleService;
import org.kuali.kpme.tklm.leave.summary.service.LeaveSummaryService;
import org.kuali.kpme.tklm.leave.timeoff.service.SystemScheduledTimeOffService;
import org.kuali.kpme.tklm.leave.transfer.service.BalanceTransferService;
import org.kuali.kpme.tklm.leave.workflow.service.LeaveCalendarDocumentHeaderService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class LmServiceLocator implements ApplicationContextAware {

	public static String SPRING_BEANS = "classpath:SpringBeans.xml";
	private static ApplicationContext CONTEXT;

    public static final String LM_ACCRUAL_SERVICE = "accrualService";
    public static final String LM_LEAVE_DONATION_SERVICE = "leaveDonationService";
    public static final String LM_SYS_SCH_TIMEOFF_SERVICE = "systemScheduledTimeOffService";
    public static final String LM_LEAVE_BLOCK_SERVICE = "leaveBlockService";
    public static final String LM_LEAVE_CALENDAR_SERVICE = "leaveCalendarService";
    public static final String LM_LEAVE_CALENDAR_DOCUMENT_HEADER_SERVICE = "leaveCalendarDocumentHeaderService";
    public static final String LM_EMPLOYEE_OVERRIDE_SERVICE = "employeeOverrideService";
	public static final String LM_LEAVE_ADJUSTMENT_SERVICE = "leaveAdjustmentService";
	public static final String LM_LEAVE_BLOCK_HISTORY_SERVICE = "leaveBlockHistoryService";
	public static final String LM_PRINCIPAL_ACCRUAL_RAN_SERVICE = "principalAccrualRanService";
	public static final String LM_LEAVE_SUMMARY_SERVICE = "leaveSummaryService";
	public static final String LM_LEAVE_APPROVAL_SERVICE = "leaveApprovalService";
    public static final String LM_BALANCE_TRANSFER_SERVICE = "balanceTransferService";
    public static final String LM_ACCRUAL_CATEGORY_MAX_BALANCE_SERVICE = "accrualCategoryMaxBalanceService";
    public static final String LM_LEAVE_REQUEST_DOC_SERVICE = "leaveRequestDocumentService";
	public static final String LM_ACCRUAL_CATEGORY_MAX_CARRY_OVER_SERVICE = "accrualCategoryMaxCarryOverService";
    public static final String LM_LEAVE_PAYOUT_SERVICE = "leavePayoutService";
	
    public static final String LM_ROLE_SERVICE = "lmRoleService";
    public static final String LM_PERMISSION_SERVICE = "lmPermissionService";
    
	public static AccrualService getLeaveAccrualService(){
		return (AccrualService) CONTEXT.getBean(LM_ACCRUAL_SERVICE);
	}
	public static LeaveDonationService getLeaveDonationService(){
		return (LeaveDonationService)CONTEXT.getBean(LM_LEAVE_DONATION_SERVICE);
	}
	
	public static SystemScheduledTimeOffService getSysSchTimeOffService(){
		return (SystemScheduledTimeOffService)CONTEXT.getBean(LM_SYS_SCH_TIMEOFF_SERVICE);
	}

    public static LeaveBlockService getLeaveBlockService(){
		return (LeaveBlockService)CONTEXT.getBean(LM_LEAVE_BLOCK_SERVICE);
	}

    public static LeaveBlockHistoryService getLeaveBlockHistoryService(){
		return (LeaveBlockHistoryService)CONTEXT.getBean(LM_LEAVE_BLOCK_HISTORY_SERVICE);
	}
    
    public static LeaveCalendarService getLeaveCalendarService(){
		return (LeaveCalendarService)CONTEXT.getBean(LM_LEAVE_CALENDAR_SERVICE);
	}

    public static LeaveCalendarDocumentHeaderService getLeaveCalendarDocumentHeaderService(){
		return (LeaveCalendarDocumentHeaderService)CONTEXT.getBean(LM_LEAVE_CALENDAR_DOCUMENT_HEADER_SERVICE);
	}
    
    public static EmployeeOverrideService getEmployeeOverrideService(){
		return (EmployeeOverrideService)CONTEXT.getBean(LM_EMPLOYEE_OVERRIDE_SERVICE);
	}
    
    public static LeaveAdjustmentService getLeaveAdjustmentService(){
		return (LeaveAdjustmentService)CONTEXT.getBean(LM_LEAVE_ADJUSTMENT_SERVICE);
	}
	
	public static PrincipalAccrualRanService getPrincipalAccrualRanService() {
		return (PrincipalAccrualRanService)CONTEXT.getBean(LM_PRINCIPAL_ACCRUAL_RAN_SERVICE);
	}
	public static LeaveSummaryService getLeaveSummaryService() {
		return (LeaveSummaryService)CONTEXT.getBean(LM_LEAVE_SUMMARY_SERVICE);
	}
	public static LeaveApprovalService getLeaveApprovalService() {
		return (LeaveApprovalService)CONTEXT.getBean(LM_LEAVE_APPROVAL_SERVICE);
	}
    public static BalanceTransferService getBalanceTransferService() {
    	return (BalanceTransferService) CONTEXT.getBean(LM_BALANCE_TRANSFER_SERVICE);
    }
    public static AccrualCategoryMaxBalanceService getAccrualCategoryMaxBalanceService() {
    	return (AccrualCategoryMaxBalanceService) CONTEXT.getBean(LM_ACCRUAL_CATEGORY_MAX_BALANCE_SERVICE);
    }
    public static LeavePayoutService getLeavePayoutService() {
        return (LeavePayoutService) CONTEXT.getBean(LM_LEAVE_PAYOUT_SERVICE);
    }
    public static LMRoleService getLMRoleService() {
    	return (LMRoleService) CONTEXT.getBean(LM_ROLE_SERVICE);
    }
	public static AccrualService getAccrualService() {
	    return (AccrualService)CONTEXT.getBean(LM_ACCRUAL_SERVICE);
	}
    public static LeaveRequestDocumentService getLeaveRequestDocumentService() {
        return (LeaveRequestDocumentService) CONTEXT.getBean(LM_LEAVE_REQUEST_DOC_SERVICE);
    }
    public static AccrualCategoryMaxCarryOverService getAccrualCategoryMaxCarryOverService() {
    	return (AccrualCategoryMaxCarryOverService) CONTEXT.getBean(LM_ACCRUAL_CATEGORY_MAX_CARRY_OVER_SERVICE);
    }
    public static LMPermissionService getLMPermissionService() {
    	return (LMPermissionService) CONTEXT.getBean(LM_PERMISSION_SERVICE);
    }

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		CONTEXT = arg0;
	}

}
