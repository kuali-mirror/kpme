package org.kuali.hr.lm.timeoff;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class SystemScheduledTimeOffMaintTest extends TkTestCase {
	
	private static final String TEST_CODE = "_T";
	private static Long systemScheduledTimeOffId;
	private static final Date TEST_DATE = new Date(Calendar.getInstance()
			.getTimeInMillis());
	private static final Timestamp TEST_TIMESTAMP = new Timestamp(Calendar
			.getInstance().getTimeInMillis());

	@Test
	public void testAccuralCategoryMaint() throws Exception {
		HtmlPage systemScheduledTimeOffLookup = HtmlUnitUtil
				.gotoPageAndLogin(TkTestConstants.Urls.SYS_SCHD_TIME_OFF_MAINT_URL);
		systemScheduledTimeOffLookup = HtmlUnitUtil.clickInputContainingText(
				systemScheduledTimeOffLookup, "search");
		assertTrue("Page contains test SystemScheduledTimeOff", systemScheduledTimeOffLookup
				.asText().contains(TEST_CODE.toString()));
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(
				systemScheduledTimeOffLookup, "edit", systemScheduledTimeOffId.toString());
		assertTrue("Maintenance Page contains test SystemScheduledTimeOff", maintPage
				.asText().contains(TEST_CODE));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		SystemScheduledTimeOff systemScheduledTimeOff = new SystemScheduledTimeOff();
		systemScheduledTimeOff.setAccrualCategory(TEST_CODE);
		systemScheduledTimeOff.setAccruedDate(TEST_DATE);
		systemScheduledTimeOff.setAmountofTime(0l);
		systemScheduledTimeOff.setExpirationDate(TEST_DATE);
		systemScheduledTimeOff.setLeaveCode(TEST_CODE);
		systemScheduledTimeOff.setLeavePlan(TEST_CODE);
		systemScheduledTimeOff.setLocation(TEST_CODE);
		systemScheduledTimeOff.setPremiumHoliday(TEST_CODE);
		systemScheduledTimeOff.setScheduledTimeOffDate(TEST_DATE);
		systemScheduledTimeOff.setTransferConversionFactor(new BigDecimal(0));
		systemScheduledTimeOff.setTransfertoLeaveCode(TEST_CODE);
		systemScheduledTimeOff.setUnusedTime(TEST_CODE);
		systemScheduledTimeOff.setActive(true);
		systemScheduledTimeOff.setDescr(TEST_CODE);
		systemScheduledTimeOff.setEffectiveDate(TEST_DATE);
		systemScheduledTimeOff.setTimestamp(TEST_TIMESTAMP);
		KNSServiceLocator.getBusinessObjectService().save(systemScheduledTimeOff);
		systemScheduledTimeOffId = systemScheduledTimeOff.getLmSystemScheduledTimeOffId();
	}

	@Override
	public void tearDown() throws Exception {
		SystemScheduledTimeOff systemScheduledTimeOff = KNSServiceLocator
				.getBusinessObjectService().findBySinglePrimaryKey(
						SystemScheduledTimeOff.class, systemScheduledTimeOffId);
		KNSServiceLocator.getBusinessObjectService().delete(systemScheduledTimeOff);
		super.tearDown();
	}

}
