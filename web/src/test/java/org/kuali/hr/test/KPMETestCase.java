/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestConstants;
import org.kuali.hr.time.util.ClearDatabaseLifecycle;
import org.kuali.hr.time.util.DatabaseCleanupDataLifecycle;
import org.kuali.hr.time.util.LoadDatabaseDataLifeCycle;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.lifecycle.BaseLifecycle;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.impl.services.CoreImplServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.service.KRADServiceLocatorInternal;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.MessageMap;
import org.kuali.rice.test.RiceInternalSuiteDataTestCase;
import org.kuali.rice.test.TransactionalLifecycle;
import org.kuali.rice.test.lifecycles.JettyServerLifecycle;
import org.kuali.rice.test.lifecycles.JettyServerLifecycle.ConfigMode;
import org.kuali.rice.test.lifecycles.KPMEXmlDataLoaderLifecycle;
import org.springframework.cache.CacheManager;
import org.springframework.mock.web.MockHttpServletRequest;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
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

/**
 *  Default test base for a full KPME unit test.
 */
public abstract class KPMETestCase extends RiceInternalSuiteDataTestCase {

	private static final String FILE_PREFIX = System.getProperty("user.dir") + "/../src/main/config/workflow/";

	private static final String RELATIVE_WEBAPP_ROOT = "/src/main/webapp";
	
	private TransactionalLifecycle transactionalLifecycle;
    private WebClient webClient;
	
	@Override
	protected String getModuleName() {
		return "kpme";
	}

	@Override
	public void setUp() throws Exception {
	    if (System.getProperty("basedir") == null) {
	        System.setProperty("basedir", System.getProperty("user.dir") + "/");
	    }
	    
		super.setUp();

		GlobalVariables.setMessageMap(new MessageMap());
		
		final boolean needsSpring = false;
		if (needsSpring) {
			transactionalLifecycle = new TransactionalLifecycle();
			transactionalLifecycle.setTransactionManager(KRADServiceLocatorInternal.getTransactionManager());
			transactionalLifecycle.start();
		}

	    new ClearDatabaseLifecycle().start();
	
		new LoadDatabaseDataLifeCycle(this.getClass()).start();
	
	    //lets try to create a user session
	    GlobalVariables.setUserSession(new UserSession("admin"));
        setWebClient(new WebClient(BrowserVersion.FIREFOX_17));
        getWebClient().getOptions().setJavaScriptEnabled(true);
        getWebClient().getOptions().setTimeout(0);
	}
	
	@Override
	public void tearDown() throws Exception {
	    // runs custom SQL at the end of each test.
	    // useful for difficult to reset test additions, not handled by
	    // our ClearDatabaseLifecycle.
        HrContext.clearTargetUser();
        getWebClient().closeAllWindows();
	    new DatabaseCleanupDataLifecycle(this.getClass()).start();
	    
		final boolean needsSpring = true;
		if (needsSpring) {
		    if ( (transactionalLifecycle != null) && (transactionalLifecycle.isStarted()) ) {
		        transactionalLifecycle.stop();
		    }
		}
		
		GlobalVariables.setMessageMap(new MessageMap());
		
		super.tearDown();
	}

    @Override
    protected List<Lifecycle> getPerTestLifecycles() {
        List<Lifecycle> lifecycles = super.getPerTestLifecycles();
        lifecycles.add(new ClearCacheLifecycle());
        return lifecycles;
    }

