package org.kuali.kpme.edo.supplemental.service;

import org.kuali.kpme.edo.supplemental.EdoSupplementalTracking;
import org.kuali.kpme.edo.supplemental.dao.EdoSupplementalTrackingDao;

import java.math.BigDecimal;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 9/10/13
 * Time: 1:40 PM
 */
public class EdoSupplementalTrackingServiceImpl implements EdoSupplementalTrackingService {

    private EdoSupplementalTrackingDao edoSupplementalTrackingDao;

    public EdoSupplementalTracking getSupplementalTrackingEntry(Integer supplementalTrackingId) {
        return this.edoSupplementalTrackingDao.getSupplementalTrackingEntry(supplementalTrackingId);
    }

    public List<EdoSupplementalTracking> getSupplementTrackingEntries() {
        return this.edoSupplementalTrackingDao.getSupplementTrackingEntries();
    }

    public void saveOrUpdate(EdoSupplementalTracking edoSupplementalTracking) {
        this.edoSupplementalTrackingDao.saveOrUpdate(edoSupplementalTracking);
    }
    public void setEdoSupplementalTrackingDao(EdoSupplementalTrackingDao edoSupplementalTrackingDao) {
        this.edoSupplementalTrackingDao = edoSupplementalTrackingDao;
    }
    public EdoSupplementalTracking getSupplementalTrackingEntryObj(Integer dossierId, BigDecimal reviewLevel) {
    	  return this.edoSupplementalTrackingDao.getSupplementalTrackingEntryObj(dossierId,reviewLevel);
    }
   /* public void updateSuppTracking(Integer supplementalTrackingId, Integer dossierId, BigDecimal reviewLevel) {
    	 return this.edoSupplementalTrackingDao.updateSuppTracking(supplementalTrackingId, dossierId, reviewLevel);
    	
    }*/
    public EdoSupplementalTracking canSeeTheAcknowledgeWithAction(Integer dossierId, BigDecimal reviewLevel) {
    	return this.edoSupplementalTrackingDao.canSeeTheAcknowledgeWithAction(dossierId, reviewLevel);
    }
    
}
