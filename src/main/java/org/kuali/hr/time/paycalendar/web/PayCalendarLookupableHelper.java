package org.kuali.hr.time.paycalendar.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.springframework.web.util.UrlPathHelper;

public class PayCalendarLookupableHelper extends KualiLookupableHelperServiceImpl {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
	StringBuilder url = new StringBuilder(urlPathHelper.getOriginatingContextPath(this.getRequest()));
	List<HtmlData> list = new ArrayList<HtmlData>();
	
	LOG.info("dt:" + getRequest().getParameter("documentType"));
	AnchorHtmlData ahl = this.getUrlData(businessObject, "payCalendar", pkNames);
	Enumeration e = this.getRequest().getAttributeNames();
	while (e.hasMoreElements()) {
	    LOG.info(e.nextElement());
	}
	
	url.append("/PayCalendarMaintenance.do?methodToCall=docHandler&command=initiate");
        //url.append("&docTypeName=").append(this.getDocumentType());
        //url.append("&documentId=").append(pc.getDo);
	
	ahl.setHref(url.toString());


	list.add(ahl);

	return list;
    }

    protected HttpServletRequest getRequest() {
	return TKContext.getHttpServletRequest();
    }

}
