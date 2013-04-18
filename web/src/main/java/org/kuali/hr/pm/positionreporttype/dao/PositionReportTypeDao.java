package org.kuali.hr.pm.positionreporttype.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.hr.pm.positionreporttype.PositionReportType;

public interface PositionReportTypeDao {
	
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId);
	
	public List<PositionReportType> getPositionReportTypeList(String positionReportType, String institution, String campus, LocalDate asOfDate);
	
	public List<PositionReportType> getPositionReportTypeListByType(String positionReportType);
	
	public List<PositionReportType> getPrtListWithInstitutionCodeAndDate(String institutionCode, LocalDate asOfDate);

	public List<PositionReportType> getPrtListWithCampusAndDate(String campus, LocalDate asOfDate);
}
