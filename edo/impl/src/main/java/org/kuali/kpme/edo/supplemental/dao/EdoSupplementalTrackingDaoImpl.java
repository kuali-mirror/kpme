package org.kuali.kpme.edo.supplemental.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.supplemental.EdoSupplementalTracking;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 9/10/13
 * Time: 1:35 PM
 */
public class EdoSupplementalTrackingDaoImpl extends PlatformAwareDaoBaseOjb implements EdoSupplementalTrackingDao {

    public EdoSupplementalTracking getSupplementalTrackingEntry(Integer supplementalTrackingId) {
        Criteria criteria = new Criteria();

        criteria.addEqualTo("supplementalTrackingId", supplementalTrackingId);

        Query query = QueryFactory.newQuery(EdoSupplementalTracking.class, criteria);
        return (EdoSupplementalTracking) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    //debug this one
    public EdoSupplementalTracking getSupplementalTrackingEntryObj(Integer dossierId, BigDecimal reviewLevel) {
        Criteria criteria = new Criteria();

        criteria.addEqualTo("reviewLevel", reviewLevel);
        criteria.addEqualTo("dossierId",dossierId);
        Query query = QueryFactory.newQuery(EdoSupplementalTracking.class, criteria);
        return (EdoSupplementalTracking) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }
    /*public void updateSuppTracking(Integer supplementalTrackingId, Integer dossierId, BigDecimal reviewLevel) {
    	  Criteria criteria = new Criteria();
    	  criteria.addEqualTo("supplementalTrackingId", supplementalTrackingId);
          criteria.addEqualTo("reviewLevel", reviewLevel);
          criteria.addEqualTo("dossierId",dossierId);
          Query query = QueryFactory.newQuery(EdoSupplementalTracking.class, criteria);
          this.getPersistenceBrokerTemplate().
    }*/


    public List<EdoSupplementalTracking> getSupplementTrackingEntries() {
        List<EdoSupplementalTracking> results = new LinkedList<EdoSupplementalTracking>();
        Criteria criteria = new Criteria();

        Query query = QueryFactory.newQuery(EdoSupplementalTracking.class, criteria);
        results.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

    public void saveOrUpdate(EdoSupplementalTracking edoSupplementalTracking) {
        this.getPersistenceBrokerTemplate().store(edoSupplementalTracking);
    }
    public EdoSupplementalTracking canSeeTheAcknowledgeWithAction(Integer dossierId, BigDecimal reviewLevel) {
        Criteria criteria = new Criteria();

        criteria.addEqualTo("dossierId", dossierId);
        criteria.addEqualTo("reviewLevel", reviewLevel);
        criteria.addEqualTo("acknowledged", false);

        Query query = QueryFactory.newQuery(EdoSupplementalTracking.class, criteria);
        return (EdoSupplementalTracking) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    	
    }
}
