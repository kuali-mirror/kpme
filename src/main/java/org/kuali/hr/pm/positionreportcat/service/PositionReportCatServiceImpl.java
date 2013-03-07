package org.kuali.hr.pm.positionreportcat.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.pm.positionreportcat.PositionReportCategory;
import org.kuali.hr.pm.positionreportcat.dao.PositionReportCatDao;

public class PositionReportCatServiceImpl implements PositionReportCatService {
	
	private PositionReportCatDao positionReportCatDao;
	
	@Override
	public PositionReportCategory getPositionReportCatById(String pmPositionReportCatId) {
		return positionReportCatDao.getPositionReportCatById(pmPositionReportCatId);
	}
	
	@Override
	public List<PositionReportCategory> getPositionReportCatList(String positionReportCat, String positionReportType, String institution, String campus, Date asOfDate) {
		return positionReportCatDao.getPositionReportCatList(positionReportCat, positionReportType, institution, campus, asOfDate);
	}

	public PositionReportCatDao getPositionReportCatDao() {
		return positionReportCatDao;
	}

	public void setPositionReportCatDao(PositionReportCatDao positionReportCatDao) {
		this.positionReportCatDao = positionReportCatDao;
	}
}
