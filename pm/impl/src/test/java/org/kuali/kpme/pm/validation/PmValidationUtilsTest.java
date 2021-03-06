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
package org.kuali.kpme.pm.validation;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.pm.PMIntegrationTestCase;
import org.kuali.kpme.pm.util.PmValidationUtils;

@IntegrationTest
public class PmValidationUtilsTest extends PMIntegrationTestCase {
	
	private static DateTime INVALID_DATE = new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
	private static DateTime VALID_DATE = new DateTime(2012, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone());
	
	@Test
	public void testValidateCampus() {
		String campus = "*";	
		boolean results = PmValidationUtils.validateCampus(campus);	// wildcard should be true
		Assert.assertTrue(results);
		
		campus = "nonExist";	
		results = PmValidationUtils.validateCampus(campus);	// non-existing
		Assert.assertFalse(results);
		
		campus = "TS";	// existing campus
		results = PmValidationUtils.validateCampus(campus);
		Assert.assertTrue(results);
		
	}
	
	@Test
	public void testValidatePositionReportType() {
		DateTime aDate = INVALID_DATE;
		String prt = "nonExist";
//		String groupKeyCode = "testGK";
		boolean results = PmValidationUtils.validatePositionReportType(prt, aDate.toLocalDate()); // non-existing
		Assert.assertFalse(results);
		
		prt = "testPRT";	
		results = PmValidationUtils.validatePositionReportType(prt, aDate.toLocalDate());	// existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = VALID_DATE;
		results = PmValidationUtils.validatePositionReportType(prt, aDate.toLocalDate());  // existing, right date
		Assert.assertTrue(results);
	}

    @Test
    public void testValidatePayGradeWithSalaryGroup() {
        DateTime aDate = INVALID_DATE;
        String payGrade = "nonExist";
        String salaryGroup = "*";
        boolean results = PmValidationUtils.validatePayGradeWithSalaryGroup(salaryGroup, payGrade, aDate.toLocalDate());  //non-existing
        Assert.assertFalse(results);

        payGrade = "T";
        results = PmValidationUtils.validatePayGradeWithSalaryGroup(salaryGroup, payGrade, aDate.toLocalDate()); //existing, Wrong Date
        Assert.assertFalse(results);

        aDate = VALID_DATE;
        salaryGroup = "nonExist";
        results = PmValidationUtils.validatePayGradeWithSalaryGroup(salaryGroup, payGrade, aDate.toLocalDate());  //existing, wrong salaryGroup
        Assert.assertFalse(results);

        salaryGroup = "testSalGrp";
        results = PmValidationUtils.validatePayGradeWithSalaryGroup(salaryGroup, payGrade, aDate.toLocalDate());
        Assert.assertTrue(results);
    }

	@Test
	public void testValidatePositionReportCategory() {
		DateTime aDate = INVALID_DATE;
		String prc = "nonExist";
		String prt = "nonExist";
//		String groupKeyCode = "testGK";
		
		boolean results = PmValidationUtils.validatePositionReportCategory(prc, prt,  aDate.toLocalDate()); // non-existing
		Assert.assertFalse(results);
		
		prc = "testPRC";	
		results = PmValidationUtils.validatePositionReportCategory(prc, prt, aDate.toLocalDate()); 	// existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = VALID_DATE;
		results = PmValidationUtils.validatePositionReportCategory(prc, prt, aDate.toLocalDate());   // existing, right date, wrong prt
		Assert.assertFalse(results);
		
		prt = "testPRT";
		results = PmValidationUtils.validatePositionReportCategory(prc, prt, aDate.toLocalDate()); 
		Assert.assertTrue(results);
	}
	
	@Test
	public void testValidatePositionReportSubCat() {
		DateTime aDate = INVALID_DATE;
		String prsc = "nonExist";
//		String groupKeyCode = "*-*";
		boolean results = PmValidationUtils.validatePositionReportSubCat(prsc,aDate.toLocalDate()); // non-existing
		Assert.assertFalse(results);
		
		prsc = "testPRSC";	
		results = PmValidationUtils.validatePositionReportSubCat(prsc, aDate.toLocalDate()); // existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = VALID_DATE;
		results = PmValidationUtils.validatePositionReportSubCat(prsc, aDate.toLocalDate());  
		Assert.assertTrue(results);
	}
	
	
//	@Test
//	public void testValidatePstnRptGrp() {
//		DateTime aDate = INVALID_DATE;
//		String prg = "nonExist";
//		
//		boolean results = PmValidationUtils.validatePstnRptGrp(prg, aDate.toLocalDate()); // non-existing
//		Assert.assertFalse(results);
//		
//		prg = "testPRG";	
//		results = PmValidationUtils.validatePstnRptGrp(prg, aDate.toLocalDate()); // existing, but wrong date
//		Assert.assertFalse(results);
//		
//		aDate = VALID_DATE;
//		results = PmValidationUtils.validatePstnRptGrp(prg, aDate.toLocalDate());   // existing, right date
//		Assert.assertTrue(results);
//		
//		results = PmValidationUtils.validatePstnRptGrp(prg, aDate.toLocalDate()); 
//		Assert.assertTrue(results);
//	}

    @Test
    public void testValidatePositionQualificationValue() {
        String qValue = "nonExist";
        boolean results = PmValidationUtils.validatePositionQualificationValue(qValue);  //non-existing
        Assert.assertFalse(results);

        qValue = "existing";
        results = PmValidationUtils.validatePositionQualificationValue(qValue);
        Assert.assertTrue(results);
    }

    @Test
    public void testValidateAffiliation() {
       DateTime aDate = INVALID_DATE;
       String deptAffl = "nonExist";
       boolean results = PmValidationUtils.validateAffiliation(deptAffl, aDate.toLocalDate()); //non-existing
       Assert.assertFalse(results);

       deptAffl = "testType";
       results = PmValidationUtils.validateAffiliation(deptAffl, aDate.toLocalDate()); //existing, but wrong date
       Assert.assertFalse(results);

       results = PmValidationUtils.validateAffiliation(deptAffl, LocalDate.now());
       Assert.assertTrue(results);

    }

    @Test
    public void testValidatePositionType(){
        DateTime aDate = INVALID_DATE;
        String pType = "nonExist";
        String institution ="*";
        String campus = "*";
        boolean results = PmValidationUtils.validatePositionType(pType,aDate.toLocalDate()); //non-existing
        Assert.assertFalse(results);
    }

    @Test
    public void testValidatePositionAppointmentType() {

    LocalDate  asOfDate = new LocalDate(2010,1,1);

    //no wild card test
    boolean results = PmValidationUtils.validatePositionAppointmentType("noWildCard","UGA-ATHENS",asOfDate);
    Assert.assertTrue(results);

    }
}
