package org.kuali.hr.time.department.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.kpme.core.bo.department.Department;
import org.kuali.kpme.core.service.HrServiceLocator;

public class DepartmentServiceImplTest extends KPMETestCase {
	
	@Test
	public void testSearchDepartments() throws Exception {
		List<Department> allResults = HrServiceLocator.getDepartmentService().getDepartments("admin", null, null, null, "Y");
		Assert.assertEquals("Search returned the wrong number of results.", 11, allResults.size());
		
		List<Department> restrictedResults = HrServiceLocator.getDepartmentService().getDepartments("testuser6", null, null, null, "Y");
		Assert.assertEquals("Search returned the wrong number of results.", 1, restrictedResults.size());
	}

}
