package org.kuali.kpme.edo.item.dao;

import java.util.List;

import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.item.EdoItemBo;

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

    public EdoItemBo getEdoItem(String edoItemID);
    public List<EdoItemBo> getItemList(String edoDossierID, String checklistItemID);
    public void saveOrUpdate(EdoItemBo item);
    public void saveOrUpdate(List<EdoItemBo> edoItems);
    public void deleteItem(EdoItemBo item);
    public Integer getNextRowIndexNum(String edoChecklistItemId, String uploader);
    public List<EdoItemBo> getPendingItemsByDossierId(String edoDossierId, String edoChecklistItemID);
    public List<EdoItemBo> getItemsByDossierIdForAddendumFalgZero(String edoDossierId, String edoChecklistItemID);
    public List<EdoItemBo> getPendingLettersByDossierId(String edoDossierId, String edoReviewLayerDefinitionId);
    
    public List<EdoItemBo> getReviewLetterEdoItems(String edoDossierId, String edoReviewLayerDefinitionId);
    public List<EdoItemBo> getListOfEdoItems(List<String> idList);
}
