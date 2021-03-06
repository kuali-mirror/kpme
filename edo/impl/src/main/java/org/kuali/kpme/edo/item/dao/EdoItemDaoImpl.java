package org.kuali.kpme.edo.item.dao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.item.EdoItemBo;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;
import org.springframework.jdbc.support.rowset.SqlRowSet;

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

    public EdoItemBo getEdoItem(String edoItemId) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edoItemId", edoItemId);

        Query query = QueryFactory.newQuery(EdoItemBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            if (c.size() == 1) {
                return (EdoItemBo)c.toArray()[0];
            }
        }
        return null;
    }
    
    public List<EdoItemBo> getItemList(String edoDossierId, String edoChecklistItemId) {

    	List<EdoItemBo> itemList = new LinkedList<EdoItemBo>();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edoChecklistItemId", edoChecklistItemId);
        cConditions.addEqualTo("edoDossierId", edoDossierId);

        Query query = QueryFactory.newQuery(EdoItemBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            itemList.addAll(c);
            return itemList;
        }
        return null;
    }

    public void saveOrUpdate(EdoItemBo item) {
        this.getPersistenceBrokerTemplate().store(item);
    }
    
    public void saveOrUpdate(List<EdoItemBo> edoItems) {
    	if (edoItems != null && edoItems.size() > 0 ) {
            for (EdoItemBo edoItem : edoItems) {
                this.getPersistenceBrokerTemplate().store(edoItem);
            }
        }
    }

    public void deleteItem(EdoItemBo item) {
        this.getPersistenceBrokerTemplate().delete(item);
    }

    private static final String rowIndexQuery = "select max(row_index) AS row_index from EDO_ITEM_T where EDO_CHECKLIST_ITEM_ID = ? and USER_PRINCIPAL_ID = ?";

    @Override
    public Integer getNextRowIndexNum(String edoChecklistItemId, String uploader) {

        SqlRowSet sqlResult = EdoServiceLocator.getEdoJdbcTemplate().queryForRowSet(rowIndexQuery, new Object[] {edoChecklistItemId, uploader});
        if (sqlResult.next()) {
            return sqlResult.getInt("row_index") + 1;
        } else {
            return 1;
        }

    }

    @Override
    public List<EdoItemBo> getPendingItemsByDossierId(String edoDossierId, String edoChecklistItemId) {
        List<EdoItemBo> itemList = new LinkedList<EdoItemBo>();
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edoDossierId", edoDossierId);
        cConditions.addEqualTo("edoChecklistItemId", edoChecklistItemId);
        cConditions.addEqualTo("routed", true);

        Query query = QueryFactory.newQuery(EdoItemBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            itemList.addAll(c);
            return itemList;
        }

        return null;
    }
    
    @Override
    public List<EdoItemBo> getItemsByDossierIdForAddendumFalgZero(String edoDossierId, String edoChecklistItemId) {
    	 List<EdoItemBo> itemList = new LinkedList<EdoItemBo>();
         Criteria cConditions = new Criteria();

         cConditions.addEqualTo("edoDossierId", edoDossierId);
         cConditions.addEqualTo("edoChecklistItemId", edoChecklistItemId);
         cConditions.addEqualTo("routed", false);

         Query query = QueryFactory.newQuery(EdoItemBo.class, cConditions);
         Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

         if (c != null && c.size() != 0) {
             itemList.addAll(c);
             return itemList;
         }

         return null;
    	
    }

    public List<EdoItemBo> getPendingLettersByDossierId(String edoDossierId, String edoChecklistItemId) {
        List<EdoItemBo> letterList = new LinkedList<EdoItemBo>();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edoDossierId", edoDossierId);
        cConditions.addEqualTo("edoReviewLayerDefId", edoChecklistItemId);
        //cConditions.addIsNull("layer_level");
        cConditions.addEqualTo("routed", true);

        Query query = QueryFactory.newQuery(EdoItemBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            letterList.addAll(c);
        }

        return letterList;
    }
    
    public List<EdoItemBo> getReviewLetterEdoItems(String edoDossierId, String edoReviewLayerDefinitionId) {
        List<EdoItemBo> items = new LinkedList<EdoItemBo>();

        Criteria criteria = new Criteria();
        criteria.addEqualTo("edoDossierId", edoDossierId);
        criteria.addEqualTo("edoReviewLayerDefId", edoReviewLayerDefinitionId);

        Query query = QueryFactory.newQuery(EdoItemBo.class, criteria);

        items.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return items;
    }
    
    public List<EdoItemBo> getListOfEdoItems(List<String> idList) {
    	Criteria cConditions = new Criteria();
        List<EdoItemBo> itemList = new LinkedList<EdoItemBo>();

        // For some reason, when use addColumnIn, it has to be the name of the column name, not the field name
        //cConditions.addColumnIn("edoItemId", idList);
        cConditions.addColumnIn("edo_item_id", idList);

        Query query = QueryFactory.newQuery(EdoItemBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (CollectionUtils.isNotEmpty(c)) {
            itemList.addAll(c);
            return itemList;
        }
        return null;
    }

}

