package org.kuali.kpme.edo.supplemental.service;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.kpme.edo.api.supplemental.EdoSupplementalTracking;
import org.kuali.kpme.edo.supplemental.EdoSupplementalTrackingBo;
import org.kuali.kpme.edo.supplemental.dao.EdoSupplementalTrackingDao;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

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
    
    protected List<EdoSupplementalTracking> convertToImmutable(List<EdoSupplementalTrackingBo> bos) {
		return ModelObjectUtils.transform(bos, EdoSupplementalTrackingBo.toImmutable);
	}
    
    public void setEdoSupplementalTrackingDao(EdoSupplementalTrackingDao edoSupplementalTrackingDao) {
        this.edoSupplementalTrackingDao = edoSupplementalTrackingDao;
    }

    public EdoSupplementalTracking getSupplementalTrackingEntry(String edoSupplementalTrackingId) {
        return EdoSupplementalTrackingBo.to(edoSupplementalTrackingDao.getSupplementalTrackingEntry(edoSupplementalTrackingId));
    }

    public List<EdoSupplementalTracking> getSupplementTrackingEntries() {
        List<EdoSupplementalTrackingBo> bos = edoSupplementalTrackingDao.getSupplementTrackingEntries();
    	return convertToImmutable(bos);
    }

    public void saveOrUpdate(EdoSupplementalTracking edoSupplementalTracking) {
        this.edoSupplementalTrackingDao.saveOrUpdate(EdoSupplementalTrackingBo.from(edoSupplementalTracking));
    }
    
    public EdoSupplementalTracking getSupplementalTrackingEntryObj(String edoDossierId, BigDecimal reviewLevel) {
    	  return EdoSupplementalTrackingBo.to(this.edoSupplementalTrackingDao.getSupplementalTrackingEntryObj(edoDossierId,reviewLevel));
    }
   /* public void updateSuppTracking(Integer supplementalTrackingId, Integer dossierId, BigDecimal reviewLevel) {
    	 return this.edoSupplementalTrackingDao.updateSuppTracking(supplementalTrackingId, dossierId, reviewLevel);
    	
    }*/
    public EdoSupplementalTracking canSeeTheAcknowledgeWithAction(String edoDossierId, BigDecimal reviewLevel) {
    	return EdoSupplementalTrackingBo.to(this.edoSupplementalTrackingDao.canSeeTheAcknowledgeWithAction(edoDossierId, reviewLevel));
    }
    
}
