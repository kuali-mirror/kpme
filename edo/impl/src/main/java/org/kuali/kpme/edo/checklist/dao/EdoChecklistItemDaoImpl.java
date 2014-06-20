package org.kuali.kpme.edo.checklist.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.paygrade.PayGradeBo;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
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

    public EdoChecklistItemBo getChecklistItemByID(String checklistItemID) {
        EdoChecklistItemBo checklistItem = new EdoChecklistItemBo();

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("checklist_item_id", checklistItemID);

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
    
    public List<EdoChecklistItemBo> getChecklistItemsBySectionID(String sectionID, LocalDate asOfDate) {
    	
		List<EdoChecklistItemBo> results = new ArrayList<EdoChecklistItemBo>();
    	Criteria root = new Criteria();

    	root.addEqualTo("checklistSectionID", sectionID);
    	root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EdoChecklistItemBo.class, asOfDate, EdoChecklistItemBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EdoChecklistItemBo.class, EdoChecklistItemBo.BUSINESS_KEYS, false));
        
        Query query = QueryFactory.newQuery(EdoChecklistItemBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }
}
