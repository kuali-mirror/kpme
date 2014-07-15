package org.kuali.kpme.edo.item.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.item.EdoItemBo;

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
    public EdoItem getEdoItem(String edoItemID);
    public EdoItemBo getEdoItemBo(String edoItemID);
    public List<EdoItem> getItemList(String edoDossierID, String checklistItemID);
    public void saveOrUpdate(EdoItemBo itemBo);
    public void saveOrUpdate(EdoItem item);
    public void saveOrUpdate(List<EdoItem> edoItems);
    public EdoItem getFile(String edoItemID, ByteArrayOutputStream bao) throws IOException;
    public Integer getNextRowIndexNum(String edoChecklistItemId, String uploader);
    public List<EdoItem> getPendingItemsByDossierId(String edoDossierId, String edoChecklistItemID);
    public List<EdoItem> getItemsByDossierIdForAddendumFalgZero(String edoDossierId, String edoChecklistItemID);
    public boolean isReviewLetterPendingRoute(String edoDossierId, String edoReviewLayerDefintionId);
    public void updateLetterAsLevelRouted(String edoDossierId, String edoReviewLayerDefinitionId);
    public void deleteItem(EdoItem item);
    
    public List<EdoItem> getReviewLetterEdoItems(String edoDossierId, String edoReviewLayerDefinitionId);
    public List<EdoItem> getListOfEdoItems(List<String> idList);
    public String getItemJSONString(EdoItem item);
    
    // From EdoItemCountVService
    // public List<EdoItemCountV> getItemCount(BigDecimal dossierId, BigDecimal checklistSectionId);
    public int getItemCount(String edoDossierId, String edoChecklistSectionId);
}
