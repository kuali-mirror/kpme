package org.kuali.kpme.edo.workflow.dao;

import java.util.*;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 4/23/14
 * Time: 11:30 AM
 */
public interface EdoWorkflowDefinitionDao {

    public List<String> getWorkflowIds();
    public Map<String, String> getWorkflowsForDisplay();

}
