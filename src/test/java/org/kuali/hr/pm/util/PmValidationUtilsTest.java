package org.kuali.hr.pm.util;

import java.sql.Date;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.util.TKUtils;

public class PmValidationUtilsTest extends KPMETestCase {
	
	@Test
	public void testValidateInstitution() {
		Date aDate = new Date((new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		String institution = "*";	
		boolean results = PmValidationUtils.validateInstitution(institution, aDate);	// wildcard should be true
		Assert.assertTrue(results);
		
		institution = "nonExist";	
		results = PmValidationUtils.validateInstitution(institution, aDate);	// non-existing
		Assert.assertFalse(results);
		
		institution = "testInst";	
		results = PmValidationUtils.validateInstitution(institution, aDate);	// existing institution, but wrong date
		Assert.assertFalse(results);
		
		aDate = new Date((new DateTime(2012, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		results = PmValidationUtils.validateInstitution(institution, aDate); // existing institution, right date
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
		
		campus = "TS";	// existing campus
		results = PmValidationUtils.validateCampus(campus);
		Assert.assertTrue(results);
		
	}
	
	@Test
	public void testValidatePositionReportType() {
		Date aDate = new Date((new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		String prt = "nonExist";
		String institution = "*";
		String campus = "*";
		boolean results = PmValidationUtils.validatePositionReportType(prt, institution, campus, aDate); // non-existing
		Assert.assertFalse(results);
		
		prt = "testPrt";	
		results = PmValidationUtils.validatePositionReportType(prt, institution, campus, aDate);	// existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = new Date((new DateTime(2012, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		results = PmValidationUtils.validatePositionReportType(prt, institution, campus, aDate);  // existing, right date
		Assert.assertTrue(results);
	}
	
	@Test
	public void testValidateInstitutionWithPRT() {
		Date aDate = new Date((new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		String prt = "nonExist";
		String institution = "testInst";
		boolean results = PmValidationUtils.validateInstitutionWithPRT(prt, institution, aDate); // non-existing
		Assert.assertFalse(results);
		
		prt = "testPrt";	
		results = PmValidationUtils.validateInstitutionWithPRT(prt, institution, aDate);	// existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = new Date((new DateTime(2012, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		results = PmValidationUtils.validateInstitutionWithPRT(prt, institution, aDate);  // existing, right date
		Assert.assertTrue(results);
	}
	
	@Test
	public void testValidateCampusWithPRT() {
		Date aDate = new Date((new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		String prt = "nonExist";
		String campus = "testCamp";
		boolean results = PmValidationUtils.validateCampusWithPRT(prt, campus, aDate);  // non-existing
		Assert.assertFalse(results);
		
		prt = "testPrt";	
		results = PmValidationUtils.validateCampusWithPRT(prt, campus, aDate);	// existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = new Date((new DateTime(2012, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		results = PmValidationUtils.validateCampusWithPRT(prt, campus, aDate); // existing, right date, wrong campus
		Assert.assertFalse(results);
		
		campus = "TS";
		results = PmValidationUtils.validateCampusWithPRT(prt, campus, aDate); 
		Assert.assertTrue(results);
	}
	
	@Test
	public void testValidatePositionReportCategory() {
		Date aDate = new Date((new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		String prc = "nonExist";
		String prt = "nonExist";
		String institution = "*";
		String campus = "*";
		boolean results = PmValidationUtils.validatePositionReportCategory(prc, prt, institution, campus, aDate); // non-existing
		Assert.assertFalse(results);
		
		prc = "testPRC";	
		results = PmValidationUtils.validatePositionReportCategory(prc, prt, institution, campus, aDate); 	// existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = new Date((new DateTime(2012, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		results = PmValidationUtils.validatePositionReportCategory(prc, prt, institution, campus, aDate);   // existing, right date, wrong prt
		Assert.assertFalse(results);
		
		prt = "testPRT";
		results = PmValidationUtils.validatePositionReportCategory(prc, prt, institution, campus, aDate); 
		Assert.assertTrue(results);
	}
	
	@Test
	public void testValidatePositionReportSubCat() {
		Date aDate = new Date((new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		String prsc = "nonExist";
		String institution = "*";
		String campus = "*";
		boolean results = PmValidationUtils.validatePositionReportSubCat(prsc, institution, campus, aDate); // non-existing
		Assert.assertFalse(results);
		
		prsc = "testPRSC";	
		results = PmValidationUtils.validatePositionReportSubCat(prsc, institution, campus, aDate); // existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = new Date((new DateTime(2012, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		institution = "nonExist";
		results = PmValidationUtils.validatePositionReportSubCat(prsc, institution, campus, aDate);   // existing, right date, wrong institution
		Assert.assertFalse(results);
		
		institution = "testInst";
		campus = "nonExist";
		results = PmValidationUtils.validatePositionReportSubCat(prsc, institution, campus, aDate);   // existing, right date, right institution, wrong campus
		Assert.assertFalse(results);
		
		campus = "TS";
		results = PmValidationUtils.validatePositionReportSubCat(prsc, institution, campus, aDate);  
		Assert.assertTrue(results);
	}
	
	
	@Test
	public void testValidatePstnRptGrp() {
		Date aDate = new Date((new DateTime(2011, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		String prg = "nonExist";
		String institution = "*";
		String campus = "*";
		boolean results = PmValidationUtils.validatePstnRptGrp(prg, institution, campus, aDate); // non-existing
		Assert.assertFalse(results);
		
		prg = "testPRG";	
		results = PmValidationUtils.validatePstnRptGrp(prg, institution, campus, aDate); ; // existing, but wrong date
		Assert.assertFalse(results);
		
		aDate = new Date((new DateTime(2012, 7, 7, 0, 0, 0, 0, TKUtils.getSystemDateTimeZone())).getMillis());
		institution = "nonExist";
		results = PmValidationUtils.validatePstnRptGrp(prg, institution, campus, aDate);   // existing, right date, wrong institution
		Assert.assertFalse(results);
		
		institution = "testInst";
		campus = "nonExist";
		results = PmValidationUtils.validatePstnRptGrp(prg, institution, campus, aDate);   // existing, right date, right institution, wrong campus
		Assert.assertFalse(results);
		
		campus = "TS";
		results = PmValidationUtils.validatePstnRptGrp(prg, institution, campus, aDate); 
		Assert.assertTrue(results);
	}
	

}
