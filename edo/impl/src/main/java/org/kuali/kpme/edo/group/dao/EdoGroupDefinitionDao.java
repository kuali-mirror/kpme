package org.kuali.kpme.edo.group.dao;

import org.kuali.kpme.edo.group.EdoGroupDefinition;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/18/13
 * Time: 10:24 AM
 */
public interface EdoGroupDefinitionDao {

    public EdoGroupDefinition getEdoGroupDefinition(Integer groupId);
    public List<EdoGroupDefinition> getEdoGroupDefinitionsByWorkflowId(String workflowId);
}
