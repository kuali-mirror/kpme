package org.kuali.hr.pm.positionreportsubcat.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.pm.positionreportsubcat.PositionReportSubCategory;
import org.kuali.hr.pm.positionreportsubcat.dao.PositionReportSubCatDao;

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
	
	public List<PositionReportSubCategory> getPositionReportSubCat(String pstnRptSubCat, String institution, String campus, Date asOfDate) {
		return positionReportSubCatDao.getPositionReportSubCat(pstnRptSubCat, institution, campus, asOfDate);
	}

}
