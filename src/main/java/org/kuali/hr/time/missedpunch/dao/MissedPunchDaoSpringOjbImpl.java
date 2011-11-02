package org.kuali.hr.time.missedpunch.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.batch.BatchJobEntry;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.paycalendar.PayCalendarEntries;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class MissedPunchDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements MissedPunchDao {

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
    public MissedPunchDocument getMissedPunchByClockLogId(Long clockLogId) {
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
	    
	    Long pcdId = batchJobEntry.getPayCalendarEntryId();
	    PayCalendarEntries pcd = TkServiceLocator.getPayCalendarEntriesSerivce().getPayCalendarEntries(pcdId);
	    if(pcd != null) {
		    for(MissedPunchDocument aDoc : aList) {
		    	String tscId = aDoc.getTimesheetDocumentId();
		    	TimesheetDocumentHeader tsdh = TkServiceLocator.getTimesheetDocumentHeaderService().getDocumentHeader(tscId);
		    	if(tsdh != null) {
		    		if(tsdh.getPayBeginDate().equals(pcd.getBeginPeriodDate()) && tsdh.getPayEndDate().equals(pcd.getEndPeriodDate())) {
		    			results.add(aDoc);
		    		}
		    	}
		    }
	    }
	    
    	return results;
    }
    
}
