package org.kuali.kpme.edo.supplemental.dao;

import org.kuali.kpme.edo.supplemental.EdoSupplementalTrackingBo;

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

    public EdoSupplementalTrackingBo getSupplementalTrackingEntry(String edoSupplementalTrackingId);
    public List<EdoSupplementalTrackingBo> getSupplementTrackingEntries();
    public void saveOrUpdate(EdoSupplementalTrackingBo edoSupplementalTracking);
    public EdoSupplementalTrackingBo getSupplementalTrackingEntryObj(String edoDossierId, BigDecimal reviewLevel);
    //public void updateSuppTracking(Integer supplementalTrackingId, Integer dossierId, BigDecimal reviewLevel);
    public EdoSupplementalTrackingBo canSeeTheAcknowledgeWithAction(String edoDossierId, BigDecimal reviewLevel);
}
