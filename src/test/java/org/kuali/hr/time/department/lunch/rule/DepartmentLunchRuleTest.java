package org.kuali.hr.time.department.lunch.rule;

import java.math.BigDecimal;
import java.sql.Date;

import org.junit.Test;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class DepartmentLunchRuleTest extends TkTestCase {
	@Test
	public void testDepartmentLunchRuleFetch() throws Exception{
		DeptLunchRule deptLunchRule = new DeptLunchRule();
		deptLunchRule.setActive(true);
		deptLunchRule.setDept("TEST");
		deptLunchRule.setWorkArea(1234L);
		deptLunchRule.setEffectiveDate(new Date(System.currentTimeMillis()));
		deptLunchRule.setJobNumber(0L);
		deptLunchRule.setPrincipalId("admin");
		deptLunchRule.setMaxMins(new BigDecimal(30));
		deptLunchRule.setRequiredClockFl(true);
		
		KNSServiceLocator.getBusinessObjectService().save(deptLunchRule);
		
		deptLunchRule = TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule("TEST", 
											1234L, "admin", 0L, new Date(System.currentTimeMillis()));
		assertTrue("dept lunch rule fetched ", deptLunchRule!=null);
		
	}
}
