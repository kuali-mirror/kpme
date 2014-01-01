/**
 * Copyright 2004-2014 The Kuali Foundation
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
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.KPMEWebTestCase;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.FunctionalTest;
import org.kuali.kpme.core.IntegrationTest;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.workflow.TimesheetDocumentHeader;
import org.kuali.kpme.tklm.utils.TkTestConstants;
import org.kuali.rice.krad.service.KRADServiceLocator;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

@FunctionalTest
public class TimesheetDocumentHeaderTest extends KPMEWebTestCase {
	
	
	
	private static Long documentId = 1L;//id entered in the bootstrap SQL

    // this test will pass once the data is correct
	//INSERT INTO `tk_document_header_t` (`DOCUMENT_ID`,`PRINCIPAL_ID`,`DOCUMENT_STATUS`,`PAY_BEGIN_DT`,`PAY_END_DT`) VALUES
	  //('1001','admin','I','2011-01-01 00:00:00','2011-01-15 00:00:00'),
	  //('1002','admin','I','2011-01-15 00:00:00','2011-02-01 00:00:00');
	@IntegrationTest
	@Test
	public void testDocumentHeaderPrevFetch() throws Exception{
		TimesheetDocumentHeader timeHeader = new TimesheetDocumentHeader();
		timeHeader.setDocumentId("1");
		timeHeader.setPrincipalId("admin");
		timeHeader.setDocumentStatus("F");
		timeHeader.setBeginDateTime(new DateTime(2011, 1, 1, 0, 0, 0));
		timeHeader.setEndDateTime(new DateTime(2011, 1, 15, 0, 0, 0));
		KRADServiceLocator.getBusinessObjectService().save(timeHeader);
        DateTime dateTime = new DateTime(2011,1,15,0,0,0,0);
		TimesheetDocumentHeader tdh = TkServiceLocator.getTimesheetDocumentHeaderService().getPreviousDocumentHeader("admin", dateTime);
		Assert.assertTrue(tdh!=null && StringUtils.equals(tdh.getDocumentId(),"1"));
	}
	
	@Ignore
	@FunctionalTest
	@Test
	public void testDocumentHeaderMaint() throws Exception {
		/**
		 * there are a number of errors appearing from kboot-xxx.js. This may be preventing the KRAD page from rendering completely from an HtmlUnit perspective.
		 * When inspected, docHeaderLookUp.asXml() only contains source/data up to the document status field rendered on the form. The field label is rendered,
		 * but rendering ceases shortly after.
		 * When using docHeaderLookUp.asText(), only the title of the page is displayed "Kuali :: Timesheet Document Header Lookup" followed by a line containing "1."
		 * Thus, no input containing text "search" exists and the test fails from an NPE within HtmlUnitUtil.clickInputContainingText(docHeaderLookUp, "search");
		 */
		HtmlPage docHeaderLookUp = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), TkTestConstants.Urls.DOC_HEADER_MAINT_URL);
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
		tdh.setBeginDate(LocalDate.now().toDate());
		tdh.setEndDate(LocalDate.now().toDate());
		
		KRADServiceLocator.getBusinessObjectService().save(tdh);
		
		tdh = new TimesheetDocumentHeader();
		tdh.setDocumentId("1000");
		tdh.setPrincipalId("admin");
		tdh.setBeginDate(LocalDate.now().toDate());
		tdh.setEndDate(LocalDate.now().toDate());
		
		KRADServiceLocator.getBusinessObjectService().save(tdh);
		
		tdh = new TimesheetDocumentHeader();
		tdh.setDocumentId("2345");
		tdh.setPrincipalId("admin");
		tdh.setBeginDate(LocalDate.now().toDate());
		tdh.setEndDate(LocalDate.now().toDate());
		
		KRADServiceLocator.getBusinessObjectService().save(tdh);
	}
}
