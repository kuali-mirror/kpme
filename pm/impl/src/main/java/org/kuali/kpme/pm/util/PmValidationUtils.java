/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.pm.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.departmentaffiliation.DepartmentAffiliationContract;
import org.kuali.kpme.core.api.paygrade.PayGrade;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.api.positionappointment.PositionAppointmentContract;
import org.kuali.kpme.pm.api.positionreportcat.PositionReportCategoryContract;
import org.kuali.kpme.pm.api.positionreportsubcat.PositionReportSubCategoryContract;
import org.kuali.kpme.pm.api.positionreporttype.PositionReportTypeContract;
import org.kuali.kpme.pm.api.positiontype.PositionTypeContract;
import org.kuali.kpme.pm.api.pstncontracttype.PstnContractTypeContract;
import org.kuali.kpme.pm.api.pstnqlfctnvl.PositionQualificationValueContract;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.location.api.campus.Campus;
import org.kuali.rice.location.api.services.LocationApiServiceLocator;

public class PmValidationUtils {
	
	/**
	 * Validate if there exists Campus that matches given campusCode 
	 * Wild card is allowed 
	 * @param campusCode
	 * @return
	 */
	public static boolean validateCampus(String campusCode) {
		boolean valid = false;
		if (ValidationUtils.isWildCard(campusCode)) {
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
	 * @param institution
	 * @param location
	 * @param asOfDate
	 * @return
	 */
	public static boolean validatePositionReportType(String positionReportType, String groupKeyCode, LocalDate asOfDate) {
		boolean valid = false;
		if (asOfDate != null) {
			List<? extends PositionReportTypeContract> prtList = PmServiceLocator.getPositionReportTypeService().getPositionReportTypeList(positionReportType, groupKeyCode, asOfDate);
			valid = (CollectionUtils.isNotEmpty(prtList));
		} 
		return valid;
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
	 * Validate if there exists Position Report Category that matches given postionReportCat, positionReportType , groupKeyCode and exists before given date 
	 * Wild card allowed
	 * @param positionReportCat
	 * @param positionReportType
	 * @param groupKeyCode
	 * @param asOfDate
	 * @return
	 */
	public static boolean validatePositionReportCategory(String positionReportCat, String positionReportType, String groupKeyCode, LocalDate asOfDate) {
		if (StringUtils.isNotEmpty(positionReportCat) && asOfDate != null) {
			List<? extends PositionReportCategoryContract> prcList = PmServiceLocator.getPositionReportCatService().getPositionReportCatList(positionReportCat, positionReportType, groupKeyCode, asOfDate);
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
	
	public static boolean validatePositionReportSubCat(String pstnRptSubCat, String groupKeyCode, LocalDate asOfDate) {
		if(asOfDate != null) {
			List<? extends PositionReportSubCategoryContract> prscList = PmServiceLocator.getPositionReportSubCatService().getPositionReportSubCat(pstnRptSubCat, groupKeyCode,asOfDate);
			return CollectionUtils.isNotEmpty(prscList);
		}
		return false;
	}
	
	public static boolean validatePositionQualificationValue(String qValue) {
		if(StringUtils.isNotEmpty(qValue)) {
			PositionQualificationValueContract aPqv = PmServiceLocator.getPositionQualificationValueService().getPositionQualificationValueByValue(qValue);
			if(aPqv != null) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean validateAffiliation(String deptAffl,  LocalDate asOfDate) {
		if (asOfDate != null) {
			List<? extends DepartmentAffiliationContract> pdaList = HrServiceLocator.getDepartmentAffiliationService().getDepartmentAffiliationList(deptAffl, asOfDate);
			return CollectionUtils.isNotEmpty(pdaList);
		}
		return false;
	}

	public static boolean validatePositionType(String pType, String institution, String campus, LocalDate asOfDate) {
		if(asOfDate != null) {
			List<? extends PositionTypeContract> ptList = PmServiceLocator.getPositionTypeService().getPositionTypeList(pType, institution, campus, asOfDate);
			return CollectionUtils.isNotEmpty(ptList);
		}
		return false;
	}
	
	public static boolean validatePositionAppointmentType(String positionAppointment, String groupKeyCode, LocalDate asOfDate) {
		if (StringUtils.isNotEmpty(positionAppointment) && asOfDate != null) {
			List<? extends PositionAppointmentContract> ptList = PmServiceLocator.getPositionAppointmentService().getPositionAppointmentList(positionAppointment, groupKeyCode, asOfDate);
			return CollectionUtils.isNotEmpty(ptList);
		}
		return false;
	}

	public static boolean validatePositionContractType(String name, String groupKeyCode, LocalDate asOfDate) {
		if (StringUtils.isNotEmpty(name) && asOfDate != null) {
			List<? extends PstnContractTypeContract> ptList = PmServiceLocator.getPstnContractTypeService().getPstnContractTypeList(name, groupKeyCode, asOfDate);
			return CollectionUtils.isNotEmpty(ptList);
		}
		return false;
	}
	
}
