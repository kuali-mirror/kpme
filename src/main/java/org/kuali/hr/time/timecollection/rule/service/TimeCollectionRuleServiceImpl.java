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
package org.kuali.hr.time.timecollection.rule.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.timecollection.rule.dao.TimeCollectionRuleDaoService;

import java.sql.Date;
import java.util.List;

public class TimeCollectionRuleServiceImpl implements TimeCollectionRuleService{
	private TimeCollectionRuleDaoService timeCollectRuleDao;

    @Override
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, String payType, Date asOfDate){
		return timeCollectRuleDao.getTimeCollectionRule(dept, workArea, payType, asOfDate);
	}

    @Override
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea,Date asOfDate){
		return timeCollectRuleDao.getTimeCollectionRule(dept, workArea, asOfDate);
	}

	public TimeCollectionRuleDaoService getTimeCollectRuleDao() {
		return timeCollectRuleDao;
	}

	public void setTimeCollectRuleDao(
			TimeCollectionRuleDaoService timeCollectRuleDao) {
		this.timeCollectRuleDao = timeCollectRuleDao;
	}

	@Override
	public TimeCollectionRule getTimeCollectionRule(String tkTimeCollectionRuleId) {
		return timeCollectRuleDao.getTimeCollectionRule(tkTimeCollectionRuleId);
	}

    @Override
    public List<TimeCollectionRule> getTimeCollectionRules(String dept, String workArea, String payType, String active) {
        Long workAreaToSearch = StringUtils.isEmpty(workArea) ? null : Long.parseLong(workArea);
        return timeCollectRuleDao.getTimeCollectionRules(dept, workAreaToSearch , payType, active, null);
    }
}
