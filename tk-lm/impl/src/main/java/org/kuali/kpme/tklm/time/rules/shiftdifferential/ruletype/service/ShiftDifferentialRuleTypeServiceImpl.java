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

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ruletype.ShiftDifferentialRuleType;
import org.kuali.kpme.tklm.time.rules.shiftdifferential.ruletype.dao.ShiftDifferentialRuleTypeDao;


public class ShiftDifferentialRuleTypeServiceImpl implements ShiftDifferentialRuleTypeService {

	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ShiftDifferentialRuleTypeServiceImpl.class);
	
	private ShiftDifferentialRuleTypeDao shiftDifferentialRuleTypeDao = null;

	public ShiftDifferentialRuleTypeDao getShiftDifferentialRuleTypeDao() {
		return shiftDifferentialRuleTypeDao;
	}

	public void setShiftDifferentialRuleTypeDao(
			ShiftDifferentialRuleTypeDao shiftDifferentialRuleTypeDao) {
		this.shiftDifferentialRuleTypeDao = shiftDifferentialRuleTypeDao;
	}

	@Override
	public ShiftDifferentialRuleType getShiftDifferentialRuleType(
			String tkShiftDifferentialRuleTypeId) {
		return shiftDifferentialRuleTypeDao.getShiftDifferentialRuleType(tkShiftDifferentialRuleTypeId);
	}

	@Override
	public ShiftDifferentialRuleType getActiveShiftDifferentialRuleType(
			String name, LocalDate asOfDate) {
		return shiftDifferentialRuleTypeDao.getActiveShiftDifferentialRuleType(name, asOfDate);
	}
	
	

}
