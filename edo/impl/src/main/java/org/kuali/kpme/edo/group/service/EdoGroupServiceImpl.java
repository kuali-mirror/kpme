package org.kuali.kpme.edo.group.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.kpme.edo.group.EdoGroup;
import org.kuali.kpme.edo.group.EdoGroupDefinition;
import org.kuali.kpme.edo.group.EdoGroupTracking;
import org.kuali.kpme.edo.group.EdoRoleResponsibility;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.group.Group;
import org.kuali.rice.kim.api.group.GroupMember;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.responsibility.Responsibility;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.role.RoleResponsibility;
import org.kuali.rice.kim.api.role.RoleResponsibilityAction;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/13/13
 * Time: 2:38 PM
 */
public class EdoGroupServiceImpl implements EdoGroupService {

    static final Logger LOG = Logger.getLogger(EdoGroupService.class);

    public EdoGroup createGroup(String groupName, String kimTypeName) {

        EdoGroup grp = new EdoGroup();
        // check for group existence first
        String grpId = findGroupId(groupName);
        if (StringUtils.isEmpty(grpId)) {
            grp.setGroupName(groupName);
            grp.setKimTypeName(kimTypeName);
        } else {
            EdoGroupTracking grpEntry = EdoServiceLocator.getEdoGroupTrackingService().getEdoGroupTrackingByGroupName(groupName);
            grp.setGroupName(groupName);
            grp.setKimGroupId(grpId);
            grp.setKimTypeName(kimTypeName);
        }

        return grp;
    }

    public boolean addMemberToGroup(String principalId, String groupId) {
        boolean result = KimApiServiceLocator.getGroupService().addPrincipalToGroup(principalId, groupId);

        return result;
    }

    // e.g. buildGroupName( "LEVEL2", "APPROVE", "TENURE", "BL-VPIT")
    //      buildGroupName( "LEVEL8", "APPROVE", "", "")
    public String buildGroupName(String routeLevel, String workflowAction, String dossierReviewLevel, String unitDesignation) {
        String grpName;
        String cleanUnitId = unitDesignation.replace("-", "");

        grpName = EdoConstants.EDO_NAME_SPACE + "-" + routeLevel + "-" + workflowAction;

        if (StringUtils.isNotEmpty(dossierReviewLevel) ) {
            grpName = grpName + "-" + dossierReviewLevel;
        }
        if ( StringUtils.isNotEmpty(cleanUnitId) ) {
            grpName = grpName + "-" + cleanUnitId;
        }

        return grpName;
    }

