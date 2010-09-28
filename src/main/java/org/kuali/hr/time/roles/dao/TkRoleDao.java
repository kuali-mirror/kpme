package org.kuali.hr.time.roles.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.roles.TkRole;

public interface TkRoleDao {

	/**
	 * Returns a list of roles matching the specified criteria. Nulls are valid
	 * as parameters, see parameter comments.
	 * 
	 * @param workArea
	 *            The work area
	 *            (optional, either workArea or department or principalId Mandatory)
	 * @param department
	 *            The department 
	 *            (optional, either workArea or department or principalId Mandatory)
	 * @param roleName
	 *            The name of the role to search over, if null, all roles are
	 *            returned.
	 * @param asOfDate
	 *            The effective date, if null, effective dating scheme is not
	 *            used.
	 * @param principalId
	 * 			  If specified, will only return roles valid for the indicated principal.
	 * 			  (optional, either workArea or department or principalId Mandatory)
	 * @return
	 */
	public List<TkRole> findRoles(Long workArea, String department, String roleName, Date asOfDate, String principalId);
	
	
	/**
	 * A role to update/save
	 * @param role
	 */
	public void saveOrUpdateRole(TkRole role); 
	
	/**
	 * A list of roles to save.
	 * @param roles
	 * @return
	 */
	public void saveOrUpdateRoles(List<TkRole> roles);
}
