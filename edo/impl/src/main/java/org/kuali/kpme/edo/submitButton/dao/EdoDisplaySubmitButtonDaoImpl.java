package org.kuali.kpme.edo.submitButton.dao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.dossier.EdoDossier;
import org.kuali.kpme.edo.submitButton.EdoSubmitButton;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class EdoDisplaySubmitButtonDaoImpl extends PlatformAwareDaoBaseOjb implements EdoDisplaySubmitButtonDao{
	
	 public boolean saveOrUpdate( EdoSubmitButton submitButton ) {
		 
		 this.getPersistenceBrokerTemplate().store( submitButton );
		 
		 return true;
	 }
	 
	 public EdoSubmitButton getEdoSubmitButton(String campusCode) {
		  Criteria cConditions = new Criteria();

	        cConditions.addEqualTo("campusCode", campusCode);
	        
	        Query query = QueryFactory.newQuery(EdoSubmitButton.class, cConditions);
	        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

	        if (c != null && c.size() != 0) {
	           return (EdoSubmitButton)c.toArray()[0];
	        }
	        return null;
	    }
	 public List<EdoSubmitButton> getEdoSubmitButtonList() {
		 List<EdoSubmitButton> submitButtonList = new LinkedList<EdoSubmitButton>();
		 Criteria cConditions = new Criteria();

	        Query query = QueryFactory.newQuery(EdoSubmitButton.class, cConditions);
	        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

	        if (c != null && c.size() != 0) {
	        	submitButtonList.addAll(c);
	        }
	        return submitButtonList;

		 
	 }
	
	
}
