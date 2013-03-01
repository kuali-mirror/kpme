package org.kuali.hr.pm.positionreportcat.service;

import java.sql.Date;

import org.kuali.hr.pm.positionreportcat.PositionReportCategory;
import org.kuali.hr.pm.positionreportcat.dao.PositionReportCatDao;

public class PositionReportCatServiceImpl implements PositionReportCatService {
	
	private PositionReportCatDao positionReportCatDao;
	
	@Override
	public PositionReportCategory getPositionReportCatById(String pmPositionReportCatId) {
		return positionReportCatDao.getPositionReportCatById(pmPositionReportCatId);
	}
	
	@Override
	public PositionReportCategory getPositionReportCatByCatAndDate(String positionReportCat, Date asOfDate) {
		return positionReportCatDao.getPositionReportCatByCatAndDate(positionReportCat, asOfDate);
	}

	public PositionReportCatDao getPositionReportCatDao() {
		return positionReportCatDao;
	}

	public void setPositionReportCatDao(PositionReportCatDao positionReportCatDao) {
		this.positionReportCatDao = positionReportCatDao;
	}
}