	@Override
	protected List<Lifecycle> getSuiteLifecycles() {
		List<Lifecycle> lifecycles = super.getPerTestLifecycles();
	    lifecycles.add(new Lifecycle() {
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
	    /**
	     * Loads the TestHarnessSpringBeans.xml file which obtains connections to the DB for us
	     */
	    /*lifecycles.add(getTestHarnessSpringResourceLoader());*/
	
	    /**
	     * Establishes the TestHarnessServiceLocator so that it has a reference to the Spring context
	     * created from TestHarnessSpringBeans.xml
	     */
	    /*lifecycles.add(new BaseLifecycle() {
	        @Override
	        public void start() throws Exception {
	            TestHarnessServiceLocator.setContext(getTestHarnessSpringResourceLoader().getContext());
	            super.start();
	        }
	    });*/
	
	    lifecycles.add(new Lifecycle() {
			private JettyServerLifecycle jettyServerLifecycle;
	
			public boolean isStarted() {
				return jettyServerLifecycle.isStarted();
			}
	
			public void start() throws Exception {
	            System.setProperty("web.bootstrap.spring.file", "classpath:TestHarnessSpringBeans.xml");
	            jettyServerLifecycle = new JettyServerLifecycle(HtmlUnitUtil.getPort(), HtmlUnitUtil.getContext(), RELATIVE_WEBAPP_ROOT);
	            jettyServerLifecycle.setConfigMode(ConfigMode.OVERRIDE);
				jettyServerLifecycle.start();
			}
	
			public void stop() throws Exception {
				this.jettyServerLifecycle.stop();
			}
		});
	
	    ClearDatabaseLifecycle clearDatabaseLifecycle = new ClearDatabaseLifecycle();
	    clearDatabaseLifecycle.getAlternativeTablesToClear().add("KREW_RULE_T");
	    clearDatabaseLifecycle.getAlternativeTablesToClear().add("KREW_RULE_RSP_T");
	    clearDatabaseLifecycle.getAlternativeTablesToClear().add("KREW_DLGN_RSP_T");
	    clearDatabaseLifecycle.getAlternativeTablesToClear().add("KREW_RULE_ATTR_T");
	    clearDatabaseLifecycle.getAlternativeTablesToClear().add("KREW_RULE_TMPL_T");
	    clearDatabaseLifecycle.getAlternativeTablesToClear().add("KREW_DOC_TYP_T");
	    lifecycles.add(clearDatabaseLifecycle);
	
	    File[] files = new File(FILE_PREFIX).listFiles();
	    if (files != null) {
	        for (File file : files) {
	            if (file.getName().endsWith(".xml")) {
	                lifecycles.add(new KPMEXmlDataLoaderLifecycle(FILE_PREFIX + file.getName()));
	            }
	        }
	    }
		return lifecycles;
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
	  	HtmlPage page = HtmlUnitUtil.gotoPageAndLogin(getWebClient(), baseUrl);
	  	Assert.assertNotNull(page);
	
	  	HtmlForm form = page.getFormByName("KualiForm");
	  	Assert.assertNotNull("Search form was missing from page.", form);
	  	// use past dates
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", "04/01/2011");
	    HtmlInput  input  = HtmlUnitUtil.getInputContainingText(form, "methodToCall.route");
	    Assert.assertNotNull("Could not locate submit button", input);
	  	page = ((HtmlButtonInput)page.getElementByName("methodToCall.route")).click();
	  	Assert.assertTrue("page text does not contain:\n" + TkTestConstants.EFFECTIVE_DATE_ERROR, page.asText().contains(TkTestConstants.EFFECTIVE_DATE_ERROR));
	  	LocalDate futureDate = LocalDate.now().plusYears(2); // 2 years in the future
	  	String futureDateString = "01/01/" + Integer.toString(futureDate.getYear());
	  	
	  	// use dates 2 years in the future
	    setFieldValue(page, "document.newMaintainableObject.effectiveDate", futureDateString);
	  	page = ((HtmlButtonInput)page.getElementByName("methodToCall.route")).click();
	  	Assert.assertTrue("page text does not contain:\n" + TkTestConstants.EFFECTIVE_DATE_ERROR, page.asText().contains(TkTestConstants.EFFECTIVE_DATE_ERROR));
	  	LocalDate validDate = LocalDate.now().plusMonths(5); // 5 month in the future
	  	String validDateString = Integer.toString(validDate.getMonthOfYear()) + '/' + Integer.toString(validDate.getDayOfMonth()) 
	  		+ '/' + Integer.toString(validDate.getYear());
	  	setFieldValue(page, "document.newMaintainableObject.effectiveDate", validDateString);
	  	page = ((HtmlElement)page.getElementByName("methodToCall.route")).click();
	  	Assert.assertFalse("page text contains:\n" + TkTestConstants.EFFECTIVE_DATE_ERROR, page.asText().contains(TkTestConstants.EFFECTIVE_DATE_ERROR));
	}

    public class ClearCacheLifecycle extends BaseLifecycle {
        private final Logger LOG = Logger.getLogger(ClearCacheLifecycle.class);

        @Override
        public void start() throws Exception {
            long startTime = System.currentTimeMillis();
            LOG.info("Starting cache flushing");
            List<CacheManager> cms = new ArrayList<CacheManager>(CoreImplServiceLocator.getCacheManagerRegistry().getCacheManagers());
            for (CacheManager cm : cms) {
                for (String cacheName : cm.getCacheNames()) {
                    //LOG.info("Clearing cache: " + cacheName);
                    cm.getCache(cacheName).clear();
                }
            }
            long endTime = System.currentTimeMillis();
            LOG.info("Caches cleared in " + (endTime - startTime) + "ms");
        }

        @Override
        public void stop() throws Exception {
            super.stop();
        }

    }

    public WebClient getWebClient() {
        return this.webClient;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}
