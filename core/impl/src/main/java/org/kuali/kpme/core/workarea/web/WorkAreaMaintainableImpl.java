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
package org.kuali.kpme.core.workarea.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.position.PositionBase;
import org.kuali.kpme.core.task.Task;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.role.PositionRoleMemberBo;
import org.kuali.kpme.core.role.PrincipalRoleMemberBo;
import org.kuali.kpme.core.role.workarea.WorkAreaPositionRoleMemberBo;
import org.kuali.kpme.core.role.workarea.WorkAreaPrincipalRoleMemberBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.role.Role;
import org.kuali.rice.kim.api.role.RoleMember;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.role.RoleMemberBo;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.web.ui.Section;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

@SuppressWarnings("deprecation")
public class WorkAreaMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = -624127817308880466L;

	@Override
    public HrBusinessObject getObjectById(String id) {
        return HrServiceLocator.getWorkAreaService().getWorkArea(id);
    }
    
	@Override
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
    }
    
    @Override
    public void processAfterNew(MaintenanceDocument document, Map<String, String[]> parameters) {
        WorkArea workArea = (WorkArea) this.getBusinessObject();
        
        if (workArea.getWorkArea() == null) {
            workArea.setWorkArea(HrServiceLocator.getWorkAreaService().getNextWorkAreaKey());
        }
        
        super.processAfterNew(document, parameters);
    }
    
    @Override
    public void processAfterEdit(MaintenanceDocument document, Map<String, String[]> parameters) {
        WorkArea oldMaintainableObject = (WorkArea) document.getOldMaintainableObject().getBusinessObject();
        WorkArea newMaintainableObject = (WorkArea) document.getNewMaintainableObject().getBusinessObject();
        
        WorkArea oldWorkArea = HrServiceLocator.getWorkAreaService().getWorkArea(oldMaintainableObject.getWorkArea(), oldMaintainableObject.getEffectiveLocalDate());

        oldMaintainableObject.setTasks(oldWorkArea.getTasks());
        oldMaintainableObject.setPrincipalRoleMembers(oldWorkArea.getPrincipalRoleMembers());
        oldMaintainableObject.setInactivePrincipalRoleMembers(oldWorkArea.getInactivePrincipalRoleMembers());
        oldMaintainableObject.setPositionRoleMembers(oldWorkArea.getPositionRoleMembers());
        oldMaintainableObject.setInactivePositionRoleMembers(oldWorkArea.getInactivePositionRoleMembers());
        
        WorkArea newWorkArea = HrServiceLocator.getWorkAreaService().getWorkArea(newMaintainableObject.getWorkArea(), newMaintainableObject.getEffectiveLocalDate());

        newMaintainableObject.setTasks(newWorkArea.getTasks());
        newMaintainableObject.setPrincipalRoleMembers(newWorkArea.getPrincipalRoleMembers());
        newMaintainableObject.setInactivePrincipalRoleMembers(newWorkArea.getInactivePrincipalRoleMembers());
        newMaintainableObject.setPositionRoleMembers(newWorkArea.getPositionRoleMembers());
        newMaintainableObject.setInactivePositionRoleMembers(newWorkArea.getInactivePositionRoleMembers());
        
        super.processAfterEdit(document, parameters);
    }

    @Override
    protected void setNewCollectionLineDefaultValues(String collectionName, PersistableBusinessObject addLine) {
        WorkArea workArea = (WorkArea) getBusinessObject();
        
        if (workArea.getEffectiveDate() != null) {
	        if (addLine instanceof Task) {
	            Task task = (Task) addLine;
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
    	WorkArea workArea = (WorkArea) getBusinessObject();
    	
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
            		PositionBase position = HrServiceLocator.getPositionService().getPosition(roleMember.getPositionNumber(), workArea.getEffectiveLocalDate());
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
        WorkArea workArea = (WorkArea) hrObj;
        
        saveTasks(workArea);
        saveRoleMembers(workArea);
    }
    
    private void saveTasks(WorkArea workArea) {
        List<Task> tasks = workArea.getTasks();
        
        for (Task task : tasks) {
            task.setTkTaskId(null);
            task.setTimestamp(new Timestamp(System.currentTimeMillis()));
        }
        workArea.setTasks(tasks);

        HrServiceLocator.getTaskService().saveTasks(tasks);
    }
    
    private void saveRoleMembers(WorkArea workArea) {
    	List<WorkAreaPrincipalRoleMemberBo> newInactivePrincipalRoleMembers = createInactivePrincipalRoleMembers(workArea.getWorkArea(), workArea.getPrincipalRoleMembers());
    	List<WorkAreaPositionRoleMemberBo> newInactivePositionRoleMembers = createInactivePositionRoleMembers(workArea.getWorkArea(), workArea.getPositionRoleMembers());
        
    	for (WorkAreaPrincipalRoleMemberBo newInactivePrincipalRoleMember : newInactivePrincipalRoleMembers) {
    		workArea.addInactivePrincipalRoleMember(newInactivePrincipalRoleMember);
    	}
    	for (WorkAreaPositionRoleMemberBo newInactivePositionRoleMember : newInactivePositionRoleMembers) {
    		workArea.addInactivePositionRoleMember(newInactivePositionRoleMember);
    	}
    	
    	for (WorkAreaPrincipalRoleMemberBo principalRoleMember : workArea.getPrincipalRoleMembers()) {
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
    	for (WorkAreaPositionRoleMemberBo positionRoleMember : workArea.getPositionRoleMembers()) {
    		RoleMember.Builder builder = RoleMember.Builder.create(positionRoleMember);
    		builder.setAttributes(Collections.singletonMap(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea.getWorkArea())));
    		
    		if (StringUtils.isBlank(positionRoleMember.getId())) {
    			KimApiServiceLocator.getRoleService().createRoleMember(builder.build());
    		} else {
    			KimApiServiceLocator.getRoleService().updateRoleMember(builder.build());
    		}
    	}
    	for (WorkAreaPositionRoleMemberBo inactivePositionRoleMember : workArea.getInactivePositionRoleMembers()) {
    		RoleMember.Builder builder = RoleMember.Builder.create(inactivePositionRoleMember);
    		builder.setAttributes(Collections.singletonMap(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea.getWorkArea())));
    		
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
    		WorkAreaPrincipalRoleMemberBo.Builder builder = WorkAreaPrincipalRoleMemberBo.Builder.create(
    				inactiveRoleMember.getRoleId(), null, inactiveRoleMember.getMemberId(), inactiveRoleMember.getType(), 
    				inactiveRoleMember.getActiveFromDate(), inactiveRoleMember.getActiveToDate(), inactiveRoleMember.getAttributes(), 
    				inactiveRoleMember.getMemberName(), inactiveRoleMember.getMemberNamespaceCode());
    		builder.setAttributes(Collections.singletonMap(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName(), String.valueOf(workArea)));
    		
    		inactivePrincipalRoleMembers.add(builder.build());
    	}
    	
    	return inactivePrincipalRoleMembers;
    }
    
    private List<WorkAreaPositionRoleMemberBo> createInactivePositionRoleMembers(Long workArea, List<WorkAreaPositionRoleMemberBo> positionRoleMembers) {
    	List<WorkAreaPositionRoleMemberBo> inactivePositionRoleMembers = new ArrayList<WorkAreaPositionRoleMemberBo>();

    	List<RoleMemberBo> inactiveRoleMembers = createInactiveRoleMembers(positionRoleMembers);
    	
    	for (RoleMemberBo inactiveRoleMember : inactiveRoleMembers) {
    		WorkAreaPositionRoleMemberBo.Builder builder = WorkAreaPositionRoleMemberBo.Builder.create(
    				inactiveRoleMember.getRoleId(), null, inactiveRoleMember.getMemberId(), inactiveRoleMember.getType(), 
    				inactiveRoleMember.getActiveFromDate(), inactiveRoleMember.getActiveToDate(), inactiveRoleMember.getAttributes(), 
    				inactiveRoleMember.getMemberName(), inactiveRoleMember.getMemberNamespaceCode());
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
        	for (RoleMemberBo oldRoleMember : oldRoleMembers) {
        		Role newRole = KimApiServiceLocator.getRoleService().getRole(newRoleMember.getRoleId());
        		Role oldRole = KimApiServiceLocator.getRoleService().getRole(newRoleMember.getRoleId());
			  	
        		if (StringUtils.equals(newRole.getName(), oldRole.getName()) && StringUtils.equals(newRoleMember.getMemberId(), oldRoleMember.getMemberId())) {
        			RoleMember.Builder builder = RoleMember.Builder.create(oldRoleMember);
    				builder.setActiveToDate(new DateTime());
  
			  		inactiveRoleMembers.add(RoleMemberBo.from(builder.build()));
			  	}
        	}
        }
        
        return inactiveRoleMembers;
    }
    
}