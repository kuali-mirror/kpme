package org.kuali.hr.lm.workflow.dao;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.workflow.LeaveCalendarDocumentHeader;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class LeaveCalendarDocumentHeaderDaoImpl extends PersistenceBrokerDaoSupport implements LeaveCalendarDocumentHeaderDao {
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

}
