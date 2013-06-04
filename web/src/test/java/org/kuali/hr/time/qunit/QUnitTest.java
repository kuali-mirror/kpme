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
package org.kuali.hr.time.qunit;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.util.HtmlUnitUtil;
import org.kuali.kpme.core.KPMETestCase;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

@Ignore
public class QUnitTest extends KPMETestCase {

	private static final Logger LOG = Logger.getLogger(QUnitTest.class);
	private List<String> failures;

	@Test
	public void testQUnit() throws Exception {
		failures = new ArrayList<String>();

		File qunitDir = new File("src/main/webapp/qunit");
		if (!qunitDir.exists() || !qunitDir.isDirectory()) {
			Assert.fail("Test dir does not exist or is not a directory: " + qunitDir);
		}

		for (File file : qunitDir.listFiles()) {
			if (file.getName().endsWith(".jsp")) {
				runTest(file);
			}
		}

		if (failures.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (String failMsg : failures) {
				if (sb.length() > 0)
					sb.append("\n");
				sb.append(failMsg);
			}
			LOG.debug("\n\n" + sb.toString() + "\n\n");

			Assert.fail("Tests failed: " + sb.toString());
		}
	}

	private void runTest(File file) throws Exception {
		setWebClient(new WebClient(BrowserVersion.FIREFOX_10));
        WebClient client = getWebClient();
		client.setJavaScriptEnabled(true);
		client.setThrowExceptionOnScriptError(false);
		client.setAjaxController(new NicelyResynchronizingAjaxController());
		client.waitForBackgroundJavaScript(1000);

		// pass the login filter
		// need to change to the testing port 8090
		HtmlPage page = client.getPage(new URL(getBaseURL() + "/TimeDetail.do"));
		HtmlForm form = page.getFormByName("login-form");
		HtmlSubmitInput button = form.getInputByName("login");
		page = button.click();

		// run the unit test
		// need to change to the testing port 8090
		page = client.getPage(new URL("http://localhost:8080/tk-dev/qunit/" + file.getName()));
        synchronized (page) {
            page.wait(5000);
        }
        HtmlUnitUtil.createTempFile(page);
		HtmlElement element = page.getHtmlElementById("qunit-tests");
		if (element.asText().indexOf("0 tests of 0") != -1)
			failures.add(file.getName() + " - No tests were run - " + element.asText());
		else if (element.asText().indexOf("0 failed") == -1)
			failures.add(file.getName() + " - " + element.asText());
	}

}
