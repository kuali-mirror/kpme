package org.kuali.hr.time.document.header;

import org.junit.Test;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kns.service.KNSServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DocumentHeaderTest extends TkTestCase {
	
	
	
	private static Long documentId = 1L;//id entered in the bootstrap SQL
		
	@Test
	public void testDocumentHeaderPrevFetch() throws Exception{
		TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader("admin", 2345L);
		assertTrue(tdh!=null && tdh.getDocumentId() == "1234");
	}
	
	@Test
	public void testDocumentHeaderMaint() throws Exception {
		HtmlPage docHeaderLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DOC_HEADER_MAINT_URL);
		docHeaderLookUp = HtmlUnitUtil.clickInputContainingText(docHeaderLookUp, "search");
		assertTrue("Page contains admin entry", docHeaderLookUp.asText().contains("admin"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(docHeaderLookUp, "edit",documentId.toString());		
		assertTrue("Maintenance Page contains admin entry",maintPage.asText().contains("admin"));		
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		TimesheetDocumentHeader tdh = new TimesheetDocumentHeader();
		tdh.setDocumentId("1234");
		tdh.setPrincipalId("admin");
		tdh.setPayBeginDate(TKUtils.getCurrentDate());
		tdh.setPayEndDate(TKUtils.getCurrentDate());
		
		KNSServiceLocator.getBusinessObjectService().save(tdh);
		
		tdh = new TimesheetDocumentHeader();
		tdh.setDocumentId("1000");
		tdh.setPrincipalId("admin");
		tdh.setPayBeginDate(TKUtils.getCurrentDate());
		tdh.setPayEndDate(TKUtils.getCurrentDate());
		
		KNSServiceLocator.getBusinessObjectService().save(tdh);
		
		tdh = new TimesheetDocumentHeader();
		tdh.setDocumentId("2345");
		tdh.setPrincipalId("admin");
		tdh.setPayBeginDate(TKUtils.getCurrentDate());
		tdh.setPayEndDate(TKUtils.getCurrentDate());
		
		KNSServiceLocator.getBusinessObjectService().save(tdh);
	}
}
