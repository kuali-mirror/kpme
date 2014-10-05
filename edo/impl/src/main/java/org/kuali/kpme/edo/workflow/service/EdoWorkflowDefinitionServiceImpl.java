package org.kuali.kpme.edo.workflow.service;

import org.kuali.kpme.edo.workflow.dao.EdoWorkflowDefinitionDao;

import java.util.List;
import java.util.Map;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 4/23/14
 * Time: 2:38 PM
 */
public class EdoWorkflowDefinitionServiceImpl implements EdoWorkflowDefinitionService {

    private EdoWorkflowDefinitionDao edoWorkflowDefinitionDao;

    public EdoWorkflowDefinitionDao getEdoWorkflowDefinitionDao() {
        return edoWorkflowDefinitionDao;
    }

    public void setEdoWorkflowDefinitionDao(EdoWorkflowDefinitionDao edoWorkflowDefinitionDao) {
        this.edoWorkflowDefinitionDao = edoWorkflowDefinitionDao;
    }

    public List<String> getEdoWorkflowIds() {
        return edoWorkflowDefinitionDao.getEdoWorkflowIds();
    }

    public Map<String,String> getWorkflowsForDisplay() {
        return edoWorkflowDefinitionDao.getWorkflowsForDisplay();
    }
}
