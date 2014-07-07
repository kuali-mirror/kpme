package org.kuali.kpme.edo.item.dao;

import java.util.List;

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
    /*  This will be needed when we remove view, but remove it for now since it's not used it
    public List<EdoItemBo> getItemList(String edoChecklistSectionID); */
    public void saveOrUpdate(EdoItemBo item);
    public void saveOrUpdate(List<EdoItemBo> edoItems);
    public void deleteItem(EdoItemBo item);
    public Integer getNextRowIndexNum(String edoChecklistItemId, String uploader);
    public List<EdoItemBo> getPendingItemsByDossierId(String edoDossierId, String edoChecklistItemID);
    public List<EdoItemBo> getItemsByDossierIdForAddendumFalgZero(String edoDossierId, String edoChecklistItemID);
    public List<EdoItemBo> getPendingLettersByDossierId(String edoDossierId, String edoReviewLayerDefinitionId);
}
