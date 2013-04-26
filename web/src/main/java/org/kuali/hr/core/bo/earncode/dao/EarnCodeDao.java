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
package org.kuali.hr.core.bo.earncode.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.core.bo.earncode.EarnCode;

public interface EarnCodeDao {

	/** Get an earn code by "row id" explicitly */
	public EarnCode getEarnCodeById(String earnCodeId);
	
	/** Provides access to earn code by name, using effdt, timestamp and active as qualifiers */
	public EarnCode getEarnCode(String earnCode, LocalDate asOfDate);
	
	public List<EarnCode> getOvertimeEarnCodes(LocalDate asOfDate);

	public int getEarnCodeCount(String earnCode);
	
	public int getNewerEarnCodeCount(String earnCode, LocalDate effdt);
	
	public List<EarnCode> getEarnCodes(String leavePlan, LocalDate asOfDate);

    List<EarnCode> getEarnCodes(String earnCode, String ovtEarnCode, String descr, String leavePlan, String accrualCategory, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist);
}
