package org.kuali.hr.lm.leaverequest.dao;


import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.lm.workflow.LeaveRequestDocument;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class LeaveRequestDocumentDaoOjbImpl extends PlatformAwareDaoBaseOjb implements LeaveRequestDocumentDao{
    @Override
    public LeaveRequestDocument getLeaveRequestDocument(String documentNumber) {
        LeaveRequestDocument lrd = null;

        Criteria root = new Criteria();
        root.addEqualTo("documentNumber", documentNumber);
        Query query = QueryFactory.newQuery(LeaveRequestDocument.class, root);
        lrd = (LeaveRequestDocument)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return lrd;
    }

    @Override
    public LeaveRequestDocument getLeaveRequestDocumentByLeaveBlockId(String leaveBlockId) {
        LeaveRequestDocument lrd = null;

        Criteria root = new Criteria();
        root.addEqualTo("lmLeaveBlockId", leaveBlockId);
        Query query = QueryFactory.newQuery(LeaveRequestDocument.class, root);
        lrd = (LeaveRequestDocument)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return lrd;
    }
}
