package org.kuali.kpme.edo.item.dao;

import org.kuali.kpme.edo.item.EdoItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 02/28/13
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EdoItemDao {

    public EdoItem getEdoItem(BigDecimal itemID);
    public List<EdoItem> getItemList( BigDecimal checklistSectionID );
    public void saveOrUpdate( EdoItem item );
    public void deleteItem( EdoItem item );
    public Integer getNextRowIndexNum(BigDecimal checklistItemId, String uploader);
    public List<EdoItem> getPendingItemsByDossierId( BigDecimal dossierId, BigDecimal checklistItemID );
    public List<EdoItem> getItemsByDossierIdForAddendumFalgZero( BigDecimal dossierId, BigDecimal checklistItemID);
    public List<EdoItem> getPendingLettersByDossierId( BigDecimal dossierId, BigDecimal reviewLayerDefinitionId );
}
