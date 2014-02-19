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
package org.kuali.kpme.core.api.accrualcategory.rule;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategory;
import org.kuali.kpme.core.api.accrualcategory.AccrualCategoryContract;
import org.kuali.kpme.core.api.accrualcategory.rule.AccrualCategoryRuleContract;
import org.springframework.cache.annotation.Cacheable;

public interface AccrualCategoryRuleService {
    /**
     * Fetch list of active accrual category rules by given accrual category id
     * @param accrualCategoryId
     * @return
     */
    @Cacheable(value= AccrualCategoryRuleContract.CACHE_NAME, key="'accrualCategoryId=' + #p0")
    public List <AccrualCategoryRule> getActiveAccrualCategoryRules(String accrualCategoryId);
    
    /**
     * Fetch accrual category rule by unique id
     * @param lmAccrualCategoryRuleId
     * @return
     */
    @Cacheable(value= AccrualCategoryRuleContract.CACHE_NAME, key="'lmAccrualCategoryRuleId=' + #p0")
    public AccrualCategoryRule getAccrualCategoryRule(String lmAccrualCategoryRuleId);
    
    /**
     * Fetch the accrual category rule applies for the given date and accrualCategory
     */
    @Cacheable(value= AccrualCategoryRuleContract.CACHE_NAME, key="'accrualCategory=' + #p0.getLmAccrualCategoryId() + '|' + 'asOfDate=' + #p1 + '|' + 'serviceDate=' + #p2")
    public AccrualCategoryRule getAccrualCategoryRuleForDate(AccrualCategory accrualCategory, LocalDate currentDate, LocalDate serviceDate);
    
    public List <AccrualCategoryRule> getActiveRulesForAccrualCategoryId(String accrualCategoryId);
    
    public List <AccrualCategoryRule> getInActiveRulesForAccrualCategoryId(String accrualCategoryId);

}
