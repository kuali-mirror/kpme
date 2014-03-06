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
package org.kuali.kpme.core.earncode.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.core.earncode.EarnCodeBo;

public interface EarnCodeDao {

	/** Get an earn code by "row id" explicitly */
	public EarnCodeBo getEarnCodeById(String earnCodeId);
	
	/** Provides access to earn code by name, using effdt, timestamp and active as qualifiers */
	public EarnCodeBo getEarnCode(String earnCode, LocalDate asOfDate);
	
	public List<EarnCodeBo> getOvertimeEarnCodes(LocalDate asOfDate);

	public int getEarnCodeCount(String earnCode);
	
	public int getNewerEarnCodeCount(String earnCode, LocalDate effdt);
	
	public List<EarnCodeBo> getEarnCodes(String leavePlan, LocalDate asOfDate);

    List<EarnCodeBo> getEarnCodes(String earnCode, String ovtEarnCode, String descr, String leavePlan, String accrualCategory, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist);
}
