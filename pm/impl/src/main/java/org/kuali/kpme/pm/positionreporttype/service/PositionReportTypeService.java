package org.kuali.kpme.pm.positionreporttype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.positionreporttype.PositionReportType;

public interface PositionReportTypeService {
	
	/**
	 * Retrieve the PositionReportType with given id
	 * @param pmPositionReportTypeId
	 * @return
	 */
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId);

	/**
	 * Get list of PositionReportType with given type, institution, campus and effective date
	 * wild card allowed
	 * @param positionReportType
	 * @param institution
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public List<PositionReportType> getPositionReportTypeList(String positionReportType, String institution, String campus, LocalDate asOfDate);
	
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
	public List<PositionReportType> getPrtListWithInstitutionCodeAndDate(String institutionCode, LocalDate asOfDate);
	
	/**
	 * Retrieve list of Position Report Type with given campus and effectiveDate
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public List<PositionReportType> getPrtListWithCampusAndDate(String campus, LocalDate asOfDate);
	
}
