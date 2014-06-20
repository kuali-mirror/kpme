package org.kuali.kpme.edo.api.checklist;

import org.kuali.kpme.core.api.mo.KpmeEffectiveKeyedDataTransferObject;

public interface EdoChecklistContract extends KpmeEffectiveKeyedDataTransferObject {
	
	/**
	 * The identifier of the EdoChecklist
	 * 
	 * <p>
	 * checklistID of the EdoChecklist
	 * <p>
	 * 
	 * @return checklistID for EdoChecklist
	 */
	public String getChecklistID();

	/**
	 * The dossier type code of the EdoChecklist
	 * 
	 * <p>
	 * dossierTypeCode of the EdoChecklist
	 * <p>
	 * 
	 * @return dossierTypeCode for EdoChecklist
	 */
	public String getDossierTypeCode();

	/**
	 * The department id that this EdoChecklist is associated with
	 * 
	 * <p>
	 * departmentID of the EdoChecklist
	 * <p>
	 * 
	 * @return departmentID for EdoChecklist
	 */
	public String getDepartmentID();

	/**
	 * The description of the EdoChecklist
	 * 
	 * <p>
	 * description of the EdoChecklist
	 * <p>
	 * 
	 * @return description for EdoChecklist
	 */
	public String getDescription();

}
