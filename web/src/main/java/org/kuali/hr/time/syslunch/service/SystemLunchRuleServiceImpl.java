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
package org.kuali.hr.time.syslunch.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.syslunch.dao.SystemLunchRuleDao;
import org.kuali.hr.time.syslunch.rule.SystemLunchRule;

public class SystemLunchRuleServiceImpl implements SystemLunchRuleService {
	public SystemLunchRuleDao systemLunchRuleDao;

	@Override
	public SystemLunchRule getSystemLunchRule(LocalDate asOfDate) {
		return systemLunchRuleDao.getSystemLunchRule(asOfDate);
	}

	public SystemLunchRuleDao getSystemLunchRuleDao() {
		return systemLunchRuleDao;
	}

	public void setSystemLunchRuleDao(SystemLunchRuleDao systemLunchRuleDao) {
		this.systemLunchRuleDao = systemLunchRuleDao;
	}

	@Override
	public boolean isShowLunchButton() {

    	Boolean isShowLunchButton = false;
    	SystemLunchRule systemLunchrule = TkServiceLocator.getSystemLunchRuleService().getSystemLunchRule(LocalDate.now());
    	if(systemLunchrule != null) {
    		isShowLunchButton = systemLunchrule.getShowLunchButton();
    	}

		return isShowLunchButton;
	}

	@Override
	public SystemLunchRule getSystemLunchRule(String tkSystemLunchRuleId) {
		return systemLunchRuleDao.getSystemLunchRule(tkSystemLunchRuleId);
	}

    @Override
    public List<SystemLunchRule> getSystemLunchRules(LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist){
        return systemLunchRuleDao.getSystemLunchRules(fromEffdt, toEffdt, active, showHist);
    }
}
