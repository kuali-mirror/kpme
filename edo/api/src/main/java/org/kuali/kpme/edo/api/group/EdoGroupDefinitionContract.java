package org.kuali.kpme.edo.api.group;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;

/**
 * <p>EdoGroupDefinitionContract interface</p>
 *
 */
public interface EdoGroupDefinitionContract extends KpmeEffectiveDataTransferObject{

	/**
	 * The identifier of the EdoGroupDefinition
	 * 
	 * <p>
	 * edoGroupId of the EdoGroupDefinition
	 * <p>
	 * 
	 * @return edoGroupId for EdoGroupDefinition
	 */
    public String getEdoGroupId();

    /**
	 * The work flow id that the EdoGroupDefinition is associated with
	 * 
	 * <p>
	 * edoWorkflowId of the EdoGroupDefinition
	 * <p>
	 * 
	 * @return edoWorkflowId for EdoGroupDefinition
	 */
    public String getEdoWorkflowId();

    /**
	 * The work flow level that the EdoGroupDefinition is associated with
	 * 
	 * <p>
	 * workflowLevel of the EdoGroupDefinition
	 * <p>
	 * 
	 * @return workflowLevel for EdoGroupDefinition
	 */
    public String getWorkflowLevel();

    /**
	 * The dossier type that the EdoGroupDefinition is associated with
	 * 
	 * <p>
	 * dossierType of the EdoGroupDefinition
	 * <p>
	 * 
	 * @return dossierType for EdoGroupDefinition
	 */
    public String getDossierType();

    /**
	 * The work flow type that the EdoGroupDefinition is associated with
	 * 
	 * <p>
	 * workflowType of the EdoGroupDefinition
	 * <p>
	 * 
	 * @return workflowType for EdoGroupDefinition
	 */
    public String getWorkflowType();

    /**
	 * The kim type name that the EdoGroupDefinition is associated with
	 * 
	 * <p>
	 * kimTypeName of the EdoGroupDefinition
	 * <p>
	 * 
	 * @return kimTypeName for EdoGroupDefinition
	 */
    public String getKimTypeName();

    /**
	 * The kim role name that the EdoGroupDefinition is associated with
	 * 
	 * <p>
	 * kimRoleName of the EdoGroupDefinition
	 * <p>
	 * 
	 * @return kimRoleName for EdoGroupDefinition
	 */
    public String getKimRoleName();
}
