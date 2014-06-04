package org.kuali.kpme.edo.item.service;

import org.kuali.kpme.edo.item.EdoItemV;
import org.kuali.kpme.edo.item.dao.EdoItemVDao;

import java.math.BigDecimal;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/16/12
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoItemVServiceImpl implements EdoItemVService {

    private EdoItemVDao edoItemVDao;

    public List<EdoItemV> getItemList( BigDecimal dossierID, BigDecimal checklistItemID) {
        return edoItemVDao.getItemList( dossierID, checklistItemID);
    }
    public EdoItemV getEdoItem( BigDecimal itemID ) {
        return edoItemVDao.getEdoItem(itemID);
    }

    public void setEdoItemVDao(EdoItemVDao item) {
        this.edoItemVDao = item;
    }

    public List<EdoItemV> getListOfEdoItems(List<BigDecimal> idList) {
        return edoItemVDao.getListOfEdoItems(idList);
    }

    public List<EdoItemV> getEdoItemVs(BigDecimal dossierId, BigDecimal reviewLayerDefinitionId, String itemTypeName) {
        return edoItemVDao.getEdoItemVs(dossierId, reviewLayerDefinitionId, itemTypeName);
    }

    public void saveOrUpdate(EdoItemV item) {
        this.edoItemVDao.saveOrUpdate(item);
    }
}
