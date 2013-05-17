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

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThan;
import static org.kuali.rice.core.api.criteria.PredicateFactory.isNull;
import static org.kuali.rice.core.api.criteria.PredicateFactory.lessThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.kpme.core.KPMENamespace;
import org.kuali.kpme.core.group.KPMEGroup;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.group.GroupMember;
import org.kuali.rice.kim.api.group.GroupService;

public class HRGroupServiceImpl implements HRGroupService {
	
	private GroupService groupService;

	@Override
	public boolean isMemberOfGroup(String principalId, String groupName, DateTime asOfDate) {
		boolean isMemberOfGroup = false;
		
		Group group = getGroupService().getGroupByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), groupName);
		
		if (group != null) {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(equal("groupId", group.getId()));
			predicates.add(or(isNull("activeFromDateValue"), lessThanOrEqual("activeFromDateValue", asOfDate)));
			predicates.add(or(isNull("activeToDateValue"), greaterThan("activeToDateValue", new DateTime())));
			
			List<GroupMember> groupMembers = getGroupService().findGroupMembers(QueryByCriteria.Builder.fromPredicates(predicates.toArray(new Predicate[] {}))).getResults();
	
			for (GroupMember groupMember : groupMembers) {
				if (MemberType.PRINCIPAL.equals(groupMember.getType())) {
					if (StringUtils.equals(groupMember.getMemberId(), principalId)) {
						isMemberOfGroup = true;
						break;
					}
				} else if (MemberType.GROUP.equals(groupMember.getType())) {
					Group nestedGroup = getGroupService().getGroupByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), groupName);
					
					if (nestedGroup != null) {
						if (isMemberOfGroup(principalId, nestedGroup.getName(), asOfDate)) {
							isMemberOfGroup = true;
							break;
						}
					}
				}
			}
		}
		
		return isMemberOfGroup;
	}
	
	@Override
	public boolean isMemberOfSystemAdministratorGroup(String principalId, DateTime asOfDate) {
    	return isMemberOfGroup(principalId, KPMEGroup.SYSTEM_ADMINISTRATOR.getGroupName(), asOfDate);
	}
	
	@Override
	public boolean isMemberOfSystemViewOnlyGroup(String principalId, DateTime asOfDate) {
		return isMemberOfGroup(principalId, KPMEGroup.SYSTEM_VIEW_ONLY.getGroupName(), asOfDate);
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

}