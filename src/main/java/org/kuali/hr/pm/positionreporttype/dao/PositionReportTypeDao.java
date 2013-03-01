package org.kuali.hr.pm.positionreporttype.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.pm.positionreporttype.PositionReportType;

public interface PositionReportTypeDao {
	
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId);
	
	public PositionReportType getPositionReportTypeByTypeAndDate(String positionReportType, Date asOfDate);
	
	public List<PositionReportType> getPositionReportTypeListByType(String positionReportType);
	
	public List<PositionReportType> getPrtListWithInstitutionCodeAndDate(String institutionCode, Date asOfDate);

	public List<PositionReportType> getPrtListWithCampusAndDate(String campus,Date asOfDate);
}
