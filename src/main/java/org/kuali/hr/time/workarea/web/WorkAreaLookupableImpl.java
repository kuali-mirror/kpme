package org.kuali.hr.time.workarea.web;

import java.util.Properties;

import org.kuali.rice.kns.lookup.KualiLookupableImpl;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.UrlFactory;

public class WorkAreaLookupableImpl extends KualiLookupableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getCreateNewUrl() {
        Properties parameters = new Properties();
        parameters.put(KNSConstants.DISPATCH_REQUEST_PARAMETER, "docHandler");
        String url = UrlFactory.parameterizeUrl("WorkAreaMaintenance.do", parameters);
		return "<a href=\"../" + url + "&command=initiate&newWorkArea=true" + "\"><img src=\"images/tinybutton-createnew.gif\" alt=\"create new\" width=\"70\" height=\"15\"/></a>";
	}

}
