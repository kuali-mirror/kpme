package org.kuali.kpme.edo.api.workflow;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.mo.KpmeDataTransferObject;

/**
 * <p>EdoWorkflowDefinitionContract interface</p>
 *
 */
public interface EdoWorkflowDefinitionContract extends KpmeDataTransferObject {

	/**
	 * The identifier of the EdoWorkflowDefinition
	 * 
	 * <p>
	 * edoWorkflowId of the EdoWorkflowDefinition
	 * <p>
	 * 
	 * @return edoWorkflowId for EdoWorkflowDefinition
	 */
	public String getEdoWorkflowId();
   
	/**
	 * The workflow name of the EdoWorkflowDefinition
	 * 
	 * <p>
	 * workflowName of the EdoWorkflowDefinition
	 * <p>
	 * 
	 * @return workflowName for EdoWorkflowDefinition
	 */
    public String getWorkflowName();

    /**
	 * The workflow description of the EdoWorkflowDefinition
	 * 
	 * <p>
	 * workflowDescription of the EdoWorkflowDefinition
	 * <p>
	 * 
	 * @return workflowDescription for EdoWorkflowDefinition
	 */
    public String getWorkflowDescription();

	/**
	 * The user principal id who takes action on the EdoItem
	 * 
	 * <p>
	 * userPrincipalId of the EdoItem
	 * <p>
	 * 
	 * @return userPrincipalId for EdoItem
	 */
	public String getUserPrincipalId();
	
	/**
	 * The actionTimestamp (DateTime) the EdoItem is associated with
	 * 
	 * <p>
	 * actionTimestamp of the EdoItem
	 * <p>
	 * 
	 * @return actionTimestamp wrapped in a DateTime object
	 */
	public DateTime getActionFullDateTime() ;
	
}
