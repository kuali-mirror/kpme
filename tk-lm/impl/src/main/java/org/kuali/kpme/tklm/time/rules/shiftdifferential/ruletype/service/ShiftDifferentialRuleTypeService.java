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
package org.kuali.kpme.tklm.time.rules.shiftdifferential.ruletype.service;

import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ruletype.ShiftDifferentialRuleType;
import org.springframework.cache.annotation.Cacheable;

public interface ShiftDifferentialRuleTypeService {
	/**
	 * Fetch a ShiftDifferentialRule object for a given id
	 * @param tkShiftDifferentialRuleId
	 * @return
	 */
    @Cacheable(value= ShiftDifferentialRuleType.CACHE_NAME, key="'tkShiftDifferentialRuleTypeId=' + #p0")
	public ShiftDifferentialRuleType getShiftDifferentialRuleType(String tkShiftDifferentialRuleTypeId);
	
    public ShiftDifferentialRuleType getActiveShiftDifferentialRuleType(String name, LocalDate asOfDate);

}
