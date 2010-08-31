package org.kuali.hr.time.workflow.dao;

import java.util.Date;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.workflow.TkDocumentHeader;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class DocumentHeaderDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements DocumentHeaderDao {

	@Override
	public TkDocumentHeader getDocumentHeader(Long documentId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("documentId", documentId);
		return (TkDocumentHeader)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TkDocumentHeader.class, crit));
	}

	@Override
	public void saveOrUpdate(TkDocumentHeader documentHeader) {
		if (documentHeader != null) {
			this.getPersistenceBrokerTemplate().store(documentHeader);
		}
	}

	@Override
	public TkDocumentHeader getDocumentHeader(String principalId, Date payEndDate) {
		Criteria crit = new Criteria();
		crit.addEqualTo("principalId", principalId);
		crit.addEqualTo("payEndDate", payEndDate);
		
		return (TkDocumentHeader)this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(TkDocumentHeader.class, crit));
	}

}
