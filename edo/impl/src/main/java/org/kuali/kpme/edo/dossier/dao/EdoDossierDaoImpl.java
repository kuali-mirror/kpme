package org.kuali.kpme.edo.dossier.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.dossier.EdoDossier;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 1/17/13
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoDossierDaoImpl  extends PlatformAwareDaoBaseOjb implements EdoDossierDao {

    public EdoDossier getCurrentDossier(String userName ) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("candidateUsername", userName );
        cConditions.addIn("dossierStatus", EdoConstants.DOSSIER_STATUS_CURRENT);

        Query query = QueryFactory.newQuery(EdoDossier.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            return (EdoDossier)c.toArray()[0];
        }
        return null;
       
    }

    public List<EdoDossier> getDossierList() {
        List<EdoDossier> dossierList = new LinkedList<EdoDossier>();

        Criteria cConditions = new Criteria();

        Query query = QueryFactory.newQuery(EdoDossier.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            dossierList.addAll(c);
        }
        return dossierList;
    }

    public EdoDossier getDossierById( BigDecimal dossierId ) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("dossierID", dossierId);

        Query query = QueryFactory.newQuery(EdoDossier.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() == 1) {
                return (EdoDossier)c.toArray()[0];
            }
        }
        return null;
    }

    public void saveOrUpdate(EdoDossier edoDossier) {
        this.getPersistenceBrokerTemplate().store(edoDossier);
    }
    public EdoDossier getDossierByDossierId( String dossierId ) {
    	if (StringUtils.isNotEmpty(dossierId)) {
            BigDecimal id = new BigDecimal(dossierId);

            Criteria criteria = new Criteria();

            criteria.addEqualTo("dossierID", id);

            Query query = QueryFactory.newQuery(EdoDossier.class, criteria);
            return (EdoDossier) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
        }
        return null;
    }
    
    //to populate the drop down on guest page
    public List<EdoDossier> getDossierListByUserName(String userName) {
    	 List<EdoDossier> dossierList = new ArrayList<EdoDossier>();

         Criteria cConditions = new Criteria();
         cConditions.addEqualTo("candidateUsername", userName);

         Query query = QueryFactory.newQuery(EdoDossier.class, cConditions);
         Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

         if (c != null && c.size() != 0) {
             dossierList.addAll(c);
         }
         return dossierList;
    	
    }

    public EdoDossier getDossier(String documentId) {
        Criteria crit = new Criteria();

        crit.addLike("documentID", documentId);

        Query query = QueryFactory.newQuery(EdoDossier.class, crit);

        return (EdoDossier) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

}
