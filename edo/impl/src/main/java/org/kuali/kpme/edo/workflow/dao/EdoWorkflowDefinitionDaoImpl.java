package org.kuali.kpme.edo.workflow.dao;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.workflow.EdoWorkflowDefinition;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.util.*;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 4/23/14
 * Time: 11:30 AM
 */
public class EdoWorkflowDefinitionDaoImpl extends PlatformAwareDaoBaseOjb implements EdoWorkflowDefinitionDao {

    public List<String> getWorkflowIds() {
        List<String> workflowIds = new LinkedList<String>();
        Criteria criteria = new Criteria();

        Query query = QueryFactory.newQuery(EdoWorkflowDefinition.class, criteria);
        Collection workflowDefinitions = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        for ( Object workDef : workflowDefinitions) {
            EdoWorkflowDefinition wd = (EdoWorkflowDefinition) workDef;
            if (StringUtils.isNotEmpty(wd.getWorkflowId() ) ) {
                workflowIds.add(wd.getWorkflowId());
            }
        }

        return workflowIds;
    }

    public Map<String, String> getWorkflowsForDisplay() {
        Map<String, String> workflows = new HashMap<String, String>();

        Criteria criteria = new Criteria();

        Query query = QueryFactory.newQuery(EdoWorkflowDefinition.class, criteria);
        Collection workflowDefinitions = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        for ( Object workDef : workflowDefinitions) {
            EdoWorkflowDefinition wd = (EdoWorkflowDefinition) workDef;
            if (StringUtils.isNotEmpty(wd.getWorkflowId() ) ) {
                workflows.put(wd.getWorkflowId(),wd.getWorkflowName());
            }
        }

        return workflows;

    }
}
