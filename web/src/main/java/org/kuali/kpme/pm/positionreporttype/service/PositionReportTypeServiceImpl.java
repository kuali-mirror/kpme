package org.kuali.kpme.pm.positionreporttype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreporttype.PositionReportType;
import org.kuali.kpme.pm.positionreporttype.dao.PositionReportTypeDao;

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
	public List<PositionReportType> getPositionReportTypeList(String positionReportType, String institution, String campus, LocalDate asOfDate) {
		return positionReportTypeDao.getPositionReportTypeList(positionReportType, institution, campus, asOfDate);
	}
	
	@Override
	public List<PositionReportType> getPositionReportTypeListByType(String positionReportType) {
		return positionReportTypeDao.getPositionReportTypeListByType(positionReportType);
	}
	
	@Override
	public List<PositionReportType> getPrtListWithInstitutionCodeAndDate(String institutionCode, LocalDate asOfDate) {
		return positionReportTypeDao.getPrtListWithInstitutionCodeAndDate(institutionCode, asOfDate);
	}

	@Override
	public List<PositionReportType> getPrtListWithCampusAndDate(String campus,LocalDate asOfDate) {
		return positionReportTypeDao.getPrtListWithCampusAndDate(campus, asOfDate);
	}

}
