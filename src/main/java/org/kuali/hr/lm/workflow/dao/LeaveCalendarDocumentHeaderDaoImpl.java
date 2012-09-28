/**
 * Copyright 2004-2012 The Kuali Foundation
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
package org.kuali.hr.lm.workflow.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeaveCalendarDocumentHeaderDaoImpl extends PlatformAwareDaoBaseOjb implements LeaveCalendarDocumentHeaderDao {
    private static final Logger LOG = Logger.getLogger(LeaveCalendarDocumentHeaderDaoImpl.class);

    @Override
    public LeaveCalendarDocumentHeader getLeaveCalendarDocumentHeader(String documentId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("documentId", documentId);
        return (LeaveCalendarDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(LeaveCalendarDocumentHeader.class, crit));
    }

    @Override
    public LeaveCalendarDocumentHeader getLeaveCalendarDocumentHeader(String principalId, Date beginDate, Date endDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        crit.addEqualTo("beginDate", beginDate);
        crit.addEqualTo("endDate", endDate);

        return (LeaveCalendarDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(LeaveCalendarDocumentHeader.class, crit));
    }

    @Override
    public void saveOrUpdate(LeaveCalendarDocumentHeader leaveCalendarDocumentHeader) {
        this.getPersistenceBrokerTemplate().store(leaveCalendarDocumentHeader);
    }
    
    /**
     * Document header IDs are ordered, so an ID less than the current will
     * always be previous to current.
     */
    public LeaveCalendarDocumentHeader getPreviousDocumentHeader(String principalId, Date beginDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        // the pay begin date is the end date of the previous pay period
        crit.addEqualTo("endDate", beginDate);
        QueryByCriteria query = new QueryByCriteria(LeaveCalendarDocumentHeader.class, crit);
        query.addOrderByDescending("documentId");
        query.setStartAtIndex(0);
        query.setEndAtIndex(1);

        return (LeaveCalendarDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public LeaveCalendarDocumentHeader getNextDocumentHeader(String principalId, Date endDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        // the pay end date is the begin date of the next pay period
        crit.addEqualTo("beginDate", endDate);
        QueryByCriteria query = new QueryByCriteria(LeaveCalendarDocumentHeader.class, crit);
        query.setStartAtIndex(0);
        query.setEndAtIndex(1);

        return (LeaveCalendarDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    
    @Override
    public LeaveCalendarDocumentHeader getMaxEndDateApprovedLeaveCalendar(String principalId) {
    	Criteria root = new Criteria();
        Criteria crit = new Criteria();
        
        crit.addEqualTo("principalId", principalId);
        crit.addEqualTo("documentStatus", TkConstants.ROUTE_STATUS.FINAL);
        ReportQueryByCriteria endDateSubQuery = QueryFactory.newReportQuery(LeaveCalendarDocumentHeader.class, crit);
        endDateSubQuery.setAttributes(new String[]{"max(endDate)"});

        root.addEqualTo("principalId", principalId);
        root.addEqualTo("documentStatus", TkConstants.ROUTE_STATUS.FINAL);
        root.addEqualTo("endDate", endDateSubQuery);

        Query query = QueryFactory.newQuery(LeaveCalendarDocumentHeader.class, root);
        return (LeaveCalendarDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    
    @Override
	public LeaveCalendarDocumentHeader getMinBeginDatePendingLeaveCalendar(String principalId) {
    	Criteria root = new Criteria();
        Criteria crit = new Criteria();
        List<String> pendingStatuses = new ArrayList<String>();
        pendingStatuses.add(TkConstants.ROUTE_STATUS.ENROUTE);
        pendingStatuses.add(TkConstants.ROUTE_STATUS.INITIATED);
        pendingStatuses.add(TkConstants.ROUTE_STATUS.SAVED);        
        
        crit.addEqualTo("principalId", principalId);
        crit.addIn("documentStatus", pendingStatuses);
        ReportQueryByCriteria startDateSubQuery = QueryFactory.newReportQuery(LeaveCalendarDocumentHeader.class, crit);
        startDateSubQuery.setAttributes(new String[]{"min(beginDate)"});

        root.addEqualTo("principalId", principalId);
        root.addIn("documentStatus", pendingStatuses);
        root.addEqualTo("beginDate", startDateSubQuery);

        Query query = QueryFactory.newQuery(LeaveCalendarDocumentHeader.class, root);
        return (LeaveCalendarDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
    @Override
    public List<LeaveCalendarDocumentHeader> getAllDocumentHeadersForPricipalId(String principalId) {
   	 	Criteria crit = new Criteria();
        List<LeaveCalendarDocumentHeader> lstDocumentHeaders = new ArrayList<LeaveCalendarDocumentHeader>();

        crit.addEqualTo("principalId", principalId);
        QueryByCriteria query = new QueryByCriteria(LeaveCalendarDocumentHeader.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
            lstDocumentHeaders.addAll(c);
        }
        return lstDocumentHeaders;
    }
    @Override
    public List<LeaveCalendarDocumentHeader> getAllDelinquentDocumentHeadersForPricipalId(String principalId) {
    	Criteria crit = new Criteria();
        List<LeaveCalendarDocumentHeader> lstDocumentHeaders = new ArrayList<LeaveCalendarDocumentHeader>();

        crit.addEqualTo("principalId", principalId);
        crit.addNotEqualTo("documentStatus", TkConstants.ROUTE_STATUS.FINAL);
        QueryByCriteria query = new QueryByCriteria(LeaveCalendarDocumentHeader.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
            lstDocumentHeaders.addAll(c);
        }
        return lstDocumentHeaders;
    }

    public void deleteLeaveCalendarHeader(String documentId){
        Criteria crit = new Criteria();
        crit.addEqualTo("documentId", documentId);
        this.getPersistenceBrokerTemplate().deleteByQuery(QueryFactory.newQuery(LeaveCalendarDocumentHeader.class, crit));
    }
}
