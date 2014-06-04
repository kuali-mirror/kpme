package org.kuali.kpme.edo.item.type.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.item.type.EdoItemType;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
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
public class EdoItemTypeDaoImpl extends PlatformAwareDaoBaseOjb  implements EdoItemTypeDao {

    /**
     * Retrieve a single item type from the database given the specified ID
     *
     * @param   itemTypeID  the database ID of the requested item type
     * @return              an object of EdoItemType, null on error or no data
     */
    public EdoItemType getItemType( BigDecimal itemTypeID ) {

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("itemTypeID", itemTypeID);

        Query query = QueryFactory.newQuery(EdoItemType.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() == 1) {
                return (EdoItemType)c.toArray()[0];
            }
        }
        return null;
    }

    public BigDecimal getItemTypeID( String itemTypeName ) {

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("itemTypeName", itemTypeName);

        Query query = QueryFactory.newQuery(EdoItemType.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() == 1) {
                EdoItemType tmpItemType = (EdoItemType)c.toArray()[0];
                return tmpItemType.getItemTypeID();
            }
        }
        return null;
    }

    /**
     * Retrieve a list of all item types from the database
     *
     * @return      a List object of EdoItemType objects, null on error or no data
     */
    public List<EdoItemType> getItemTypeList() {

        List<EdoItemType> itemTypeList = new LinkedList<EdoItemType>();

        Criteria cConditions = new Criteria();

        Query query = QueryFactory.newQuery(EdoItemType.class, cConditions);
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
    public void saveOrUpdate( EdoItemType itemType ) {
        this.getPersistenceBrokerTemplate().store( itemType );
    }

    public List<EdoItemType> getItemTypes(String itemTypeName) {
        List<EdoItemType> itemTypeList = new LinkedList<EdoItemType>();

        Criteria criteria = new Criteria();

        if (StringUtils.isNotEmpty(itemTypeName)) {
            criteria.addLike("itemTypeName", itemTypeName);
        }

        Query query = QueryFactory.newQuery(EdoItemType.class, criteria);

        itemTypeList.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return itemTypeList;
    }
}
