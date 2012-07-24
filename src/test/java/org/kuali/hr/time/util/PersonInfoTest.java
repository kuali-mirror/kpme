package org.kuali.hr.time.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.ApplicationInitializeListener;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.web.TKRequestProcessor;
import org.kuali.hr.time.web.TkLoginFilter;
import org.kuali.rice.core.impl.config.property.ConfigFactoryBean;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;
import org.springframework.mock.web.MockHttpServletRequest;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PersonInfoTest extends TkTestCase {
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		ApplicationInitializeListener.ALTERNATE_LOG4J_FILE = "classpath:test_log4j.properties";
		setContextName("/tk-dev");
		setRelativeWebappRoot("/src/main/webapp");
		
		ConfigFactoryBean.CONFIG_OVERRIDE_LOCATION = "classpath:META-INF/tk-test-config.xml";		
		TkLoginFilter.TEST_ID = "eric";
		GlobalVariables.setMessageMap(new MessageMap());
		TKContext.setHttpServletRequest(new MockHttpServletRequest());
	}
	
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
