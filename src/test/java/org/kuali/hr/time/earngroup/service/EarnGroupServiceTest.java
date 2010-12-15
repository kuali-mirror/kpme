package org.kuali.hr.time.earngroup.service;

import java.sql.Date;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.kuali.hr.time.earngroup.EarnGroup;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class EarnGroupServiceTest extends TkTestCase{
	
	private static Long earnGroupId = 1L;//id entered in the bootstrap SQL
	
	@Test
	public void testEarnGroupFetch() throws Exception{
		EarnGroup earnGroup = TkServiceLocator.getEarnGroupService().getEarnGroup("REG", new Date(System.currentTimeMillis()));
		assertTrue("Test Earn Group fetch failed", earnGroup!=null && StringUtils.equals("REG", earnGroup.getEarnGroup()));
		assertTrue("Test earn group def fetch failed", earnGroup.getEarnGroups()!=null && earnGroup.getEarnGroups().get(0).getTkEarnGroupId()==100);	
	}
	
	@Test
	public void testEarnGroupMaintenancePage() throws Exception{				
		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.EARN_GROUP_MAINT_URL);
		earnCodeLookUp = HtmlUnitUtil.clickInputContainingText(earnCodeLookUp, "search");
		assertTrue("Page contains SD1 entry", earnCodeLookUp.asText().contains("SD1"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnCodeLookUp, "edit",earnGroupId.toString());		
		assertTrue("Maintenance Page contains SD1 entry",maintPage.asText().contains("SD1"));		
	}	
}
