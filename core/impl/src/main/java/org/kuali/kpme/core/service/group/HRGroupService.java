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
package org.kuali.kpme.core.service.group;

import org.joda.time.DateTime;

public interface HRGroupService {

	/**
	 * Checks whether the given {@code principalId} is a member of the group {@code groupName}.
	 *
	 * @param principalId The person to check the group membership for
	 * @param groupName The name of the group
	 * @param asOfDate The effective date of the group membership
	 * 
	 * @return true if {@code principalId} is a member of the group {@code groupName}, false otherwise.
	 */
	boolean isMemberOfGroup(String principalId, String groupName, DateTime asOfDate);

	/**
	 * Checks whether the given {@code principalId} is a system administrator.
	 * 
	 * @param principalId The person to check the group membership for
	 * @param asOfDate The effective date of the group membership
	 * 
	 * @return true if {@code principalId} is a system administrator, false otherwise
	 */
	boolean isMemberOfSystemAdministratorGroup(String principalId, DateTime asOfDate);

	/**
	 * Checks whether the given {@code principalId} is a system view only user.
	 * 
	 * @param principalId The person to check the group membership for
	 * @param asOfDate The effective date of the group membership
	 * 
	 * @return true if {@code principalId} is a system view only user, false otherwise
	 */
	boolean isMemberOfSystemViewOnlyGroup(String principalId, DateTime asOfDate);
	
}