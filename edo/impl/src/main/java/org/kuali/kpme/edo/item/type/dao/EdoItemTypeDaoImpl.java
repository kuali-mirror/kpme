package org.kuali.kpme.edo.item.type.dao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.edo.item.type.EdoItemTypeBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/20/12
 * Time: 9:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoItemTypeDaoImpl extends PlatformAwareDaoBaseOjb  implements EdoItemTypeDao {

    /**
     * Retrieve a single item type from the database given the specified ID
     *
     * @param   itemTypeID  the database ID of the requested item type
     * @return              an object of EdoItemType, null on error or no data
     */
    public EdoItemTypeBo getItemType(String edoItemTypeID) {

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edoItemTypeId", edoItemTypeID);

        Query query = QueryFactory.newQuery(EdoItemTypeBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() == 1) {
                return (EdoItemTypeBo)c.toArray()[0];
            }
        }
        return null;
    }

    public String getItemTypeID(String itemTypeName, LocalDate asOfDate) {

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("itemTypeName", itemTypeName);
        cConditions.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EdoItemTypeBo.class, asOfDate, EdoItemTypeBo.BUSINESS_KEYS, false));
        cConditions.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EdoItemTypeBo.class, EdoItemTypeBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        cConditions.addAndCriteria(activeFilter);
        
        Query query = QueryFactory.newQuery(EdoItemTypeBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() == 1) {
                EdoItemTypeBo tmpItemType = (EdoItemTypeBo)c.toArray()[0];
                return tmpItemType.getEdoItemTypeId();
            }
        }
        return null;
    }

    /**
     * Retrieve a list of all item types from the database
     *
     * @return      a List object of EdoItemType objects, null on error or no data
     */
    public List<EdoItemTypeBo> getItemTypeList(LocalDate asOfDate) {

        List<EdoItemTypeBo> itemTypeList = new LinkedList<EdoItemTypeBo>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EdoItemTypeBo.class, asOfDate, EdoItemTypeBo.BUSINESS_KEYS, false));
        cConditions.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EdoItemTypeBo.class, EdoItemTypeBo.BUSINESS_KEYS, false));

        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        cConditions.addAndCriteria(activeFilter);
        
        Query query = QueryFactory.newQuery(EdoItemTypeBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() > 0) {
                itemTypeList.addAll(c);
                return itemTypeList;
            }
        }
        return null;
    }

    /**
     * Save or update the EdoItemType object back to the database
     *
     * @param   itemType    the object of EdoItemType to save or update
     */
    public void saveOrUpdate(EdoItemTypeBo itemType) {
        this.getPersistenceBrokerTemplate().store( itemType );
    }

}
