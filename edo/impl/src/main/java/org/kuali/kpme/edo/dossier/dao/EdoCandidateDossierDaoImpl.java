package org.kuali.kpme.edo.dossier.dao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.candidate.dao.EdoCandidateDaoImpl;
import org.kuali.kpme.edo.dossier.EdoCandidateDossier;
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
 * Date: 5/13/13
 * Time: 9:54 AM
 */
public class EdoCandidateDossierDaoImpl extends PlatformAwareDaoBaseOjb implements EdoCandidateDossierDao {

    private static final Logger LOG = Logger.getLogger(EdoCandidateDaoImpl.class);

    public List<EdoCandidateDossier> getEdoCandidateDossierList () {

        List<EdoCandidateDossier> dossierLists = new LinkedList<EdoCandidateDossier>();

        Criteria cConditions = new Criteria();

        Query query = QueryFactory.newQuery(EdoCandidateDossier.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            dossierLists.addAll(c);
        }
        return dossierLists;
    }

    public List<EdoCandidateDossier> getCurrentCandidateDossierList () {

        List<EdoCandidateDossier> dossierLists = new LinkedList<EdoCandidateDossier>();

        Criteria criteria = new Criteria();
        //added RECONSIDER and CLOSED to DOSSIER_STATUS_CURRENT structure
        //this accomodates super users and admins to see all the dossiers of the candidates.
        criteria.addIn("dossierStatus", EdoConstants.DOSSIER_STATUS_CURRENT);

        Query query = QueryFactory.newQuery(EdoCandidateDossier.class, criteria);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            dossierLists.addAll(c);
        }
        return dossierLists;
    }

    public EdoCandidateDossier getCandidateDossier(BigDecimal dossierID) {

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("dossierId", dossierID);

        Query query = QueryFactory.newQuery(EdoCandidateDossier.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            return (EdoCandidateDossier)c.toArray()[0];
        }
        return null;
    }

    public List<EdoCandidateDossier> getCandidateDossierByUsername(String userName) {

        List<EdoCandidateDossier> dossierLists = new LinkedList<EdoCandidateDossier>();
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("userName", userName);
        Query query = QueryFactory.newQuery(EdoCandidateDossier.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            dossierLists.addAll(c);
        }
        return dossierLists;
    }
    //for guest role : when a guets logs in make sure he see only the dossier he is assigned as guest
    
    public EdoCandidateDossier getCandidateDossierByDossierId(String dossierID) {
    	BigDecimal id = new BigDecimal(dossierID);
    	Criteria cConditions = new Criteria();
    	cConditions.addEqualTo("dossierId", id);
    	Query query = QueryFactory.newQuery(EdoCandidateDossier.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            return (EdoCandidateDossier)c.toArray()[0];
        }
        return null;
    }

    //when a candidate delegate logs in show only open dossier of a candidate
    public List<EdoCandidateDossier> getCandidateDelegateDossierByUsername(String userName) {

        List<EdoCandidateDossier> dossierLists = new LinkedList<EdoCandidateDossier>();
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("userName", userName);
        cConditions.addIn("dossierStatus", EdoConstants.DOSSIER_STATUS_CURRENT);

        Query query = QueryFactory.newQuery(EdoCandidateDossier.class, cConditions);

        dossierLists.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return dossierLists;
    }


    public List<EdoCandidateDossier> getCurrentCandidateDossierByUsername(String userName) {

        List<EdoCandidateDossier> dossierLists = new LinkedList<EdoCandidateDossier>();
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("userName", userName);
        cConditions.addIn("dossierStatus", EdoConstants.DOSSIER_STATUS_CURRENT);

        Query query = QueryFactory.newQuery(EdoCandidateDossier.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            dossierLists.addAll(c);
        }
        return dossierLists;
    }

    public List<EdoCandidateDossier> getCandidateDossiers(List<String> campuses, List<String> schools, List<String> departments) {
        List<EdoCandidateDossier> dossierList = new ArrayList<EdoCandidateDossier>();

        Criteria criteria = new Criteria();

        //Campus criteria
        if (CollectionUtils.isNotEmpty(campuses)) {
            for (String campus : campuses) {
                Criteria campusCriteria = new Criteria();
                campusCriteria.addLike("campus", campus);
                criteria.addOrCriteria(campusCriteria);
            }
        }

        //Department criteria
        if (CollectionUtils.isNotEmpty(departments)) {
            for (String department : departments) {
                Criteria departmentCriteria = new Criteria();
                departmentCriteria.addLike("department", department);
                criteria.addOrCriteria(departmentCriteria);
            }
        }

        //School criteria
        if (CollectionUtils.isNotEmpty(schools)) {
            for (String school : schools) {
                Criteria schoolCriteria = new Criteria();
                schoolCriteria.addLike("school", school);
                criteria.addOrCriteria(schoolCriteria);
            }
        }

        Query query = QueryFactory.newQuery(EdoCandidateDossier.class, criteria);
        dossierList.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return dossierList;
    }
}
