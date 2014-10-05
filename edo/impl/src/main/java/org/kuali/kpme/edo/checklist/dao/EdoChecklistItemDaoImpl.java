package org.kuali.kpme.edo.checklist.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.checklist.EdoChecklistItemBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/22/14
 * Time: 10:01 AM
 */
public class EdoChecklistItemDaoImpl extends PlatformAwareDaoBaseOjb implements EdoChecklistItemDao {

    public EdoChecklistItemBo getChecklistItemById(String edoChecklistItemId) {
        EdoChecklistItemBo checklistItem = new EdoChecklistItemBo();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edoChecklistItemId", edoChecklistItemId);

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklistItemBo.class, cConditions);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        Iterator<EdoChecklistItemBo> i = c.iterator();

        if (c != null && c.size() != 0) {
            while (i.hasNext()) {
            	checklistItem = i.next();
            }
        }

        return checklistItem;

    }
    
    public List<EdoChecklistItemBo> getChecklistItemsBySectionId(String edoChecklistSectionId) {
    	
		List<EdoChecklistItemBo> results = new ArrayList<EdoChecklistItemBo>();
    	Criteria root = new Criteria();

    	root.addEqualTo("edoChecklistSectionId", edoChecklistSectionId);
        
        Query query = QueryFactory.newQuery(EdoChecklistItemBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }
    
    public List<EdoChecklistItemBo> getChecklistItemsBySectionIds(List<String> idList) {

        List<EdoChecklistItemBo> results = new LinkedList<EdoChecklistItemBo>();
        Criteria root = new Criteria();

        // For some reason, when use addColumnIn, it has to be the name of the column name, not the field name 
        //root.addColumnIn("edoChecklistSectionId", idList);  
        root.addColumnIn("edo_checklist_section_id", idList);

        Query query = QueryFactory.newQuery(EdoChecklistItemBo.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (CollectionUtils.isNotEmpty(c)) {
        	results.addAll(c);
            return results;
        }
        return null;
    }
}
