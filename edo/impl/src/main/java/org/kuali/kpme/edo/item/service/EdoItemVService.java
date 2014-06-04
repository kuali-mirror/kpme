package org.kuali.kpme.edo.item.service;

import org.kuali.kpme.edo.item.EdoItemV;

import java.math.BigDecimal;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/16/12
 * Time: 4:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EdoItemVService {

    public EdoItemV getEdoItem( BigDecimal itemID );
    public List<EdoItemV> getItemList( BigDecimal dossierID, BigDecimal checklistSectionID );
    public List<EdoItemV> getListOfEdoItems( List<BigDecimal> idList );
    public void saveOrUpdate( EdoItemV item );
    public List<EdoItemV> getEdoItemVs(BigDecimal dossierId, BigDecimal reviewLayerDefinitionId, String itemTypeName);
}
