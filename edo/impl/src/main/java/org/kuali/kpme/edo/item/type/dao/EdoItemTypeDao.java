package org.kuali.kpme.edo.item.type.dao;

import org.kuali.kpme.edo.item.type.EdoItemType;

import java.math.BigDecimal;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 9:29 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EdoItemTypeDao {

    public EdoItemType getItemType( BigDecimal itemTypeID );
    public BigDecimal getItemTypeID( String itemTypeName );
    public List<EdoItemType> getItemTypeList();
    public void saveOrUpdate( EdoItemType itemType );
    public List<EdoItemType> getItemTypes(String itemTypeName);
}
