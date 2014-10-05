package org.kuali.kpme.edo.item.type.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.item.type.EdoItemType;

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

    public List<EdoItemType> getItemTypeList(LocalDate asOfDate);
    public EdoItemType getItemType(String edoItemTypeId);
    public String getItemTypeId(String itemTypeName, LocalDate asOfDate);
    public void saveOrUpdate(EdoItemType itemTypeObj);
    public String getEdoItemTypeJSONString(EdoItemType aType);
}
