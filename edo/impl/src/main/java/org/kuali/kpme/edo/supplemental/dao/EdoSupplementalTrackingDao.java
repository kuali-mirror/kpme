package org.kuali.kpme.edo.supplemental.dao;

import org.kuali.kpme.edo.supplemental.EdoSupplementalTracking;

import java.math.BigDecimal;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 9/10/13
 * Time: 11:41 AM
 */
public interface EdoSupplementalTrackingDao {

    public EdoSupplementalTracking getSupplementalTrackingEntry(Integer supplementalTrackingId);
    public List<EdoSupplementalTracking> getSupplementTrackingEntries();
    public void saveOrUpdate(EdoSupplementalTracking edoSupplementalTracking);
    public EdoSupplementalTracking getSupplementalTrackingEntryObj(Integer dossierId, BigDecimal reviewLevel);
    //public void updateSuppTracking(Integer supplementalTrackingId, Integer dossierId, BigDecimal reviewLevel);
    public EdoSupplementalTracking canSeeTheAcknowledgeWithAction(Integer dossierId, BigDecimal reviewLevel);
}
