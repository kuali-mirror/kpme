package org.kuali.kpme.edo.api.item.type;

import org.kuali.kpme.core.api.mo.KpmeEffectiveDataTransferObject;

/**
 * <p>EdoItemTypeContract interface</p>
 *
 */
public interface EdoItemTypeContract extends KpmeEffectiveDataTransferObject {

	/**
	 * The identifier of the EdoItemType
	 * 
	 * <p>
	 * edoItemTypeID of the EdoItemType
	 * <p>
	 * 
	 * @return edoItemTypeID for EdoItemType
	 */
    public String getEdoItemTypeID();

    /**
	 * The name of the EdoItemType
	 * 
	 * <p>
	 * itemTypeName of the EdoItemType
	 * <p>
	 * 
	 * @return itemTypeName for EdoItemType
	 */
    public String getItemTypeName();

    /**
	 * The description of the EdoItemType
	 * 
	 * <p>
	 * itemTypeDescription of the EdoItemType
	 * <p>
	 * 
	 * @return itemTypeDescription for EdoItemType
	 */
    public String getItemTypeDescription();

    /**
	 * The instruction of the EdoItemType
	 * 
	 * <p>
	 * itemTypeInstructions of the EdoItemType
	 * <p>
	 * 
	 * @return itemTypeInstructions for EdoItemType
	 */
    public String getItemTypeInstructions();

    /**
	 * Indicates whether external reviewers who have access to the system should be able to 
	 * see the EdoItemType
	 * 
	 * <p>
	 * itemTypeExtAvailable of the EdoItemType
	 * <p>
	 * 
	 * @return Y for Yes, N for No
	 */  
    public boolean isItemTypeExtAvailable();

}
