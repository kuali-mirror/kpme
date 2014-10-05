package org.kuali.kpme.edo.group.service;

import org.kuali.kpme.edo.group.EdoGroup;

import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/13/13
 * Time: 2:34 PM
 */
public interface EdoGroupService {
    public EdoGroup createGroup(String groupName, String kimTypeName);
    public String buildGroupName(String routeLevel, String workflowAction, String dossierReviewLevel, String unitDesignation);
    public List<EdoGroup> createKimGroups(String workflowId, String departmentId, String schoolId, String campusId, String institutionId);
    public void addGroupToRole(EdoGroup group);
    public boolean addMemberToGroup(String principalId, String groupId);
    public String findGroupId(String grpName);
    public List<String> findGroupMembers(String groupId);
    public boolean isMemberOfGroup(String principalName, String groupName);
}
