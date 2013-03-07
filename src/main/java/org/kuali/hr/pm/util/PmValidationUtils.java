package org.kuali.hr.pm.util;

import java.sql.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.hr.pm.institution.Institution;
import org.kuali.hr.pm.positionreportcat.PositionReportCategory;
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
	 * Validate if there exists Position Report Type that matches given postionReportType, institution, campus and exists before given date 
	 * Wild card is allowed for String parameters
	 * @param positionReportType
	 * @param asOfDate
	 * @return
	 */
	public static boolean validatePositionReportType(String positionReportType, String institution, String campus, Date asOfDate) {
		boolean valid = false;
		if (asOfDate != null) {
			List<PositionReportType> prtList = PmServiceLocator.getPositionReportTypeService().getPositionReportTypeList(positionReportType, institution, campus, asOfDate);
			valid = (CollectionUtils.isNotEmpty(prtList));
		} 
		return valid;
	}	
	
	/** 
	 * Validate if the institution is consistent with given Position Report Type 
	 * Wild card is allowed for String parameters
	 * @param positionReportType
	 * @param institutionCode
	 * @param asOfDate
	 * @return
	 */
	public static boolean validateInstitutionWithPRT(String positionReportType, String institutionCode, Date asOfDate) {
		if (asOfDate != null) {
			List<PositionReportType> prtList = PmServiceLocator.getPositionReportTypeService()
				.getPositionReportTypeList(positionReportType, institutionCode, TkConstants.WILDCARD_CHARACTER, asOfDate);
			return CollectionUtils.isNotEmpty(prtList);
		} 
		return false;
	}
	
	/**
	 * Validate if the campus is consistent with given positonReportType
	 * Wild card is allowed for String parameters
	 * @param positionReportType
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public static boolean validateCampusWithPRT(String positionReportType, String campus, Date asOfDate) {
		if (asOfDate != null) {
			List<PositionReportType> prtList = PmServiceLocator.getPositionReportTypeService()
				.getPositionReportTypeList(positionReportType, TkConstants.WILDCARD_CHARACTER, campus, asOfDate);
			return CollectionUtils.isNotEmpty(prtList);
		} 
		return false;
	}
	
	/**
	 * Validate if there exists Position Report Category that matches given postionReportCat, positionReportType , institution, campus and exists before given date 
	 * Wild card allowed
	 * @param positionReportCat
	 * @param positionReportType
	 * @param institution
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	public static boolean validatePositionReportCategory(String positionReportCat, String positionReportType, String institution, String campus, Date asOfDate) {
		if (StringUtils.isNotEmpty(positionReportCat) && asOfDate != null) {
			List<PositionReportCategory> prcList = PmServiceLocator.getPositionReportCatService().getPositionReportCatList(positionReportCat, positionReportType, institution, campus, asOfDate);
			return CollectionUtils.isNotEmpty(prcList);
		}
		return false;
	}
	
	
}
