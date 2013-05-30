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
package org.kuali.hr.time.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

public class HtmlUnitUtil {
    private static final Logger LOG = Logger.getLogger(HtmlUnitUtil.class);

    /**
     * 
     * @param url
     * @return htmlpage without js enabled
     * @throws Exception
     */
    public static HtmlPage gotoPageAndLogin(WebClient webClient, String url) throws Exception {
    	return gotoPageAndLogin(webClient, url, false);
    }
    
    /**
     * 
     * @param url
     * @param enableJavascript
     * @return htmlpage with js enabled
     * @throws Exception
     */
    public static HtmlPage gotoPageAndLogin(WebClient webClient, String url, boolean enableJavascript) throws Exception {
    	LOG.debug("URL: " + url);

    	// this is required and needs to set to true, otherwise the values set by the onClick event won't be triggered, e.g. methodToCall
        webClient.getOptions().setJavaScriptEnabled(enableJavascript);
    	return (HtmlPage) webClient.getPage(new URL(url));
    }

    public static boolean pageContainsText(HtmlPage page, String text) {
	return page.asText().indexOf(text) >= 0;
    }

	/**
	 *
	 * @param page: current html page
	 * @param criteria: The key is the field name and the value is a string array which contains the field value and the field type which can be chosen from TkTestConstants
	 * @return HtmlPage resultPage
	 * @throws Exception
	 */
	public static HtmlPage fillOutForm(HtmlPage page, Map<String, Object> criteria) throws Exception {
		HtmlForm lookupForm = HtmlUnitUtil.getDefaultForm(page);
		String formFieldPrefix = "";
		HtmlPage resultPage = null;
		HtmlSelect select = null;
		HtmlInput input = null;
		HtmlCheckBoxInput checkBox = null;
		HtmlTextArea textArea = null;


		// Common class of both HtmlInput and HtmlTextArea -- since either of these can appear
		// on a web-form.
		HtmlElement htmlBasicInput = null;

		Set<Map.Entry<String, Object>> entries = criteria.entrySet();
		Iterator<Map.Entry<String, Object>> it = entries.iterator();

		while (it.hasNext()) {
			Map.Entry<String,Object> entry = it.next();

			// if the field type is not <input>
			if(entry.getValue() instanceof String[]) {
				String key = Arrays.asList((String[])entry.getValue()).get(0).toString();
				String value = Arrays.asList((String[])entry.getValue()).get(1).toString();

				// drop-down
				if(key.equals(TkTestConstants.FormElementTypes.DROPDOWN)) {

					try {
						select = (HtmlSelect) lookupForm.getSelectByName(formFieldPrefix  + entry.getKey());
					} catch (Exception e) {
						select = (HtmlSelect) lookupForm.getElementById(formFieldPrefix  + entry.getKey());
					}
                   //  try to get a useful option, other than the typical blank default, by getting the last option
                   //  if the size of the options list is zero, then there is a problem. might as well error here with an array out of bounds.
                   resultPage = (HtmlPage) select.getOption(select.getOptionSize()-1).setSelected(true);
                   HtmlUnitUtil.createTempFile(resultPage);
               }
				// check box
				else if(key.equals(TkTestConstants.FormElementTypes.CHECKBOX)) {
					try {
					  checkBox = page.getHtmlElementById(formFieldPrefix  + entry.getKey());
					}
					catch(Exception e) {
						checkBox = page.getElementByName(formFieldPrefix  + entry.getKey());
					}
					resultPage = (HtmlPage) checkBox.setChecked(Boolean.parseBoolean(value));
				}
				// text area
				else if(key.equals(TkTestConstants.FormElementTypes.TEXTAREA)) {
					try {
					   textArea = page.getHtmlElementById(formFieldPrefix  + entry.getKey());
					} catch (Exception e){
						textArea = page.getElementByName(formFieldPrefix  + entry.getKey());
					}
					textArea.setText(Arrays.asList((String[])entry.getValue()).get(1).toString());
				}
			} else {
				try {
					htmlBasicInput = page.getHtmlElementById(formFieldPrefix + entry.getKey());
					if (htmlBasicInput instanceof HtmlTextArea) {
						textArea = (HtmlTextArea)htmlBasicInput;
						textArea.setText(entry.getValue().toString());
						resultPage = (HtmlPage) textArea.getPage();
					} else if (htmlBasicInput instanceof HtmlInput) {
						input = (HtmlInput)htmlBasicInput;
						resultPage = (HtmlPage) input.setValueAttribute(entry.getValue().toString());
					} else {
						LOG.error("Request to populate a non-input html form field.");
					}
				} catch (Exception e) {
					htmlBasicInput = page.getElementByName(formFieldPrefix + entry.getKey());

					if (htmlBasicInput instanceof HtmlTextArea) {
						textArea = (HtmlTextArea)htmlBasicInput;
						textArea.setText(entry.getValue().toString());
						resultPage = (HtmlPage) textArea.getPage();
					} else if (htmlBasicInput instanceof HtmlInput) {
						input = (HtmlInput)htmlBasicInput;
						resultPage = (HtmlPage) input.setValueAttribute(entry.getValue().toString());
					} else {
						LOG.error("Request to populate a non-input html form field.");
					}
				}
			}
		}
		HtmlUnitUtil.createTempFile(resultPage);
		return resultPage;
	}
	
   /**
    * Method to obtain the HREF onclick='' value from the button when
    * the client side typically processes the request.
    * @param button
    */
   public static String getOnClickHref(HtmlElement button) {
       NamedNodeMap attributes = button.getAttributes();
       Node node = attributes.getNamedItem("onclick");

       //location.href='TimesheetSubmit.do?action=R&documentId=2000&methodToCall=approveTimesheet'
       String hrefRaw = node.getNodeValue();
       int sidx = hrefRaw.indexOf("='");

       return hrefRaw.substring(sidx+2, hrefRaw.length() - 1);
   }
	/**
	 *
	 * @param page: current html page //NOTE doesnt seem to work currently for js setting of form variables
	 * @param name: the button name
	 * @return
	 * @throws Exception
	 */
	public static HtmlPage clickButton(HtmlPage page, String name) throws Exception {
		HtmlForm form = HtmlUnitUtil.getDefaultForm(page);
		HtmlSubmitInput input = form.getInputByName(name);
		return (HtmlPage) input.click();
	}
	
	public static HtmlPage clickClockInOrOutButton(HtmlPage page) throws Exception {
		HtmlForm form = HtmlUnitUtil.getDefaultForm(page);
		
		HtmlSubmitInput input = form.getInputByName("clockAction");
		form.getInputByName("methodToCall").setValueAttribute("clockAction");
		return (HtmlPage) input.click();
	}
	
	public static HtmlPage clickLunchInOrOutButton(HtmlPage page, String lunchAction) throws Exception {
		HtmlForm form = HtmlUnitUtil.getDefaultForm(page);
		
		HtmlSubmitInput input = form.getInputByName("clockAction");
		form.getInputByName("methodToCall").setValueAttribute("clockAction");
		form.getInputByName("currentClockAction").setValueAttribute(lunchAction);
		return (HtmlPage) input.click();
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
