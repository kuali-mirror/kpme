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
package org.kuali.kpme.core.accrualcategory.rule.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.accrualcategory.rule.AccrualCategoryRule;

public interface AccrualCategoryRuleService {
	
    /**
     * Fetch list of active accrual category rules by given accrual category id
     * @param accrualCategoryId
     * @return
     */
    public List <AccrualCategoryRule> getActiveAccrualCategoryRules(String accrualCategoryId);
    
    /**
     * Fetch accrual category rule by unique id
     * @param lmAccrualCategoryRuleId
     * @return
     */
    public AccrualCategoryRule getAccrualCategoryRule(String lmAccrualCategoryRuleId);
    
    /**
     * Fetch the accrual category rule applies for the given date and accrualCategory
     */
    public AccrualCategoryRule getAccrualCategoryRuleForDate(AccrualCategory accrualCategory, LocalDate currentDate, LocalDate serviceDate);
    
    public List <AccrualCategoryRule> getActiveRulesForAccrualCategoryId(String accrualCategoryId);
    
    public List <AccrualCategoryRule> getInActiveRulesForAccrualCategoryId(String accrualCategoryId);

}
