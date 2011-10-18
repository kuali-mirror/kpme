package org.kuali.hr.lm.accrual;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.kuali.hr.lm.accrual.LeaveAccrualCategory;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class LeaveAccrualCategoryMaintTest extends TkTestCase {
	
	private static final String TEST_CODE = "T";
	private static Long leaveAccrualCategoryId;
	private static final Date TEST_DATE = new Date(Calendar.getInstance()
			.getTimeInMillis());
	private static final Timestamp TEST_TIMESTAMP = new Timestamp(Calendar
			.getInstance().getTimeInMillis());

	@Test
	public void testAccuralCategoryMaint() throws Exception {
		HtmlPage accuralCategoryLookup = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_ACCRUAL_CATEGORY_MAINT_URL);
		accuralCategoryLookup = HtmlUnitUtil.clickInputContainingText(
				accuralCategoryLookup, "search");
		assertTrue("Page contains test Leave accrual Category", accuralCategoryLookup
				.asText().contains(TEST_CODE.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				accuralCategoryLookup, "edit", leaveAccrualCategoryId.toString());
		assertTrue("Maintenance Page contains test Leave accrual Category", maintPage
				.asText().contains(TEST_CODE));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		LeaveAccrualCategory leaveAccrualCategory = new LeaveAccrualCategory();
		leaveAccrualCategory.setAccrualCategory(TEST_CODE);
		leaveAccrualCategory.setLeavePlan(TEST_CODE);
		leaveAccrualCategory.setAccrualEarnInterval(TEST_CODE);
		leaveAccrualCategory.setProration(TEST_CODE);
		leaveAccrualCategory.setDonation(TEST_CODE);
		leaveAccrualCategory.setUnitOfTime(TEST_CODE);
		leaveAccrualCategory.setShowOnGrid(TEST_CODE);
		leaveAccrualCategory.setActive(true);
		leaveAccrualCategory.setDescr(TEST_CODE);
		leaveAccrualCategory.setEffectiveDate(TEST_DATE);
		leaveAccrualCategory.setTimestamp(TEST_TIMESTAMP);
		KNSServiceLocator.getBusinessObjectService().save(leaveAccrualCategory);
		leaveAccrualCategoryId = leaveAccrualCategory.getLmAccrualCategoryId();
	}

	@Override
	public void tearDown() throws Exception {
		LeaveAccrualCategory leaveAccrualCategory = KNSServiceLocator
				.getBusinessObjectService().findBySinglePrimaryKey(
						LeaveAccrualCategory.class, leaveAccrualCategoryId);
		KNSServiceLocator.getBusinessObjectService().delete(leaveAccrualCategory);
		super.tearDown();
	}

}
