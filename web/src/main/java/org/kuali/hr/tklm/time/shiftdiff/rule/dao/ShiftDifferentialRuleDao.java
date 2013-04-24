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
package org.kuali.hr.tklm.time.shiftdiff.rule.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.tklm.time.shiftdiff.rule.ShiftDifferentialRule;

public interface ShiftDifferentialRuleDao {

	public List<ShiftDifferentialRule> findShiftDifferentialRules(String location, String hrSalGroup, String payGrade, String pyCalendarGroup, LocalDate asOfDate);
	public ShiftDifferentialRule findShiftDifferentialRule(String id);
	public void saveOrUpdate(ShiftDifferentialRule shiftDifferentialRule);
	public void saveOrUpdate(List<ShiftDifferentialRule> shiftDifferentialRules);

    public List<ShiftDifferentialRule> getShiftDifferentialRules(String location, String hrSalGroup, String payGrade, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHist);
}
