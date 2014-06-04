package org.kuali.kpme.edo.checklist.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.checklist.EdoChecklistItem;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/22/14
 * Time: 10:01 AM
 */
public class EdoChecklistItemDaoImpl extends PlatformAwareDaoBaseOjb implements EdoChecklistItemDao {

    public EdoChecklistItem getChecklistItemByID(BigDecimal checklistItemID) {
        EdoChecklistItem checkList = new EdoChecklistItem();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("checklist_item_id", checklistItemID);

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklistItem.class, cConditions);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        Iterator<EdoChecklistItem> i = c.iterator();

        if (c != null && c.size() != 0) {
            while (i.hasNext()) {
                checkList = i.next();
            }
        }

        return checkList;

    }

}
