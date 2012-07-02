package org.kuali.hr.time.earngroup.service;

import java.sql.Date;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.earncodegroup.EarnCodeGroup;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class EarnCodeGroupServiceTest extends TkTestCase{
	@Test
	public void testEarnGroupFetch() throws Exception{
		EarnCodeGroup earnGroup = TkServiceLocator.getEarnCodeGroupService().getEarnCodeGroup("REG", new Date(System.currentTimeMillis()));
		Assert.assertTrue("Test Earn Group fetch failed", earnGroup!=null && StringUtils.equals("REG", earnGroup.getEarnCodeGroup()));
		Assert.assertTrue("Test earn group def fetch failed", earnGroup.getEarnCodeGroups()!=null && earnGroup.getEarnCodeGroups().get(0).getHrEarnCodeGroupId().equals("100"));
	}
	
	@Test
	public void testEarnGroupMaintenancePage() throws Exception{				
		HtmlPage earnCodeLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.EARN_CODE_GROUP_MAINT_URL);
		earnCodeLookUp = HtmlUnitUtil.clickInputContainingText(earnCodeLookUp, "search");
		HtmlUnitUtil.createTempFile(earnCodeLookUp);
		Assert.assertTrue("Page contains REG entry", earnCodeLookUp.asText().contains("REG"));	
		
		EarnCodeGroup earnGroup = TkServiceLocator.getEarnCodeGroupService().getEarnCodeGroup("REG", new Date(System.currentTimeMillis()));
		String earnGroupId = earnGroup.getHrEarnCodeGroupId().toString();
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(earnCodeLookUp, "edit", earnGroupId);		
		HtmlUnitUtil.createTempFile(maintPage);
		Assert.assertTrue("Maintenance Page contains REG entry",maintPage.asText().contains("REG"));		
	}	
}
