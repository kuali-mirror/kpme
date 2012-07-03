package org.kuali.hr.lm.accrual.service;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.hr.lm.LMConstants;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.accrual.RateRangeAggregate;
import org.kuali.hr.lm.accrual.dao.AccrualCategoryDao;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.cache.CacheResult;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;

public class AccrualCategoryServiceImpl implements AccrualCategoryService {

	private static final Logger LOG = Logger.getLogger(AccrualCategoryServiceImpl.class);
	private AccrualCategoryDao accrualCategoryDao;
	public AccrualCategoryServiceImpl() {
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public AccrualCategory getAccrualCategory(String accrualCategory, Date asOfDate) {
		return accrualCategoryDao.getAccrualCategory(accrualCategory, asOfDate);
	}
/*
	@Override
	public void saveOrUpdate(AccrualCategory accrualCategory) {
		accrualCategoryDao.saveOrUpdate(accrualCategory);
	}
*/
	public AccrualCategoryDao getAccrualCategoryDao() {
		return accrualCategoryDao;
	}

	public void setAccrualCategoryDao(AccrualCategoryDao accrualCategoryDao) {
		this.accrualCategoryDao = accrualCategoryDao;
	}

	@Override
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public AccrualCategory getAccrualCategory(String lmAccrualCategoryId) {
		return accrualCategoryDao.getAccrualCategory(lmAccrualCategoryId);
	}

	@Override	
	@CacheResult(secondsRefreshPeriod=TkConstants.DEFAULT_CACHE_TIME)
	public List <AccrualCategory> getActiveAccrualCategories(Date asOfDate){
		return accrualCategoryDao.getActiveAccrualCategories(asOfDate);
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
	
	private RateRangeAggregate getRateRangeAggregate(String principalId, Date asOfDate, String calendar){
		Calendar leaveCal = TkServiceLocator.getCalendarService().getCalendarByGroup(calendar);
		CalendarEntries leaveCalEntry = TkServiceLocator.getCalendarEntriesService().getCurrentCalendarEntriesByCalendarId(leaveCal.getHrCalendarId(),asOfDate);
		
		
		return null;
	}
	
	private AccrualCategoryRule getAccrualRuleForServiceDate(String serviceUnitOfTime, Date serviceDate, List<AccrualCategoryRule> accrualCatRules){
		int months = 0;
		
		if(StringUtils.equals(serviceUnitOfTime, LMConstants.SERVICE_TIME_YEAR)){
			
		}
		
		
		for(AccrualCategoryRule accrualCatRule : accrualCatRules){
			//accrualCatRule.get
		}
		
		return null;
	}
	
    public List <AccrualCategory> getActiveAccrualCategoriesForLeavePlan(String leavePlan, Date asOfDate) {
    	return accrualCategoryDao.getActiveAccrualCategories(leavePlan, asOfDate);
    }
}
