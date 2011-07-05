package org.kuali.hr.time.web;

import org.junit.Before;
import org.junit.Test;
import org.kuali.hr.time.ApplicationInitializeListener;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.core.config.spring.ConfigFactoryBean;
import org.kuali.rice.kns.util.ErrorMap;
import org.kuali.rice.kns.util.GlobalVariables;
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
		GlobalVariables.setErrorMap(new ErrorMap());
		TKContext.setHttpServletRequest(new MockHttpServletRequest());
		
		new TKRequestProcessor().setUserOnContext(TKContext.getHttpServletRequest());
		//this clears the cache that was loaded from the above call.  Do not comment
		TKContext.setHttpServletRequest(new MockHttpServletRequest());
	}
	
	@Test
	public void testPersonInfo() throws Exception{	
		// pass the login filter
		HtmlPage clockPage = HtmlUnitUtil.gotoPageAndLogin(TkTestConstants.Urls.PERSON_INFO_URL);
		HtmlUnitUtil.createTempFile(clockPage);
		assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Job Number"));
		assertTrue("Person Info Page renders with inappropriate data",clockPage.asText().contains("Organization Admin"));
	}
 
}