    public List<EdoGroup> createKimGroups(String workflowId, String departmentId, String schoolId, String campusId, String institutionId) {
        EdoGroupService grpSvc = EdoServiceLocator.getEdoGroupService();
        EdoGroupDefinitionService grpDefSvc = EdoServiceLocator.getEdoGroupDefinitionService();

        List<EdoGroupDefinition> grpDefList = grpDefSvc.getEdoGroupDefinitionsByWorkflowId(EdoConstants.EDO_DEFAULT_WORKFLOW_ID);
        List<Group> kimGrpList = new LinkedList<Group>();
        List<EdoGroup> grpList = new LinkedList<EdoGroup>();


        if (CollectionUtils.isNotEmpty(grpDefList)) {
            for (EdoGroupDefinition grpDef : grpDefList) {
                if (grpDef.getKimTypeName().equalsIgnoreCase("department")) {
                    String grpName = grpSvc.buildGroupName(grpDef.getWorkflowLevel(), grpDef.getWorkflowType(), grpDef.getDossierType(), departmentId);
                    EdoGroup grp = grpSvc.createGroup(grpName, grpDef.getKimTypeName());
                    grp.setKimRoleName(grpDef.getKimRoleName());
                    grp.addGroupAttribute(EdoConstants.EDO_ATTRIBUTE_DEPARTMENT_ID, departmentId);
                    if (!EdoServiceLocator.getEdoGroupTrackingService().kimGroupTrackingExists(grpName)) {
                        if (!grp.isEmpty()) {
                            // if group exists in Rice, skip the Rice add and just update the group tracking table
                            Group kimGrp = KimApiServiceLocator.getGroupService().getGroupByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, grpName);
                            if (ObjectUtils.isNull(kimGrp)) {
                                kimGrp = grp.addGroup();
                                grpList.add(grp);
                            }
                            EdoGroupTracking grpTrk =  new EdoGroupTracking();
                            grpTrk.setCampusId(campusId);
                            grpTrk.setDepartmentId(departmentId);
                            grpTrk.setSchoolId(schoolId);
                            grpTrk.setReviewLevelName(grpDef.getWorkflowLevel());
                            grpTrk.setGroupName(grpName);
                            grpTrk.setDateAdded(new Date());
                            EdoServiceLocator.getEdoGroupTrackingService().saveOrUpdate(grpTrk);
                            kimGrpList.add(kimGrp);
                        }
                    }
                }
                if (grpDef.getKimTypeName().equalsIgnoreCase("school")) {
                    String grpName = grpSvc.buildGroupName(grpDef.getWorkflowLevel(), grpDef.getWorkflowType(), grpDef.getDossierType(), schoolId);
                    EdoGroup grp = grpSvc.createGroup(grpName, grpDef.getKimTypeName());
                    grp.setKimRoleName(grpDef.getKimRoleName());
                    grp.addGroupAttribute(EdoConstants.EDO_ATTRIBUTE_SCHOOL_ID, schoolId);
                    if (!EdoServiceLocator.getEdoGroupTrackingService().kimGroupTrackingExists(grpName)) {
                        if (!grp.isEmpty()) {
                            // if group exists in Rice, skip the Rice add and just update the group tracking table
                            Group kimGrp = KimApiServiceLocator.getGroupService().getGroupByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, grpName);
                            if (ObjectUtils.isNull(kimGrp)) {
                                kimGrp = grp.addGroup();
                                grpList.add(grp);
                            }
                            EdoGroupTracking grpTrk =  new EdoGroupTracking();
                            grpTrk.setCampusId(campusId);
                            //grpTrk.setDepartmentId("");
                            grpTrk.setSchoolId(schoolId);
                            grpTrk.setReviewLevelName(grpDef.getWorkflowLevel());
                            grpTrk.setGroupName(grpName);
                            grpTrk.setDateAdded(new Date());
                            EdoServiceLocator.getEdoGroupTrackingService().saveOrUpdate(grpTrk);

                            kimGrpList.add(kimGrp);
                        }
                    }
                }
                if (grpDef.getKimTypeName().equalsIgnoreCase("campus")) {
                    String grpName = grpSvc.buildGroupName(grpDef.getWorkflowLevel(), grpDef.getWorkflowType(), grpDef.getDossierType(), campusId);
                    EdoGroup grp = grpSvc.createGroup(grpName, grpDef.getKimTypeName());
                    grp.setKimRoleName(grpDef.getKimRoleName());
                    grp.addGroupAttribute(EdoConstants.EDO_ATTRIBUTE_CAMPUS_ID, campusId);
                    if (!EdoServiceLocator.getEdoGroupTrackingService().kimGroupTrackingExists(grpName)) {
                        if (!grp.isEmpty()) {
                            // if group exists in Rice, skip the Rice add and just update the group tracking table
                            Group kimGrp = KimApiServiceLocator.getGroupService().getGroupByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, grpName);
                            if (ObjectUtils.isNull(kimGrp)) {
                                kimGrp = grp.addGroup();
                                grpList.add(grp);
                            }
                            EdoGroupTracking grpTrk =  new EdoGroupTracking();
                            grpTrk.setCampusId(campusId);
                            //grpTrk.setDepartmentId("");
                            //grpTrk.setSchoolId("");
                            grpTrk.setReviewLevelName(grpDef.getWorkflowLevel());
                            grpTrk.setGroupName(grpName);
                            grpTrk.setDateAdded(new Date());
                            EdoServiceLocator.getEdoGroupTrackingService().saveOrUpdate(grpTrk);
                            kimGrpList.add(kimGrp);
                        }
                    }
                }
                if (grpDef.getKimTypeName().equalsIgnoreCase("institution")) {
                    String grpName = grpSvc.buildGroupName(grpDef.getWorkflowLevel(), grpDef.getWorkflowType(), "", "");
                    EdoGroup grp = grpSvc.createGroup(grpName, grpDef.getKimTypeName());
                    grp.setKimRoleName(grpDef.getKimRoleName());
                    grp.addGroupAttribute(EdoConstants.EDO_ATTRIBUTE_INSTITUTION_ID, institutionId);
                    if (!EdoServiceLocator.getEdoGroupTrackingService().kimGroupTrackingExists(grpName)) {
                        if (!grp.isEmpty()) {
                            // if group exists in Rice, skip the Rice add and just update the group tracking table
                            Group kimGrp = KimApiServiceLocator.getGroupService().getGroupByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, grpName);
                            if (ObjectUtils.isNull(kimGrp)) {
                                kimGrp = grp.addGroup();
                                grpList.add(grp);
                            }
                            EdoGroupTracking grpTrk =  new EdoGroupTracking();
                            //grpTrk.setCampusId("");
                            //grpTrk.setDepartmentId("");
                            //grpTrk.setSchoolId("");
                            grpTrk.setReviewLevelName(grpDef.getWorkflowLevel());
                            grpTrk.setGroupName(grpName);
                            grpTrk.setDateAdded(new Date());
                            EdoServiceLocator.getEdoGroupTrackingService().saveOrUpdate(grpTrk);
                            kimGrpList.add(kimGrp);
                        }
                    }
                }
            }

        }
        return grpList;
    }

    public void addGroupToRole(EdoGroup group) {

        // assign the group to a role
        RoleMember roleMember = KimApiServiceLocator.getRoleService().assignGroupToRole(group.getKimGroupId(), EdoConstants.EDO_NAME_SPACE, group.getKimRoleName(), group.getGrpAttributes());

        List<RoleResponsibility> kimRoleResponsibilities = KimApiServiceLocator.getRoleService().getRoleResponsibilities(roleMember.getRoleId());

        for (RoleResponsibility kimRoleResp : kimRoleResponsibilities) {
            Role kimRole = KimApiServiceLocator.getRoleService().getRole(kimRoleResp.getRoleId());
            Responsibility kimResponsibility = KimApiServiceLocator.getResponsibilityService().getResponsibility(kimRoleResp.getResponsibilityId());
            List<EdoRoleResponsibility> edoRoleResponsibilityList = EdoServiceLocator.getEdoRoleResponsibilityService().getEdoRoleResponsibilityByRoleAndResponsibility(kimRole.getName(),kimResponsibility.getName());

            for (EdoRoleResponsibility edoRoleResp : edoRoleResponsibilityList) {
                RoleResponsibilityAction.Builder roleRespActionBuilder = RoleResponsibilityAction.Builder.create();

                roleRespActionBuilder.setRoleResponsibility(kimRoleResp);
                roleRespActionBuilder.setRoleResponsibilityId(kimRoleResp.getRoleResponsibilityId());
                roleRespActionBuilder.setRoleMemberId(roleMember.getId());
                roleRespActionBuilder.setForceAction(BooleanUtils.toBoolean(edoRoleResp.getKimForceAction().intValue()));
                roleRespActionBuilder.setActionTypeCode(edoRoleResp.getKimActionTypeCode());
                roleRespActionBuilder.setActionPolicyCode(edoRoleResp.getKimActionPolicyCode());
                roleRespActionBuilder.setPriorityNumber(edoRoleResp.getKimPriority().intValue());
                RoleResponsibilityAction roleResponsibilityAction = roleRespActionBuilder.build();

                // and add it to the list
                RoleResponsibilityAction foo = KimApiServiceLocator.getRoleService().createRoleResponsibilityAction(roleResponsibilityAction);
            }
        }

        return;
    }

    public String findGroupId(String grpName) {
        String id = null;
        QueryByCriteria.Builder qb = QueryByCriteria.Builder.create();
        qb.setPredicates(equal("grp_nm", grpName));
        List<String> grps = KimApiServiceLocator.getGroupService().findGroupIds(qb.build());

        if (CollectionUtils.isNotEmpty(grps)) {
            LOG.info("Group [" + grpName + "] found");
            id = grps.get(0);
        }
        return id;
    }

    public List<String> findGroupMembers(String groupId) {
        List<String> members = new LinkedList<String>();
        List<GroupMember> kimMembers = new LinkedList<GroupMember>();

        kimMembers = KimApiServiceLocator.getGroupService().getMembersOfGroup(groupId);
        if (CollectionUtils.isNotEmpty(kimMembers)) {
            for (GroupMember mbr : kimMembers) {
                Principal principal = KimApiServiceLocator.getIdentityService().getPrincipal(mbr.getMemberId());
                if (principal == null) {
                    LOG.info("No principal with principal ID of " + mbr.getMemberId());
                    return members;
                }
                members.add(principal.getPrincipalId());
            }
        }
        return members;
    }

    public boolean isMemberOfGroup(String principalName, String groupName) {
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(principalName);
        if (ObjectUtils.isNull(principal)) {
            LOG.info("No such principal " + principalName);
            return false;
        }
        Group grp = KimApiServiceLocator.getGroupService().getGroupByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, groupName);
        if (ObjectUtils.isNull(grp)) {
            LOG.info("No such group " + groupName);
            return false;
        }
        return KimApiServiceLocator.getGroupService().isMemberOfGroup(principal.getPrincipalId(), grp.getId());
    }

}
