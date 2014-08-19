package org.kuali.kpme.edo.committeesready.dao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.committeesready.EdoCommitteesReadyBo;
import org.kuali.kpme.edo.submitButton.EdoSubmitButton;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class EdoCommitteesReadyDaoImpl extends PlatformAwareDaoBaseOjb implements EdoCommitteesReadyDao{
	
	 public boolean saveOrUpdate(EdoCommitteesReadyBo edoCommitteesReady) {
		 
		 this.getPersistenceBrokerTemplate().store(edoCommitteesReady);
		 
		 return true;
	 }
	 
	 public EdoCommitteesReadyBo getEdoCommitteesReady(String groupKeyCode) {
		  Criteria cConditions = new Criteria();

	        cConditions.addEqualTo("groupKeyCode", groupKeyCode);
	        
	        Query query = QueryFactory.newQuery(EdoCommitteesReadyBo.class, cConditions);
	        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

	        if (c != null && c.size() != 0) {
	           return (EdoCommitteesReadyBo)c.toArray()[0];
	        }
	        return null;
	    }
	 public List<EdoCommitteesReadyBo> getEdoCommitteesReadyList() {
		 List<EdoCommitteesReadyBo> edoCommitteesReadyList = new LinkedList<EdoCommitteesReadyBo>();
		 Criteria cConditions = new Criteria();

	        Query query = QueryFactory.newQuery(EdoCommitteesReadyBo.class, cConditions);
	        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

	        if (c != null && c.size() != 0) {
	        	edoCommitteesReadyList.addAll(c);
	        }
	        return edoCommitteesReadyList;

	 }
	
	
}
