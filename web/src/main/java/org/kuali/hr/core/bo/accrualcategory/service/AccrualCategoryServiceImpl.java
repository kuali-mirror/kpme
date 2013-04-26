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
package org.kuali.hr.core.bo.accrualcategory.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.hr.core.bo.accrualcategory.AccrualCategory;
import org.kuali.hr.core.bo.accrualcategory.dao.AccrualCategoryDao;
import org.kuali.hr.core.bo.leaveplan.LeavePlan;
import org.kuali.hr.core.bo.principal.PrincipalHRAttributes;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.leave.block.LeaveBlock;
import org.kuali.hr.tklm.leave.service.LmServiceLocator;
import org.kuali.hr.tklm.leave.util.LMConstants;

public class AccrualCategoryServiceImpl implements AccrualCategoryService {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(AccrualCategoryServiceImpl.class);
	private AccrualCategoryDao accrualCategoryDao;
	public AccrualCategoryServiceImpl() {
	}

	public AccrualCategory getAccrualCategory(String accrualCategory, LocalDate asOfDate) {
		return accrualCategoryDao.getAccrualCategory(accrualCategory, asOfDate);
	}

	@Override
	public void saveOrUpdate(AccrualCategory accrualCategory) {
		accrualCategoryDao.saveOrUpdate(accrualCategory);
	}

	public AccrualCategoryDao getAccrualCategoryDao() {
		return accrualCategoryDao;
	}

	public void setAccrualCategoryDao(AccrualCategoryDao accrualCategoryDao) {
		this.accrualCategoryDao = accrualCategoryDao;
	}

	@Override
	public AccrualCategory getAccrualCategory(String lmAccrualCategoryId) {
		return accrualCategoryDao.getAccrualCategory(lmAccrualCategoryId);
	}

	@Override
	public List <AccrualCategory> getActiveAccrualCategories(LocalDate asOfDate){
		return accrualCategoryDao.getActiveAccrualCategories(asOfDate);
	}

    @Override
    public List<AccrualCategory> getAccrualCategories(String accrualCategory, String accrualCatDescr, String leavePlan, String accrualEarnInterval, String unitOfTime, String minPercentWorked, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
        return accrualCategoryDao.getAccrualCategories(accrualCategory, accrualCatDescr, leavePlan, accrualEarnInterval, unitOfTime, minPercentWorked, fromEffdt, toEffdt, active, showHistory);
    }
   

	public void runAccrual(String principalId, LocalDate asOfDate){
		PrincipalHRAttributes principalHRAttributes = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
		if(principalHRAttributes == null){
			throw new RuntimeException("Cannot find principal hr attributes for "+principalId);
		}
		//Grab the service date
		LocalDate serviceDate = principalHRAttributes.getServiceLocalDate();
		if(serviceDate == null){
			throw new RuntimeException("Cannot find service date on principal hr attribute for "+principalId);
		}
		
		String leavePlanStr = principalHRAttributes.getLeavePlan();
		if(StringUtils.isBlank(leavePlanStr)){
			throw new RuntimeException("Cannot find leave plan for "+principalId);
		}
		LeavePlan leavePlan = HrServiceLocator.getLeavePlanService().getLeavePlan(leavePlanStr, asOfDate);
		if(leavePlan == null){
			throw new RuntimeException("Cannot find leave plan object for leave plan " + leavePlanStr);
		}

		//Grab all the accrual categories for leave plan
		List<AccrualCategory> accrualCategories = accrualCategoryDao.getActiveAccrualCategories(leavePlanStr, asOfDate); 
		for(AccrualCategory accrualCat : accrualCategories){
			//if no rules continue
			if(StringUtils.equals(accrualCat.getAccrualEarnInterval(), LMConstants.ACCRUAL_EARN_INTERVAL.NO_ACCRUAL) 
					|| accrualCat.getAccrualCategoryRules().isEmpty()){
				continue;
			}
			String serviceUnitOfTime = accrualCat.getUnitOfTime();
			accrualCat.getAccrualEarnInterval();
			
			
			
		}
	}	

	public List <AccrualCategory> getActiveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate) {
    	return accrualCategoryDao.getActiveAccrualCategories(leavePlan, asOfDate);
    }
    
	 @Override
    public List <AccrualCategory> getActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate) {
    	return accrualCategoryDao.getActiveLeaveAccrualCategoriesForLeavePlan(leavePlan, asOfDate);
    }
    
    @Override
    public List <AccrualCategory> getInActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate) {
    	return accrualCategoryDao.getInActiveLeaveAccrualCategoriesForLeavePlan(leavePlan, asOfDate);
    }

    @Override
	public BigDecimal getAccruedBalanceForPrincipal(String principalId,
			AccrualCategory accrualCategory, LocalDate asOfDate) {
    	BigDecimal balance = new BigDecimal(0);
    	PrincipalHRAttributes pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
    	if(pha == null)
    		return BigDecimal.ZERO;
    	
    	List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksWithAccrualCategory(principalId, pha.getServiceLocalDate(), asOfDate, accrualCategory.getAccrualCategory());
    	for(LeaveBlock block : leaveBlocks) {
    		if(!(StringUtils.equals(block.getRequestStatus(),LMConstants.REQUEST_STATUS.DEFERRED)
    				|| StringUtils.equals(block.getRequestStatus(),LMConstants.REQUEST_STATUS.DISAPPROVED))) {
    			balance = balance.add(block.getLeaveAmount());
/*    			EarnCode code = accrualCategory.getEarnCodeObj();
    			if(StringUtils.equals(LMConstants.ACCRUAL_BALANCE_ACTION_MAP.get(code.getAccrualBalanceAction()), "Usage"))
    				balance = balance.subtract(block.getLeaveAmount().abs());
    			if(StringUtils.equals(LMConstants.ACCRUAL_BALANCE_ACTION_MAP.get(code.getAccrualBalanceAction()), "Adjustment"))
    				balance = balance.add(block.getLeaveAmount());*/
    		}
    	}
		return balance;
	}

	@Override
	public BigDecimal getApprovedBalanceForPrincipal(String principalId,
			AccrualCategory accrualCategory, LocalDate asOfDate) {
    	BigDecimal balance = new BigDecimal(0);
    	PrincipalHRAttributes pha = HrServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
    	List<LeaveBlock> leaveBlocks = LmServiceLocator.getLeaveBlockService().getLeaveBlocksWithAccrualCategory(principalId, pha.getServiceLocalDate(), asOfDate, accrualCategory.getAccrualCategory());
    	for(LeaveBlock block : leaveBlocks) {
    		if(StringUtils.equals(block.getRequestStatus(),LMConstants.REQUEST_STATUS.APPROVED)) {
				balance = balance.add(block.getLeaveAmount());
/*    			EarnCode code = accrualCategory.getEarnCodeObj();
    			if(StringUtils.equals(LMConstants.ACCRUAL_BALANCE_ACTION_MAP.get(code.getAccrualBalanceAction()), "Usage"))
    				balance = balance.subtract(block.getLeaveAmount().abs());
    			if(StringUtils.equals(LMConstants.ACCRUAL_BALANCE_ACTION_MAP.get(code.getAccrualBalanceAction()), "Adjustment"))
    				balance = balance.add(block.getLeaveAmount());*/
    		}
    	}
		return balance;
	}
}
