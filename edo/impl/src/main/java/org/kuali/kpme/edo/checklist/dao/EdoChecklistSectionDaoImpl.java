package org.kuali.kpme.edo.checklist.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.checklist.EdoChecklistSectionBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 5/22/14
 * Time: 10:01 AM
 */
public class EdoChecklistSectionDaoImpl extends PlatformAwareDaoBaseOjb implements EdoChecklistSectionDao {

    public EdoChecklistSectionBo getChecklistSectionById(String edoChecklistSectionId) {

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("edoChecklistSectionId", edoChecklistSectionId);
        
        QueryByCriteria query = QueryFactory.newQuery(EdoChecklistSectionBo.class, cConditions);
		return (EdoChecklistSectionBo)this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }
    
    public List<EdoChecklistSectionBo> getChecklistSectionsByChecklistId(String edoChecklistId) {
    	
		List<EdoChecklistSectionBo> results = new ArrayList<EdoChecklistSectionBo>();
    	Criteria root = new Criteria();

    	root.addEqualTo("edoChecklistId", edoChecklistId);
                
        Query query = QueryFactory.newQuery(EdoChecklistSectionBo.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }
}
