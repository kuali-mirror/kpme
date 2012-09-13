package org.kuali.hr.time.missedpunch.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.hr.time.calendar.CalendarEntries;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class MissedPunchDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements MissedPunchDao {

    @Override
    public MissedPunchDocument getMissedPunchByRouteHeader(String headerId) {
    	MissedPunchDocument mp = null;

        Criteria root = new Criteria();
        root.addEqualTo("documentNumber", headerId);
        Query query = QueryFactory.newQuery(MissedPunchDocument.class, root);
        mp = (MissedPunchDocument)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return mp;
    }
    
    @Override
    public MissedPunchDocument getMissedPunchByClockLogId(String clockLogId) {
    	MissedPunchDocument mp = null;

        Criteria root = new Criteria();
        root.addEqualTo("tkClockLogId", clockLogId);
        Query query = QueryFactory.newQuery(MissedPunchDocument.class, root);
        mp = (MissedPunchDocument)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return mp;
    }
    
    @Override
    public List<MissedPunchDocument> getMissedPunchDocsByBatchJobEntry(BatchJobEntry batchJobEntry) {
    	List<MissedPunchDocument> results = new ArrayList<MissedPunchDocument>();
		Criteria root = new Criteria();
		
		root.addEqualTo("principalId", batchJobEntry.getPrincipalId());
	    root.addEqualTo("documentStatus", TkConstants.ROUTE_STATUS.ENROUTE);
	    Query query = QueryFactory.newQuery(MissedPunchDocument.class, root);
	    List<MissedPunchDocument> aList = (List<MissedPunchDocument>) this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
	    
	    String pcdId = batchJobEntry.getHrPyCalendarEntryId();
	    CalendarEntries pcd = TkServiceLocator.getCalendarEntriesService().getCalendarEntries(pcdId.toString());
	    if(pcd != null) {
		    for(MissedPunchDocument aDoc : aList) {
		    	String tscId = aDoc.getTimesheetDocumentId();
		    	TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(tscId);
		    	if(tsdh != null) {
		    		if(tsdh.getBeginDate().equals(pcd.getBeginPeriodDate()) && tsdh.getEndDate().equals(pcd.getEndPeriodDate())) {
		    			results.add(aDoc);
		    		}
		    	}
		    }
	    }
	    
    	return results;
    }
    
}
