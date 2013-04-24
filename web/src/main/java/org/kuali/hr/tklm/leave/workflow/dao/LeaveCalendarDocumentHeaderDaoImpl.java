/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.tklm.leave.workflow.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.joda.time.DateTime;
import org.kuali.hr.tklm.leave.workflow.LeaveCalendarDocumentHeader;
import org.kuali.hr.tklm.time.util.TkConstants;
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
    public LeaveCalendarDocumentHeader getLeaveCalendarDocumentHeader(String principalId, DateTime beginDate, DateTime endDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        crit.addEqualTo("beginDate", beginDate.toDate());
        crit.addEqualTo("endDate", endDate.toDate());

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
    public LeaveCalendarDocumentHeader getPreviousDocumentHeader(String principalId, DateTime beginDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        // the pay begin date is the end date of the previous pay period
        crit.addEqualTo("endDate", beginDate.toDate());
        QueryByCriteria query = new QueryByCriteria(LeaveCalendarDocumentHeader.class, crit);
        query.addOrderByDescending("documentId");
        query.setStartAtIndex(0);
        query.setEndAtIndex(1);

        return (LeaveCalendarDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public LeaveCalendarDocumentHeader getNextDocumentHeader(String principalId, DateTime endDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        // the pay end date is the begin date of the next pay period
        crit.addEqualTo("beginDate", endDate.toDate());
        QueryByCriteria query = new QueryByCriteria(LeaveCalendarDocumentHeader.class, crit);
        query.setStartAtIndex(0);
        query.setEndAtIndex(1);

        return (LeaveCalendarDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    
    @Override
    public List<LeaveCalendarDocumentHeader> getDocumentHeaders(DateTime beginDate, DateTime endDate) {
        Criteria crit = new Criteria();
        List<LeaveCalendarDocumentHeader> lstDocumentHeaders = new ArrayList<LeaveCalendarDocumentHeader>();

        crit.addEqualTo("beginDate", beginDate.toDate());
        crit.addEqualTo("endDate", endDate.toDate());
        QueryByCriteria query = new QueryByCriteria(LeaveCalendarDocumentHeader.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
            lstDocumentHeaders.addAll(c);
        }
        
        return lstDocumentHeaders;
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
    public List<LeaveCalendarDocumentHeader> getSubmissionDelinquentDocumentHeaders(String principalId, DateTime beforeDate) {
    	Criteria crit = new Criteria();
        List<LeaveCalendarDocumentHeader> lstDocumentHeaders = new ArrayList<LeaveCalendarDocumentHeader>();

        crit.addEqualTo("principalId", principalId);
        crit.addLessThan("endDate", beforeDate.toDate());
        crit.addNotEqualTo("documentStatus", TkConstants.ROUTE_STATUS.INITIATED);
        crit.addNotEqualTo("documentStatus", TkConstants.ROUTE_STATUS.ENROUTE);
        crit.addNotEqualTo("documentStatus", TkConstants.ROUTE_STATUS.FINAL);
        QueryByCriteria query = new QueryByCriteria(LeaveCalendarDocumentHeader.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
            lstDocumentHeaders.addAll(c);
        }
        return lstDocumentHeaders;
    }
    
    @Override
    public List<LeaveCalendarDocumentHeader> getApprovalDelinquentDocumentHeaders(String principalId) {
    	Criteria crit = new Criteria();
        List<LeaveCalendarDocumentHeader> lstDocumentHeaders = new ArrayList<LeaveCalendarDocumentHeader>();

        crit.addEqualTo("principalId", principalId);
        crit.addNotEqualTo("documentStatus", TkConstants.ROUTE_STATUS.INITIATED);
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
    
    @Override
    public List<LeaveCalendarDocumentHeader> getAllDocumentHeadersInRangeForPricipalId(String principalId, DateTime startDate, DateTime endDate) {
    	Criteria root = new Criteria();
        List<LeaveCalendarDocumentHeader> lstDocumentHeaders = new ArrayList<LeaveCalendarDocumentHeader>();

        Criteria beginRoot = new Criteria();
        beginRoot.addEqualTo("principalId", principalId);
        beginRoot.addLessOrEqualThan("beginDate", startDate.toDate());
        beginRoot.addGreaterOrEqualThan("endDate", startDate.toDate());  
        
        Criteria endRoot = new Criteria();
        endRoot.addEqualTo("principalId", principalId);
        endRoot.addLessOrEqualThan("beginDate", endDate.toDate());
        endRoot.addGreaterOrEqualThan("endDate", endDate.toDate()); 
        
        root.addEqualTo("principalId", principalId);
        root.addGreaterOrEqualThan("beginDate", startDate.toDate());
        root.addLessOrEqualThan("beginDate", endDate.toDate());
        root.addGreaterOrEqualThan("endDate", startDate.toDate());
        root.addLessOrEqualThan("endDate", endDate.toDate());
        root.addOrCriteria(beginRoot);
        root.addOrCriteria(endRoot);
        
        QueryByCriteria query = new QueryByCriteria(LeaveCalendarDocumentHeader.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
            lstDocumentHeaders.addAll(c);
        }
        return lstDocumentHeaders;
    }
}
