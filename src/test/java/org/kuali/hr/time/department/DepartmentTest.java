package org.kuali.hr.time.department;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TestHarnessWebBase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DepartmentTest extends TestHarnessWebBase {
	@Test
	public void testDepartmentMaint() throws Exception{
		HtmlPage deptLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DEPT_MAINT_URL);
		HtmlUnitUtil.createTempFile(deptLookup);
	}
	
	
}
