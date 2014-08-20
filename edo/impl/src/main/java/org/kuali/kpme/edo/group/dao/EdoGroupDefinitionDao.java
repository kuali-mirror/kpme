package org.kuali.kpme.edo.group.dao;

import org.kuali.kpme.edo.group.EdoGroupDefinitionBo;
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

    public EdoGroupDefinitionBo getEdoGroupDefinition(String edoGroupId);
    public List<EdoGroupDefinitionBo> getEdoGroupDefinitionsByWorkflowId(String edoWorkflowId);
}
