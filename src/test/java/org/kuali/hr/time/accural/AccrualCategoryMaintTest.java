package org.kuali.hr.time.accural;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Test;
import org.kuali.hr.lm.accrual.AccrualCategory;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class AccrualCategoryMaintTest extends TkTestCase {

	private static final String TEST_CODE = "_T";
	private static String accrualCategoryId;
	private static final Date TEST_DATE = new Date(Calendar.getInstance().getTimeInMillis());
	private static final Timestamp TEST_TIMESTAMP = new Timestamp(Calendar.getInstance().getTimeInMillis());

	@Test
	public void testAccrualCategoryMaint() throws Exception {
		HtmlPage accuralCategoryLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.ACCURAL_CATEGORY_MAINT_URL);
		accuralCategoryLookup = HtmlUnitUtil.clickInputContainingText(accuralCategoryLookup, "search");
		assertTrue("Page contains test AccuralCategory", accuralCategoryLookup.asText().contains(TEST_CODE.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(accuralCategoryLookup, "edit", accrualCategoryId.toString());
		assertTrue("Maintenance Page contains test AccuralCategory", maintPage.asText().contains(TEST_CODE));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		AccrualCategory accrualCategory = new AccrualCategory();
		accrualCategory.setAccrualCategory(TEST_CODE);
		accrualCategory.setActive(true);
		accrualCategory.setDescr(TEST_CODE);
		accrualCategory.setEffectiveDate(TEST_DATE);
		accrualCategory.setTimestamp(TEST_TIMESTAMP);
        accrualCategory.setLeavePlan("");
        accrualCategory.setAccrualEarnInterval("");
        accrualCategory.setUnitOfTime("");
		KNSServiceLocator.getBusinessObjectService().save(accrualCategory);
		accrualCategoryId = accrualCategory.getLmAccrualCategoryId();
	}

	@Override
	public void tearDown() throws Exception {
		AccrualCategory accrualCategory = KNSServiceLocator
				.getBusinessObjectService().findBySinglePrimaryKey(
						AccrualCategory.class, accrualCategoryId);
		KNSServiceLocator.getBusinessObjectService().delete(accrualCategory);
		super.tearDown();
	}

}
