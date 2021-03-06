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
package org.kuali.kpme.pm.positionreportcat.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.api.positionreportcat.PositionReportCategory;
import org.kuali.kpme.pm.api.positionreportcat.service.PositionReportCatService;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategoryBo;
import org.kuali.kpme.pm.positionreportcat.dao.PositionReportCatDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class PositionReportCatServiceImpl implements PositionReportCatService {
	
	private PositionReportCatDao positionReportCatDao;
	
	@Override
	public PositionReportCategory getPositionReportCatById(String pmPositionReportCatId) {
		return PositionReportCategoryBo.to(positionReportCatDao.getPositionReportCatById(pmPositionReportCatId));
	}
	
	@Override
	public List<PositionReportCategory> getPositionReportCatList(String positionReportCat, String positionReportType, LocalDate asOfDate) {
		return ModelObjectUtils.transform(positionReportCatDao.getPositionReportCatList(positionReportCat, positionReportType, asOfDate),PositionReportCategoryBo.toImmutable);
	}

	public PositionReportCatDao getPositionReportCatDao() {
		return positionReportCatDao;
	}

	public void setPositionReportCatDao(PositionReportCatDao positionReportCatDao) {
		this.positionReportCatDao = positionReportCatDao;
	}

	@Override
	public PositionReportCategory getPositionReportCat(String positionReportCat, LocalDate asOfDate) {
		return PositionReportCategoryBo.to(positionReportCatDao.getPositionReportCat(positionReportCat, asOfDate));
	}
}
