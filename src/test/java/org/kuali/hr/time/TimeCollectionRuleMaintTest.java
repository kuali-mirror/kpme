package org.kuali.hr.time;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TimeCollectionRuleMaintTest extends TkTestCase{
	private static final String TEST_CODE="X";
	private static final String TEST_ID_STRING="100";	
	private static final java.sql.Date TEST_DATE=new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	private static Long timeCollectionRuleId;
	
	@Test
	public void testTimeCollectionRuleMaint() throws Exception {
		HtmlPage timeCollectionRuleLookup = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.TIME_COLLECTION_RULE_MAINT_URL);
		timeCollectionRuleLookup = HtmlUnitUtil.clickInputContainingText(timeCollectionRuleLookup, "search");
		assertTrue("Page contains test timeCollectionRule", timeCollectionRuleLookup.asText().contains(TEST_CODE));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(timeCollectionRuleLookup, "edit",timeCollectionRuleId.toString());		
		assertTrue("Maintenance Page contains test timeCollectionRule",maintPage.asText().contains(TEST_CODE));		
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		TimeCollectionRule timeCollectionRule = new TimeCollectionRule();
		timeCollectionRule.setDeptId(TEST_ID_STRING);
		timeCollectionRule.setEffDate(TEST_DATE);
		timeCollectionRule.setHrsDistributionF(TEST_CODE);
		timeCollectionRule.setTimeStamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		timeCollectionRule.setUserPrincipalId(TEST_CODE);
		//timeCollectionRule.setWorkArea(TEST_ID_LONG);				
		KNSServiceLocator.getBusinessObjectService().save(timeCollectionRule);
		timeCollectionRuleId=timeCollectionRule.getTimeCollectionRuleId();
	}

	@Override
	public void tearDown() throws Exception {
		//cleaning up
		TimeCollectionRule timeCollectionRuleObj = KNSServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(TimeCollectionRule.class, timeCollectionRuleId);
		KNSServiceLocator.getBusinessObjectService().delete(timeCollectionRuleObj);
		super.tearDown();
	}

}
