package org.kuali.hr.pm.positionreportsubcat.service;

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

}
