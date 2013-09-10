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
package org.kuali.kpme.tklm.time.rules.timecollection.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.springframework.cache.annotation.Cacheable;

public interface TimeCollectionRuleService {
	
    @Cacheable(value= TimeCollectionRule.CACHE_NAME,
            key="'dept=' + #p0" +
                "+ '|' + 'workArea=' + #p1" +
                "+ '|' + 'payType=' + #p2" +
                "+ '|' + 'asOfDate=' + #p3")
	public TimeCollectionRule getTimeCollectionRule(String dept, Long workArea, String payType, LocalDate asOfDate);

    @Cacheable(value= TimeCollectionRule.CACHE_NAME, key="'tkTimeCollectionRuleId=' + #p0")
	public TimeCollectionRule getTimeCollectionRule(String tkTimeCollectionRuleId);

    List<TimeCollectionRule> getTimeCollectionRules(String userPrincipalId, String dept, String workArea, String payType, String active, String showHistory);
}
