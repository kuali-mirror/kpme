package org.kuali.hr.time.workflow.dao;

import java.util.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class TimesheetDocumentHeaderDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements TimesheetDocumentHeaderDao {

	@Override
	public TimesheetDocumentHeader getTimesheetDocumentHeader(Long documentId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("documentId", documentId);
		return (TimesheetDocumentHeader)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TimesheetDocumentHeader.class, crit));
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
		
		return (TimesheetDocumentHeader)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TimesheetDocumentHeader.class, crit));
	}
	
	public TimesheetDocumentHeader getPreviousDocumentHeader(String principalId, Long currDocumentId){
		Criteria crit = new Criteria();
		crit.addEqualTo("principalId", principalId);
		crit.addNotEqualTo("documentId", currDocumentId);
		QueryByCriteria query = new QueryByCriteria(TimesheetDocumentHeader.class, crit);
		query.addOrderByDescending("documentId");
		query.setStartAtIndex(0);
		query.setEndAtIndex(1);
		TimesheetDocumentHeader prevDocumentHeader = (TimesheetDocumentHeader)this.getPersistenceBrokerTemplate().getObjectByQuery(query);
		return prevDocumentHeader;
	}

}
