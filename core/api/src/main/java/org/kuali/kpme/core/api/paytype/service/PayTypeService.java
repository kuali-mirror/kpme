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
package org.kuali.kpme.core.api.paytype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.AssignmentContract;
import org.kuali.kpme.core.api.block.CalendarBlockPermissions;
import org.kuali.kpme.core.api.paytype.PayTypeContract;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface PayTypeService {
	/**
	 * Save or Update a Paytype
	 * @param payType
	 */
    @CacheEvict(value={PayTypeContract.CACHE_NAME, AssignmentContract.CACHE_NAME, CalendarBlockPermissions.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(PayTypeContract payType);
	/**
	 * Save or Update a List of PayType objects
	 * @param payTypeList
	 */
    @CacheEvict(value={PayTypeContract.CACHE_NAME, AssignmentContract.CACHE_NAME, CalendarBlockPermissions.CACHE_NAME}, allEntries = true)
	public void saveOrUpdate(List<? extends PayTypeContract> payTypeList);
	
	/**
	 * Provides access to the PayType.   The PayCalendar will be loaded as well.
	 * @param payType
	 * @return A fully populated PayType.
	 */
    @Cacheable(value= PayTypeContract.CACHE_NAME, key="'payType=' + #p0 + '|' + 'effectiveDate=' + #p1")
	public PayTypeContract getPayType(String payType, LocalDate effectiveDate);

    @Cacheable(value= PayTypeContract.CACHE_NAME, key="'hrPayTypeId=' + #p0")
	public PayTypeContract getPayType(String hrPayTypeId);
	
	/**
	 * get count of pay type with give payType
	 * @param payType
	 * @return int
	 */
	public int getPayTypeCount(String payType);

    List<? extends PayTypeContract> getPayTypes(String payType, String regEarnCode, String descr, String location, String institution, String flsaStatus,
    		String payFrequency, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist);
}
