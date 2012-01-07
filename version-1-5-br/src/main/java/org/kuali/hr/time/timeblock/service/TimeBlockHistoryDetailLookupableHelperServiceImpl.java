package org.kuali.hr.time.timeblock.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.Interval;
import org.kuali.hr.time.timeblock.TimeBlockHistory;
import org.kuali.hr.time.timeblock.TimeBlockHistoryDetail;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.lookup.KualiLookupableHelperServiceImpl;

public class TimeBlockHistoryDetailLookupableHelperServiceImpl extends KualiLookupableHelperServiceImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final String DOC_ID = "documentId";
	static final String DOC_STATUS_ID = "timesheetDocumentHeader.documentStatus";
	static final String BEGIN_DATE_ID = "beginDate";

    @SuppressWarnings("unchecked")
	@Override
    public List<? extends BusinessObject> getSearchResults(java.util.Map<String, String> fieldValues) {

        String docStatus = "", beginDateString = "";

        if (fieldValues.containsKey(DOC_STATUS_ID)) {
            docStatus = fieldValues.get(DOC_STATUS_ID);
            fieldValues.remove(DOC_STATUS_ID);
        }
        if (fieldValues.containsKey(BEGIN_DATE_ID)) {
            beginDateString = fieldValues.get(BEGIN_DATE_ID);
            fieldValues.remove(BEGIN_DATE_ID);
        }
        List<TimeBlockHistoryDetail> objectList = (List<TimeBlockHistoryDetail>) super.getSearchResults(fieldValues);
        Map<String,List<TimeBlockHistoryDetail>> timeBlockHistoryToDetailMap = new HashMap<String,List<TimeBlockHistoryDetail>>();
        Map<String,List<TimeBlockHistoryDetail>> filteredTimeBlockHistoryToDetailMap = new HashMap<String,List<TimeBlockHistoryDetail>>();
        
        if (!objectList.isEmpty()) {
        	for(TimeBlockHistoryDetail tbhd : objectList){
        		if(!timeBlockHistoryToDetailMap.containsKey(tbhd.getTkTimeBlockHistoryId())){
        			List<TimeBlockHistoryDetail> thdList = new ArrayList<TimeBlockHistoryDetail>();
        			timeBlockHistoryToDetailMap.put(tbhd.getTkTimeBlockHistoryId(), thdList);
        		}
        		
        		List<TimeBlockHistoryDetail> thdList = timeBlockHistoryToDetailMap.get(tbhd.getTkTimeBlockHistoryId());
        		thdList.add(tbhd);
        	}
        	filteredTimeBlockHistoryToDetailMap.putAll(timeBlockHistoryToDetailMap);

        	Iterator<String> itr = timeBlockHistoryToDetailMap.keySet().iterator();
        	while(itr.hasNext()){
        		String timeHourDetailId = itr.next();
        		List<TimeBlockHistoryDetail> tbhdList = timeBlockHistoryToDetailMap.get(timeHourDetailId);
        		TimeBlockHistoryDetail tbhd = tbhdList.get(0);
        		TimeBlockHistory tbh = tbhd.getTimeBlockHistory();
        		
        		if(StringUtils.isNotEmpty(docStatus)){
        			if(tbh.getTimesheetDocumentHeader() == null){
        				filteredTimeBlockHistoryToDetailMap.remove(timeHourDetailId);
        				continue;
        			} else {
        				if (tbh.getTimesheetDocumentHeader().getDocumentStatus() != null) {
        					if (!tbh.getTimesheetDocumentHeader().getDocumentStatus().equals(docStatus)) {
        						filteredTimeBlockHistoryToDetailMap.remove(timeHourDetailId);
        						continue;
        					}
        				} else {
        					filteredTimeBlockHistoryToDetailMap.remove(timeHourDetailId);
        					continue;
        				}
        			}
        		}
        		
                if(StringUtils.isNotEmpty(beginDateString)) {
					if(tbh.getBeginDate() != null) {
						if(!this.inDateRange(tbh.getBeginDate(), beginDateString)) {
							filteredTimeBlockHistoryToDetailMap.remove(timeHourDetailId);
							continue;
						} 
					} else {
						filteredTimeBlockHistoryToDetailMap.remove(timeHourDetailId);
						continue;
					}
				}
        	}
        }
        
        List<TimeBlockHistoryDetail> lstFinalList = new ArrayList<TimeBlockHistoryDetail>();
        for(List<TimeBlockHistoryDetail> tbhdList : filteredTimeBlockHistoryToDetailMap.values()){
        	lstFinalList.addAll(tbhdList);
        }

        sortByTimeBlockId(lstFinalList);
        return lstFinalList;
    }

    private void sortByTimeBlockId(List<TimeBlockHistoryDetail> objectList) {
        Collections.sort(objectList, new Comparator<TimeBlockHistoryDetail>() { // Sort the Time Blocks
            @Override
            public int compare(TimeBlockHistoryDetail timeBlockHistory, TimeBlockHistoryDetail timeBlockHistory1) {
                return timeBlockHistory.getTimeBlockHistory().getTkTimeBlockId().compareTo(timeBlockHistory1.getTimeBlockHistory().getTkTimeBlockId());
            }
        });
    }
    
	 public boolean inDateRange(Date asOfDate, String dateString) {
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
					Interval range = new Interval(dateFrom.getTime(),dateTo.getTime());
					return range.contains(asOfDate.getTime());
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
}
