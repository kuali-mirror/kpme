package org.kuali.kpme.edo.item.dao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.item.EdoItemV;
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
 * Date: 11/16/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoItemVDaoImpl extends PlatformAwareDaoBaseOjb implements EdoItemVDao {

    public EdoItemV getEdoItem(BigDecimal itemID) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("itemID", itemID);

        Query query = QueryFactory.newQuery(EdoItemV.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() == 1) {
                return (EdoItemV)c.toArray()[0];
            }
        }
        return null;
    }

    public List<EdoItemV> getItemList( BigDecimal dossierID, BigDecimal checklistItemID ) {

        List<EdoItemV> itemList = new LinkedList<EdoItemV>();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("checklistItemID", checklistItemID);
        cConditions.addEqualTo("dossierID", dossierID);

        Query query = QueryFactory.newQuery(EdoItemV.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            itemList.addAll(c);
            return itemList;
        }
        return null;
    }

    public List<EdoItemV> getListOfEdoItems(List<BigDecimal> idList) {
        Criteria cConditions = new Criteria();
        List<EdoItemV> itemList = new LinkedList<EdoItemV>();

        cConditions.addColumnIn("item_id", idList);

        Query query = QueryFactory.newQuery(EdoItemV.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (CollectionUtils.isNotEmpty(c)) {
            itemList.addAll(c);
            return itemList;
        }
        return null;
    }

    public List<EdoItemV> getEdoItemVs(BigDecimal dossierId, BigDecimal reviewLayerDefinitionId, String itemTypeName) {
        List<EdoItemV> items = new LinkedList<EdoItemV>();

        Criteria criteria = new Criteria();
        criteria.addEqualTo("dossierID", dossierId);
        criteria.addEqualTo("reviewLayerDefID", reviewLayerDefinitionId);
        criteria.addEqualTo("itemTypeName", itemTypeName);

        Query query = QueryFactory.newQuery(EdoItemV.class, criteria);

        items.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return items;
    }

    public void saveOrUpdate(EdoItemV item) {
        this.getPersistenceBrokerTemplate().store(item);
    }

}

