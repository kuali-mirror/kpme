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
package org.kuali.kpme.core.bo.accrualcategory.rule.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.bo.accrualcategory.rule.AccrualCategoryRule;
import org.kuali.kpme.core.bo.accrualcategory.rule.dao.AccrualCategoryRuleDao;

public class AccrualCategoryRuleServiceImpl implements AccrualCategoryRuleService{
	
	private AccrualCategoryRuleDao accrualCategoryRuleDao;
	
	public List <AccrualCategoryRule> getActiveAccrualCategoryRules(String accrualCategoryId) {
		return accrualCategoryRuleDao.getActiveAccrualCategoryRules(accrualCategoryId);
	}
	 
	public AccrualCategoryRule getAccrualCategoryRule(String lmAccrualCategoryRuleId) {
		return accrualCategoryRuleDao.getAccrualCategoryRule(lmAccrualCategoryRuleId);
			
	}
	
    public AccrualCategoryRule getAccrualCategoryRuleForDate(AccrualCategory accrualCategory, LocalDate currentDate, LocalDate serviceDate) {
    	if(serviceDate == null) {
    		return null;
    	}
    	List <AccrualCategoryRule> acrList = this.getActiveAccrualCategoryRules(accrualCategory.getLmAccrualCategoryId());
    	for(AccrualCategoryRule acr : acrList) {
    		String uot = acr.getServiceUnitOfTime();
    		int startTime = acr.getStart().intValue();
			int endTime = acr.getEnd().intValue();
			
			LocalDate startDate = serviceDate;
			LocalDate endDate = serviceDate;
    		if(uot.equals("M")) {		// monthly
    			startDate = startDate.plusMonths(startTime);
    			endDate = endDate.plusMonths(endTime).minusDays(1);
    		} else if(uot.endsWith("Y")) { // yearly
    			startDate = startDate.plusYears(startTime);
    			endDate = endDate.plusYears(endTime).minusDays(1);
    		}
    		
    		// max days in months differ, if the date is bigger than the max day, set it to the max day of the month
			if(startDate.getDayOfMonth() > startDate.dayOfMonth().getMaximumValue()) {
				startDate = startDate.withDayOfMonth(startDate.dayOfMonth().getMaximumValue());
			}
			if(endDate.getDayOfMonth() > endDate.dayOfMonth().getMaximumValue()) {
				endDate = endDate.withDayOfMonth(endDate.dayOfMonth().getMaximumValue());
			}
    		
    		if(currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <=0 ) {
    			return acr;
    		}
    	}
    	return null;
	}

	public AccrualCategoryRuleDao getAccrualCategoryRuleDao() {
		return accrualCategoryRuleDao;
	}

	public void setAccrualCategoryRuleDao(
			AccrualCategoryRuleDao accrualCategoryRuleDao) {
		this.accrualCategoryRuleDao = accrualCategoryRuleDao;
	}
	@Override
	public List <AccrualCategoryRule> getActiveRulesForAccrualCategoryId(String accrualCategoryId) {
		return this.accrualCategoryRuleDao.getActiveRulesForAccrualCategoryId(accrualCategoryId);
	}
    @Override
    public List <AccrualCategoryRule> getInActiveRulesForAccrualCategoryId(String accrualCategoryId) {
    	return this.accrualCategoryRuleDao.getInActiveRulesForAccrualCategoryId(accrualCategoryId);
    }	
}
