package org.kuali.kpme.edo.checklist.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.edo.checklist.EdoChecklistBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/22/14
 * Time: 10:01 AM
 */
public class EdoChecklistDaoImpl extends PlatformAwareDaoBaseOjb implements EdoChecklistDao {

    public EdoChecklistBo getChecklistById(String edoChecklistId) {

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("edoChecklistId", edoChecklistId);

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklistBo.class, cConditions);
		return (EdoChecklistBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }
    
    public List<EdoChecklistBo> getChecklists(String groupKey, String organizationCode, String departmentId, LocalDate asOfDate) {
    	
    	List<EdoChecklistBo> results = new ArrayList<EdoChecklistBo>();
    	Criteria root = new Criteria();

    	root.addEqualTo("groupKeyCode", groupKey);
    	root.addEqualTo("organizationCode", organizationCode);
    	root.addEqualTo("departmentId", departmentId);
    	root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(EdoChecklistBo.class, asOfDate, EdoChecklistBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EdoChecklistBo.class, EdoChecklistBo.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);
        
        Query query = QueryFactory.newQuery(EdoChecklistBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));
        return results;
    }
}
