package org.kuali.hr.lm.accrual.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryRule;
import org.kuali.hr.lm.accrual.dao.AccrualCategoryRuleDao;

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
    		} else if(uot.endsWith("Y")) { // yearly
    			startCal.add(Calendar.YEAR, startTime);
    			endCal.add(Calendar.YEAR, endTime);
    		}
    		
    		if(currentDate.after(startCal.getTime()) && currentDate.before(endCal.getTime())) {
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
}
