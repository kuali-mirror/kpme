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
	
	public List<PositionReportSubCategory> getPositionReportSubCat(String pstnRptSubCat, String institution, String campus, LocalDate asOfDate) {
		return positionReportSubCatDao.getPositionReportSubCat(pstnRptSubCat, institution, campus, asOfDate);
	}

}
