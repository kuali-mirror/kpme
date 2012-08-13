package org.kuali.hr.lm.employeeoverride;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.base.web.TkInquirableImpl;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;

public class EmployeeOverrideInquirableTest extends KPMETestCase {

	private String INQUIRY_URL = "inquiry.do?businessObjectClassName=org.kuali.hr.lm.accrual.AccrualCategory&methodToCall=start&effectiveDate=03%2F04%2F2012&accrualCategory=testAC&leavePlan=&lmAccrualCategoryId=";
	@Test
	public void testGetInquiryUrl() throws Exception {
		EmployeeOverride eo = new EmployeeOverride();
		eo.setAccrualCategory("testAC");
		eo.setLeavePlan("testLeavePlan");
		Date aDate = new java.sql.Date(new SimpleDateFormat("MM/dd/yyyy").parse("03/04/2012").getTime());
		eo.setEffectiveDate(aDate);
		
		HtmlData hd = (HtmlData) new TkInquirableImpl().getInquiryUrl(eo, "accrualCategory", false);
		Assert.assertNotNull("No HtmlData found", hd);
		String inquiryUrl = ((AnchorHtmlData) hd).getHref();
		Assert.assertTrue("Inquiry url is wrong", StringUtils.contains(inquiryUrl, INQUIRY_URL));
		
	}
}
