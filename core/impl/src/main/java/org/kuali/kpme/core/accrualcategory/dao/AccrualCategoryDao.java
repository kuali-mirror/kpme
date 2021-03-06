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
package org.kuali.kpme.core.accrualcategory.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategoryBo;

public interface AccrualCategoryDao {

	/**
	 * Fetch accrual category as of a particular date
	 * @param accrualCategory
	 * @param asOfDate
	 * @return
	 */
    public AccrualCategoryBo getAccrualCategory(String accrualCategory, LocalDate asOfDate);
    /**
     * Fetch accrual category by a unique id
     * @param lmAccrualCategoryId
     * @return
     */
    public AccrualCategoryBo getAccrualCategory(String lmAccrualCategoryId);
    /**
     * Fetch list of active accrual categories
     * @param asOfDate
     * @return
     */
    public List<AccrualCategoryBo> getActiveAccrualCategories(LocalDate asOfDate);

    List<AccrualCategoryBo> getAccrualCategories(String accrualCategory, String descr, String leavePlan, String accrualEarnInterval, String unitOfTime, String minPercentWorked, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory);

    /**
     * 
     * @param leavePlan
     * @param asOfDate
     * @return
     */
   public List<AccrualCategoryBo> getActiveAccrualCategories(String leavePlan, LocalDate asOfDate);
     
    public List<AccrualCategoryBo> getActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate);
	
    
    public List <AccrualCategoryBo> getInActiveLeaveAccrualCategoriesForLeavePlan(String leavePlan, LocalDate asOfDate);

}
