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
package org.kuali.hr.core.earncode.security.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.core.earncode.security.EarnCodeSecurity;
import org.springframework.cache.annotation.Cacheable;

public interface EarnCodeSecurityService {

	/** This should handle wild cards on department and hr_sal_group.
	 * 
	 */
    @Cacheable(value= EarnCodeSecurity.CACHE_NAME,
            key="'department=' + #p0" +
                    "+ '|' + 'hrSalGroup=' + #p1" +
                    "+ '|' + 'location=' + #p2" +
                    "+ '|' + 'asOfDate=' + #p3")
	public List<EarnCodeSecurity> getEarnCodeSecurities(String department, String hrSalGroup, String location, LocalDate asOfDate);
	
	/**
	 * Fetch department earn code by id
	 * @param hrDeptEarnCodeId
	 * @return
	 */
    @Cacheable(value= EarnCodeSecurity.CACHE_NAME, key="'hrEarnCodeSecId=' + #p0")
	public EarnCodeSecurity getEarnCodeSecurity(String hrEarnCodeSecId);
	
	public List<EarnCodeSecurity> searchEarnCodeSecurities(String dept, String salGroup, String earnCode, String location,
			LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);
	
    /**
     * get the count of Department Earn Code by given parameters
     * @param earnGroup
     * @return int
     */
	public int getEarnCodeSecurityCount(String dept, String salGroup, String earnCode, String employee, String approver, String location,
			String active, LocalDate effdt,String hrDeptEarnCodeId);
	
    /**
     * get the count of newer versions of the given earnCode
     * @param earnCode
     * @param effdt
     * @return int
     */
	public int getNewerEarnCodeSecurityCount(String earnCode, LocalDate effdt);
}
