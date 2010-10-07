package org.kuali.hr.time.overtime.daily.rule.service;

import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.kuali.hr.time.overtime.daily.rule.DailyOvertimeRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;

public class DailyOvertimeRuleServiceTest extends TkTestCase {

	
	@Test
	public void testGetDailyOvertimeRules() throws Exception {
		DailyOvertimeRuleService doors = TkServiceLocator.getDailyOvertimeRuleService();
		String dept = null;
		Long ruleId = null;
		Long workArea = null;
		Long task = null;
		Date asOfDate = new Date((new DateTime(2010, 1, 1, 12, 0, 0, 0, DateTimeZone.forID("EST"))).getMillis());
		List<DailyOvertimeRule> rules = null;

		// 1: dept, workarea task
		dept = "TEST-DEPT";
		ruleId = 1L;
		workArea = 1234L;
		task = 1L;		
		rules = doors.getDailyOvertimeRules(dept, workArea, task, asOfDate);
		assertNotNull("Null list of rules", rules);
		assertEquals("Wrong number of results returned", 1, rules.size());
		assertEquals("ID of rule is incorrect.", ruleId.longValue(), (rules.get(0)).getTkDailyOvertimeRuleId().longValue());

		// 2: dept, workarea, -1 
		dept = "TEST-DEPT";
		ruleId = 2L;
		workArea = 1234L;
		task = -999L;		
		rules = doors.getDailyOvertimeRules(dept, workArea, task, asOfDate);
		assertNotNull("Null list of rules", rules);
		assertEquals("Wrong number of results returned", 1, rules.size());
		assertEquals("ID of rule is incorrect.", ruleId.longValue(), (rules.get(0)).getTkDailyOvertimeRuleId().longValue());

		// 3: dept, -1, task
		dept = "TEST-DEPT";
		ruleId = 3L;
		workArea = -999L;
		task = 1L;		
		rules = doors.getDailyOvertimeRules(dept, workArea, task, asOfDate);
		assertNotNull("Null list of rules", rules);
		assertEquals("Wrong number of results returned", 1, rules.size());
		assertEquals("ID of rule is incorrect.", ruleId.longValue(), (rules.get(0)).getTkDailyOvertimeRuleId().longValue());

		// 4: dept, -1, -1
		dept = "TEST-DEPT";
		ruleId = 4L;
		workArea = -999L;
		task = -999L;		
		rules = doors.getDailyOvertimeRules(dept, workArea, task, asOfDate);
		assertNotNull("Null list of rules", rules);
		assertEquals("Wrong number of results returned", 1, rules.size());
		assertEquals("ID of rule is incorrect.", ruleId.longValue(), (rules.get(0)).getTkDailyOvertimeRuleId().longValue());

		// 5: *, workarea, task
		dept = "NOTFOUND";
		ruleId = 5L;
		workArea = 1234L;
		task = 1L;		
		rules = doors.getDailyOvertimeRules(dept, workArea, task, asOfDate);
		assertNotNull("Null list of rules", rules);
		assertEquals("Wrong number of results returned", 1, rules.size());
		assertEquals("ID of rule is incorrect.", ruleId.longValue(), (rules.get(0)).getTkDailyOvertimeRuleId().longValue());

		// 6: *, workarea, -1
		dept = "NOTFOUND";
		ruleId = 6L;
		workArea = 1234L;
		task = -999L;		
		rules = doors.getDailyOvertimeRules(dept, workArea, task, asOfDate);
		assertNotNull("Null list of rules", rules);
		assertEquals("Wrong number of results returned", 1, rules.size());
		assertEquals("ID of rule is incorrect.", ruleId.longValue(), (rules.get(0)).getTkDailyOvertimeRuleId().longValue());

		// 7: *, -1, task
		dept = "NOTFOUND";
		ruleId = 7L;
		workArea = -999L;
		task = 1L;		
		rules = doors.getDailyOvertimeRules(dept, workArea, task, asOfDate);
		assertNotNull("Null list of rules", rules);
		assertEquals("Wrong number of results returned", 1, rules.size());
		assertEquals("ID of rule is incorrect.", ruleId.longValue(), (rules.get(0)).getTkDailyOvertimeRuleId().longValue());

		// 8: *,-1,-1 
		dept = "NOTFOUND";
		ruleId = 8L;
		workArea = -999L;
		task = -999L;		
		rules = doors.getDailyOvertimeRules(dept, workArea, task, asOfDate);
		assertNotNull("Null list of rules", rules);
		assertEquals("Wrong number of results returned", 1, rules.size());
		assertEquals("ID of rule is incorrect.", ruleId.longValue(), (rules.get(0)).getTkDailyOvertimeRuleId().longValue());
	}
}
