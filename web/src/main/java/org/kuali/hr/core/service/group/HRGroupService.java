package org.kuali.hr.core.service.group;

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