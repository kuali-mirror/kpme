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
package org.kuali.kpme.core.bo.paygrade.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.paygrade.PayGrade;

public interface PayGradeDao {
	/**
	 * Get paygrade as of a particular date
	 * @param payGrade
	 * @param asOfDate
	 * @return
	 */
	public PayGrade getPayGrade(String payGrade, String salGroup, LocalDate asOfDate);
	/**
	 * Get paygrade by unique id
	 * @param hrPayGradeId
	 * @return
	 */
	public PayGrade getPayGrade(String hrPayGradeId);
	
	public int getPayGradeCount(String payGrade);

    List<PayGrade> getPayGrades(String payGrade, String payGradeDescr, String active, String showHistory);
    /**
     * Retreives a list of pay grades active on the salary group as of a specific date.
     * @param salaryGroup
     * @param asOfDate
     * @return
     */
	public List<PayGrade> getPayGradesForSalaryGroup(String salaryGroup,
			LocalDate asOfDate);
}
