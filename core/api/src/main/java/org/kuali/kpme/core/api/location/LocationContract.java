package org.kuali.kpme.core.api.location;

import java.util.List;

import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.role.location.LocationPrincipalRoleMemberBoContract;

/**
 * <p>LocationContract interface.</p>
 *
 */
public interface LocationContract extends HrBusinessObjectContract {
	
	/**
	 * The Primary Key of a Location entry saved in a database
	 * 
	 * <p>
	 * hrLocationId of Location
	 * <p>
	 * 
	 * @return hrLocationId for Location
	 */
	public String getHrLocationId();

	/**
	 * Text field used to identify the location
	 * 
	 * <p>
	 * location of Location
	 * <p>
	 * 
	 * @return location for Location
	 */
	public String getLocation();

	/**
	 * Indicates the timezone for this location
	 * 
	 * <p>
	 * timezone of Location
	 * <p>
	 * 
	 * @return timezone for Location
	 */
	public String getTimezone();

	/**
	 * Text which describes the location code
	 * 
	 * <p>
	 * description of Location
	 * <p>
	 * 
	 * @return description for Location
	 */
	public String getDescription();	
	
	// TODO: not sure if this field is needed...
	public String getUserPrincipalId();

	/**
	 * History flag for Location lookups 
	 * 
	 * <p>
	 * history of Location
	 * </p>
	 * 
	 * @return Y if want to show history, N if not
	 */
	public String getHistory();
	
	/**
	 * Active Role member list for the Location 
	 * 
	 * <p>
	 * roleMembers of Location
	 * </p>
	 * 
	 * @return roleMembers for Location
	 */
	public List<? extends LocationPrincipalRoleMemberBoContract> getRoleMembers();
	
	/**
	 * Inactive Role member list for the Location 
	 * 
	 * <p>
	 * roleMembers of Location
	 * </p>
	 * 
	 * @return roleMembers for Location
	 */
	public List<? extends LocationPrincipalRoleMemberBoContract> getInactiveRoleMembers();
	
}
