package org.kuali.kpme.edo.service;

//import java.sql.Date;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.kuali.kpme.edo.candidate.delegate.EdoCandidateDelegate;
import org.kuali.kpme.edo.candidate.delegate.EdoChairDelegate;
import org.kuali.kpme.edo.candidate.guest.EdoCandidateGuest;
import org.kuali.kpme.edo.dossier.EdoDossier;
import org.kuali.kpme.edo.dossier.type.EdoDossierType;
import org.kuali.kpme.edo.role.EDORole;
import org.kuali.kpme.edo.util.EdoConstants;
import org.kuali.kpme.edo.util.EdoContext;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.kim.api.common.delegate.DelegateMember;
import org.kuali.rice.kim.api.common.delegate.DelegateMember.Builder;
import org.kuali.rice.kim.api.common.delegate.DelegateType;
import org.kuali.rice.kim.api.group.GroupService;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.role.RoleMembership;
import org.kuali.rice.kim.api.role.RoleMembershipQueryResults;
import org.kuali.rice.kim.api.role.RoleService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.core.api.delegation.DelegationType;


import java.math.BigDecimal;
import java.util.*;


public class MaintenanceServiceImpl implements MaintenanceService {
    static final Logger LOG = Logger.getLogger(MaintenanceServiceImpl.class);

    //ADDED EDO CONSTANTS

    /*public boolean saveDelegateForCandidate(String emplId, String facultyId) {
         Map<String, String> qualifications = new HashMap<String, String>();
         //qualifications.put("edoRoleCandidateDelegateDossierId", facultyId);
         qualifications.put(EdoConstants.ROLE_CANDIDATE_DELEGATE_DOSSIER_ID, facultyId);
         //getRoleService().assignPrincipalToRole(emplId, "EDO", "EDO_CANDIDATE_DELEGATE", qualifications);
         getRoleService().assignPrincipalToRole(emplId, EdoConstants.EDO_NAME_SPACE, EdoConstants.CANDIDATE_DELEGATE_ROLE, qualifications);

         return true;

     }*/
    public boolean saveDelegateForCandidate(String emplId, Date startDate, Date endDate, String facultyId, String roleName) {
        Role candidateDelegateRole = getRoleService().getRoleByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, roleName);
        DateTime activeFrom = null;
        DateTime activeTo = null;

        if (startDate != null) {
            activeFrom = new DateTime(startDate.getTime());
        }

        if (endDate != null) {
            activeTo = new DateTime(endDate.getTime());
        }

        RoleMember member = RoleMember.Builder.create(candidateDelegateRole.getId(), null, emplId, MemberType.PRINCIPAL, activeFrom, activeTo, Collections.singletonMap("edoRoleCandidateDelegateDossierId", facultyId), null, null).build();
        RoleMember savedRoleMember = getRoleService().createRoleMember(member);

