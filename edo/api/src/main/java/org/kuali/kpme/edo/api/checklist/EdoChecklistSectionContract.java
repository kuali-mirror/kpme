package org.kuali.kpme.edo.api.checklist;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;

/**
 * <p>EdoChecklistSectionContract interface</p>
 *
 */
public interface EdoChecklistSectionContract extends KpmeEffectiveDataTransferObject {

	/**
	 * The identifier of the EdoChecklistSection
	 * 
	 * <p>
	 * checklistSectionID of the EdoChecklistSection
	 * <p>
	 * 
	 * @return checklistSectionID for EdoChecklistSection
	 */
    public String getChecklistSectionID();

    /**
	 * The checklist id this EdoChecklistSection belongs to
	 * 
	 * <p>
	 * checklistID of the EdoChecklistSection
	 * <p>
	 * 
	 * @return checklistID for EdoChecklistSection
	 */
    public String getChecklistID();
    
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
	 * Indicates how many checklist items are needed for this EdoChecklistSection 
	 * 
	 * <p>
	 * checklistSectionOrdinal of the EdoChecklistSection
	 * <p>
	 * 
	 * @return checklistSectionOrdinal for EdoChecklistSection
	 */
    public int getChecklistSectionOrdinal();

}
