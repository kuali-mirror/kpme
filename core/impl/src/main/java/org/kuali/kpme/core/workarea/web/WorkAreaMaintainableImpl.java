/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.workarea.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.kpme.core.api.namespace.KPMENamespace;
import org.kuali.kpme.core.api.position.PositionBaseContract;
import org.kuali.kpme.core.api.task.Task;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.role.KPMERole;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.role.PositionRoleMemberBo;
import org.kuali.kpme.core.role.PrincipalRoleMemberBo;
import org.kuali.kpme.core.role.workarea.WorkAreaPositionRoleMemberBo;
import org.kuali.kpme.core.role.workarea.WorkAreaPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.service.HrServiceLocatorInternal;
import org.kuali.kpme.core.task.TaskBo;
import org.kuali.kpme.core.workarea.WorkAreaBo;
import org.kuali.rice.core.api.membership.MemberType;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;
/*import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.Maintainable;*/
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

@SuppressWarnings("deprecation")
public class WorkAreaMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = -624127817308880466L;

	@Override
    public HrBusinessObject getObjectById(String id) {
        return HrServiceLocatorInternal.getWorkAreaInternalService().getWorkArea(id);
    }
    
	/*@Override
	@SuppressWarnings("rawtypes")
    public List getSections(MaintenanceDocument document, Maintainable oldMaintainable) {
        List sections = super.getSections(document, oldMaintainable);
        
        for (Object obj : sections) {
            Section sec = (Section) obj;
            if (sec.getSectionId().equals("inactivePrincipalRoleMembers") || sec.getSectionId().equals("inactivePositionRoleMembers")) {
            	sec.setHidden(!document.isOldBusinessObjectInDocument());
            }
        }
        
        return sections;
    }*/
    
    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> parameters) {
        WorkAreaBo workArea = (WorkAreaBo) this.getBusinessObject();
        
        if (workArea.getWorkArea() == null) {
            workArea.setWorkArea(HrServiceLocator.getWorkAreaService().getNextWorkAreaKey());
        }
        
        super.processAfterNew(document, parameters);
    }
    
    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> requestParameters) {
        WorkAreaBo oldMaintainableObject = (WorkAreaBo) document.getOldMaintainableObject().getDataObject();
        WorkAreaBo newMaintainableObject = (WorkAreaBo) document.getNewMaintainableObject().getDataObject();
        
        WorkAreaBo oldWorkArea = oldMaintainableObject;
        if(StringUtils.isNotBlank(oldMaintainableObject.getTkWorkAreaId())) {
        	oldWorkArea = HrServiceLocatorInternal.getWorkAreaInternalService().getWorkArea(oldMaintainableObject.getTkWorkAreaId());
        } else {
        	oldWorkArea = HrServiceLocatorInternal.getWorkAreaInternalService().getWorkArea(oldMaintainableObject.getWorkArea(), oldMaintainableObject.getEffectiveLocalDate());
        }
        //KPME-3312: reinitiate all collection lists so old and new collections are unique
        List<TaskBo> oldTasks = new ArrayList<TaskBo>();
        oldTasks.addAll(oldWorkArea.getTasks());
        oldMaintainableObject.setTasks(oldTasks);

        List<WorkAreaPrincipalRoleMemberBo> oldPrinicipalMembers = new ArrayList<WorkAreaPrincipalRoleMemberBo>();
        oldPrinicipalMembers.addAll(oldWorkArea.getPrincipalRoleMembers());
        oldMaintainableObject.setPrincipalRoleMembers(oldPrinicipalMembers);

        List<WorkAreaPrincipalRoleMemberBo> oldInactivePrincipalMembers = new ArrayList<WorkAreaPrincipalRoleMemberBo>();
        oldInactivePrincipalMembers.addAll(oldWorkArea.getInactivePrincipalRoleMembers());
        oldMaintainableObject.setInactivePrincipalRoleMembers(oldInactivePrincipalMembers);
        
        List<WorkAreaPositionRoleMemberBo> oldPositionMembers = new ArrayList<WorkAreaPositionRoleMemberBo>();
        oldPositionMembers.addAll(oldWorkArea.getPositionRoleMembers());
        oldMaintainableObject.setPositionRoleMembers(oldPositionMembers);
        
        List<WorkAreaPositionRoleMemberBo> oldInactivePositionMembers = new ArrayList<WorkAreaPositionRoleMemberBo>();
        oldInactivePositionMembers.addAll(oldWorkArea.getInactivePositionRoleMembers());
        oldMaintainableObject.setInactivePositionRoleMembers(oldInactivePositionMembers);
        
        WorkAreaBo newWorkArea = newMaintainableObject;
        if(StringUtils.isNotBlank(newMaintainableObject.getTkWorkAreaId())) {
        	newWorkArea = HrServiceLocatorInternal.getWorkAreaInternalService().getWorkArea(newMaintainableObject.getTkWorkAreaId());
        } else {
        	newWorkArea = HrServiceLocatorInternal.getWorkAreaInternalService().getWorkArea(newMaintainableObject.getWorkArea(), newMaintainableObject.getEffectiveLocalDate());
        }
        List<TaskBo> newTasks = new ArrayList<TaskBo>();
        newTasks.addAll(newWorkArea.getTasks());
        newMaintainableObject.setTasks(newTasks);

        List<WorkAreaPrincipalRoleMemberBo> newPrinicipalMembers = new ArrayList<WorkAreaPrincipalRoleMemberBo>();
        newPrinicipalMembers.addAll(newWorkArea.getPrincipalRoleMembers());
        newMaintainableObject.setPrincipalRoleMembers(newPrinicipalMembers);

        List<WorkAreaPrincipalRoleMemberBo> newInactivePrincipalMembers = new ArrayList<WorkAreaPrincipalRoleMemberBo>();
        newInactivePrincipalMembers.addAll(newWorkArea.getInactivePrincipalRoleMembers());
        newMaintainableObject.setInactivePrincipalRoleMembers(newInactivePrincipalMembers);

        List<WorkAreaPositionRoleMemberBo> newPositionMembers = new ArrayList<WorkAreaPositionRoleMemberBo>();
        newPositionMembers.addAll(newWorkArea.getPositionRoleMembers());
        newMaintainableObject.setPositionRoleMembers(newPositionMembers);

        List<WorkAreaPositionRoleMemberBo> newInactivePositionMembers = new ArrayList<WorkAreaPositionRoleMemberBo>();
        newInactivePositionMembers.addAll(newWorkArea.getInactivePositionRoleMembers());
        newMaintainableObject.setInactivePositionRoleMembers(newInactivePositionMembers);
        
        super.processAfterEdit(document, requestParameters);
    }

    @Override
    protected void setNewCollectionLineDefaultValues(String collectionName, PersistableBusinessObject addLine) {
    	WorkAreaBo workArea = (WorkAreaBo) getBusinessObject();
        if (workArea.getEffectiveDate() != null) {
	        if (addLine instanceof TaskBo) {
	            TaskBo task = (TaskBo) addLine;
	            task.setEffectiveDate(workArea.getEffectiveDate());
	        } else if (addLine instanceof RoleMemberBo) {
	        	RoleMemberBo roleMember = (RoleMemberBo) addLine;
	        	roleMember.setActiveFromDateValue(new Timestamp(workArea.getEffectiveDate().getTime()));
	        }
        }

        super.setNewCollectionLineDefaultValues(collectionName, addLine);
    }

    @Override
    public void addNewLineToCollection(String collectionName) {
    	WorkAreaBo workArea = (WorkAreaBo) getBusinessObject();
    	
		if (collectionName.equals("principalRoleMembers")) {
			PrincipalRoleMemberBo roleMember = (PrincipalRoleMemberBo) newCollectionLines.get(collectionName);
            if (roleMember != null) {
            	if (!StringUtils.isEmpty(roleMember.getPrincipalId())) {
            		Principal person = KimApiServiceLocator.getIdentityService().getPrincipal(roleMember.getPrincipalId());
            		if (person == null) {
            			GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"principalRoleMembers", 
                				"error.role.person.notexist", roleMember.getMemberId());
                		return;
            		}
            	}
            }
        } else if (collectionName.equals("positionRoleMembers")) {
        	PositionRoleMemberBo roleMember = (PositionRoleMemberBo) newCollectionLines.get(collectionName);
            if (roleMember != null) {
            	if (!StringUtils.isEmpty(roleMember.getPositionNumber())) {
            		PositionBaseContract position = HrServiceLocator.getPositionService().getPosition(roleMember.getPositionNumber(), workArea.getEffectiveLocalDate());
            		if (position == null) {
            			GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"positionRoleMembers", 
                				"error.role.positionNumber.notexist", roleMember.getPositionNumber());
                		return;
            		}
            	}
            }
        }
		
        super.addNewLineToCollection(collectionName);
    }

    @Override
    public void customSaveLogic(HrBusinessObject hrObj) {
        WorkAreaBo workArea = (WorkAreaBo) hrObj;
        
        saveTasks(workArea);
        saveRoleMembers(workArea);
    }
    
    private void saveTasks(WorkAreaBo workArea) {
        List<TaskBo> tasks = workArea.getTasks();
        
        for (TaskBo task : tasks) {
            task.setTkTaskId(null);
            task.setTimestamp(new Timestamp(System.currentTimeMillis()));
            task.setWorkArea(workArea.getWorkArea());
        }
        workArea.setTasks(tasks);


        HrServiceLocator.getTaskService().saveTasks(ModelObjectUtils.transform(tasks, TaskBo.toTask));
    }
    
    private void saveRoleMembers(WorkAreaBo workArea) {
    	List<WorkAreaPrincipalRoleMemberBo> newInactivePrincipalRoleMembers = createInactivePrincipalRoleMembers(workArea.getWorkArea(), workArea.getPrincipalRoleMembers());
    	List<WorkAreaPositionRoleMemberBo> newInactivePositionRoleMembers = createInactivePositionRoleMembers(workArea.getWorkArea(), workArea.getPositionRoleMembers());
          	
    	List<WorkAreaPrincipalRoleMemberBo> principalRoleList = new ArrayList<WorkAreaPrincipalRoleMemberBo> ();
    	principalRoleList.addAll(workArea.getPrincipalRoleMembers());
    	
    	for (WorkAreaPrincipalRoleMemberBo newInactivePrincipalRoleMember : newInactivePrincipalRoleMembers) {
    		workArea.addInactivePrincipalRoleMember(newInactivePrincipalRoleMember);
    		List<WorkAreaPrincipalRoleMemberBo> tempRoleList = workArea.getPrincipalRoleMembers();
    		for(WorkAreaPrincipalRoleMemberBo role : tempRoleList) {
    			if(StringUtils.isNotEmpty(role.getId())
    					&& StringUtils.isNotEmpty(newInactivePrincipalRoleMember.getId())
    					&& StringUtils.equals(role.getId(), newInactivePrincipalRoleMember.getId())) {
    				principalRoleList.remove(role);
    			}
    		}
    	}
    	
    	List<WorkAreaPositionRoleMemberBo> positionRoleList = new ArrayList<WorkAreaPositionRoleMemberBo> ();
    	positionRoleList.addAll(workArea.getPositionRoleMembers());
    	for (WorkAreaPositionRoleMemberBo newInactivePositionRoleMember : newInactivePositionRoleMembers) {
    		workArea.addInactivePositionRoleMember(newInactivePositionRoleMember);
    		List<WorkAreaPositionRoleMemberBo> tempRoleList = workArea.getPositionRoleMembers();
    		for(WorkAreaPositionRoleMemberBo role : tempRoleList) {
    			if(StringUtils.isNotEmpty(role.getId())
    					&& StringUtils.isNotEmpty(newInactivePositionRoleMember.getId())
    					&& StringUtils.equals(role.getId(), newInactivePositionRoleMember.getId())) {
    				positionRoleList.remove(role);
    			}
    		}
    	}
    	
    	for (WorkAreaPrincipalRoleMemberBo principalRoleMember : principalRoleList) {
    		RoleMember.Builder builder = RoleMember.Builder.create(principalRoleMember);
    		builder.setAttributes(Collections.singletonMap(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea.getWorkArea())));
    		
    		if (StringUtils.isBlank(principalRoleMember.getId())) {
    			KimApiServiceLocator.getRoleService().createRoleMember(builder.build());
    		} else {
    			KimApiServiceLocator.getRoleService().updateRoleMember(builder.build());
    		}
    	}
    	for (WorkAreaPrincipalRoleMemberBo inactivePrincipalRoleMember : workArea.getInactivePrincipalRoleMembers()) {
    		RoleMember.Builder builder = RoleMember.Builder.create(inactivePrincipalRoleMember);
    		builder.setAttributes(Collections.singletonMap(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea.getWorkArea())));
    		
    		if (StringUtils.isBlank(inactivePrincipalRoleMember.getId())) {
    			KimApiServiceLocator.getRoleService().createRoleMember(builder.build());
    		} else {
    			KimApiServiceLocator.getRoleService().updateRoleMember(builder.build());
    		}
    	}
    	
    	Role positionRole = KimApiServiceLocator.getRoleService().getRoleByNamespaceCodeAndName(KPMENamespace.KPME_HR.getNamespaceCode(), KPMERole.DERIVED_ROLE_POSITION.getRoleName());
    	
    	for (WorkAreaPositionRoleMemberBo positionRoleMember : positionRoleList) {
    		if(StringUtils.isBlank(positionRoleMember.getMemberId()) && positionRole != null) {
    			positionRoleMember.setMemberId(positionRole.getId());
    		}
    		positionRoleMember.setType(MemberType.ROLE);
    		
    		RoleMember.Builder builder = RoleMember.Builder.create(positionRoleMember);
        	Map<String, String> aMap = new HashMap<String, String>();
        	aMap.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea.getWorkArea()));
        	aMap.put(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName(), positionRoleMember.getPositionNumber());
        	builder.setAttributes(aMap);
        	
    		if (StringUtils.isBlank(positionRoleMember.getId())) {
    			KimApiServiceLocator.getRoleService().createRoleMember(builder.build());
    		} else {
    			KimApiServiceLocator.getRoleService().updateRoleMember(builder.build());
    		}
    	}
    	
    	for (WorkAreaPositionRoleMemberBo inactivePositionRoleMember : workArea.getInactivePositionRoleMembers()) {
    		if(StringUtils.isBlank(inactivePositionRoleMember.getMemberId()) && positionRole != null) {
    			inactivePositionRoleMember.setMemberId(positionRole.getId());
    		}
    		inactivePositionRoleMember.setType(MemberType.ROLE);
    		
    		RoleMember.Builder builder = RoleMember.Builder.create(inactivePositionRoleMember);
    		Map<String, String> aMap = new HashMap<String, String>();
        	aMap.put(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea.getWorkArea()));
        	aMap.put(KPMERoleMemberAttribute.POSITION.getRoleMemberAttributeName(), inactivePositionRoleMember.getPositionNumber());
        	builder.setAttributes(aMap);
        	
    		if (StringUtils.isBlank(inactivePositionRoleMember.getId())) {
    			KimApiServiceLocator.getRoleService().createRoleMember(builder.build());
    		} else {
    			KimApiServiceLocator.getRoleService().updateRoleMember(builder.build());
    		}
    	}
    }
    
    private List<WorkAreaPrincipalRoleMemberBo> createInactivePrincipalRoleMembers(Long workArea, List<WorkAreaPrincipalRoleMemberBo> principalRoleMembers) {
    	List<WorkAreaPrincipalRoleMemberBo> inactivePrincipalRoleMembers = new ArrayList<WorkAreaPrincipalRoleMemberBo>();
    	
    	List<RoleMemberBo> inactiveRoleMembers = createInactiveRoleMembers(principalRoleMembers);
    	
    	for (RoleMemberBo inactiveRoleMember : inactiveRoleMembers) {
//    		WorkAreaPrincipalRoleMemberBo.Builder builder = WorkAreaPrincipalRoleMemberBo.Builder.create(
//    				inactiveRoleMember.getRoleId(), null, inactiveRoleMember.getMemberId(), inactiveRoleMember.getType(), 
//    				inactiveRoleMember.getActiveFromDate(), inactiveRoleMember.getActiveToDate(), inactiveRoleMember.getAttributes(), 
//    				inactiveRoleMember.getMemberName(), inactiveRoleMember.getMemberNamespaceCode());
    		
    		WorkAreaPrincipalRoleMemberBo.Builder builder = WorkAreaPrincipalRoleMemberBo.Builder.create(inactiveRoleMember);
    		builder.setAttributes(Collections.singletonMap(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea)));
    		
    		inactivePrincipalRoleMembers.add(builder.build());
    	}
    	
    	return inactivePrincipalRoleMembers;
    }
    
    private List<WorkAreaPositionRoleMemberBo> createInactivePositionRoleMembers(Long workArea, List<WorkAreaPositionRoleMemberBo> positionRoleMembers) {
    	List<WorkAreaPositionRoleMemberBo> inactivePositionRoleMembers = new ArrayList<WorkAreaPositionRoleMemberBo>();

    	List<RoleMemberBo> inactiveRoleMembers = createInactiveRoleMembers(positionRoleMembers);
    	
    	for (RoleMemberBo inactiveRoleMember : inactiveRoleMembers) {
//    		WorkAreaPositionRoleMemberBo.Builder builder = WorkAreaPositionRoleMemberBo.Builder.create(
//    				inactiveRoleMember.getRoleId(), null, inactiveRoleMember.getMemberId(), inactiveRoleMember.getType(), 
//    				inactiveRoleMember.getActiveFromDate(), inactiveRoleMember.getActiveToDate(), inactiveRoleMember.getAttributes(), 
//    				inactiveRoleMember.getMemberName(), inactiveRoleMember.getMemberNamespaceCode());
    		WorkAreaPositionRoleMemberBo.Builder builder = WorkAreaPositionRoleMemberBo.Builder.create(inactiveRoleMember);
    		builder.setAttributes(Collections.singletonMap(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea)));

    		inactivePositionRoleMembers.add(builder.build());
    	}
    	
    	return inactivePositionRoleMembers;
    }
    
    private List<RoleMemberBo> createInactiveRoleMembers(List<? extends RoleMemberBo> roleMembers) {
    	List<RoleMemberBo> inactiveRoleMembers = new ArrayList<RoleMemberBo>();
    	
        List<RoleMemberBo> oldRoleMembers = new ArrayList<RoleMemberBo>();
        List<RoleMemberBo> newRoleMembers = new ArrayList<RoleMemberBo>();
        for (RoleMemberBo roleMember : roleMembers) {
		  	if (!StringUtils.isEmpty(roleMember.getId())) {
		  		oldRoleMembers.add(roleMember);
		  	} else {
		  		newRoleMembers.add(roleMember);
		  	}
        }
        
        for (RoleMemberBo newRoleMember : newRoleMembers) {
    		Role newRole = KimApiServiceLocator.getRoleService().getRole(newRoleMember.getRoleId());
        	for (RoleMemberBo oldRoleMember : oldRoleMembers) {
        		Role oldRole = KimApiServiceLocator.getRoleService().getRole(oldRoleMember.getRoleId());
			  	
        		if (StringUtils.equals(newRole.getName(), oldRole.getName()) && StringUtils.equals(newRoleMember.getMemberId(), oldRoleMember.getMemberId())) {
        			RoleMember.Builder builder = RoleMember.Builder.create(oldRoleMember);
    				builder.setActiveToDate(DateTime.now());
  
			  		inactiveRoleMembers.add(RoleMemberBo.from(builder.build()));
			  	}
        	}
        }
        
        return inactiveRoleMembers;
    }

    //KPME-2624 added logic to save current logged in user to UserPrincipal id for collections
    @Override
    public void prepareForSave() {
        WorkAreaBo workArea = (WorkAreaBo)this.getBusinessObject();
        for (TaskBo task : workArea.getTasks()) {
            task.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        }
        super.prepareForSave();
    }
}