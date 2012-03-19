package org.kuali.hr.time.paycalendar.service;

import org.kuali.hr.time.paycalendar.PayCalendar;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

import java.util.List;
import java.util.Map;

public class PayCalendarLookupableHelper extends
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
		PayCalendar payCalendar = (PayCalendar) businessObject;
		final String className = this.getBusinessObjectClass().getName();
		final String hrPyCalendarId = payCalendar.getHrPyCalendarId();
		HtmlData htmlData = new HtmlData() {

			@Override
			public String constructCompleteHtmlTag() {
				return "<a target=\"_blank\" href=\"inquiry.do?businessObjectClassName="
						+ className + "&methodToCall=start&hrPyCalendarId=" + hrPyCalendarId
						+ "&pyCalendarGroup=\">view</a>";
			}
		};
		customActionUrls.add(htmlData);
		return customActionUrls;
	}
	
	@Override
	public List<? extends BusinessObject> getSearchResults(Map<String, String> fieldValues) {

        String pyCalendarGroup = fieldValues.get("pyCalendarGroup");
        String flsaBeginDay = fieldValues.get("flsaBeginDay");
        String flsaBeginTime = fieldValues.get("flsaBeginTime");
        String active = fieldValues.get("active");

        List<PayCalendar> payCalendars = TkServiceLocator.getPayCalendarSerivce().getPayCalendars(pyCalendarGroup, flsaBeginDay, flsaBeginTime, active);

//		if(fieldValues.containsKey("flsaBeginTime")){
//			flsaTime = fieldValues.get("flsaBeginTime");
//			fieldValues.remove("flsaBeginTime");
//		}
//		List<? extends BusinessObject> objectList = super.getSearchResults(fieldValues);
//		if(!objectList.isEmpty()  && flsaTime != null && StringUtils.isNotBlank(flsaTime)){
//			SimpleDateFormat sdFormat = new SimpleDateFormat("hh:mm aa");
//			Time flsaBeginTime = null;
//			try {
//				flsaBeginTime = new Time(sdFormat.parse(flsaTime).getTime());
//				Iterator itr = objectList.iterator();
//				while(itr.hasNext()){
//					PayCalendar pc = (PayCalendar)itr.next();
//					if(pc.getFlsaBeginTime()!= null && !pc.getFlsaBeginTime().equals(flsaBeginTime)){
//						itr.remove();
//					}
//				}
//			} catch (ParseException e) {
//			}
//		}
		return payCalendars;
	}

}
