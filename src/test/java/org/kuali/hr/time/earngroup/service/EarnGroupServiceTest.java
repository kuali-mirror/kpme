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
	@Test
	public void testEarnGroupFetch() throws Exception{
		EarnGroup earnGroup = TkServiceLocator.getEarnGroupService().getEarnGroup("REG", new Date(System.currentTimeMillis()));
		assertTrue("Test Earn Group fetch failed", earnGroup!=null && StringUtils.equals("REG", earnGroup.getEarnGroup()));
		assertTrue("Test earn group def fetch failed", earnGroup.getEarnGroups()!=null && earnGroup.getEarnGroups().get(0).getHrEarnGroupId().equals("100"));
	}
	
	@Test
	public void testEarnGroupMaintenancePage() throws Exception{				
		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.EARN_GROUP_MAINT_URL);
		earnCodeLookUp = HtmlUnitUtil.clickInputContainingText(earnCodeLookUp, "search");
		HtmlUnitUtil.createTempFile(earnCodeLookUp);
		assertTrue("Page contains REG entry", earnCodeLookUp.asText().contains("REG"));	
		
		EarnGroup earnGroup = TkServiceLocator.getEarnGroupService().getEarnGroup("REG", new Date(System.currentTimeMillis()));
		String earnGroupId = earnGroup.getHrEarnGroupId().toString();
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnCodeLookUp, "edit", earnGroupId);		
		HtmlUnitUtil.createTempFile(maintPage);
		assertTrue("Maintenance Page contains REG entry",maintPage.asText().contains("REG"));		
	}	
}
