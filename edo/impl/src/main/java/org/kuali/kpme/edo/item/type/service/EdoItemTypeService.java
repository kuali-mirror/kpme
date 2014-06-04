package org.kuali.kpme.edo.item.type.service;

import org.kuali.kpme.edo.item.type.EdoItemType;

import java.math.BigDecimal;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EdoItemTypeService {

    public List<EdoItemType> getItemTypeList();
    public EdoItemType getItemType( BigDecimal itemTypeID );
    public BigDecimal getItemTypeID( String itemTypeName );
    public void saveOrUpdate( EdoItemType itemTypeObj );
    public List<EdoItemType> getItemTypes(String itemTypeName);
}
