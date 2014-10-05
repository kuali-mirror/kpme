package org.kuali.kpme.edo.api.checklist;

import java.util.List;

import org.kuali.kpme.core.api.mo.KpmeDataTransferObject;

/**
 * <p>EdoChecklistSectionContract interface</p>
 *
 */
public interface EdoChecklistSectionContract extends KpmeDataTransferObject {

	/**
	 * The identifier of the EdoChecklistSection
	 * 
	 * <p>
	 * edoChecklistSectionId of the EdoChecklistSection
	 * <p>
	 * 
	 * @return edoChecklistSectionId for EdoChecklistSection
	 */
    public String getEdoChecklistSectionId();

    /**
	 * The checklist id this EdoChecklistSection belongs to
	 * 
	 * <p>
	 * edoChecklistId of the EdoChecklistSection
	 * <p>
	 * 
	 * @return edoChecklistId for EdoChecklistSection
	 */
    public String getEdoChecklistId();
    
    /**
	 * The name of the EdoChecklistSection
	 * 
	 * <p>
	 * checklistSectionName of the EdoChecklistSection
	 * <p>
	 * 
	 * @return checklistSectionName for EdoChecklistSection
	 */
    public String getChecklistSectionName();

    /**
	 * The description of the EdoChecklistSection
	 * 
	 * <p>
	 * description of the EdoChecklistSection
	 * <p>
	 * 
	 * @return description for EdoChecklistSection
	 */
    public String getDescription();

    /**
	 * Indicates the order in which the EdoChecklistSection is displayed
	 * 
	 * <p>
	 * checklistSectionOrdinal of the EdoChecklistSection
	 * <p>
	 * 
	 * @return checklistSectionOrdinal for EdoChecklistSection
	 */
    public int getChecklistSectionOrdinal();
    
    /**
	 * The list of ChecklistItem that is associated with the EdoChecklistSection
	 * 
	 * <p>
	 * checklistItems of the EdoChecklist
	 * <p>
	 * 
	 * @return checklistItems for EdoChecklist
	 */
    public List<? extends EdoChecklistItemContract> getChecklistItems();

}
