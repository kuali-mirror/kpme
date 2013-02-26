package org.kuali.hr.pm.positionreporttype.service;

import java.sql.Date;

import org.kuali.hr.pm.positionreporttype.PositionReportType;
import org.kuali.hr.pm.positionreporttype.dao.PositionReportTypeDao;

public class PositionReportTypeServiceImpl implements PositionReportTypeService {
	
	 private PositionReportTypeDao positionReportTypeDao;
	
	@Override
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId) {
		return positionReportTypeDao.getPositionReportTypeById(pmPositionReportTypeId);
	}

	public PositionReportTypeDao getPositionReportTypeDao() {
		return positionReportTypeDao;
	}

	public void setPositionReportTypeDao(PositionReportTypeDao positionReportTypeDao) {
		this.positionReportTypeDao = positionReportTypeDao;
	}
	
	public PositionReportType getPositionReportTypeByTypeAndDate(String positionReportType, Date asOfDate) {
		return positionReportTypeDao.getPositionReportTypeByTypeAndDate(positionReportType, asOfDate);
	}

}