        if (ObjectUtils.isNotNull(savedRoleMember)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateDelegateForCandidate(String emplId, Date startDate, Date endDate, String facultyId, String roleName) {
        Role candidateDelegateRole = getRoleService().getRoleByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, roleName);
        DateTime activeFrom = null;
        DateTime activeTo = null;

        if (startDate != null) {
            activeFrom = new DateTime(startDate.getTime());
        }

        if (endDate != null) {
            activeTo = new DateTime(endDate.getTime());
        }

        RoleMember member = RoleMember.Builder.create(candidateDelegateRole.getId(), null, emplId, MemberType.PRINCIPAL, activeFrom, activeTo, Collections.singletonMap("edoRoleCandidateDelegateDossierId", facultyId), null, null).build();
        RoleMember updatedRoleMember = getRoleService().updateRoleMember(member);

        if (ObjectUtils.isNotNull(updatedRoleMember)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean expireAllDelegatesForCandidate(String facultyId) {

        Role candidateDelegateRole = getRoleService().getRoleByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, EDORole.EDO_CANDIDATE_DELEGATE.getEdoRole());
        List<String> existingDelegates = EdoServiceLocator.getEdoMaintenanceService().getCandidateDelegatesEmplIds(facultyId, EDORole.EDO_CANDIDATE_DELEGATE.getEdoRole());

        if (CollectionUtils.isNotEmpty(existingDelegates)) {
            for (String emplId : existingDelegates) {
                RoleMember member = RoleMember.Builder.create(candidateDelegateRole.getId(), null, emplId, MemberType.PRINCIPAL, null, new DateTime(), Collections.singletonMap("edoRoleCandidateDelegateDossierId", facultyId), null, null).build();
                RoleMember updatedRoleMember = getRoleService().updateRoleMember(member);
                if (ObjectUtils.isNull(updatedRoleMember)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean saveDelegateForChair(String emplId, Date startDate, Date endDate, String facultyId, String roleName) {
        Role candidateDelegateRole = getRoleService().getRoleByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, roleName);
        DateTime activeFrom = null;
        DateTime activeTo = null;

        if (startDate != null) {
            activeFrom = new DateTime(startDate.getTime());
        }

        if (endDate != null) {
            activeTo = new DateTime(endDate.getTime());
        }

        RoleMember member = RoleMember.Builder.create(candidateDelegateRole.getId(), null, emplId, MemberType.PRINCIPAL, activeFrom, activeTo, Collections.singletonMap(EdoConstants.ROLE_CHAIR_DELEGATE_ID, facultyId), null, null).build();
        RoleMember savedRoleMember = getRoleService().createRoleMember(member);

        if (ObjectUtils.isNotNull(savedRoleMember)) {
        	//also save that deleagte to delegation type
        	//first find the role of the faculty id
        	//and then add the emplid to the delegations 
        	List<String> groupIds = getGroupService().getGroupIdsByPrincipalIdAndNamespaceCode(facultyId,EdoConstants.EDO_NAME_SPACE);
        	//List<String> groupIds = getGroupService().getGroupIdsByPrincipalId("0001766187");
        	//we want a role for which the group is a member of
        	for(String groupId : groupIds) {
        	
        		QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        		//List<Predicate> pList = new ArrayList<Predicate>();
        		qBuilder.setPredicates( PredicateFactory.equal("mbr_id", groupId));
        		//this will fetch the roles for which the group is a member of
        		//RoleQueryResults roleQueryResults =  getRoleService().findRoles(qBuilder.build());
        		RoleMembershipQueryResults roleQueryResults = getRoleService().findRoleMemberships(qBuilder.build());
        		//get roles list from the role query result
        		List<RoleMembership> roles = roleQueryResults.getResults();
        		//loop through the roles
        		for(RoleMembership roleMembership : roles){
        		//get the role id for a role	
        		String roleId = roleMembership.getRoleId();
        		Role role = getRoleService().getRole(roleId);
        		DelegationType delegationType = DelegationType.PRIMARY;
        		DelegateType delegateType;
        		delegateType = getRoleService().getDelegateTypeByRoleIdAndDelegateTypeCode(roleId,delegationType);
        		if(ObjectUtils.isNull(delegateType)|| StringUtils.isBlank(delegateType.getDelegationId())) {
        			//create a delegation type
        		
        			List<Builder> members = null;
        			DelegateType.Builder delegateTypeCreate = DelegateType.Builder.create(roleId, delegationType, members);
        			
        			delegateTypeCreate.setKimTypeId(role.getKimTypeId());
        			delegateType = getRoleService().createDelegateType(delegateTypeCreate.build());
        		}
        		
                Builder delegateMember = DelegateMember.Builder.create();
		        	delegateMember.setActiveFromDate(activeFrom);
		        	delegateMember.setActiveToDate(activeTo);
		        	delegateMember.setRoleMemberId(roleMembership.getId());
		        	delegateMember.setMemberId(emplId); //look into this tomorrow
		        	delegateMember.setAttributes(roleMembership.getQualifier());
		        	delegateMember.setDelegationId(delegateType.getDelegationId());
		        	delegateMember.setType(MemberType.PRINCIPAL);
		        	
		        	//this will create the delegate member.
		        	getRoleService().createDelegateMember(delegateMember.build());
        			 

        	}
        }
            return true;
        } else {
            return false;
        }
    }

    public boolean saveGuestForDossierId(String emplid, Date startDate, Date endDate, BigDecimal dossierId) {
        Role candidateGuestRole = getRoleService().getRoleByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, EdoConstants.CANDIDATE_GUEST_ROLE);
        DateTime activeFrom = null;
        DateTime activeTo = null;

        if (startDate != null) {
            activeFrom = new DateTime(startDate.getTime());
        }

        if (endDate != null) {
            activeTo = new DateTime(endDate.getTime());
        }

        RoleMember member = RoleMember.Builder.create(candidateGuestRole.getId(), null, emplid, MemberType.PRINCIPAL, activeFrom, activeTo, Collections.singletonMap(EdoConstants.ROLE_GUEST_DOSSIER_ID, dossierId.toString()), null, null).build();
        RoleMember savedRoleMember = getRoleService().createRoleMember(member);

        if (ObjectUtils.isNotNull(savedRoleMember)) {
            return true;
        }

        return false;
    }


    public boolean deleteCandidateDelegate(String delegateId, String candidateId, String roleName) {
        LOG.debug("delegateId = " + delegateId + "candidateId = " + candidateId);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(delegateId);
        if (ObjectUtils.isNotNull(principal)) {
            String candidateDeleagteId = principal.getPrincipalId();
            Map<String, String> qualifications = new HashMap<String, String>();
            qualifications.put(EdoConstants.ROLE_CANDIDATE_DELEGATE_DOSSIER_ID, candidateId);
            getRoleService().removePrincipalFromRole(candidateDeleagteId, EdoConstants.EDO_NAME_SPACE, roleName, qualifications);

            return true;
        }

        return false;
    }
    public boolean deleteChairDelegate(String emplId, String facultyId, String roleName) {
        LOG.debug("delegateId = " + emplId + "candidateId = " + facultyId);
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(emplId);
        if (ObjectUtils.isNotNull(principal)) {
            String chairDeleagtePrincipalId = principal.getPrincipalId();
            Map<String, String> qualifications = new HashMap<String, String>();
            qualifications.put(EdoConstants.ROLE_CHAIR_DELEGATE_ID, facultyId);
            getRoleService().removePrincipalFromRole(chairDeleagtePrincipalId, EdoConstants.EDO_NAME_SPACE, roleName, qualifications);
            
            	List<String> groupIds = getGroupService().getGroupIdsByPrincipalIdAndNamespaceCode(facultyId,EdoConstants.EDO_NAME_SPACE);
            	
            	for(String groupId : groupIds) {
            	
            		QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
            		qBuilder.setPredicates( PredicateFactory.equal("mbr_id", groupId));
            		RoleMembershipQueryResults roleQueryResults = getRoleService().findRoleMemberships(qBuilder.build());
            		List<RoleMembership> roles = roleQueryResults.getResults();
            		//loop through the roles
            		for(RoleMembership roleMembership : roles){
            		//get the role id for a role	
            		String roleId = roleMembership.getRoleId();
            		Role role = getRoleService().getRole(roleId);
            		DelegateType delegation = getRoleService().getDelegateTypeByRoleIdAndDelegateTypeCode(roleId, DelegationType.PRIMARY);
            		List<DelegateMember> delegationMembers = getRoleService().getDelegationMembersByDelegationId(delegation.getDelegationId());
            		for(DelegateMember delegationMember : delegationMembers) {
            		    if(delegationMember.getType().equals(MemberType.PRINCIPAL) && StringUtils.equals(delegationMember.getMemberId(), chairDeleagtePrincipalId)) {
            		        getRoleService().removeDelegateMembers(Collections.singletonList(delegationMember));
            		    }
            		}

            			 
            		}
            	}
            	return true;
        }
        else {
        	return false;
        }
            		}

    public boolean deleteGuestForCandidateDossier(String guestId, String dossierId) {
        Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(guestId);
        if (ObjectUtils.isNotNull(principal)) {
            String candidateGuestId = principal.getPrincipalId();
            Map<String, String> qualifications = new HashMap<String, String>();
            qualifications.put(EdoConstants.ROLE_GUEST_DOSSIER_ID, dossierId);
            getRoleService().removePrincipalFromRole(candidateGuestId, EdoConstants.EDO_NAME_SPACE, EdoConstants.CANDIDATE_GUEST_ROLE, qualifications);

            return true;
        }

        return false;
    }


    public Map<String, String> getCandidateDelegates(String emplid, String roleName) {
        Collection<String> delegates = new ArrayList<String>();
        List<String> candidateDelegates = new ArrayList<String>();
        HashMap<String, String> candidateDelegatesNames = new HashMap<String, String>();
        HashMap<String, String> qualifications = new HashMap<String, String>();
        qualifications.put(EdoConstants.ROLE_CANDIDATE_DELEGATE_DOSSIER_ID, emplid);
        delegates = getRoleService().getRoleMemberPrincipalIds(EdoConstants.EDO_NAME_SPACE, roleName, qualifications);

        Iterator<String> collectionIterator = delegates.iterator();
        while (collectionIterator.hasNext()) {
            candidateDelegates.add(collectionIterator.next());
        }

        for (String candidateDeleagteId : candidateDelegates) {
            EntityNamePrincipalName name = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(candidateDeleagteId);
            if (ObjectUtils.isNotNull(name) && ObjectUtils.isNotNull(name.getDefaultName()) && ObjectUtils.isNotNull(name.getDefaultName().getCompositeName())) {
                candidateDelegatesNames.put(name.getPrincipalName(), name.getDefaultName().getCompositeName());
            }
        }

        return candidateDelegatesNames;
    }


    //created a new candidate Delegate object
    public List<EdoCandidateDelegate> getCandidateDelegateInfo(String emplid, String roleName) {

        List<RoleMembership> candidateDelegates = new ArrayList<RoleMembership>();
        List<EdoCandidateDelegate> candidateDelegatesList = new ArrayList<EdoCandidateDelegate>();
        HashMap<String, String> qualifications = new HashMap<String, String>();
        qualifications.put(EdoConstants.ROLE_CANDIDATE_DELEGATE_DOSSIER_ID, emplid);
        List<String> roleIds = new ArrayList<String>();
        if (StringUtils.isEmpty(roleName)) {
            return candidateDelegatesList;
        }

        String roleId = getRoleService().getRoleIdByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, roleName);
        if (StringUtils.isNotBlank(roleId)) {
            roleIds.add(roleId);
            candidateDelegates = getRoleService().getRoleMembers(roleIds, qualifications);
        }

        for (RoleMembership edoCandidateDelegate : candidateDelegates) {
            EdoCandidateDelegate candidateDelegate = new EdoCandidateDelegate();
            EntityNamePrincipalName name = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(edoCandidateDelegate.getMemberId());
            candidateDelegate.setDelegatePrincipalId(edoCandidateDelegate.getMemberId());
            if (ObjectUtils.isNotNull(name) && ObjectUtils.isNotNull(name.getDefaultName()) && ObjectUtils.isNotNull(name.getDefaultName().getCompositeName())) {
                candidateDelegate.setDelegatePrincipalName(name.getPrincipalName());
                candidateDelegate.setDelegateFullName(name.getDefaultName().getCompositeName());
            }
            //candidateDelegate.setStartDate(edoCandidateDelegate.getActiveFromDate());
            //candidateDelegate.setEndDate(edoCandidateDelegate.getActiveToDate());
            candidateDelegatesList.add(candidateDelegate);
        }

        return candidateDelegatesList;
    }

    //created a new chair Delegate object
    public List<EdoChairDelegate> getChairDelegateInfo(String emplid, String roleName) {

        List<RoleMembership> chairDelegates = new ArrayList<RoleMembership>();
        List<EdoChairDelegate> chairDelegatesList = new ArrayList<EdoChairDelegate>();
        HashMap<String, String> qualifications = new HashMap<String, String>();
        qualifications.put(EdoConstants.ROLE_CHAIR_DELEGATE_ID, emplid);
        List<String> roleIds = new ArrayList<String>();
        if (StringUtils.isEmpty(roleName)) {
            return chairDelegatesList;
        }

        String roleId = getRoleService().getRoleIdByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, roleName);
        if (StringUtils.isNotBlank(roleId)) {
            roleIds.add(roleId);
            chairDelegates = getRoleService().getRoleMembers(roleIds, qualifications);
        }

        for (RoleMembership edoChairDelegate : chairDelegates) {
            EdoChairDelegate chairDelegate = new EdoChairDelegate();
            EntityNamePrincipalName name = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(edoChairDelegate.getMemberId());
            chairDelegate.setDelegatePrincipalId(edoChairDelegate.getMemberId());
            if (ObjectUtils.isNotNull(name) && ObjectUtils.isNotNull(name.getDefaultName()) && ObjectUtils.isNotNull(name.getDefaultName().getCompositeName())) {
                chairDelegate.setDelegatePrincipalName(name.getPrincipalName());
                chairDelegate.setDelegateFullName(name.getDefaultName().getCompositeName());
            }
            //chairDelegate.setStartDate(edoChairDelegate.getActiveFromDate());
            //chairDelegate.setEndDate(edoChairDelegate.getActiveToDate());
            chairDelegatesList.add(chairDelegate);
        }

        return chairDelegatesList;
    }
  
    public List<EdoCandidateGuest> getCandidateGuests(String userName) {
        //first get the dossier list of the candidate
        List<RoleMembership> guests = new ArrayList<RoleMembership>();
        List<EdoCandidateGuest> candidateGuestsList = new ArrayList<EdoCandidateGuest>();
        List<EdoDossier> edossierList = EdoServiceLocator.getEdoDossierService().getDossierListByUserName(userName);
        //loop through the dossierlist and get the app guest for dossier

        for (EdoDossier edossier : edossierList) {
            List<RoleMembership> candidateGuests = new ArrayList<RoleMembership>();
            HashMap<String, String> qualifications = new HashMap<String, String>();
            qualifications.put(EdoConstants.ROLE_GUEST_DOSSIER_ID, edossier.getDossierID().toString());
            List<String> roleIds = new ArrayList<String>();
            String roleId = getRoleService().getRoleIdByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, EdoConstants.CANDIDATE_GUEST_ROLE);
            if (StringUtils.isNotBlank(roleId)) {
                roleIds.add(roleId);
                candidateGuests = getRoleService().getRoleMembers(roleIds, qualifications);
                //add this to a super list
                guests.addAll(candidateGuests);
            }
        }

        for (RoleMembership edoCandidateGuest : guests) {
            EdoCandidateGuest candidateGuest = new EdoCandidateGuest();
            EntityNamePrincipalName name = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(edoCandidateGuest.getMemberId());
            candidateGuest.setGuestPrincipalId(edoCandidateGuest.getMemberId());
            if (ObjectUtils.isNotNull(name) && ObjectUtils.isNotNull(name.getDefaultName()) && ObjectUtils.isNotNull(name.getDefaultName().getCompositeName())) {
                candidateGuest.setGuestPrincipalName(name.getPrincipalName());
                candidateGuest.setGuestFullName(name.getDefaultName().getCompositeName());
            }
            //candidateGuest.setStartDate(edoCandidateGuest.getActiveFromDate());
            //candidateGuest.setEndDate(edoCandidateGuest.getActiveToDate());
            candidateGuest.setGuestDossierId(edoCandidateGuest.getQualifier());
            candidateGuest.getGuestDossierId().putAll(edoCandidateGuest.getQualifier());
            //make a call to edo dossier table to fecth dossier and then get dossier type
            EdoDossier edossier = EdoServiceLocator.getEdoDossierService().getDossierByDossierId(candidateGuest.getGuestDossierId().get(EdoConstants.ROLE_GUEST_DOSSIER_ID));
            if (edossier != null) {
                candidateGuest.setDossierStatus(edossier.getDossierStatus());
                //now fetch the dossier type
                EdoDossierType edossierType = EdoServiceLocator.getEdoDossierTypeService().getEdoDossierType(edossier.getDossierTypeID());
                candidateGuest.setDossierType(edossierType.getDossierTypeName());
            }
            candidateGuestsList.add(candidateGuest);
        }

        return candidateGuestsList;
    }

