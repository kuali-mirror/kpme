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
package org.kuali.hr.time.graceperiod.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.kuali.hr.time.graceperiod.rule.GracePeriodRule;
import org.springframework.cache.annotation.Cacheable;

public interface GracePeriodService {
	/**
	 * Fetch GracePeriodRule as of a particular date
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= GracePeriodRule.CACHE_NAME, key="'asOfDate=' + #p0")
	public GracePeriodRule getGracePeriodRule(Date asOfDate);
	/**
	 * Process grace period rule as of a particular date with the corresponding timestamp
	 * @param actualTime
	 * @param asOfDate
	 * @return
	 */
	public Timestamp processGracePeriodRule(Timestamp actualTime, Date asOfDate);
	
	/**
	 * Fetch Grace period rule by id
	 * @param tkGracePeriodId
	 * @return
	 */
    @Cacheable(value= GracePeriodRule.CACHE_NAME, key="'tkGracePeriodId=' + #p0")
	public GracePeriodRule getGracePeriodRule(String tkGracePeriodId);

    List<GracePeriodRule> getGracePeriodRules(String hourFactor, String active);
}
