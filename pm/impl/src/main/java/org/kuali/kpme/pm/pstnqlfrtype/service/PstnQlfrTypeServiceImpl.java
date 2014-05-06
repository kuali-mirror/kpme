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
package org.kuali.kpme.pm.pstnqlfrtype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.pstnqlfrtype.PstnQlfrType;
import org.kuali.kpme.pm.api.pstnqlfrtype.service.PstnQlfrTypeService;
import org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrTypeBo;
import org.kuali.kpme.pm.pstnqlfrtype.dao.PstnQlfrTypeDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class PstnQlfrTypeServiceImpl implements PstnQlfrTypeService {

	private PstnQlfrTypeDao pstnQlfrTypeDao;

	protected List<PstnQlfrType> convertToImmutable(List<PstnQlfrTypeBo> bos) {
		return ModelObjectUtils.transform(bos, PstnQlfrTypeBo.toImmutable);
	}

	@Override
	public PstnQlfrType getPstnQlfrTypeById(String pmPstnQlfrTypeId) {
		return PstnQlfrTypeBo.to(pstnQlfrTypeDao
				.getPstnQlfrTypeById(pmPstnQlfrTypeId));

	}

	@Override
	public PstnQlfrType getPstnQlfrTypeByType(String pmPstnQlfrType) {
		return PstnQlfrTypeBo.to(pstnQlfrTypeDao
				.getPstnQlfrTypeByType(pmPstnQlfrType));

	}

	public PstnQlfrTypeDao getPstnQlfrTypeDao() {
		return pstnQlfrTypeDao;
	}

	public void setPstnQlfrTypeDao(PstnQlfrTypeDao pstnQlfrTypeDao) {
		this.pstnQlfrTypeDao = pstnQlfrTypeDao;
	}

	@Override
	public List<PstnQlfrType> getAllActivePstnQlfrTypes(LocalDate asOfDate) {
		List<PstnQlfrTypeBo> bos = pstnQlfrTypeDao.getAllActivePstnQlfrTypes(asOfDate);
		return convertToImmutable(bos);
	}

}
