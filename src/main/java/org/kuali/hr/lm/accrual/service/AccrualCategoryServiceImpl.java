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
package org.kuali.hr.lm.accrual.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.dao.AccrualCategoryDao;
import org.kuali.hr.lm.leaveblock.LeaveBlock;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class AccrualCategoryServiceImpl implements AccrualCategoryService {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(AccrualCategoryServiceImpl.class);
	private AccrualCategoryDao accrualCategoryDao;
	public AccrualCategoryServiceImpl() {
	}

	public AccrualCategory getAccrualCategory(String accrualCategory, Date asOfDate) {
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
	public List <AccrualCategory> getActiveAccrualCategories(Date asOfDate){
		return accrualCategoryDao.getActiveAccrualCategories(asOfDate);
	}

    @Override
    public List<AccrualCategory> getAccrualCategories(String accrualCategory, String accrualCatDescr, String leavePlan, String accrualEarnInterval, String unitOfTime, String minPercentWorked, Date fromEffdt, Date toEffdt, String active, String showHistory) {
        return accrualCategoryDao.getAccrualCategories(accrualCategory, accrualCatDescr, leavePlan, accrualEarnInterval, unitOfTime, minPercentWorked, fromEffdt, toEffdt, active, showHistory);
    }
   

	public void runAccrual(String principalId, Date asOfDate){
		PrincipalHRAttributes principalHRAttributes = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
		if(principalHRAttributes == null){
			throw new RuntimeException("Cannot find principal hr attributes for "+principalId);
		}
		//Grab the service date
		Date serviceDate = principalHRAttributes.getServiceDate();
		if(serviceDate == null){
			throw new RuntimeException("Cannot find service date on principal hr attribute for "+principalId);
		}
		
		String leavePlanStr = principalHRAttributes.getLeavePlan();
		if(StringUtils.isBlank(leavePlanStr)){
			throw new RuntimeException("Cannot find leave plan for "+principalId);
		}
		LeavePlan leavePlan = TkServiceLocator.getLeavePlanService().getLeavePlan(leavePlanStr, asOfDate);
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

	public List <AccrualCategory> getActiveAccrualCategoriesForLeavePlan(String leavePlan, Date asOfDate) {
    	return accrualCategoryDao.getActiveAccrualCategories(leavePlan, asOfDate);
    }
    
	 @Override
    public List <AccrualCategory> getActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, Date asOfDate) {
    	return accrualCategoryDao.getActiveLeaveAccrualCategoriesForLeavePlan(leavePlan, asOfDate);
    }
    
    @Override
    public List <AccrualCategory> getInActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, Date asOfDate) {
    	return accrualCategoryDao.getInActiveLeaveAccrualCategoriesForLeavePlan(leavePlan, asOfDate);
    }

    @Override
	public BigDecimal getAccruedBalanceForPrincipal(String principalId,
			AccrualCategory accrualCategory, Date asOfDate) {
    	BigDecimal balance = new BigDecimal(0);
    	PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
    	List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksWithAccrualCategory(principalId, pha.getServiceDate(), new java.sql.Date(asOfDate.getTime()), accrualCategory.getAccrualCategory());
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
			AccrualCategory accrualCategory, Date asOfDate) {
    	BigDecimal balance = new BigDecimal(0);
    	PrincipalHRAttributes pha = TkServiceLocator.getPrincipalHRAttributeService().getPrincipalCalendar(principalId, asOfDate);
    	List<LeaveBlock> leaveBlocks = TkServiceLocator.getLeaveBlockService().getLeaveBlocksWithAccrualCategory(principalId, pha.getServiceDate(), new java.sql.Date(asOfDate.getTime()), accrualCategory.getAccrualCategory());
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
