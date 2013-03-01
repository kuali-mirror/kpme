package org.kuali.hr.pm.positionreporttype.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.pm.positionreporttype.PositionReportType;
import org.kuali.hr.pm.positionreporttype.dao.PositionReportTypeDao;

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
		return positionReportTypeDao.getPositionReportTypeById(pmPositionReportTypeId);
	}

	@Override
	public PositionReportType getPositionReportTypeByTypeAndDate(String positionReportType, Date asOfDate) {
		return positionReportTypeDao.getPositionReportTypeByTypeAndDate(positionReportType, asOfDate);
	}
	
	@Override
	public List<PositionReportType> getPositionReportTypeListByType(String positionReportType) {
		return positionReportTypeDao.getPositionReportTypeListByType(positionReportType);
	}
	
	@Override
	public List<PositionReportType> getPrtListWithInstitutionCodeAndDate(String institutionCode, Date asOfDate) {
		return positionReportTypeDao.getPrtListWithInstitutionCodeAndDate(institutionCode, asOfDate);
	}

	@Override
	public List<PositionReportType> getPrtListWithCampusAndDate(String campus,Date asOfDate) {
		return positionReportTypeDao.getPrtListWithCampusAndDate(campus, asOfDate);
	}

}
