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
	 * edoChecklistSectionID of the EdoChecklistSection
	 * <p>
	 * 
	 * @return edoChecklistSectionID for EdoChecklistSection
	 */
    public String getEdoChecklistSectionID();

    /**
	 * The checklist id this EdoChecklistSection belongs to
	 * 
	 * <p>
	 * edoChecklistID of the EdoChecklistSection
	 * <p>
	 * 
	 * @return edoChecklistID for EdoChecklistSection
	 */
    public String getEdoChecklistID();
    
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

}
