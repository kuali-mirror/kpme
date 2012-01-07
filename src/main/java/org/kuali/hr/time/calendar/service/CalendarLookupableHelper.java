package org.kuali.hr.time.calendar.service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.calendar.Calendar;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class CalendarLookupableHelper extends
		KualiLookupableHelperServiceImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
			List pkNames) {
		List<HtmlData> customActionUrls = super.getCustomActionUrls(
				businessObject, pkNames);
		Calendar calendar = (Calendar) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final String hrCalendarId = calendar.getHrCalendarId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&hrCalendarId=" + hrCalendarId
						+ "&calendarName=\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
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
