package org.kuali.kpme.edo.checklist.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;
import org.apache.ojb.broker.query.QueryFactory;
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

    public EdoChecklistBo getChecklistByID(String edoChecklistID) {

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("edo_checklist_id", edoChecklistID);

        QueryByCriteria query = QueryFactory.newQuery(EdoChecklistBo.class, cConditions);
		return (EdoChecklistBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

    }
}
