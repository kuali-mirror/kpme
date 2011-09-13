package org.kuali.hr.time.timeblock.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.timeblock.TimeBlock;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.HtmlData;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class TimeBlockHistoryLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final String DOC_ID = "documentId";
	static final String DOC_STATUS_ID = "timesheetDocumentHeader.documentStatus";
	static final String BEGIN_DATE_ID = "timesheetDocumentHeader.payBeginDate";
	static final String END_DATE_ID = "timesheetDocumentHeader.payEndDate";
		
	 @Override
    public List<? extends BusinessObject> getSearchResults(java.util.Map<String, String> fieldValues) {
	 
		 String docStatus = "", beginDateString="", endDateString="";

		 if(fieldValues.containsKey(DOC_STATUS_ID)){
				docStatus = fieldValues.get(DOC_STATUS_ID);
				fieldValues.remove(DOC_STATUS_ID);
			}
		 if(fieldValues.containsKey(BEGIN_DATE_ID)){
			 	beginDateString = fieldValues.get(BEGIN_DATE_ID);
				fieldValues.remove(BEGIN_DATE_ID);
			}
		 if(fieldValues.containsKey(END_DATE_ID)){
			 	endDateString = fieldValues.get(END_DATE_ID);
				fieldValues.remove(END_DATE_ID);
			}
        
        List<? extends BusinessObject> objectList = super.getSearchResults(fieldValues);
      
        if(!objectList.isEmpty()) {
        	Iterator itr = objectList.iterator();
			while(itr.hasNext()){
				TimeBlockHistory tb = (TimeBlockHistory)itr.next();
				
				if(StringUtils.isNotEmpty(docStatus)) {
					if(tb.getTimesheetDocumentHeader() == null) {
						itr.remove();
						continue;
					} else {
						if(tb.getTimesheetDocumentHeader().getDocumentStatus() != null) {
							if(!tb.getTimesheetDocumentHeader().getDocumentStatus().equals(docStatus)){
								itr.remove();
								continue;
							}
						} else {
							itr.remove();
							continue;
						}
					}
				}
					
				if(StringUtils.isNotEmpty(beginDateString)) {
					if(!this.checkDate(tb, tb.getTimesheetDocumentHeader().getPayBeginDate(), beginDateString)) {
						itr.remove();
						continue;
					}
				}
					
				if(StringUtils.isNotEmpty(endDateString)) {
					if(!this.checkDate(tb,tb.getTimesheetDocumentHeader().getPayEndDate(), endDateString)) {
						itr.remove();
						continue;
					}
				}
				
			}
        }
        return objectList;
	 }
	 
	 private boolean checkDate(TimeBlockHistory tb, Date asOfDate, String dateString) {
		 if(tb.getTimesheetDocumentHeader() == null) {
				return false;
		 }
		try {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date dateFrom;
			Date dateTo;
			String subDateString;
			if(dateString.indexOf("..") == 10) {
				subDateString= dateString.substring(0, 10);
				dateFrom = df.parse(subDateString);
				subDateString= dateString.substring(12, dateString.length());
				dateTo = df.parse(subDateString);
				if(asOfDate != null) {
					if(!( (asOfDate.after(dateFrom) || asOfDate.equals(dateFrom))
							&& (asOfDate.before(dateTo) || asOfDate.equals(dateTo)))) {
						return false;
					}
				} else {
					return false;
				}
			} else{
				subDateString= dateString.substring(2, dateString.length());
				dateTo = df.parse(subDateString);
				if(asOfDate != null) {
					if( (dateString.startsWith(">=") && asOfDate.before(dateTo))
							|| (dateString.startsWith("<=") && asOfDate.after(dateTo))) {
						return false;
					}
				} else {
					return false;
				}
			}
		} catch (ParseException e) {
		}
	  return true;
	 }
		@Override
		public List<HtmlData> getCustomActionUrls(BusinessObject businessObject,
				@SuppressWarnings("rawtypes") List pkNames) {
			List<HtmlData> customActionUrls = super.getCustomActionUrls(businessObject, pkNames);
			List<HtmlData> overrideUrls = new ArrayList<HtmlData>();
			for(HtmlData actionUrl : customActionUrls){
				if(!StringUtils.equals(actionUrl.getMethodToCall(), "copy")){
					overrideUrls.add(actionUrl);
				}
			}
			return overrideUrls;
		}
}
