package org.kuali.hr.lm.accrualcategory.validation;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.lm.accrual.AccrualCategoryInquirableImpl;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;

public class AccrualCategoryInquirableImplTest extends KPMETestCase {

	private String INQUIRY_URL = "inquiry.do?businessObjectClassName=org.kuali.hr.lm.leaveplan.LeavePlan&lmLeavePlanId=&methodToCall=start&effectiveDate=03%2F04%2F2012&leavePlan=testLeavePlan";
	@Test
	public void testGetInquiryUrl() throws Exception {
		AccrualCategory ac = new AccrualCategory();
		ac.setAccrualCategory("testAC");
		ac.setLeavePlan("testLeavePlan");
		Date aDate = new java.sql.Date(new SimpleDateFormat("MM/dd/yyyy").parse("03/04/2012").getTime());
		ac.setEffectiveDate(aDate);
		
		HtmlData hd = (HtmlData) new AccrualCategoryInquirableImpl().getInquiryUrl(ac, "leavePlan", false);
		Assert.assertNotNull("No HtmlData found", hd);
		String inquiryUrl = ((AnchorHtmlData) hd).getHref();
		Assert.assertTrue("Inquiry url is wrong", StringUtils.contains(inquiryUrl, INQUIRY_URL));
		
	}
}
