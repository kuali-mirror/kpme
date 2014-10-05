package org.kuali.kpme.edo.group.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.group.EdoGroupDefinitionBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/18/13
 * Time: 10:25 AM
 */
public class EdoGroupDefinitionDaoImpl   extends PlatformAwareDaoBaseOjb implements EdoGroupDefinitionDao {

    public EdoGroupDefinitionBo getEdoGroupDefinition(String edoGroupId) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edoGroupId", edoGroupId);

        Query query = QueryFactory.newQuery(EdoGroupDefinitionBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            return (EdoGroupDefinitionBo)c.toArray()[0];
        }
        return null;
    }

    public List<EdoGroupDefinitionBo> getEdoGroupDefinitionsByWorkflowId(String workflowId) {
        List<EdoGroupDefinitionBo> groupDefList = new ArrayList<EdoGroupDefinitionBo>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("edoWorkflowId", workflowId);

        Query query = QueryFactory.newQuery(EdoGroupDefinitionBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            groupDefList.addAll(c);
        }
        return groupDefList;
    }

}
