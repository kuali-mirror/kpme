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
package org.kuali.kpme.core.bo.paygrade.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.paygrade.PayGrade;
import org.springframework.cache.annotation.Cacheable;

public interface PayGradeService {
	/**
	 * Pulls back a particular paygrade as of a particular date
	 * @param payGrade
	 * @param salGroup 
	 * @param asOfDate
	 * @return
	 */
    @Cacheable(value= PayGrade.CACHE_NAME, key="'payGrade=' + #p0 + '|' + 'salGroup=' + #p1 + '|' + 'asOfDate=' + #p2")
	public PayGrade getPayGrade(String payGrade, String salGroup, LocalDate asOfDate);
	/**
	 * Get pay grade by a unique id
	 * @param hrPayGradeId
	 * @return
	 */
    @Cacheable(value= PayGrade.CACHE_NAME, key="'hrPayGradeId=' + #p0")
	public PayGrade getPayGrade(String hrPayGradeId);
	/**
	 * get count of pay grade with given payGrade
	 * @param payGrade
	 * @return int
	 */
	public int getPayGradeCount(String payGrade);
	
    List<PayGrade> getPayGrades(String payGrade, String payGradeDescr, String active);

}
