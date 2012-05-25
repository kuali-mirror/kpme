package org.kuali.hr.lm.accrual.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.lm.leaveblock.LeaveBlock;

public class AccrualServiceImpl implements AccrualService {

	@Override
	public void runAccrual(String principalId) {
		Date startDate = getStartAccrualDate(principalId);
		Date endDate = getEndAccrualDate(principalId);

		System.out.println("AccrualServiceImpl.runAccrual() STARTED with Principal: "+principalId);
		
		runAccrual(principalId,startDate,endDate);
		
	}

	@Override
	public void runAccrual(String principalId, Date startDate, Date endDate) {
		List<LeaveBlock> accrualLeaveBlocks = new ArrayList<LeaveBlock>();
		Map<String, BigDecimal> accumulatedAccrualCatToAccrualAmounts = new HashMap<String,BigDecimal>();
		if (startDate != null && endDate != null) {
			System.out.println("AccrualServiceImpl.runAccrual() STARTED with Principal: "+principalId+" Start: "+startDate.toString()+" End: "+endDate.toString());
		}
		//Inactivate all previous accrual entries for this span of time
		//Fetch Leave Plan for user from hr principal attributes
		//Fetch List of AccrualCategories for leave plan
		//Fetch the List of all system scheduled time off values for this span of time
		//Build a rate range aggregate with appropriate information for this period of time detailing Rate Ranges for job
		//entries for this range of time
		
		
		//Iterate over every day in span 
		//Determine which accrual rules apply for today and iterate over those
		//Fetch the accural rate based on rate range for today(Rate range is the accumulated list of jobs and accrual rate for today)
		//Add to total accumulatedAccrualCatToAccrualAmounts
		//Determine if we are at the accrual earn interval in the span, if so add leave block for accumulated accrual amount to list
		//and reset accumulatedAccrualCatToAccrualAmounts for this accrual category
		//Determine if today is a system scheduled time off and accrue holiday if so.  
		
		//Save accrual leave blocks at the very end
		
	}
	
	public Date getStartAccrualDate(String principalId){
		return null;
	}
	
	public Date getEndAccrualDate(String principalId){
		//KPME-1246  Fetch planning months
		
		return null;
	}

	@Override
	public void runAccrual(List<String> principalIds) {
		for(String principalId : principalIds){
			runAccrual(principalId);
		}
	}

}