    public List<String> getCandidateDelegatesEmplIds(String emplid, String roleName) {
        Collection<String> delegates = new ArrayList<String>();
        List<String> candidateDelegates = new ArrayList<String>();
        HashMap<String, String> qualifications = new HashMap<String, String>();

        //	qualifications.put("edoRoleCandidateDelegateDossierId", emplid);
        qualifications.put(EdoConstants.ROLE_CANDIDATE_DELEGATE_DOSSIER_ID, emplid);

        //	delegates = getRoleService().getRoleMemberPrincipalIds("EDO", "EDO_CANDIDATE_DELEGATE", qualifications);
        delegates = getRoleService().getRoleMemberPrincipalIds(EdoConstants.EDO_NAME_SPACE, roleName, qualifications);

        Iterator<String> collectionIterator = delegates.iterator();
        while (collectionIterator.hasNext()) {
            candidateDelegates.add(collectionIterator.next());
        }

        return candidateDelegates;


    }
    //check to see the existing delegates for the logged in chair
    public List<String> getChairDelegatesEmplIds(String emplid, String roleName) {
        Collection<String> delegates = new ArrayList<String>();
        List<String> chairDelegates = new ArrayList<String>();
        HashMap<String, String> qualifications = new HashMap<String, String>();
        qualifications.put(EdoConstants.ROLE_CHAIR_DELEGATE_ID, emplid);
        delegates = getRoleService().getRoleMemberPrincipalIds(EdoConstants.EDO_NAME_SPACE, roleName, qualifications);

        Iterator<String> collectionIterator = delegates.iterator();
        while (collectionIterator.hasNext()) {
        	chairDelegates.add(collectionIterator.next());
        }

        return chairDelegates;


    }
    //check to see the existing candidate guests

