package org.kuali.hr.time.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.kuali.hr.time.ApplicationInitializeListener;
import org.kuali.hr.time.util.ClearDatabaseLifecycle;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.web.TKRequestProcessor;
import org.kuali.hr.time.web.TkLoginFilter;
import org.kuali.rice.core.config.Config;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.core.config.spring.ConfigFactoryBean;
import org.kuali.rice.core.lifecycle.Lifecycle;
import org.kuali.rice.kns.util.ErrorMap;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.test.lifecycles.JettyServerLifecycle;
import org.springframework.mock.web.MockHttpServletRequest;

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

@SuppressWarnings("deprecation")
@Ignore
public class TkTestCase extends KNSTestCase{

	public void setUp() throws Exception {
		ApplicationInitializeListener.ALTERNATE_LOG4J_FILE = "classpath:test_log4j.properties";
		setContextName("/tk-dev");
		setRelativeWebappRoot("/src/main/webapp");

		ConfigFactoryBean.CONFIG_OVERRIDE_LOCATION = "classpath:META-INF/tk-test-config.xml";
		TkLoginFilter.TEST_ID = "admin";
		GlobalVariables.setErrorMap(new ErrorMap());
		TKContext.setHttpServletRequest(new MockHttpServletRequest());
		super.setUp();
		new TKRequestProcessor().setUserOnContext(TKContext.getHttpServletRequest());
		//this clears the cache that was loaded from the above call.  Do not comment
		TKContext.setHttpServletRequest(new MockHttpServletRequest());
		new ClearDatabaseLifecycle().start();
	}

	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> lifeCycles = super.getPerTestLifecycles();
		lifeCycles.add(new Lifecycle() {
			boolean started = false;

			public boolean isStarted() {
				return this.started;
			}

			public void start() throws Exception {
				setModuleName(getModuleName());
				setBaseDirSystemProperty(getModuleName());
				Config config = getTestHarnessConfig();
				ConfigContext.init(config);

				this.started = true;
			}

			public void stop() throws Exception {
				this.started = false;
			}
		});
		lifeCycles.add(new Lifecycle() {
			private JettyServerLifecycle jettyServerLifecycle;

			public boolean isStarted() {
				return jettyServerLifecycle.isStarted();
			}

			public void start() throws Exception {
				jettyServerLifecycle = new JettyServerLifecycle(getPort(), getContextName(), getRelativeWebappRoot());
				jettyServerLifecycle.start();
			}

			public void stop() throws Exception {
				this.jettyServerLifecycle.stop();
			}
		});
		return lifeCycles;
	}

	@Override
	protected List<String> getConfigLocations() {
		List<String> og_config = super.getConfigLocations();
		og_config.add("classpath:META-INF/tk-test-config.xml");
	    return og_config;
	}

	@Override
	protected String getModuleName() {
		return "";
	}

   protected final void setFieldValue(HtmlPage page, String fieldId, String fieldValue) {
        HtmlElement element = page.getHtmlElementById(fieldId);
        assertTrue("element " + fieldId + " is null, page: " + page.asText(), element != null);

        if (element instanceof HtmlTextInput) {
            HtmlTextInput textField = (HtmlTextInput) element;
            textField.setValueAttribute(fieldValue);
        } else if (element instanceof HtmlTextArea) {
            HtmlTextArea textAreaField = (HtmlTextArea) element;
            textAreaField.setText(fieldValue);
        } else if (element instanceof HtmlHiddenInput) {
            HtmlHiddenInput hiddenField = (HtmlHiddenInput) element;
            hiddenField.setValueAttribute(fieldValue);
        } else if (element instanceof HtmlSelect) {
            HtmlSelect selectField = (HtmlSelect) element;
            try {
                selectField.setSelectedAttribute(fieldValue, true);
            } catch (IllegalArgumentException e) {
                Assert.fail("select element [" + element.asText() + "] " + e.getMessage());
            }
        } else if (element instanceof HtmlCheckBoxInput) {
            HtmlCheckBoxInput checkboxField = (HtmlCheckBoxInput) element;
            if (fieldValue.equals("on")) {
                checkboxField.setChecked(true);
            } else if (fieldValue.equals("off")) {
                checkboxField.setChecked(false);
            } else {
                assertTrue("Invalid checkbox value", false);
            }
        } else if (element instanceof HtmlFileInput) {
            HtmlFileInput fileInputField = (HtmlFileInput) element;
            fileInputField.setValueAttribute(fieldValue);
        } else if (element instanceof HtmlRadioButtonInput) {
        	HtmlRadioButtonInput radioButton = (HtmlRadioButtonInput) element;
        	if (fieldValue.equals("on")) {
        		radioButton.setChecked(true);
        	} else if (fieldValue.equals("off")) {
        		radioButton.setChecked(false);
        	}
        } else {
            fail("Unknown control field: " + fieldId);
        }
    }

}
