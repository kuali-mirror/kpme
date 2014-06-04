package org.kuali.kpme.edo.item.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.item.EdoItem;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 2/28/13
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoItemDaoImpl extends PlatformAwareDaoBaseOjb implements EdoItemDao {

    public EdoItem getEdoItem(BigDecimal itemID) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("itemID", itemID);

        Query query = QueryFactory.newQuery(EdoItem.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() == 1) {
                return (EdoItem)c.toArray()[0];
            }
        }
        return null;
    }

    public List<EdoItem> getItemList( BigDecimal checklistSectionID ) {

        List<EdoItem> itemList = new LinkedList<EdoItem>();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("checklistSectionID", checklistSectionID);

        Query query = QueryFactory.newQuery(EdoItem.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() == 1) {
                itemList.addAll(c);
                return itemList;
            }
        }
        return null;
    }

    public void saveOrUpdate(EdoItem item) {
        this.getPersistenceBrokerTemplate().store(item);
    }

    public void deleteItem(EdoItem item) {
        this.getPersistenceBrokerTemplate().delete(item);
    }

    private static final String rowIndexQuery = "select max(row_index) AS row_index from EDO_ITEM_T where CHECKLIST_ITEM_ID = ? and UPLOADER_USERNAME = ?";

    @Override
    public Integer getNextRowIndexNum(BigDecimal checklistItemId, String uploader) {

        SqlRowSet sqlResult = EdoServiceLocator.getEdoJdbcTemplate().queryForRowSet(rowIndexQuery, new Object[] { checklistItemId, uploader });
        if (sqlResult.next()) {
            return sqlResult.getInt("row_index") + 1;
        } else {
            return 1;
        }

    }

    @Override
    public List<EdoItem> getPendingItemsByDossierId(BigDecimal dossierId, BigDecimal checklistItemID) {
        List<EdoItem> itemList = new LinkedList<EdoItem>();
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("dossierID", dossierId);
        cConditions.addEqualTo("checklist_item_id", checklistItemID);
        cConditions.addEqualTo("addendum_routed", 1);

        Query query = QueryFactory.newQuery(EdoItem.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            itemList.addAll(c);
            return itemList;
        }

        return null;
    }
    
    @Override
    public List<EdoItem> getItemsByDossierIdForAddendumFalgZero( BigDecimal dossierId, BigDecimal checklistItemID) {
    	 List<EdoItem> itemList = new LinkedList<EdoItem>();
         Criteria cConditions = new Criteria();

         cConditions.addEqualTo("dossierID", dossierId);
         cConditions.addEqualTo("checklist_item_id", checklistItemID);
         cConditions.addEqualTo("addendum_routed", 0);

         Query query = QueryFactory.newQuery(EdoItem.class, cConditions);
         Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

         if (c != null && c.size() != 0) {
             itemList.addAll(c);
             return itemList;
         }

         return null;
    	
    }

    public List<EdoItem> getPendingLettersByDossierId( BigDecimal dossierId, BigDecimal reviewLayerDefinitionId ) {
        List<EdoItem> letterList = new LinkedList<EdoItem>();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("dossierID", dossierId);
        cConditions.addEqualTo("review_layer_def_id", reviewLayerDefinitionId);
        cConditions.addIsNull("layer_level");

        Query query = QueryFactory.newQuery(EdoItem.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            letterList.addAll(c);
        }

        return letterList;
    }

}

