package org.kuali.kpme.edo.supplemental.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.supplemental.EdoSupplementalTrackingBo;
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

    public EdoSupplementalTrackingBo getSupplementalTrackingEntry(String edoSupplementalTrackingId) {
        Criteria criteria = new Criteria();

        criteria.addEqualTo("edoSupplementalTrackingId", edoSupplementalTrackingId);

        Query query = QueryFactory.newQuery(EdoSupplementalTrackingBo.class, criteria);
        return (EdoSupplementalTrackingBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }
    //debug this one
    public EdoSupplementalTrackingBo getSupplementalTrackingEntryObj(String edoDossierId, BigDecimal reviewLevel) {
        Criteria criteria = new Criteria();

        criteria.addEqualTo("reviewLevel", reviewLevel);
        criteria.addEqualTo("edoDossierId",edoDossierId);
        Query query = QueryFactory.newQuery(EdoSupplementalTrackingBo.class, criteria);
        return (EdoSupplementalTrackingBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }
    /*public void updateSuppTracking(Integer supplementalTrackingId, Integer dossierId, BigDecimal reviewLevel) {
    	  Criteria criteria = new Criteria();
    	  criteria.addEqualTo("supplementalTrackingId", supplementalTrackingId);
          criteria.addEqualTo("reviewLevel", reviewLevel);
          criteria.addEqualTo("dossierId",dossierId);
          Query query = QueryFactory.newQuery(EdoSupplementalTracking.class, criteria);
          this.getPersistenceBrokerTemplate().
    }*/


    public List<EdoSupplementalTrackingBo> getSupplementTrackingEntries() {
        List<EdoSupplementalTrackingBo> results = new LinkedList<EdoSupplementalTrackingBo>();
        Criteria criteria = new Criteria();

        Query query = QueryFactory.newQuery(EdoSupplementalTrackingBo.class, criteria);
        results.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

    public void saveOrUpdate(EdoSupplementalTrackingBo edoSupplementalTracking) {
        this.getPersistenceBrokerTemplate().store(edoSupplementalTracking);
    }
    public EdoSupplementalTrackingBo canSeeTheAcknowledgeWithAction(String edoDossierId, BigDecimal reviewLevel) {
        Criteria criteria = new Criteria();

        criteria.addEqualTo("edoDossierId", edoDossierId);
        criteria.addEqualTo("reviewLevel", reviewLevel);
        criteria.addEqualTo("acknowledged", false);

        Query query = QueryFactory.newQuery(EdoSupplementalTrackingBo.class, criteria);
        return (EdoSupplementalTrackingBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    	
    }
}
