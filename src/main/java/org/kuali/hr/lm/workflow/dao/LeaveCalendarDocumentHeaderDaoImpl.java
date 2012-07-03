package org.kuali.hr.lm.workflow.dao;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
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

}
