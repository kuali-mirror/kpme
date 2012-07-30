package org.kuali.hr.lm.accrual.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.accrual.dao.AccrualCategoryRuleDao;
import org.kuali.hr.time.util.TKUtils;

public class AccrualCategoryRuleServiceImpl implements AccrualCategoryRuleService{
	
	private AccrualCategoryRuleDao accrualCategoryRuleDao;
	
	public List <AccrualCategoryRule> getActiveAccrualCategoryRules(String accrualCategoryId) {
		return accrualCategoryRuleDao.getActiveAccrualCategoryRules(accrualCategoryId);
	}
	 
	public AccrualCategoryRule getAccrualCategoryRule(String lmAccrualCategoryRuleId) {
		return accrualCategoryRuleDao.getAccrualCategoryRule(lmAccrualCategoryRuleId);
			
	}
	
    public AccrualCategoryRule getAccrualCategoryRuleForDate(AccrualCategory accrualCategory, Date currentDate, Date serviceDate) {
    	List <AccrualCategoryRule> acrList = this.getActiveAccrualCategoryRules(accrualCategory.getLmAccrualCategoryId());
    	Calendar startCal = new GregorianCalendar();
    	Calendar endCal = new GregorianCalendar();
    	for(AccrualCategoryRule acr : acrList) {
    		String uot = acr.getServiceUnitOfTime();
    		int startTime = acr.getStart().intValue();
			int endTime = acr.getEnd().intValue();
			
			startCal.setTime(serviceDate);
			endCal.setTime(serviceDate);
    		if(uot.equals("M")) {		// monthly
    			startCal.add(Calendar.MONTH, startTime);
    			endCal.add(Calendar.MONTH, endTime);
    			endCal.add(Calendar.DATE, -1);
    		} else if(uot.endsWith("Y")) { // yearly
    			startCal.add(Calendar.YEAR, startTime);
    			endCal.add(Calendar.YEAR, endTime);
    			endCal.add(Calendar.DATE, -1);
    		}
    		
    		// max days in months differ, if the date is bigger than the max day, set it to the max day of the month
			if(startCal.getActualMaximum(Calendar.DAY_OF_MONTH) < startCal.get(Calendar.DATE)) {
				startCal.set(Calendar.DATE, startCal.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
			if(endCal.getActualMaximum(Calendar.DAY_OF_MONTH) < endCal.get(Calendar.DATE)) {
				endCal.set(Calendar.DATE, endCal.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
    		
    		if(TKUtils.removeTime(currentDate).compareTo(TKUtils.removeTime(startCal.getTime())) >= 0 
    				&& TKUtils.removeTime(currentDate).compareTo(TKUtils.removeTime(endCal.getTime())) <=0 ) {
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
	public List <AccrualCategoryRule> getActiveRulesForAccrualCategoryId(String accrualCategoryId, Date asOfDate) {
		return this.accrualCategoryRuleDao.getActiveRulesForAccrualCategoryId(accrualCategoryId, asOfDate);
	}
    @Override
    public List <AccrualCategoryRule> getInActiveRulesForAccrualCategoryId(String accrualCategoryId, Date asOfDate) {
    	return this.accrualCategoryRuleDao.getInActiveRulesForAccrualCategoryId(accrualCategoryId, asOfDate);
    }
	
}
