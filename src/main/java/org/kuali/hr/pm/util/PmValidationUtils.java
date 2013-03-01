package org.kuali.hr.pm.util;

import java.sql.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.hr.pm.institution.Institution;
import org.kuali.hr.pm.positionreporttype.PositionReportType;
import org.kuali.hr.pm.service.base.PmServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.location.api.campus.Campus;
import org.kuali.rice.location.api.services.LocationApiServiceLocator;

public class PmValidationUtils {

	/**
	 * Validate if there exists Institution that matches given institutionCode and exists before given date 
	 * Wild card is allowed 
	 * @param institutionCode
	 * @param asOfDate
	 * @return
	 */
	public static boolean validateInstitution(String institutionCode, Date asOfDate) {
		boolean valid = false;
		if (StringUtils.equals(institutionCode, TkConstants.WILDCARD_CHARACTER)) {
			valid = true;
		} else if (asOfDate != null) {
			Institution inst = PmServiceLocator.getInstitutionService().getInstitution(institutionCode, asOfDate);
			valid = (inst != null);
		} else {
			List<Institution> instList = PmServiceLocator.getInstitutionService().getInstitutionsByCode(institutionCode);
			valid = CollectionUtils.isNotEmpty(instList);
		}
		return valid;
	}
	
	/**
	 * Validate if there exists Campus that matches given campusCode 
	 * Wild card is allowed 
	 * @param campusCode
	 * @return
	 */
	public static boolean validateCampus(String campusCode) {
		boolean valid = false;
		if (StringUtils.equals(campusCode, TkConstants.WILDCARD_CHARACTER)) {
			valid = true;
		} else {
			Campus campusObj = LocationApiServiceLocator.getCampusService().getCampus(campusCode);
			valid = (campusObj != null);
		}
		return valid;
	}
	
	/**
	 * Validate if there exists Position Report Type that matches given postionReportType and exists before given date 
	 * Wild card is allowed 
	 * @param positionReportType
	 * @param asOfDate
	 * @return
	 */
	public static boolean validatePositionReportType(String positionReportType, Date asOfDate) {
		boolean valid = false;
		if (StringUtils.equals(positionReportType, TkConstants.WILDCARD_CHARACTER)) {
			valid = true;
		} else if (asOfDate != null) {
			PositionReportType prt = PmServiceLocator.getPositionReportTypeService().getPositionReportTypeByTypeAndDate(positionReportType, asOfDate);
			valid = (prt != null);
		} else {
			List<PositionReportType> prtList = PmServiceLocator.getPositionReportTypeService().getPositionReportTypeListByType(positionReportType);
			valid = CollectionUtils.isNotEmpty(prtList);
		}
		return valid;
	}	
	
	/** 
	 * Validate if the institution is consistent with given Position Report Type 
	 * @param positionReportType
	 * @param institutionCode
	 * @param asOfDate
	 * @return
	 */
	public static boolean validateInstitutionWithPRT(String positionReportType, String institutionCode, Date asOfDate) {
		if (StringUtils.equals(institutionCode, TkConstants.WILDCARD_CHARACTER)) {
			return true;
		} 
		if(StringUtils.equals(positionReportType, TkConstants.WILDCARD_CHARACTER)) {
			// look up existing position Report Type with the institutionCode
			List<PositionReportType> prtList = PmServiceLocator.getPositionReportTypeService().getPrtListWithInstitutionCodeAndDate(institutionCode, asOfDate);
			if(CollectionUtils.isEmpty(prtList))
				return false;
		}
		if (asOfDate != null) {
			PositionReportType prt = PmServiceLocator.getPositionReportTypeService().getPositionReportTypeByTypeAndDate(positionReportType, asOfDate);
			if(prt != null && prt.getInstitution().equals(institutionCode))
				return true;
		} 
		return false;
	}
	
	/**
	 * Validate if the campus is consistent with given positonReportType
	 * @param positionReportType
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public static boolean validateCampusWithPRT(String positionReportType, String campus, Date asOfDate) {
		if (StringUtils.equals(campus, TkConstants.WILDCARD_CHARACTER))
			return true;
		
		if(StringUtils.equals(positionReportType, TkConstants.WILDCARD_CHARACTER)) {
			// look up existing position Report Type with the given campus
			List<PositionReportType> prtList = PmServiceLocator.getPositionReportTypeService().getPrtListWithCampusAndDate(campus, asOfDate);
			if(CollectionUtils.isEmpty(prtList))
				return false;
		}
		
		if (asOfDate != null) {
			PositionReportType prt = PmServiceLocator.getPositionReportTypeService().getPositionReportTypeByTypeAndDate(positionReportType, asOfDate);
			if(prt != null && prt.getCampus().equals(campus))
				return true;
		} 
		return false;
	}
	
}
