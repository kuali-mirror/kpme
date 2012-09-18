package org.kuali.hr.time.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.web.TkLoginFilter;
import org.kuali.rice.core.api.config.property.ConfigContext;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.kuali.rice.krad.util.GlobalVariables;

public class HtmlUnitUtil {

    private static final Logger LOG = Logger.getLogger(HtmlUnitUtil.class);

    /**
     * 
     * @param url
     * @return htmlpage without js enabled
     * @throws Exception
     */
    public static HtmlPage gotoPageAndLogin(String url) throws Exception {
    	return gotoPageAndLogin(url, false);
    }
    
    /**
     * 
     * @param url
     * @param enableJavascript
     * @return htmlpage with js enabled
     * @throws Exception
     */
    public static HtmlPage gotoPageAndLogin(String url, boolean enableJavascript) throws Exception {
    	LOG.debug("URL: " + url);
    	final WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_7);
    	// this is required and needs to set to true, otherwise the values set by the onClick event won't be triggered, e.g. methodToCall
    	webClient.setJavaScriptEnabled(enableJavascript);
    	webClient.setTimeout(0);
    	return (HtmlPage) webClient.getPage(new URL(url));
    }

    public static boolean pageContainsText(HtmlPage page, String text) {
	return page.asText().indexOf(text) >= 0;
    }

	public static HtmlPage clickInputContainingText(HtmlPage page, String...values) throws Exception {
		page = (HtmlPage)getInputContainingText(page, values).click();
		return page;
	}

    public static HtmlInput getInputContainingText(HtmlPage page, String... values) throws Exception {
		List<HtmlForm> forms = page.getForms();
		for (HtmlForm form : forms){
			for(HtmlElement element : form.getHtmlElementDescendants()) {
				if (element instanceof HtmlInput) {
					if (elementContainsValues(element, values)) {
						return (HtmlInput)element;
					}
				}
			}
		}
		return null;
	}


    public static List<HtmlInput> getInputsContainingText(HtmlPage page, String... values) throws Exception {
		List<HtmlInput> inputs = new ArrayList<HtmlInput>();
		List<HtmlForm> forms = page.getForms();
		for (HtmlForm form : forms){

			for(HtmlElement element : form.getHtmlElementDescendants()) {
				if (element instanceof HtmlInput) {
					if (elementContainsValues(element, values)) {
						inputs.add((HtmlInput)element);
					}
				}
			}
		}
		return inputs;
	}

	protected static boolean elementContainsValues(HtmlElement element, String... values) {
		for (String value : values) {
			if (element.toString().indexOf(value) == -1) {
				return false;
			}
        }
		return true;
	}

	public static HtmlPage clickAnchorContainingText(HtmlPage page, String... values) throws Exception {
		return (HtmlPage) getAnchorContainingText(page, values).click();
	}

	@SuppressWarnings("unchecked")
	public static HtmlAnchor getAnchorContainingText(HtmlPage page, String... values) throws Exception {
		for (Iterator iterator = page.getAnchors().iterator(); iterator.hasNext();) {
			HtmlAnchor anchor = (HtmlAnchor) iterator.next();
			if (elementContainsValues(anchor, values)) {
				return anchor;
			}
		}
		return null;
	}

    public static String getBaseURL() {
	    return ConfigContext.getCurrentContextConfig().getProperty("application.url");
    }
    
    public static String getContext() {
    	return "/" + ConfigContext.getCurrentContextConfig().getProperty("app.context.name");
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

    public static HtmlInput getInputContainingText(HtmlForm form, String text) throws Exception {

		for (HtmlElement element : form.getHtmlElementDescendants()) {
			if (element instanceof HtmlInput) {
				HtmlInput i = (HtmlInput) element;
				if (element.toString().contains(text)) {
					return i;
				}
			}

		}
		return null;
    }
    
	public static HtmlForm getDefaultForm(HtmlPage htmlPage) {
		if (htmlPage.getForms().size() == 1) {
			return (HtmlForm)htmlPage.getForms().get(0);
		}
		return (HtmlForm)htmlPage.getForms().get(1);
	}

}
