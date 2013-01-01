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

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.syslunch.rule.SystemLunchRule;
import org.springframework.cache.annotation.Cacheable;

public interface SystemLunchRuleService {
	/**
	 * Fetch SystemLunchRule as of a particular date
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= SystemLunchRule.CACHE_NAME, key="'asOfDate=' + #p0")
	public SystemLunchRule getSystemLunchRule(Date asOfDate);
	/**
	 * Determines if the Lunch button should show
	 * @return
	 */
	public boolean isShowLunchButton();

    @Cacheable(value= SystemLunchRule.CACHE_NAME, key="'tkSystemLunchRuleId=' + #p0")
	public SystemLunchRule getSystemLunchRule(String tkSystemLunchRuleId);

    List<SystemLunchRule> getSystemLunchRules(Date fromEffdt, Date toEffdt, String active, String showHist);
}
