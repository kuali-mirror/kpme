/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.paygrade.dao;

import org.kuali.hr.paygrade.PayGrade;

import java.sql.Date;

public interface PayGradeDao {
	/**
	 * Get paygrade as of a particular date
	 * @param payGrade
	 * @param asOfDate
	 * @return
	 */
	public PayGrade getPayGrade(String payGrade,Date asOfDate);
	/**
	 * Get paygrade by unique id
	 * @param hrPayGradeId
	 * @return
	 */
	public PayGrade getPayGrade(String hrPayGradeId);
	
	public int getPayGradeCount(String payGrade);
}
