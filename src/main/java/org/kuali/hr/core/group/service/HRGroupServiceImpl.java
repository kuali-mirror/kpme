package org.kuali.hr.core.group.service;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.greaterThan;
import static org.kuali.rice.core.api.criteria.PredicateFactory.isNull;
import static org.kuali.rice.core.api.criteria.PredicateFactory.lessThanOrEqual;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.kuali.hr.core.KPMENamespace;
import org.kuali.hr.core.group.KPMEGroup;
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