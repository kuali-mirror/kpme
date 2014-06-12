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
package org.kuali.kpme.pm.positiontype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.positiontype.PositionType;
import org.kuali.kpme.pm.api.positiontype.service.PositionTypeService;
import org.kuali.kpme.pm.positiontype.PositionTypeBo;
import org.kuali.kpme.pm.positiontype.dao.PositionTypeDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class PositionTypeServiceImpl implements PositionTypeService {

	private PositionTypeDao positionTypeDao;

	protected List<PositionType> convertToImmutable(List<PositionTypeBo> bos) {
		return ModelObjectUtils.transform(bos, PositionTypeBo.toImmutable);
	}

	@Override
	public PositionType getPositionTypeById(String pmPositionTypeId) {
		return PositionTypeBo.to(positionTypeDao
				.getPositionTypeById(pmPositionTypeId));
	}

	@Override
	public List<PositionType> getPositionTypeList(String positionType, LocalDate asOfDate) {

		List<PositionTypeBo> bos = positionTypeDao.getPositionTypeList(
				positionType, asOfDate);
		return convertToImmutable(bos);
	}

	public PositionTypeDao getPositionTypeDao() {
		return positionTypeDao;
	}

	public void setPositionTypeDao(PositionTypeDao positionTypeDao) {
		this.positionTypeDao = positionTypeDao;
	}

	@Override
	public PositionType getPositionType(String positionType,
			LocalDate asOfDate) {
		return PositionTypeBo.to(positionTypeDao.getPositionType(positionType, asOfDate));
	}

}
