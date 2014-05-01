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
package org.kuali.kpme.pm.positionreporttype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.positionreporttype.PositionReportType;
import org.kuali.kpme.pm.api.positionreporttype.service.PositionReportTypeService;
import org.kuali.kpme.pm.positionreporttype.PositionReportTypeBo;
import org.kuali.kpme.pm.positionreporttype.dao.PositionReportTypeDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class PositionReportTypeServiceImpl implements PositionReportTypeService {
	
	 private PositionReportTypeDao positionReportTypeDao;
	
	
	public PositionReportTypeDao getPositionReportTypeDao() {
		return positionReportTypeDao;
	}

	public void setPositionReportTypeDao(PositionReportTypeDao positionReportTypeDao) {
		this.positionReportTypeDao = positionReportTypeDao;
	}
	
	@Override
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId) {
		return PositionReportTypeBo.to(positionReportTypeDao.getPositionReportTypeById(pmPositionReportTypeId));
	}

	@Override
	public List<PositionReportType> getPositionReportTypeList(String positionReportType, String groupKeyCode, LocalDate asOfDate) {
		return ModelObjectUtils.transform(positionReportTypeDao.getPositionReportTypeList(positionReportType, groupKeyCode, asOfDate),PositionReportTypeBo.toImmutable);
	}
	
	@Override
	public List<PositionReportType> getPositionReportTypeListByType(String positionReportType) {
		return ModelObjectUtils.transform(positionReportTypeDao.getPositionReportTypeListByType(positionReportType),PositionReportTypeBo.toImmutable);
	}
	
	@Override
	public List<PositionReportType> getPrtListWithInstitutionCodeAndDate(String institutionCode, LocalDate asOfDate) {
		return ModelObjectUtils.transform(positionReportTypeDao.getPrtListWithInstitutionCodeAndDate(institutionCode, asOfDate),PositionReportTypeBo.toImmutable);
	}

	@Override
	public List<PositionReportType> getPrtListWithLocationAndDate(String location,LocalDate asOfDate) {
		return ModelObjectUtils.transform(positionReportTypeDao.getPrtListWithLocationAndDate(location, asOfDate),PositionReportTypeBo.toImmutable);
	}

	@Override
	public PositionReportType getPositionReportType(
			String positionReportType, LocalDate asOfDate) {
		return PositionReportTypeBo.to(positionReportTypeDao.getPositionReportType(positionReportType, asOfDate));
	}

}
