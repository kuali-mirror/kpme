package org.kuali.kpme.edo.group.service;

import java.util.List;

import org.kuali.kpme.edo.api.group.EdoGroupDefinition;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/18/13
 * Time: 10:44 AM
 */
public interface EdoGroupDefinitionService {
    public EdoGroupDefinition getEdoGroupDefinition(String edoGroupId);
    public List<EdoGroupDefinition> getEdoGroupDefinitionsByWorkflowId(String edoWorkflowId);

}
