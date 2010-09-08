package org.kuali.hr.time.workarea;

import java.sql.Date;

import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WorkAreaMaintenanceTest extends TkTestCase {

	@Test
	public void testWorkAreaMaintenanceScreen() throws Exception{
    	String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workarea.WorkArea&returnLocation=http://localhost:8080/tk-dev/portal.do&hideReturnLink=true&docFormKey=88888888";	
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	page = HtmlUnitUtil.clickInputContainingText(page, "search");
    	page = HtmlUnitUtil.clickAnchorContainingText(page, "edit");
    	HtmlUnitUtil.createTempFile(page);
    	assertTrue("Test that maintenance screen rendered", page.asText().contains("1111"));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		WorkArea workArea = new WorkArea();
		workArea.setWorkAreaId(1111L);
		workArea.setEffectiveDate(new Date(System.currentTimeMillis()));
		KNSServiceLocator.getBusinessObjectService().save(workArea);
	}

	@Override
	public void tearDown() throws Exception {
		WorkArea workArea = (WorkArea)KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(WorkArea.class, 1111);
		KNSServiceLocator.getBusinessObjectService().delete(workArea);
		super.tearDown();
	}
	
	
}
