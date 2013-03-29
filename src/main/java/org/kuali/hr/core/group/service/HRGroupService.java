package org.kuali.hr.core.group.service;

import org.joda.time.DateTime;

public interface HRGroupService {

	boolean isMemberOfGroup(String principalId, String groupName, DateTime asOfDate);

	boolean isMemberOfSystemAdministratorGroup(String principalId, DateTime asOfDate);

	boolean isMemberOfSystemViewOnlyGroup(String principalId, DateTime asOfDate);
	
}