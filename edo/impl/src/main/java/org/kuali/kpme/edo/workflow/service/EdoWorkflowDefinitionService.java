package org.kuali.kpme.edo.workflow.service;

import java.util.List;
import java.util.Map;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 4/23/14
 * Time: 2:37 PM
 */
public interface EdoWorkflowDefinitionService {

    public List<String> getWorkflowIds();
    public Map<String, String> getWorkflowsForDisplay();
}