    public List<String> getCandidateExistingGuests(BigDecimal dossierId) {
        Collection<String> guests = new ArrayList<String>();
        List<String> candidateGuests = new ArrayList<String>();
        HashMap<String, String> qualifications = new HashMap<String, String>();
        if (ObjectUtils.isNotNull(dossierId)) {
            qualifications.put(EdoConstants.ROLE_GUEST_DOSSIER_ID, dossierId.toString());
            guests = getRoleService().getRoleMemberPrincipalIds(EdoConstants.EDO_NAME_SPACE, EdoConstants.CANDIDATE_GUEST_ROLE, qualifications);

            Iterator<String> collectionIterator = guests.iterator();
            while (collectionIterator.hasNext()) {
                candidateGuests.add(collectionIterator.next());
            }
        }
        return candidateGuests;
    }

    public List<String> getDelegatesCandidateList(String delegateEmplid) {
        List<String> roleIds = new ArrayList<String>();
        List<String> candidatesList = new ArrayList<String>();
        String roleId = getRoleService().getRoleIdByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, EdoConstants.CANDIDATE_DELEGATE_ROLE);
        if (StringUtils.isNotBlank(roleId)) {
            roleIds.add(roleId);
            List<Map<String, String>> lstAttrSet = getRoleService().getRoleQualifersForPrincipalByRoleIds(delegateEmplid, roleIds, new HashMap<String, String>());
            for (Map<String, String> attrSet : lstAttrSet) {
                String facultyIdsList = (String) attrSet.get(EdoConstants.ROLE_CANDIDATE_DELEGATE_DOSSIER_ID);
                if (StringUtils.isNotBlank(facultyIdsList)) {
                    String[] faculties = StringUtils.split(facultyIdsList, ",");
                    for (String faculty : faculties) {
                        if (StringUtils.isNotBlank(faculty)) {
                            candidatesList.add(faculty);
                        }
                    }
                }
            }
        }
        return candidatesList;
    }
    // get the committee chair list for whom the logged in user is a delegate
    public List<String> getChairListForDelegate(String chairDelegateEmplid) {
        List<String> roleIds = new ArrayList<String>();
        List<String> chairList = new ArrayList<String>();
        String roleId = getRoleService().getRoleIdByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, EdoConstants.CHAIR_DELEGATE_ROLE);
        if (StringUtils.isNotBlank(roleId)) {
            roleIds.add(roleId);
            List<Map<String, String>> lstAttrSet = getRoleService().getRoleQualifersForPrincipalByRoleIds(chairDelegateEmplid, roleIds, new HashMap<String, String>());
            for (Map<String, String> attrSet : lstAttrSet) {
                String facultyIdsList = (String) attrSet.get(EdoConstants.ROLE_CHAIR_DELEGATE_ID);
                if (StringUtils.isNotBlank(facultyIdsList)) {
                    String[] faculties = StringUtils.split(facultyIdsList, ",");
                    for (String faculty : faculties) {
                        if (StringUtils.isNotBlank(faculty)) {
                        	chairList.add(faculty);
                        }
                    }
                }
            }
        }
        return chairList;
    }

    //guest role - eDossiers
    public List<String> getGuestDossierList(String guestEmplid) {
        List<String> roleIds = new ArrayList<String>();
        List<String> guestsList = new ArrayList<String>();
        String roleId = getRoleService().getRoleIdByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, EdoConstants.CANDIDATE_GUEST_ROLE);
        if (StringUtils.isNotBlank(roleId)) {
            roleIds.add(roleId);
            List<Map<String, String>> lstAttrSet = getRoleService().getRoleQualifersForPrincipalByRoleIds(guestEmplid, roleIds, new HashMap<String, String>());
            for (Map<String, String> attrSet : lstAttrSet) {
                String facultyIdsList = (String) attrSet.get(EdoConstants.ROLE_GUEST_DOSSIER_ID);
                if (StringUtils.isNotBlank(facultyIdsList)) {
                    String[] faculties = StringUtils.split(facultyIdsList, ",");
                    for (String faculty : faculties) {
                        if (StringUtils.isNotBlank(faculty)) {
                            guestsList.add(faculty);
                        }
                    }
                }
            }
        }
        return guestsList;
    }
    //second unit reviewer role - eDossiers
    public List<String> getSecondUnitReviewerDossierList(String secondUnitRevEmplid) {
        List<String> roleIds = new ArrayList<String>();
        List<String> secondUnitRevList = new ArrayList<String>();
        String roleId = getRoleService().getRoleIdByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, EdoConstants.SECOND_UNIT_REVIEWER_ROLE);
        if (StringUtils.isNotBlank(roleId)) {
            roleIds.add(roleId);
            List<Map<String, String>> lstAttrSet = getRoleService().getRoleQualifersForPrincipalByRoleIds(secondUnitRevEmplid, roleIds, new HashMap<String, String>());
            for (Map<String, String> attrSet : lstAttrSet) {
                String facultyIdsList = (String) attrSet.get(EdoConstants.ROLE_SECOND_UNIT_REVIEWER_DOSSIER_ID);
                if (StringUtils.isNotBlank(facultyIdsList)) {
                    String[] faculties = StringUtils.split(facultyIdsList, ",");
                    for (String faculty : faculties) {
                        if (StringUtils.isNotBlank(faculty)) {
                        	secondUnitRevList.add(faculty);
                        }
                    }
                }
            }
        }
        return secondUnitRevList;
    }
    //i have to debug this one
    public List<String> getCandidateSecondaryUnitReviewers(BigDecimal dossierId) {
        Collection<String> secUnitReviewers = new ArrayList<String>();
        List<String> candidateSecUnitRew = new ArrayList<String>();
        HashMap<String, String> qualifications = new HashMap<String, String>();
        if (ObjectUtils.isNotNull(dossierId)) {
            qualifications.put(EdoConstants.ROLE_SECOND_UNIT_REVIEWER_DOSSIER_ID, dossierId.toString());
            secUnitReviewers = getRoleService().getRoleMemberPrincipalIds(EdoConstants.EDO_NAME_SPACE, EdoConstants.SECOND_UNIT_REVIEWER_ROLE, qualifications);

            Iterator<String> collectionIterator = secUnitReviewers.iterator();
            while (collectionIterator.hasNext()) {
            	candidateSecUnitRew.add(collectionIterator.next());
            }
        }
        return candidateSecUnitRew;
    }
    
       /*    public List<String> getCandidateSecondaryUnitReviewers(String userName) {
        //first get the dossier list of the candidate
        List<String> secondaryUnitReviewers = new ArrayList<String>();
        List<EdoDossier> edossierList = EdoServiceLocator.getEdoDossierService().getDossierListByUserName(userName);
        //loop through the dossierlist and get the app guest for dossier

        for (EdoDossier edossier : edossierList) {
            List<RoleMembership> candidateSecondaryUnitReviewers = new ArrayList<RoleMembership>();
            HashMap<String, String> qualifications = new HashMap<String, String>();
            qualifications.put(EdoConstants.ROLE_SECOND_UNIT_REVIEWER_DOSSIER_ID, edossier.getDossierID().toString());
            List<String> roleIds = new ArrayList<String>();
            String roleId = getRoleService().getRoleIdByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, EdoConstants.SECOND_UNIT_REVIEWER_ROLE);
            if (StringUtils.isNotBlank(roleId)) {
                roleIds.add(roleId);
                candidateSecondaryUnitReviewers = getRoleService().getRoleMembers(roleIds, qualifications);
                for(RoleMembership canSecUntRev : candidateSecondaryUnitReviewers) {
               // Principal principal = KimApiServiceLocator.getIdentityService().getPrincipalByPrincipalName(emplId);
                	secondaryUnitReviewers.add(canSecUntRev.getMemberId());
                }
            }
        }

       

        return secondaryUnitReviewers;
    }*/
    public boolean hasSuperUserRole_W(String emplid) {
    	 boolean hasRole = false;
    	 List<String> allPrincipals = EdoContext.getPrincipalDelegators(emplid);

         for (String principal : allPrincipals) {
        	 hasRole = hasRole || hasSuperUserRole(principal);
         }
         return hasRole;
    }

    public boolean hasSuperUserRole(String emplid) {
        List<String> principalIds = new LinkedList<String>();

        principalIds.addAll(KimApiServiceLocator.getRoleService().getRoleMemberPrincipalIds(EdoConstants.EDO_NAME_SPACE, EDORole.EDO_SUPERUSER.getEdoRole(), new HashMap<String, String>()));

        return principalIds.contains(emplid);
    }
    public boolean hasCandidateRole_W(String emplid) {
    	 boolean hasRole = false;
    	 List<String> allPrincipals = EdoContext.getPrincipalDelegators(emplid);

         for (String principal : allPrincipals) {
        	 hasRole = hasRole || hasCandidateRole(principal);
         }
         return hasRole;
    	
    }

    public boolean hasCandidateRole(String emplid) {
        List<String> candidateIds = new LinkedList<String>();

        candidateIds.addAll(KimApiServiceLocator.getRoleService().getRoleMemberPrincipalIds(EdoConstants.EDO_NAME_SPACE, EDORole.EDO_CANDIDATE.getEdoRole(), new HashMap<String, String>()));

        return candidateIds.contains(emplid);
    }
    
    public boolean hasChairRole_W(String emplid) {
    	 boolean hasRole = false;
    	 List<String> allPrincipals = EdoContext.getPrincipalDelegators(emplid);

         for (String principal : allPrincipals) {
        	 hasRole = hasRole || hasChairRole(principal);
         }
         return hasRole;
    }

    public boolean hasChairRole(String emplid) {

        boolean hasRole = false;
        /*
        List<String> principalIds = new LinkedList<String>();
        List<String> chairRoles = new ArrayList<String>();
        List<String> chairRoleIds = new ArrayList<String>();

        // get all CHAIR roles
        EDORole[] allRoles = EDORole.values();
        for (EDORole role : allRoles) {
            if (role.getEdoRole().contains("COMMITTEE_CHAIR") || role.getEdoRole().contains("APPROVE") ) {
                chairRoles.add(role.getEdoRole());
            }
        }
        // translate all CHAIR rolenames to role IDs
        for (String roleName : chairRoles) {
            String roleId = getRoleService().getRoleIdByNamespaceCodeAndName(EdoConstants.EDO_NAME_SPACE, roleName);
            if (StringUtils.isNotEmpty(roleId)) {
                chairRoleIds.add(roleId);
            }
        }

        List<RoleMembership> roleMemberships = KimApiServiceLocator.getRoleService().getRoleMembers(chairRoleIds, new HashMap<String, String>());
        for (RoleMembership roleMembership : roleMemberships ) {
            principalIds.add(roleMembership.getMemberId());
        }

        return principalIds.contains(emplid);  */

        if (EdoServiceLocator.getAuthorizationService().isAuthorizedToDelegateChair(emplid)) {
            hasRole = true;
        }
        return hasRole;
    }


    private RoleService getRoleService() {
        return KimApiServiceLocator.getRoleService();
    }
    
    private GroupService getGroupService() {
    	return KimApiServiceLocator.getGroupService();
    }
    
}
