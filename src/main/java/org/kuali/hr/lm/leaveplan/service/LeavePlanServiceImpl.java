/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.leaveplan.service;

import org.apache.log4j.Logger;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.lm.leaveplan.dao.LeavePlanDao;

import java.sql.Date;
import java.util.List;

public class LeavePlanServiceImpl implements LeavePlanService {

	private static final Logger LOG = Logger.getLogger(LeavePlanServiceImpl.class);
	private LeavePlanDao leavePlanDao;

 
	public LeavePlanDao getLeavePlanDao() {
		return leavePlanDao;
	}


	public void setLeavePlanDao(LeavePlanDao leavePlanDao) {
		this.leavePlanDao = leavePlanDao;
	}


	@Override
	public LeavePlan getLeavePlan(String lmLeavePlanId) {
		return getLeavePlanDao().getLeavePlan(lmLeavePlanId);
	}
	
	@Override
	public LeavePlan getLeavePlan(String leavePlan, Date asOfDate) {
		return getLeavePlanDao().getLeavePlan(leavePlan, asOfDate);
	}
   
	@Override
	public boolean isValidLeavePlan(String leavePlan){
		boolean valid = false;
		int count = getLeavePlanDao().getNumberLeavePlan(leavePlan);
		valid = (count > 0);
		return valid;
	}
	
	@Override
	public List<LeavePlan> getAllActiveLeavePlan(String leavePlan, Date asOfDate) {
		 return leavePlanDao.getAllActiveLeavePlan(leavePlan, asOfDate);
	 }
	@Override
	public List<LeavePlan> getAllInActiveLeavePlan(String leavePlan, Date asOfDate) {
		 return leavePlanDao.getAllInActiveLeavePlan(leavePlan, asOfDate);
	 }

    @Override
    public List<LeavePlan> getLeavePlans(String leavePlan, String calendarYearStart, String descr, String planningMonths, Date fromEffdt, Date toEffdt, String active, String showHistory) {
        return leavePlanDao.getLeavePlans(leavePlan, calendarYearStart, descr, planningMonths, fromEffdt, toEffdt, active, showHistory);
    }
}
