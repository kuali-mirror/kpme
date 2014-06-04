package org.kuali.kpme.edo.item.type.service;

import org.kuali.kpme.edo.item.type.EdoItemType;
import org.kuali.kpme.edo.item.type.dao.EdoItemTypeDao;

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
public class EdoItemTypeServiceImpl implements EdoItemTypeService {

    private EdoItemTypeDao edoItemTypeDao;

    public List<EdoItemType> getItemTypeList() {
        return edoItemTypeDao.getItemTypeList();
    }

    public EdoItemType getItemType( BigDecimal itemTypeID ) {
        return edoItemTypeDao.getItemType( itemTypeID );
    }

    public void setEdoItemTypeDao( EdoItemTypeDao itemType ) {
        this.edoItemTypeDao = itemType;
    }

    public void saveOrUpdate( EdoItemType itemTypeObj ) {
        this.edoItemTypeDao.saveOrUpdate( itemTypeObj );
    }

    public List<EdoItemType> getItemTypes(String itemTypeName) {
        return this.edoItemTypeDao.getItemTypes(itemTypeName);
    }

    public BigDecimal getItemTypeID( String itemTypeName ) {
        return this.edoItemTypeDao.getItemTypeID( itemTypeName );
    }
}
