package org.kuali.kpme.edo.supplemental.service;

import java.math.BigDecimal;
import java.util.List;

import org.kuali.kpme.edo.api.supplemental.EdoSupplementalTracking;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 9/10/13
 * Time: 11:57 AM
 */
public interface EdoSupplementalTrackingService {

    public EdoSupplementalTracking getSupplementalTrackingEntry(String edoSupplementalTrackingId);
    public List<EdoSupplementalTracking> getSupplementTrackingEntries();
    public void saveOrUpdate(EdoSupplementalTracking edoSupplementalTracking);
    public EdoSupplementalTracking getSupplementalTrackingEntryObj(String edoDossierId, BigDecimal reviewLevel); 
   // public void updateSuppTracking(Integer supplementalTrackingId, Integer dossierId, BigDecimal reviewLevel);
    public EdoSupplementalTracking canSeeTheAcknowledgeWithAction(String edoDossierId, BigDecimal reviewLevel);

}
