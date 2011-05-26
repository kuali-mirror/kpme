package org.kuali.hr.time.workflow.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import java.util.*;

public class TimesheetDocumentHeaderDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements TimesheetDocumentHeaderDao {

    @Override
    public TimesheetDocumentHeader getTimesheetDocumentHeader(String documentId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("documentId", documentId);
        return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TimesheetDocumentHeader.class, crit));
    }

    @Override
    public void saveOrUpdate(TimesheetDocumentHeader documentHeader) {
        if (documentHeader != null) {
            this.getPersistenceBrokerTemplate().store(documentHeader);
        }
    }

    @Override
    public TimesheetDocumentHeader getTimesheetDocumentHeader(String principalId, Date payBeginDate, Date payEndDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        crit.addEqualTo("payEndDate", payEndDate);
        crit.addEqualTo("payBeginDate", payBeginDate);

        return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TimesheetDocumentHeader.class, crit));
    }

    /**
     * Document header IDs are ordered, so an ID less than the current will
     * always be previous to current.
     */
    public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, Date payBeginDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        // the pay begin date is the end date of the previous pay period
        crit.addEqualTo("payEndDate", payBeginDate);
        QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
        query.addOrderByDescending("documentId");
        query.setStartAtIndex(0);
        query.setEndAtIndex(1);

        return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public TimesheetDocumentHeader getNextDocumentHeader(String principalId, Date payEndDate) {
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId", principalId);
        // the pay end date is the begin date of the next pay period
        crit.addEqualTo("payBeginDate", payEndDate);
        QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
        query.setStartAtIndex(0);
        query.setEndAtIndex(1);

        return (TimesheetDocumentHeader) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    public List<TimesheetDocumentHeader> getDocumentHeaders(Date payBeginDate) {
        Criteria crit = new Criteria();
        List<TimesheetDocumentHeader> lstDocumentHeaders = new ArrayList<TimesheetDocumentHeader>();

        crit.addEqualTo("payBeginDate", payBeginDate);
        QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        if (c != null) {
            lstDocumentHeaders.addAll(c);
        }
        return lstDocumentHeaders;

    }

    @Override
    public List<TimesheetDocumentHeader> getDocumentHeaders(Criteria crit, int start, int end) {
        QueryByCriteria q = QueryFactory.newQuery(TimesheetDocumentHeader.class, crit);
        q.addOrderByAscending("documentId");
        q.setStartAtIndex(start);
        q.setEndAtIndex(end);

        List<TimesheetDocumentHeader> lstDocumentHeaders = new ArrayList<TimesheetDocumentHeader>();
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(q);
        if (c != null) {
            lstDocumentHeaders.addAll(c);
        }
        return lstDocumentHeaders;
    }

    @Override
    public List<String> getDataByField(Criteria crit, String[] fields) {
        ReportQueryByCriteria q = QueryFactory.newReportQuery(TimesheetDocumentHeader.class, crit, true);
        q.setAttributes(fields);
        q.setStartAtIndex(0);
        q.setEndAtIndex(4);

        List<String> results = new ArrayList<String>();
        Iterator iterator = this.getPersistenceBrokerTemplate().getReportQueryIteratorByQuery(q);

        while (iterator.hasNext()) {
            Object[] objects = (Object[])iterator.next();
            results.add(objects[0].toString());
        }
        return results;
    }


}
