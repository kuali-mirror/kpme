package org.kuali.kpme.edo.group.service;

import org.apache.log4j.Logger;
import org.kuali.kpme.edo.group.EdoGroupDefinition;
import org.kuali.kpme.edo.group.dao.EdoGroupDefinitionDao;

import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/18/13
 * Time: 10:44 AM
 */
public class EdoGroupDefinitionServiceImpl implements EdoGroupDefinitionService {

    private static final Logger LOG = Logger.getLogger(EdoGroupDefinitionServiceImpl.class);
    private EdoGroupDefinitionDao edoGroupDefinitionDao;

    public EdoGroupDefinitionDao getEdoGroupDefinitionDao() {
        return edoGroupDefinitionDao;
    }

    public void setEdoGroupDefinitionDao(EdoGroupDefinitionDao edoGroupDefinitionDao) {
        this.edoGroupDefinitionDao = edoGroupDefinitionDao;
    }

    public EdoGroupDefinition getEdoGroupDefinition(Integer groupId) {
        return edoGroupDefinitionDao.getEdoGroupDefinition(groupId);
    }

    public List<EdoGroupDefinition> getEdoGroupDefinitionsByWorkflowId(String workflowId) {
        return edoGroupDefinitionDao.getEdoGroupDefinitionsByWorkflowId(workflowId);
    }

}
