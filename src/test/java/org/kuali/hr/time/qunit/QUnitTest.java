package org.kuali.hr.time.qunit;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.hr.time.test.HtmlUnitUtil;
import org.kuali.hr.time.test.TkTestCase;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

@Ignore
public class QUnitTest extends TkTestCase {

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
		WebClient client = new WebClient(BrowserVersion.FIREFOX_3);
		client.setJavaScriptEnabled(true);
		client.setThrowExceptionOnScriptError(false);
		client.setAjaxController(new NicelyResynchronizingAjaxController());
		client.waitForBackgroundJavaScript(1000);

		// pass the login filter
		// need to change to the testing port 8090
		HtmlPage page = client.getPage(new URL(HtmlUnitUtil.getBaseURL() + "/TimeDetail.do"));
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
