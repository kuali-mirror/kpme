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
package org.kuali.kpme.core.accrualcategory.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.springframework.cache.annotation.Cacheable;

public interface AccrualCategoryService {

	/**
	 * Get an AccrualCategory as of a particular date
	 * @param accrualCategory
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= AccrualCategory.CACHE_NAME, key="'accrualCategory=' + #p0 + '|' + 'asOfDate=' + #p1")
    public AccrualCategory getAccrualCategory(String accrualCategory, LocalDate asOfDate);
    /**
     * Save or Update an accrual category
     * @param accrualCategory
     */
    public void saveOrUpdate(AccrualCategory accrualCategory);
    
    /**
     * Fetch accrual category by unique id
     * @param lmAccrualCategoryId
     * @return
     */
    @Cacheable(value= AccrualCategory.CACHE_NAME, key="'accrualCategoryId=' + #p0")
    public AccrualCategory getAccrualCategory(String lmAccrualCategoryId);
    
    /**
     * Fetch list of active accrual categories as of a particular date
     * @param asOfDate
     * @return
     */
    @Cacheable(value= AccrualCategory.CACHE_NAME, key="'asOfDate=' + #p0")
    public List <AccrualCategory> getActiveAccrualCategories(LocalDate asOfDate);

    List <AccrualCategory> getAccrualCategories(String accrualCategory, String accrualCatDescr, String leavePlan, String accrualEarnInterval, String unitOfTime, String minPercentWorked, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);
    
    /**
     * Fetch list of active accrual categories with given leavePlan and date
     * @param leavePlan
     * @param asOfDate
     * @return List <AccrualCategory>
     */
   public List <AccrualCategory> getActiveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate);
     
    public List <AccrualCategory> getActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate);

    
    public List <AccrualCategory> getInActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate);
	
}
