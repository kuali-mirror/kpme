package org.kuali.kpme.edo.item.service;

import org.kuali.kpme.edo.item.EdoItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 2/28/13
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EdoItemService {
    public EdoItem getEdoItem( BigDecimal itemID );
    public List<EdoItem> getItemList(BigDecimal checklistSectionID);
    public void saveOrUpdate( EdoItem item );
    public EdoItem getFile( BigDecimal itemID, ByteArrayOutputStream bao) throws IOException;
    public Integer getNextRowIndexNum(BigDecimal checklistItemId, String uploader);
    public List<EdoItem> getPendingItemsByDossierId( BigDecimal dossierId, BigDecimal checklistItemID);
    public List<EdoItem> getItemsByDossierIdForAddendumFalgZero( BigDecimal dossierId, BigDecimal checklistItemID);
    public boolean isReviewLetterPendingRoute( BigDecimal dossierId, BigDecimal reviewLayerDefintionId );
    public void updateLetterAsLevelRouted( BigDecimal dossierId, BigDecimal reviewLayerDefinitionId );
}
