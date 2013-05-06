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

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.time.rules.graceperiod.GracePeriodRule;
import org.springframework.cache.annotation.Cacheable;

public interface GracePeriodService {
	/**
	 * Fetch GracePeriodRule as of a particular date
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= GracePeriodRule.CACHE_NAME, key="'asOfDate=' + #p0")
	public GracePeriodRule getGracePeriodRule(LocalDate asOfDate);
	/**
	 * Process grace period rule as of a particular date with the corresponding timestamp
	 * @param actualDateTime
	 * @param asOfDate
	 * @return
	 */
	public DateTime processGracePeriodRule(DateTime actualDateTime, LocalDate asOfDate);
	
	/**
	 * Fetch Grace period rule by id
	 * @param tkGracePeriodId
	 * @return
	 */
    @Cacheable(value= GracePeriodRule.CACHE_NAME, key="'tkGracePeriodId=' + #p0")
	public GracePeriodRule getGracePeriodRule(String tkGracePeriodId);

    List<GracePeriodRule> getGracePeriodRules(String hourFactor, String active);
}
