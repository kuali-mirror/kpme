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
package org.kuali.hr.core.bo.earncode.group.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.core.bo.earncode.EarnCode;
import org.kuali.hr.core.bo.earncode.group.EarnCodeGroup;

public interface EarnCodeGroupDaoService {
	public EarnCodeGroup getEarnCodeGroup(String earnCodeGroup, LocalDate asOfDate);
	public EarnCodeGroup getEarnCodeGroupForEarnCode(String earnCode, LocalDate asOfDate);
	public EarnCodeGroup getEarnCodeGroupSummaryForEarnCode(String earnCode, LocalDate asOfDate);
	public EarnCodeGroup getEarnCodeGroup(String hrEarnGroupId);
	public int getEarnCodeGroupCount(String earnGroup);
	public int getNewerEarnCodeGroupCount(String earnGroup, LocalDate effdt);
	public List<EarnCode> getEarnCodeGroups(String earnCodeGroup, String descr, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist);
}
