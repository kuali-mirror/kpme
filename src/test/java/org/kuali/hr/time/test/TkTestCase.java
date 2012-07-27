package org.kuali.hr.time.test;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.kuali.hr.time.ApplicationInitializeListener;
import org.kuali.hr.time.util.ClearDatabaseLifecycle;
import org.kuali.hr.time.util.DatabaseCleanupDataLifecycle;
import org.kuali.hr.time.util.LoadDatabaseDataLifeCycle;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.web.TKRequestProcessor;
import org.kuali.hr.time.web.TkLoginFilter;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.impl.config.property.ConfigFactoryBean;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.rice.test.lifecycles.JettyServerLifecycle;
import org.springframework.mock.web.MockHttpServletRequest;

import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
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
		GlobalVariables.setMessageMap(new MessageMap());
		TKContext.setHttpServletRequest(new MockHttpServletRequest());
		super.setUp();
		new TKRequestProcessor().setUserOnContext();
		//this clears the cache that was loaded from the above call.  Do not comment
		TKContext.setHttpServletRequest(new MockHttpServletRequest());
		new ClearDatabaseLifecycle().start();
		new LoadDatabaseDataLifeCycle(this.getClass()).start();
	}

    @Override
    public void tearDown() throws Exception {
        // runs custom SQL at the end of each test.
        // useful for difficult to reset test additions, not handled by
        // our ClearDatabaseLifecycle.
        new DatabaseCleanupDataLifecycle(this.getClass()).start();
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
        Assert.assertTrue("element " + fieldId + " is null, page: " + page.asText(), element != null);

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
            	Assert.assertTrue("Invalid checkbox value", false);
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
        	Assert.fail("Unknown control field: " + fieldId);
        }
    }
   
   public void futureEffectiveDateValidation(String baseUrl) throws Exception {
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(baseUrl);
	  	Assert.assertNotNull(page);
	 
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	// use past dates
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2011");
	    HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	    Assert.assertNotNull("Could not locate submit button", input);
	  	page = page.getElementByName("methodToCall.route").click();
	  	Assert.assertTrue("page text does not contain:\n" + TkTestConstants.EFFECTIVE_DATE_ERROR, page.asText().contains(TkTestConstants.EFFECTIVE_DATE_ERROR));
	  	Calendar futureDate = Calendar.getInstance();
	  	futureDate.add(java.util.Calendar.YEAR, 2);// 2 years in the future
	  	String futureDateString = "01/01/" + Integer.toString(futureDate.get(Calendar.YEAR));
	  	
	  	// use dates 2 years in the future
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", futureDateString);
	  	page = page.getElementByName("methodToCall.route").click();
	  	Assert.assertTrue("page text does not contain:\n" + TkTestConstants.EFFECTIVE_DATE_ERROR, page.asText().contains(TkTestConstants.EFFECTIVE_DATE_ERROR));
		Calendar validDate = Calendar.getInstance();
	  	validDate.add(java.util.Calendar.MONTH, 5); // 5 month in the future
	  	String validDateString = Integer.toString(validDate.get(Calendar.MONTH)) + '/' + Integer.toString(validDate.get(Calendar.DAY_OF_MONTH)) 
	  		+ '/' + Integer.toString(validDate.get(Calendar.YEAR));
	  	setFieldValue(page, "document.newMaintainableObject.effectiveDate", validDateString);
	  	page = page.getElementByName("methodToCall.route").click();
	  	Assert.assertFalse("page text contains:\n" + TkTestConstants.EFFECTIVE_DATE_ERROR, page.asText().contains(TkTestConstants.EFFECTIVE_DATE_ERROR));
	}


}
