/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.tklm.time.rules.clocklocation.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.time.clocklog.ClockLogBo;
import org.kuali.kpme.tklm.time.rules.clocklocation.ClockLocationRule;
import org.kuali.kpme.tklm.time.rules.timecollection.TimeCollectionRule;
import org.springframework.cache.annotation.Cacheable;

public interface ClockLocationRuleService {
	/**
	 * Fetch Clock Location Rule based on criteria
	 * @param dept
	 * @param workArea
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= ClockLocationRule.CACHE_NAME,
            key="'dept=' + #p0" +
                    "+ '|' + 'workArea=' + #p1" +
                    "+ '|' + 'principalId=' + #p2" +
                    "+ '|' + 'jobNumber=' + #p3" +
                    "+ '|' + 'asOfDate=' + #p4")
	public List<ClockLocationRule> getClockLocationRule(String groupKeyCode, String dept, Long workArea,
			String principalId, Long jobNumber, LocalDate asOfDate);
	/**
	 * Process clock location rule based on clock log passed in
	 * @param clockLog
	 * @param asOfDate
	 */
	public void processClockLocationRule(ClockLogBo clockLog, LocalDate asOfDate);
	
	/**
	 * 
	 * @param dept
	 * @param workArea
	 * @param principalId
	 * @param jobNumber
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= ClockLocationRule.CACHE_NAME,
            key="'{getNewerVersionClockLocationRule}' + 'dept=' + #p0" +
                    "+ '|' + 'workArea=' + #p1" +
                    "+ '|' + 'principalId=' + #p2" +
                    "+ '|' + 'jobNumber=' + #p3" +
                    "+ '|' + 'asOfDate=' + #p4")
	public List<ClockLocationRule> getNewerVersionClockLocationRule(String groupKeyCode, String dept, Long workArea, String principalId, 
			Long jobNumber, LocalDate asOfDate);
	/**
	 * 
	 * @param tkClockLocationRuleId
	 * @return
	 */
    @Cacheable(value= ClockLocationRule.CACHE_NAME, key="'tkClockLocationRuleId=' + #p0")
	public ClockLocationRule getClockLocationRule(String tkClockLocationRuleId);

	/**
	 * populate ip addresses for given ClockLocationRule
	 * @param clr
	 * @return
	 */
	public void populateIPAddressesForCLR(ClockLocationRule clr);

    List<ClockLocationRule> getClockLocationRules(String groupKeyCode, String userPrincipalId, LocalDate fromEffdt, LocalDate toEffdt, String principalId, String jobNumber,
                                                  String dept, String workArea, String active, String showHistory);
    
    public boolean isInValidIPClockLocation(String groupKeyCode, String dept, Long workArea,String principalId, Long jobNumber, String ipAddress, LocalDate asOfDate);
    
    public List<ClockLocationRule> getClockLocationRules(String userPrincipalId, List <ClockLocationRule> clockLocationRuleObjs);
}
