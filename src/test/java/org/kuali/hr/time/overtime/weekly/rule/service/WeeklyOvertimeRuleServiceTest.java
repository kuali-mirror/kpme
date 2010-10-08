package org.kuali.hr.time.overtime.weekly.rule.service;

import java.sql.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;

public class WeeklyOvertimeRuleServiceTest extends TkTestCase {

	// TODO: Refactor this to match changes to the signature changes of the service/DAO
	@Test
	public void testGetWeeklyOvertimeRules() throws Exception {
		WeeklyOvertimeRuleService wors = TkServiceLocator.getWeeklyOvertimeRuleService();
		
		String fromEarnGroup = "";
		Date asOfDate = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		
		wors.getWeeklyOvertimeRules(fromEarnGroup, asOfDate);
	}
}
