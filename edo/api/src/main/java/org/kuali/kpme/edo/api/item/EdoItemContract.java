package org.kuali.kpme.edo.api.item;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.mo.KpmeDataTransferObject;


public interface EdoItemContract extends KpmeDataTransferObject, Comparable<EdoItemContract> {
	
	/**
	 * The identifier of the EdoItem
	 * 
	 * <p>
	 * edoItemID of the EdoItem
	 * <p>
	 * 
	 * @return edoItemID for EdoItem
	 */
    public String getEdoItemID();

    /**
	 * The item type id that the EdoItem is associated with
	 * 
	 * <p>
	 * edoItemTypeID of the EdoItem
	 * <p>
	 * 
	 * @return edoItemTypeID for EdoItem
	 */
    public String getEdoItemTypeID();

    /**
	 * The checklist item id that the EdoItem is associated with
	 * 
	 * <p>
	 * edoChecklistItemID of the EdoItem
	 * <p>
	 * 
	 * @return edoChecklistItemID for EdoItem
	 */
    public String getEdoChecklistItemID();

    /**
	 * The dossier id that the EdoItem is associated with
	 * 
	 * <p>
	 * edoDossierID of the EdoItem
	 * <p>
	 * 
	 * @return edoDossierID for EdoItem
	 */
    public String getEdoDossierID();
   
    /**
	 * The name of the uploaded file
	 * 
	 * <p>
	 * fileName of the EdoItem
	 * <p>
	 * 
	 * @return fileName for EdoItem
	 */
    public String getFileName();

    /**
	 * The location of the uploaded file
	 * 
	 * <p>
	 * fileLocation of the EdoItem
	 * <p>
	 * 
	 * @return fileLocation for EdoItem
	 */
    public String getFileLocation();
  
    /**
	 * Explanation the candidate would like to attach to the dossier.
	 * 
	 * <p>
	 * notes of the EdoItem
	 * <p>
	 * 
	 * @return notes for EdoItem
	 */
    public String getNotes();

    /**
	 * The flag to show if the item has been routed.
	 * 
	 * <p>
	 * routed of the EdoItem
	 * <p>
	 * 
	 * @return 
	 */
    public boolean isRouted();

    /**
	 * The mime-type header information of the uploaded file
	 * 
	 * <p>
	 * contentType of the EdoItem
	 * <p>
	 * 
	 * @return contentType for EdoItem
	 */
    public String getContentType();

    /**
	 * The order in which the item is displayed.
	 * 
	 * <p>
	 * rowIndex of the EdoItem
	 * <p>
	 * 
	 * @return rowIndex for EdoItem
	 */
    public int getRowIndex();
    
    /**
	 * The unique id for the review layer definition to which the item is related. For items of type "Review Letter" only.
	 * 
	 * <p>
	 * edoReviewLayerDefID of the EdoItem
	 * <p>
	 * 
	 * @return edoReviewLayerDefID for EdoItem
	 */
    public String getEdoReviewLayerDefID();

    /**
	 * The description of the uploaded file
	 * 
	 * <p>
	 * fileDescription of the EdoItem
	 * <p>
	 * 
	 * @return fileDescription for EdoItem
	 */
    public String getFileDescription();
 
    /**
	 * The flag to indicate if the item is active or not
	 * 
	 * <p>
	 * active of the EdoItem
	 * <p>
	 * 
	 * @return true or false
	 */
    public boolean isActive();

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
	 * The text to describe what action is taken
	 * 
	 * <p>
	 * action of the EdoItem
	 * <p>
	 * 
	 * @return action for EdoItem
	 */
	public String getAction();
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
