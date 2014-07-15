package org.kuali.kpme.edo.group.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.group.EdoGroupDefinition;
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

    public EdoGroupDefinition getEdoGroupDefinition(Integer groupId) {
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("group_id", groupId);

        Query query = QueryFactory.newQuery(EdoGroupDefinition.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            return (EdoGroupDefinition)c.toArray()[0];
        }
        return null;
    }

    public List<EdoGroupDefinition> getEdoGroupDefinitionsByWorkflowId(String workflowId) {
        List<EdoGroupDefinition> groupDefList = new ArrayList<EdoGroupDefinition>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("workflow_id", workflowId);

        Query query = QueryFactory.newQuery(EdoGroupDefinition.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            groupDefList.addAll(c);
        }
        return groupDefList;
    }

}
