package org.kuali.hr.time.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.kuali.rice.core.config.ConfigContext;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitUtil {

    private static final Logger LOG = Logger.getLogger(HtmlUnitUtil.class);

    public static HtmlPage gotoPageAndLogin(String url) throws Exception {
	LOG.debug("URL: " + url);
	final WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_6_0);
	webClient.setJavaScriptEnabled(false);

	HtmlPage loginPage = (HtmlPage) webClient.getPage(new URL(url));

	return loginPage;
    }

    public static boolean pageContainsText(HtmlPage page, String text) {
	return page.asText().indexOf(text) >= 0;
    }

    public static String getBaseURL() {
	return "http://localhost:" + getPort() + "/tk-dev";
    }

    public static String getTempDir() {
	return ConfigContext.getCurrentContextConfig().getProperty("temp.dir");
    }

    public static Integer getPort() {
	return new Integer(ConfigContext.getCurrentContextConfig().getProperty("kns.test.port"));
    }

    public static void createTempFile(HtmlPage page) throws Exception {
	createTempFile(page, null);
    }

    public static void createTempFile(HtmlPage page, String name) throws Exception {
	name = name == null ? "TestOutput" : name;
	File temp = File.createTempFile(name, ".html", new File(HtmlUnitUtil.getTempDir()));
	FileOutputStream fos = new FileOutputStream(temp);
	String xml = page.asXml();
	StringReader xmlReader = new StringReader(xml);
	int i;
	while ((i = xmlReader.read()) != -1) {
	    fos.write(i);
	}
	try {
	    fos.close();
	} catch (Exception e) {
	}
	try {
	    xmlReader.close();
	} catch (Exception e) {
	}
    }

    @SuppressWarnings("unchecked")
    public static HtmlInput getInputContainingText(HtmlForm form, String text) throws Exception {
	for (Iterator iterator = form.getAllHtmlChildElements(); iterator.hasNext();) {
	    HtmlElement element = (HtmlElement) iterator.next();
	    if (element instanceof HtmlInput) {
		HtmlInput i = (HtmlInput) element;
		if (element.toString().contains(text)) {
		    return i;
		}
	    }

	}
	return null;
    }

}
