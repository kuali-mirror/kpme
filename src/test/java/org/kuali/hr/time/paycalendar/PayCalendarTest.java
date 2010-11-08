package org.kuali.hr.time.paycalendar;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PayCalendarTest extends TkTestCase {

	@Test
	public void scratchTest() throws Exception {
		//List<String> roleIds = new ArrayList<String>();
		
		//roleIds.add(KIMServiceLocator.getRoleService().getRoleIdByName("KUALI", "TK_APPROVER"));
		//String role_id = KIMServiceLocator.getRoleService().getRoleIdByName("KUALI", "TK_APPROVER");
		//KimRoleInfo kri = KIMServiceLocator.getRoleService().getRole(role_id);
		//String tid = kri.getKimTypeId();
		//KimTypeInfo kti = KIMServiceLocator.getTypeInfoService().getKimType("30");
		//KIMServiceLocator.getRoleManagementService().saveRole("98", "TK_APPROVER", "i <3 rice", true, "30", "KUALI");


//	        KimAttributeImpl documentTypeAttribute = new KimAttributeImpl();
//	        documentTypeAttribute.setKimAttributeId("30");
//	        documentTypeAttribute.setAttributeName("workAreaId");
//	        documentTypeAttribute.setNamespaceCode("KUALI");
//	        documentTypeAttribute.setAttributeLabel("");
//	        documentTypeAttribute.setComponentName(KimAttributes.class.getName());
//	        documentTypeAttribute.setActive(true);
//	        KNSServiceLocator.getBusinessObjectService().save(documentTypeAttribute);
		
	
		//AttributeSet as = new AttributeSet();
//		as.put("workAreaId", "999");
//		KIMServiceLocator.getRoleManagementService().assignPrincipalToRole("admin", "KUALI", "TK_APPROVER", as);
//		
//		List<RoleMembershipInfo> list = KIMServiceLocator.getRoleService().getRoleMembers(roleIds, as);
		
		
		//KIMServiceLocator.getRoleService().getRoleQualifiersForPrincipal("0002170302", roleIds, new AttributeSet());
		assertTrue(true);
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		PayCalendar payCalendar = new PayCalendar();
		payCalendar.setCalendarGroup("test");
		payCalendar.setChart("BL");
		payCalendar.setBeginDate(new Date(System.currentTimeMillis()));
		payCalendar.setBeginTime(new Time(System.currentTimeMillis()));
		payCalendar.setEndDate(new Date(System.currentTimeMillis()));
		payCalendar.setEndTime(new Time(System.currentTimeMillis()));
		payCalendar.setPayCalendarId(new Long(1000));
		
		KNSServiceLocator.getBusinessObjectService().save(payCalendar);
		PayCalendarDates payCalendarDates = new PayCalendarDates();
		payCalendarDates.setPayCalendarId(new Long(1000));
		payCalendarDates.setPayCalendarDatesId(new Long(1000));
		payCalendarDates.setBeginPeriodDateTime(new Date(System.currentTimeMillis()));
		payCalendarDates.setEndPeriodDateTime(new Date(System.currentTimeMillis()));
		KNSServiceLocator.getBusinessObjectService().save(payCalendarDates);
	}

	
	@Test
	public void testPayCalendar() throws Exception{
    	String baseUrl = HtmlUnitUtil.getBaseURL() + "/kr/lookup.do?methodToCall=start&businessObjectClassName=org.kuali.hr.time.paycalendar.PayCalendar&returnLocation=http://localhost:8080/tk-dev/portal.do&hideReturnLink=true&docFormKey=88888888";	
    	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
    	page = HtmlUnitUtil.clickInputContainingText(page, "search");
    	page = HtmlUnitUtil.clickAnchorContainingText(page, "edit");
    	assertTrue("Test that maintenance screen rendered", page.asText().contains("test"));
	}
	
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
