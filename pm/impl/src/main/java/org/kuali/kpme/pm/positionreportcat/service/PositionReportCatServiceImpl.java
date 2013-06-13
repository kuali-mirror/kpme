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
package org.kuali.kpme.pm.positionreportcat.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategory;
import org.kuali.kpme.pm.positionreportcat.dao.PositionReportCatDao;

public class PositionReportCatServiceImpl implements PositionReportCatService {
	
	private PositionReportCatDao positionReportCatDao;
	
	@Override
	public PositionReportCategory getPositionReportCatById(String pmPositionReportCatId) {
		return positionReportCatDao.getPositionReportCatById(pmPositionReportCatId);
	}
	
	@Override
	public List<PositionReportCategory> getPositionReportCatList(String positionReportCat, String positionReportType, String institution, String location, LocalDate asOfDate) {
		return positionReportCatDao.getPositionReportCatList(positionReportCat, positionReportType, institution, location, asOfDate);
	}

	public PositionReportCatDao getPositionReportCatDao() {
		return positionReportCatDao;
	}

	public void setPositionReportCatDao(PositionReportCatDao positionReportCatDao) {
		this.positionReportCatDao = positionReportCatDao;
	}

	@Override
	public PositionReportCategory getPositionReportCat(String positionReportCat, LocalDate asOfDate) {
		return positionReportCatDao.getPositionReportCat(positionReportCat, asOfDate);
	}
}
