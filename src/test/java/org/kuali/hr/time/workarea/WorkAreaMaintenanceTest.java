package org.kuali.hr.time.workarea;

import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.kuali.rice.krad.util.GlobalVariables;

public class WorkAreaMaintenanceTest extends TkTestCase {

	@Test
	public void testWorkAreaMaintenanceScreen() throws Exception{
    	String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.workarea.WorkArea&returnLocation=" + HtmlUnitUtil.getBaseURL() + "/portal.do&hideReturnLink=true&docFormKey=88888888";
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	setFieldValue(page,"workArea","30");
    	page = HtmlUnitUtil.clickInputContainingText(page, "search");
    	page = HtmlUnitUtil.clickAnchorContainingText(page, "edit");
    	Assert.assertTrue("Test that maintenance screen rendered", page.asText().contains("30"));
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		WorkArea workArea = new WorkArea();
		workArea.setTkWorkAreaId("1111");
		workArea.setWorkArea(4444L);
		workArea.setOvertimeEditRole(TkConstants.ROLE_TK_EMPLOYEE);
		workArea.setEffectiveDate(new Date(System.currentTimeMillis()));
		KRADServiceLocator.getBusinessObjectService().save(workArea);
	}

	@Override
	public void tearDown() throws Exception {
		WorkArea workArea = (WorkArea)KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(WorkArea.class, 1111);
		KRADServiceLocator.getBusinessObjectService().delete(workArea);
		super.tearDown();
	}
	
	@Test
	public void testWorkAreaFetch() throws Exception{
		WorkArea workArea = TkServiceLocator.getWorkAreaService().getWorkArea(1234L, TKUtils.getCurrentDate());
		Assert.assertTrue("Work area is not null and valid", workArea != null && workArea.getWorkArea().longValue() == 1234L);
	}
	
}
