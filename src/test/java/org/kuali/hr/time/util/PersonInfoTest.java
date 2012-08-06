package org.kuali.hr.time.util;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PersonInfoTest extends TkTestCase {
	
	@Test
	public void testPersonInfo() throws Exception{	
		// pass the login filter
		HtmlPage clockPage = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.PERSON_INFO_URL);
		HtmlUnitUtil.createTempFile(clockPage);
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Principal Name"));
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Name"));
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Employee, Eric"));
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Job Number"));
		Assert.assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Department Admin"));
		
	}
 
}
