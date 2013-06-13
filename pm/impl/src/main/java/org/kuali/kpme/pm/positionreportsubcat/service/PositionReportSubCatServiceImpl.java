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
package org.kuali.kpme.pm.positionreportsubcat.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory;
import org.kuali.kpme.pm.positionreportsubcat.dao.PositionReportSubCatDao;

public class PositionReportSubCatServiceImpl implements PositionReportSubCatService{
	private PositionReportSubCatDao positionReportSubCatDao;
	
	@Override
	public PositionReportSubCategory getPositionReportSubCatById(
			String pmPositionReportSubCatId) {
		return positionReportSubCatDao.getPositionReportSubCatById(pmPositionReportSubCatId);
	}

	public PositionReportSubCatDao getPositionReportSubCatDao() {
		return positionReportSubCatDao;
	}

	public void setPositionReportSubCatDao(
			PositionReportSubCatDao positionReportSubCatDao) {
		this.positionReportSubCatDao = positionReportSubCatDao;
	}
	
	public List<PositionReportSubCategory> getPositionReportSubCat(String pstnRptSubCat, String institution, String location, LocalDate asOfDate) {
		return positionReportSubCatDao.getPositionReportSubCat(pstnRptSubCat, institution, location, asOfDate);
	}

	@Override
	public PositionReportSubCategory getPositionReportSubCat(
			String pstnRptSubCat, LocalDate asOfDate) {
		return positionReportSubCatDao.getPositionReportSubCat(pstnRptSubCat, asOfDate);
	}

}
