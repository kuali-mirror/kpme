package org.kuali.kpme.pm.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.institution.Institution;
import org.kuali.kpme.core.bo.paygrade.PayGrade;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.pm.PMConstants;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategory;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroup;
import org.kuali.kpme.pm.positionreportsubcat.PositionReportSubCategory;
import org.kuali.kpme.pm.positionreporttype.PositionReportType;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.kpme.tklm.common.TkConstants;
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
	public static boolean validateInstitution(String institutionCode, LocalDate asOfDate) {
		boolean valid = false;
		if (PmValidationUtils.isWildCard(institutionCode)) {
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
		if (PmValidationUtils.isWildCard(campusCode)) {
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
	public static boolean validatePositionReportType(String positionReportType, String institution, String campus, LocalDate asOfDate) {
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
	public static boolean validateInstitutionWithPRT(String positionReportType, String institutionCode, LocalDate asOfDate) {
		if (asOfDate != null) {
			List<PositionReportType> prtList = PmServiceLocator.getPositionReportTypeService()
				.getPositionReportTypeList(positionReportType, institutionCode, PMConstants.WILDCARD_CHARACTER, asOfDate);
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
	public static boolean validateCampusWithPRT(String positionReportType, String campus, LocalDate asOfDate) {
		if (asOfDate != null) {
			List<PositionReportType> prtList = PmServiceLocator.getPositionReportTypeService()
				.getPositionReportTypeList(positionReportType, TkConstants.WILDCARD_CHARACTER, campus, asOfDate);
			return CollectionUtils.isNotEmpty(prtList);
		} 
		return false;
	}
	
	public static boolean validatePayGradeWithSalaryGroup(String salaryGroup, String payGrade, LocalDate asOfDate) {
		if (asOfDate != null) {
			PayGrade grade = HrServiceLocator.getPayGradeService().getPayGrade(payGrade, salaryGroup, asOfDate);
			if(grade != null && StringUtils.isNotBlank(grade.getSalGroup())) 
				return StringUtils.equals(grade.getSalGroup(), salaryGroup);
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
	public static boolean validatePositionReportCategory(String positionReportCat, String positionReportType, String institution, String campus, LocalDate asOfDate) {
		if (StringUtils.isNotEmpty(positionReportCat) && asOfDate != null) {
			List<PositionReportCategory> prcList = PmServiceLocator.getPositionReportCatService().getPositionReportCatList(positionReportCat, positionReportType, institution, campus, asOfDate);
			return CollectionUtils.isNotEmpty(prcList);
		}
		return false;
	}
	
	/**
	 * Validate if there exists Position Report Sub Categories that match given pstnRptSubCat, institution, campus and exists before given date 
	 * Wild card allowed
	 * @param pstnRptSubCat
	 * @param institution
	 * @param campus
	 * @param asOfDate
	 * @return
	 */
	
	public static boolean validatePositionReportSubCat(String pstnRptSubCat, String institution, String campus, LocalDate asOfDate) {
		if(asOfDate != null) {
			List<PositionReportSubCategory> prscList = PmServiceLocator.getPositionReportSubCatService().getPositionReportSubCat(pstnRptSubCat, institution, campus, asOfDate);
			return CollectionUtils.isNotEmpty(prscList);
		}
		return false;
	}
	
	public static boolean validatePstnRptGrp(String PstnRptGrp, String institution, String campus, LocalDate asOfDate) {
		if(asOfDate != null) {
			List<PositionReportGroup> prgList = PmServiceLocator.getPositionReportGroupService().getPositionReportGroupList(PstnRptGrp, institution, campus, asOfDate);
			return CollectionUtils.isNotEmpty(prgList);
		}
		return false;
	}

	public static boolean validatePayGrade(String payGrade) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static boolean isWildCard(String aString) {
		return (StringUtils.equals(aString, TkConstants.WILDCARD_CHARACTER) ||
					StringUtils.equals(aString,PMConstants.WILDCARD_CHARACTER));
	}
	
	
}
