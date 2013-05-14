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
package org.kuali.kpme.tklm.time.rules.graceperiod.service;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.time.rules.graceperiod.GracePeriodRule;
import org.kuali.kpme.tklm.time.rules.graceperiod.dao.GracePeriodDao;

public class GracePeriodServiceImpl implements GracePeriodService {
	private GracePeriodDao gracePeriodDao;

	public GracePeriodDao getGracePeriodDao() {
		return gracePeriodDao;
	}

	public void setGracePeriodDao(GracePeriodDao gracePeriodDao) {
		this.gracePeriodDao = gracePeriodDao;
	}

    @Override
	public GracePeriodRule getGracePeriodRule(LocalDate asOfDate){
		return gracePeriodDao.getGracePeriodRule(asOfDate);
	}

	public DateTime processGracePeriodRule(DateTime actualDateTime, LocalDate asOfDate) {
		DateTime gracePeriodDateTime = actualDateTime;
		
		GracePeriodRule gracePeriodRule = getGracePeriodRule(asOfDate);
		if (gracePeriodRule!=null) {
			//go ahead and round off seconds
			gracePeriodDateTime = gracePeriodDateTime.withSecondOfMinute(0);
			
			BigDecimal minuteIncrement = gracePeriodRule.getHourFactor();
			BigDecimal minutes = new BigDecimal(gracePeriodDateTime.getMinuteOfHour());
			int bottomIntervalFactor = minutes.divide(minuteIncrement, 0, BigDecimal.ROUND_FLOOR).intValue();
			BigDecimal bottomInterval = new BigDecimal(bottomIntervalFactor).multiply(minuteIncrement);
	        BigDecimal topInterval = new BigDecimal(bottomIntervalFactor + 1).multiply(minuteIncrement);
	        if (bottomInterval.subtract(minutes).abs().intValue() < topInterval.subtract(minutes).abs().intValue()) {
	        	gracePeriodDateTime = gracePeriodDateTime.withMinuteOfHour(bottomInterval.setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
	        } else {
	        	if (topInterval.equals(new BigDecimal(60))) {
	        		gracePeriodDateTime = gracePeriodDateTime.withHourOfDay(gracePeriodDateTime.getHourOfDay() + 1).withMinuteOfHour(0);
	        	} else {
	        		gracePeriodDateTime = gracePeriodDateTime.withMinuteOfHour(topInterval.setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
	        	}
	        }
		}
		return gracePeriodDateTime;
	}

	@Override
	public GracePeriodRule getGracePeriodRule(String tkGracePeriodId) {
		return gracePeriodDao.getGracePeriodRule(tkGracePeriodId);
	}

    @Override
    public List<GracePeriodRule> getGracePeriodRules(String hourFactor, String active, String showHistory) {
        return gracePeriodDao.getGracePeriodRules(hourFactor, active, showHistory);
    }
}
