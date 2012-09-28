/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.document.header;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.test.KPMETestCase;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.test.TkTestUtils;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DocumentHeaderTest extends KPMETestCase {
	
	
	
	private static Long documentId = 1L;//id entered in the bootstrap SQL

    // this test will pass once the data is correct
	//INSERT INTO `tk_document_header_t` (`DOCUMENT_ID`,`PRINCIPAL_ID`,`DOCUMENT_STATUS`,`PAY_BEGIN_DT`,`PAY_END_DT`) VALUES
	  //('1001','admin','I','2011-01-01 00:00:00','2011-01-15 00:00:00'),
	  //('1002','admin','I','2011-01-15 00:00:00','2011-02-01 00:00:00');
	@Test
	public void testDocumentHeaderPrevFetch() throws Exception{
		TimesheetDocumentHeader timeHeader = new TimesheetDocumentHeader();
		timeHeader.setDocumentId("1");
		timeHeader.setPrincipalId("admin");
		timeHeader.setDocumentStatus("F");
		timeHeader.setBeginDate(TkTestUtils.createDate(1, 1, 2011, 0, 0, 0));
		timeHeader.setEndDate(TkTestUtils.createDate(1, 15, 2011, 0, 0, 0));
		KRADServiceLocator.getBusinessObjectService().save(timeHeader);
        DateTime dateTime = new DateTime(2011,1,15,0,0,0,0);
		TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader("admin", new java.util.Date(dateTime.getMillis()));
		Assert.assertTrue(tdh!=null && StringUtils.equals(tdh.getDocumentId(),"1"));
	}
	
	@Test
	public void testDocumentHeaderMaint() throws Exception {
		HtmlPage docHeaderLookUp = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.DOC_HEADER_MAINT_URL);
		docHeaderLookUp = HtmlUnitUtil.clickInputContainingText(docHeaderLookUp, "search");
		Assert.assertTrue("Page contains admin entry", docHeaderLookUp.asText().contains("admin"));		
		HtmlPage maintPage = HtmlUnitUtil.clickAnchorContainingText(docHeaderLookUp, "edit",documentId.toString());		
		Assert.assertTrue("Maintenance Page contains admin entry",maintPage.asText().contains("admin"));		
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		TimesheetDocumentHeader tdh = new TimesheetDocumentHeader();
		tdh.setDocumentId("1234");
		tdh.setPrincipalId("admin");
		tdh.setBeginDate(TKUtils.getCurrentDate());
		tdh.setEndDate(TKUtils.getCurrentDate());
		
		KRADServiceLocator.getBusinessObjectService().save(tdh);
		
		tdh = new TimesheetDocumentHeader();
		tdh.setDocumentId("1000");
		tdh.setPrincipalId("admin");
		tdh.setBeginDate(TKUtils.getCurrentDate());
		tdh.setEndDate(TKUtils.getCurrentDate());
		
		KRADServiceLocator.getBusinessObjectService().save(tdh);
		
		tdh = new TimesheetDocumentHeader();
		tdh.setDocumentId("2345");
		tdh.setPrincipalId("admin");
		tdh.setBeginDate(TKUtils.getCurrentDate());
		tdh.setEndDate(TKUtils.getCurrentDate());
		
		KRADServiceLocator.getBusinessObjectService().save(tdh);
	}
}
