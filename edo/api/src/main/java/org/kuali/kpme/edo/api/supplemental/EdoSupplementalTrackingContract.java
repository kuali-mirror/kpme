package org.kuali.kpme.edo.api.supplemental;

import org.joda.time.DateTime;
import org.kuali.kpme.core.api.mo.KpmeDataTransferObject;

/**
 * <p>EdoSupplementalTrackingContract interface</p>
 *
 */
public interface EdoSupplementalTrackingContract extends KpmeDataTransferObject {

	/**
	 * The identifier of the EdoSupplementalTracking
	 * 
	 * <p>
	 * edoSupplementalTrackingId of the EdoSupplementalTracking
	 * <p>
	 * 
	 * @return edoSupplementalTrackingId for EdoSupplementalTracking
	 */
	public String getEdoSupplementalTrackingId();

	/**
	 * The dossier id that the EdoSupplementalTracking is associated with
	 * 
	 * <p>
	 * edoDossierId of the EdoSupplementalTracking
	 * <p>
	 * 
	 * @return edoDossierId for EdoSupplementalTracking
	 */
    public String getEdoDossierId();

    /**
	 * The review level of the EdoSupplementalTracking
	 * 
	 * <p>
	 * reviewLevel of the EdoSupplementalTracking
	 * <p>
	 * 
	 * @return reviewLevel for EdoSupplementalTracking
	 */
    public int getReviewLevel();

    /**
     * TODO Put a better comment
	 * Indicates whehter the EdoSupplementalTracking is acknowledged or not 
	 * 
	 * <p>
	 * acknowledged of the EdoSupplementalTracking
	 * <p>
	 * 
	 * @return Y for Yes, N for No
	 */  
	public boolean isAcknowledged();
	
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
