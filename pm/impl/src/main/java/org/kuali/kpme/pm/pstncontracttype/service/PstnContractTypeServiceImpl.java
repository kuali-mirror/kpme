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
package org.kuali.kpme.pm.pstncontracttype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.pstncontracttype.PstnContractType;
import org.kuali.kpme.pm.pstncontracttype.dao.PstnContractTypeDao;

public class PstnContractTypeServiceImpl implements PstnContractTypeService {

	private PstnContractTypeDao pstnContractTypeDao;

	public PstnContractTypeDao getPstnContractTypeDao() {
		return pstnContractTypeDao;
	}

	public void setPstnContractTypeDao(
			PstnContractTypeDao pstnContractTypeDao) {
		this.pstnContractTypeDao = pstnContractTypeDao;
	}


	@Override
	public PstnContractType getPstnContractTypeById(
			String pmPositionTypeId) {
		return pstnContractTypeDao.getPstnContractTypeById(pmPositionTypeId);
	}

	@Override
	public List<PstnContractType> getPstnContractTypeList(String institution, String location, LocalDate asOfDate) {
		return pstnContractTypeDao.getPstnContractTypeList( institution, location, asOfDate);
	}

}
