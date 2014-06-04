package org.kuali.kpme.edo.item.dao;

import org.kuali.kpme.edo.item.EdoItemV;

import java.math.BigDecimal;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/16/12
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EdoItemVDao {

    public EdoItemV getEdoItem(BigDecimal itemID);
    public List<EdoItemV> getItemList( BigDecimal dossierID, BigDecimal checklistItemID );
    public void saveOrUpdate( EdoItemV item );
    public List<EdoItemV> getListOfEdoItems( List<BigDecimal> idList );
    public List<EdoItemV> getEdoItemVs(BigDecimal dossierId, BigDecimal reviewLayerDefinitionId, String itemTypeName);
}
