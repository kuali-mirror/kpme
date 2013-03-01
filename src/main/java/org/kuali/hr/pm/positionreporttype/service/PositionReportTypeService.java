package org.kuali.hr.pm.positionreporttype.service;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.pm.positionreporttype.PositionReportType;

public interface PositionReportTypeService {
	
	/**
	 * Retrieve the PositionReportType with given id
	 * @param pmPositionReportTypeId
	 * @return
	 */
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId);
	
	/**
	 * Get the newest active PositionReportType with given type and effective date
	 * @param positionReportType
	 * @param asOfDate
	 * @return
	 */
	public PositionReportType getPositionReportTypeByTypeAndDate(String positionReportType, Date asOfDate);
	
	/**
	 * Retrieve list of position report type by given positionReportType 
	 * @param positionReportType
	 * @return
	 */
	public List<PositionReportType> getPositionReportTypeListByType(String positionReportType);
	
	/**
	 * Retrieve list of Position Report Type with given institutionCode and effectiveDate
	 * @param institutionCode
	 * @param asOfDate
	 * @return
	 */
	public List<PositionReportType> getPrtListWithInstitutionCodeAndDate(String institutionCode, Date asOfDate);
	
	/**
	 * Retrieve list of Position Report Type with given campus and effectiveDate
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public List<PositionReportType> getPrtListWithCampusAndDate(String campus, Date asOfDate);
	
}
