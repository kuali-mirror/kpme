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
package org.kuali.hr.time.earncode.dao;

import org.kuali.hr.lm.leavecode.LeaveCode;
import org.kuali.hr.time.earncode.EarnCode;

import java.sql.Date;
import java.util.List;

public interface EarnCodeDao {

	/** Get an earn code by "row id" explicitly */
	public EarnCode getEarnCodeById(String earnCodeId);
	
	/** Provides access to earn code by name, using effdt, timestamp and active as qualifiers */
	public EarnCode getEarnCode(String earnCode, Date asOfDate);
	
	public List<EarnCode> getOvertimeEarnCodes(Date asOfDate);

	public int getEarnCodeCount(String earnCode);
	
	public int getNewerEarnCodeCount(String earnCode, Date effdt);
	
	public List<EarnCode> getEarnCodes(String leavePlan, Date asOfDate);

    List<EarnCode> getEarnCodes(String earnCode, String ovtEarnCode, String descr, String leavePlan, String accrualCategory, Date fromEffdt, Date toEffdt, String active, String showHist);
}
