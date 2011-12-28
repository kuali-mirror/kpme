package org.kuali.hr.time.workarea;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import java.sql.Date;

public class WorkAreaMaintenanceTest extends TkTestCase {

	@Test
	public void testWorkAreaMaintenanceScreen() throws Exception{
    	String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workarea.WorkArea&returnLocation=http://localhost:8080/tk-dev/portal.do&hideReturnLink=true&docFormKey=88888888";	
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	page = HtmlUnitUtil.clickInputContainingText(page, "search");
    	page = HtmlUnitUtil.clickAnchorContainingText(page, "edit");
    	assertTrue("Test that maintenance screen rendered", page.asText().contains("30"));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		WorkArea workArea = new WorkArea();
		workArea.setTkWorkAreaId("1111");
		workArea.setWorkArea(4444L);
		workArea.setOvertimeEditRole(TkConstants.ROLE_TK_EMPLOYEE);
		workArea.setEffectiveDate(new Date(System.currentTimeMillis()));
		KNSServiceLocator.getBusinessObjectService().save(workArea);
	}

	@Override
	public void tearDown() throws Exception {
		WorkArea workArea = (WorkArea)KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(WorkArea.class, 1111);
		KNSServiceLocator.getBusinessObjectService().delete(workArea);
		super.tearDown();
	}
	
	@Test
	public void testWorkAreaFetch() throws Exception{
		WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(1234L, TKUtils.getCurrentDate());
		assertTrue("Work area is not null and valid", workArea != null && workArea.getWorkArea().longValue() == 1234L);
	}
	
	
}
