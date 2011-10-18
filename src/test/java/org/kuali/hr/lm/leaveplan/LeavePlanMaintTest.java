package org.kuali.hr.lm.leaveplan;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.kuali.hr.lm.leaveplan.LeavePlan;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class LeavePlanMaintTest extends TkTestCase {
	
	private static final String TEST_CODE = "_T";
	private static Long leavePlanId;
	private static final Date TEST_DATE = new Date(Calendar.getInstance()
			.getTimeInMillis());
	private static final Timestamp TEST_TIMESTAMP = new Timestamp(Calendar
			.getInstance().getTimeInMillis());

	@Test
	public void testAccuralCategoryMaint() throws Exception {
		HtmlPage leavePlanLookup = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.LEAVE_PLAN_MAINT_URL);
		leavePlanLookup = HtmlUnitUtil.clickInputContainingText(
				leavePlanLookup, "search");
		assertTrue("Page contains test LeavePlan", leavePlanLookup
				.asText().contains(TEST_CODE.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				leavePlanLookup, "edit", leavePlanId.toString());
		assertTrue("Maintenance Page contains test LeavePlan", maintPage
				.asText().contains(TEST_CODE));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		LeavePlan leavePlan = new LeavePlan();
		leavePlan.setLeavePlan(TEST_CODE);
		leavePlan.setCalendarYearStart(TEST_CODE);
		leavePlan.setActive(true);
		leavePlan.setDescr(TEST_CODE);
		leavePlan.setEffectiveDate(TEST_DATE);
		leavePlan.setTimestamp(TEST_TIMESTAMP);
		KNSServiceLocator.getBusinessObjectService().save(leavePlan);
		leavePlanId = leavePlan.getLmLeavePlanId();
	}

	@Override
	public void tearDown() throws Exception {
		LeavePlan leavePlan = KNSServiceLocator
				.getBusinessObjectService().findBySinglePrimaryKey(
						LeavePlan.class, leavePlanId);
		KNSServiceLocator.getBusinessObjectService().delete(leavePlan);
		super.tearDown();
	}

}
