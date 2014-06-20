package org.kuali.kpme.edo.api.checklist;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;

/**
 * <p>EdoChecklistItemContract interface</p>
 *
 */
public interface EdoChecklistItemContract extends KpmeEffectiveDataTransferObject {

	/**
	 * The identifier of the EdoChecklistItem
	 * 
	 * <p>
	 * checklistItemID of the EdoChecklistItem
	 * <p>
	 * 
	 * @return checklistItemID for EdoChecklistItem
	 */
    public String getChecklistItemID();

    /**
	 * The checklist section id that this EdoChecklistItem is associated with
	 * 
	 * <p>
	 * checklistSectionID of the EdoChecklistItem
	 * <p>
	 * 
	 * @return checklistSectionID for EdoChecklistItem
	 */
    public String getChecklistSectionID();
    
    /**
	 * The name of the EdoChecklistItem
	 * 
	 * <p>
	 * checklistItemName of the EdoChecklistItem
	 * <p>
	 * 
	 * @return checklistItemName for EdoChecklistItem
	 */
    public String getChecklistItemName();

    /**
	 * The description of the EdoChecklistItem
	 * 
	 * <p>
	 * itemDescription of the EdoChecklistItem
	 * <p>
	 * 
	 * @return itemDescription for EdoChecklistItem
	 */
    public String getItemDescription();

    /**
	 * Indicates if an item is required for the EdoChecklistItem(Category)
	 * 
	 * <p>
	 * required of the EdoChecklistItem
	 * <p>
	 * 
	 * @return Y for Yes, N for No
	 */
    public boolean isRequired();

    /**
	 * Indicates in which order this EdoChecklistItem was uploaded 
	 * 
	 * <p>
	 * checklistItemOrdinal of the EdoChecklistItem
	 * <p>
	 * 
	 * @return checklistItemOrdinal for EdoChecklistItem
	 */
    public int getChecklistItemOrdinal();

}
