package org.kuali.kpme.edo.dossier.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.dossier.EdoDossierBo;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;


public class EdoDossierDaoImpl  extends PlatformAwareDaoBaseOjb implements EdoDossierDao {

    public EdoDossierBo getCurrentDossierPrincipalName(String candidatePrincipalName) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("candidatePrincipalName", candidatePrincipalName );
        cConditions.addIn("dossierStatus", EdoConstants.DOSSIER_STATUS_CURRENT);

        Query query = QueryFactory.newQuery(EdoDossierBo.class, cConditions);
        
        return (EdoDossierBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);       
    }

    public List<EdoDossierBo> getDossierList() {	
    	
        List<EdoDossierBo> dossierList = new LinkedList<EdoDossierBo>();

        Criteria cConditions = new Criteria();

        Query query = QueryFactory.newQuery(EdoDossierBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            dossierList.addAll(c);
        }
        return dossierList;
    }

    public EdoDossierBo getEdoDossierById( String edoDossierId ) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edoDossierId", edoDossierId);
        

        Query query = QueryFactory.newQuery(EdoDossierBo.class, cConditions);
        
        return (EdoDossierBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    public void saveOrUpdate(EdoDossierBo edoDossier) {
        this.getPersistenceBrokerTemplate().store(edoDossier);
    }
    
    
    //to populate the drop down on guest page
    public List<EdoDossierBo> getDossierListByUserName(String userName) {
    	 List<EdoDossierBo> dossierList = new ArrayList<EdoDossierBo>();

         Criteria cConditions = new Criteria();
         cConditions.addEqualTo("candidateUsername", userName);

         Query query = QueryFactory.newQuery(EdoDossierBo.class, cConditions);
         Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

         if (c != null && c.size() != 0) {
             dossierList.addAll(c);
         }
         return dossierList;
    	
    }

    public EdoDossierBo getDossier(String documentId) {
        Criteria crit = new Criteria();

        crit.addLike("documentID", documentId);

        Query query = QueryFactory.newQuery(EdoDossierBo.class, crit);

        return (EdoDossierBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

}
