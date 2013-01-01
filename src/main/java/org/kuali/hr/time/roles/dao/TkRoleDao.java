/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.roles.dao;

import java.sql.Date;
import java.util.List;

import org.kuali.hr.time.roles.TkRole;

public interface TkRoleDao {

	/**
	 * Returns a list of roles matching the specified criteria. Nulls are valid
	 * as parameters, see parameter comments.
	 *
     * @param principalId (optional - null is allowed)
     * @param asOfDate The effective date (required)
     * @param roleName (optional - null is allowed)
     * @param workArea (optional - null is allowed)
     * @param department (optional - null is allowed)
     * @param chart (optional - null is allowed)
     * @return A List<TkRole> of roles matching the specified parameters.
	 */
	public List<TkRole> findRoles(String principalId, Date asOfDate, String roleName, Long workArea, String department, String chart);
	/**
	 * 
	 * @param positionNumber
	 * @param asOfDate
	 * @param roleName
	 * @param workArea
	 * @param department
	 * @param chart
	 * @return
	 */
	public List<TkRole> findPositionRoles(String positionNumber, Date asOfDate, String roleName, Long workArea, String department, String chart);
	/**
	 * Returns a list of inactive roles matching the specified criteria. Nulls are valid
	 * as parameters, see parameter comments.
	 *
	 * @param principalId (optional - null is allowed)
	 * @param asOfDate The effective date (required)
	 * @param roleName (optional - null is allowed)
	 * @param workArea (optional - null is allowed)
	 * @param department (optional - null is allowed)
	 * @param chart (optional - null is allowed)
	 * @return A List<TkRole> of roles matching the specified parameters.
	 */
	public List<TkRole> findInActiveRoles(String principalId, Date asOfDate, String roleName, Long workArea, String department, String chart);

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
	
	public TkRole getRole(String tkRoleId);
	
	public TkRole getRolesByPosition(String positionNumber);
	
	public TkRole getInactiveRolesByPosition(String positionNumber);
	
	public List<TkRole> getPositionRolesForWorkArea(Long workArea, Date asOfDate);

}
