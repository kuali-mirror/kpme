/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.pm.util;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.pm.util.PmValidationUtils;

public class PmValidationUtilsTest extends KPMETestCase {
	
	private static DateTime INVALID_DATE = new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
	private static DateTime VALID_DATE = new DateTime(2012, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
	
	@Test
	public void testValidateInstitution() {
		DateTime aDate = INVALID_DATE;
		String institution = "*";	
		boolean results = PmValidationUtils.validateInstitution(institution, aDate.toLocalDate());	// wildcard should be true
		Assert.assertTrue(results);
		
		institution = "nonExist";	
		results = PmValidationUtils.validateInstitution(institution, aDate.toLocalDate());	// non-existing
		Assert.assertFalse(results);
		
		institution = "testInst";	
		results = PmValidationUtils.validateInstitution(institution, aDate.toLocalDate());	// existing institution, but wrong date
		Assert.assertFalse(results);
		
		aDate = VALID_DATE;
		results = PmValidationUtils.validateInstitution(institution, aDate.toLocalDate()); // existing institution, right date
		Assert.assertTrue(results);
	}
	
	@Test
	public void testValidateCampus() {
		String campus = "*";	
		boolean results = PmValidationUtils.validateCampus(campus);	// wildcard should be true
		Assert.assertTrue(results);
		
		campus = "nonExist";	
		results = PmValidationUtils.validateCampus(campus);	// non-existing
		Assert.assertFalse(results);
		
		campus = "BL";	// existing campus
		results = PmValidationUtils.validateCampus(campus);
		Assert.assertTrue(results);
		
	}
	
	@Test
	public void testValidatePositionReportType() {
		DateTime aDate = INVALID_DATE;
		String prt = "nonExist";
		String institution = "*";
		String campus = "*";
		boolean results = PmValidationUtils.validatePositionReportType(prt, institution, campus, aDate.toLocalDate()); // non-existing
		Assert.assertFalse(results);
		
		prt = "testPRT";	
		results = PmValidationUtils.validatePositionReportType(prt, institution, campus, aDate.toLocalDate());	// existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = VALID_DATE;
		results = PmValidationUtils.validatePositionReportType(prt, institution, campus, aDate.toLocalDate());  // existing, right date
		Assert.assertTrue(results);
	}
	
	@Test
	public void testValidateInstitutionWithPRT() {
		DateTime aDate = INVALID_DATE;
		String prt = "nonExist";
		String institution = "testInst";
		boolean results = PmValidationUtils.validateInstitutionWithPRT(prt, institution, aDate.toLocalDate()); // non-existing
		Assert.assertFalse(results);
		
		prt = "testPRT";	
		results = PmValidationUtils.validateInstitutionWithPRT(prt, institution, aDate.toLocalDate());	// existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = VALID_DATE;
		results = PmValidationUtils.validateInstitutionWithPRT(prt, institution, aDate.toLocalDate());  // existing, right date
		Assert.assertTrue(results);
	}
	
	@Test
	public void testValidateCampusWithPRT() {
		DateTime aDate = INVALID_DATE;
		String prt = "nonExist";
		String campus = "testCamp";
		boolean results = PmValidationUtils.validateCampusWithPRT(prt, campus, aDate.toLocalDate());  // non-existing
		Assert.assertFalse(results);
		
		prt = "testPRT";	
		results = PmValidationUtils.validateCampusWithPRT(prt, campus, aDate.toLocalDate());	// existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = VALID_DATE;
		results = PmValidationUtils.validateCampusWithPRT(prt, campus, aDate.toLocalDate()); // existing, right date, wrong campus
		Assert.assertFalse(results);
		
		campus = "BL";
		results = PmValidationUtils.validateCampusWithPRT(prt, campus, aDate.toLocalDate()); 
		Assert.assertTrue(results);
	}
	
	@Test
	public void testValidatePositionReportCategory() {
		DateTime aDate = INVALID_DATE;
		String prc = "nonExist";
		String prt = "nonExist";
		String institution = "*";
		String campus = "*";
		boolean results = PmValidationUtils.validatePositionReportCategory(prc, prt, institution, campus, aDate.toLocalDate()); // non-existing
		Assert.assertFalse(results);
		
		prc = "testPRC";	
		results = PmValidationUtils.validatePositionReportCategory(prc, prt, institution, campus, aDate.toLocalDate()); 	// existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = VALID_DATE;
		results = PmValidationUtils.validatePositionReportCategory(prc, prt, institution, campus, aDate.toLocalDate());   // existing, right date, wrong prt
		Assert.assertFalse(results);
		
		prt = "testPRT";
		results = PmValidationUtils.validatePositionReportCategory(prc, prt, institution, campus, aDate.toLocalDate()); 
		Assert.assertTrue(results);
	}
	
	@Test
	public void testValidatePositionReportSubCat() {
		DateTime aDate = INVALID_DATE;
		String prsc = "nonExist";
		String institution = "*";
		String campus = "*";
		boolean results = PmValidationUtils.validatePositionReportSubCat(prsc, institution, campus, aDate.toLocalDate()); // non-existing
		Assert.assertFalse(results);
		
		prsc = "testPRSC";	
		results = PmValidationUtils.validatePositionReportSubCat(prsc, institution, campus, aDate.toLocalDate()); // existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = VALID_DATE;
		institution = "nonExist";
		results = PmValidationUtils.validatePositionReportSubCat(prsc, institution, campus, aDate.toLocalDate());   // existing, right date, wrong institution
		Assert.assertFalse(results);
		
		institution = "testInst";
		campus = "nonExist";
		results = PmValidationUtils.validatePositionReportSubCat(prsc, institution, campus, aDate.toLocalDate());   // existing, right date, right institution, wrong campus
		Assert.assertFalse(results);
		
		campus = "BL";
		results = PmValidationUtils.validatePositionReportSubCat(prsc, institution, campus, aDate.toLocalDate());  
		Assert.assertTrue(results);
	}
	
	
	@Test
	public void testValidatePstnRptGrp() {
		DateTime aDate = INVALID_DATE;
		String prg = "nonExist";
		String institution = "*";
		String campus = "*";
		boolean results = PmValidationUtils.validatePstnRptGrp(prg, institution, campus, aDate.toLocalDate()); // non-existing
		Assert.assertFalse(results);
		
		prg = "testPRG";	
		results = PmValidationUtils.validatePstnRptGrp(prg, institution, campus, aDate.toLocalDate()); ; // existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = VALID_DATE;
		institution = "nonExist";
		results = PmValidationUtils.validatePstnRptGrp(prg, institution, campus, aDate.toLocalDate());   // existing, right date, wrong institution
		Assert.assertFalse(results);
		
		institution = "testInst";
		campus = "nonExist";
		results = PmValidationUtils.validatePstnRptGrp(prg, institution, campus, aDate.toLocalDate());   // existing, right date, right institution, wrong campus
		Assert.assertFalse(results);
		
		campus = "BL";
		results = PmValidationUtils.validatePstnRptGrp(prg, institution, campus, aDate.toLocalDate()); 
		Assert.assertTrue(results);
	}
}
