package org.kuali.hr.time.calendar.service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.HtmlData.AnchorHtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;
import org.kuali.rice.krad.bo.BusinessObject;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;

public class CalendarLookupableHelper extends KualiLookupableHelperServiceImpl {

	private static final long serialVersionUID = 2324703412211619217L;

	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject, List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
		
		Calendar calendar = (Calendar) businessObject;
		String hrCalendarId = calendar.getHrCalendarId();
		
		Properties params = new Properties();
		params.put(KRADConstants.BUSINESS_OBJECT_CLASS_ATTRIBUTE, getBusinessObjectClass().getName());
		params.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.MAINTENANCE_NEW_METHOD_TO_CALL);
		params.put("hrCalendarId", hrCalendarId);
		AnchorHtmlData viewUrl = new AnchorHtmlData(UrlFactory.parameterizeUrl(KRADConstants.INQUIRY_ACTION, params), "view");
		viewUrl.setDisplayText("view");
		viewUrl.setTarget(AnchorHtmlData.TARGET_BLANK);
		customActionUrls.add(viewUrl);
		
		return customActionUrls;
	}
	
	@Override
	public List<? extends BusinessObject> getSearchResults(
			Map<String, String> fieldValues) {
		String flsaTime = null;
		if(fieldValues.containsKey("flsaBeginTime")){
			flsaTime = fieldValues.get("flsaBeginTime");
			fieldValues.remove("flsaBeginTime");
		}
		List<? extends BusinessObject> objectList = super.getSearchResults(fieldValues);
		if(!objectList.isEmpty()  && flsaTime != null && StringUtils.isNotBlank(flsaTime)){
			SimpleDateFormat sdFormat = new SimpleDateFormat("hh:mm aa");
			Time flsaBeginTime = null;
			try {
				flsaBeginTime = new Time(sdFormat.parse(flsaTime).getTime());
				Iterator itr = objectList.iterator();
				while(itr.hasNext()){
					Calendar pc = (Calendar)itr.next();
					if(pc.getFlsaBeginTime()!= null && !pc.getFlsaBeginTime().equals(flsaBeginTime)){
						itr.remove();
					}
				}
			} catch (ParseException e) {
			}	
		}
		return objectList;
	}

}
