package org.kuali.kpme.edo.item.type.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.item.type.EdoItemTypeBo;

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

    public EdoItemTypeBo getItemType(String edoItemTypeId);
    public String getItemTypeId(String itemTypeName, LocalDate asOfDate);
    public List<EdoItemTypeBo> getItemTypeList(LocalDate asOfDate);
    public void saveOrUpdate(EdoItemTypeBo itemType);
}
