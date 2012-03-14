package org.kuali.hr.time.workarea;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.web.ui.Section;

public class WorkAreaMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	private static final long serialVersionUID = 6264585236631982347L;

	@Override
	protected void setNewCollectionLineDefaultValues(String arg0,
			PersistableBusinessObject arg1) {
		WorkArea workArea = (WorkArea) this.getBusinessObject();
		if (arg1 instanceof TkRole) {
			TkRole role = (TkRole) arg1;
			role.setEffectiveDate(workArea.getEffectiveDate());
		} else if (arg1 instanceof Task) {
			Task task = (Task) arg1;
			task.setEffectiveDate(workArea.getEffectiveDate());
		}
		super.setNewCollectionLineDefaultValues(arg0, arg1);
	}

	@Override
    public PersistableBusinessObject getNewCollectionLine(String collectionName) {
        return super.getNewCollectionLine(collectionName);    //To change body of overridden methods use File | Settings | File Templates.
    }
	
	@Override
    public void addNewLineToCollection(String collectionName) {
		if (collectionName.equals("roles")) {
        	TkRole aRole = (TkRole)newCollectionLines.get(collectionName );
            if ( aRole != null ) {
            	if(!StringUtils.isEmpty(aRole.getPrincipalId()) && !StringUtils.isEmpty(aRole.getPositionNumber())) {
            		GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KNSConstants.MAINTENANCE_NEW_MAINTAINABLE +"roles", 
            				"error.role.principalId.positonNubmer", aRole.getPrincipalId());
            		return;
            	}
            	if(aRole.getPrincipalId() != null && !aRole.getPrincipalId().isEmpty()) {
            		Person aPerson = KIMServiceLocator.getPersonService().getPerson(aRole.getPrincipalId());
            		if(aPerson == null) {
            			GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KNSConstants.MAINTENANCE_NEW_MAINTAINABLE +"roles", 
                				"error.role.person.notexist", aRole.getPrincipalId());
                		return;
            		}
            	}
            	if(aRole.getPositionNumber() != null && !aRole.getPositionNumber().isEmpty()) {
            		Position aPositon = TkServiceLocator.getPositionService().getPosition(aRole.getPositionNumber());
            		if(aPositon == null) {
            			GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KNSConstants.MAINTENANCE_NEW_MAINTAINABLE +"roles", 
                				"error.role.position.notexist", aRole.getPositionNumber());
                		return;
            		}
            	}
            }
        }
        super.addNewLineToCollection(collectionName);
	}

    @Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		WorkArea waOld = (WorkArea) document.getOldMaintainableObject()
				.getBusinessObject();
		WorkArea waNew = (WorkArea) document.getNewMaintainableObject()
				.getBusinessObject();
		
		List<TkRole> positionRoles = TkServiceLocator.getTkRoleService().getPositionRolesForWorkArea(waOld.getWorkArea(), waOld.getEffectiveDate());
		TkServiceLocator.getWorkAreaService().populateWorkAreaRoles(waOld);
		waOld.getRoles().addAll(positionRoles);
		
		TkServiceLocator.getWorkAreaService().populateWorkAreaRoles(waNew);
		waNew.getRoles().addAll(positionRoles);
		super.processAfterEdit(document, parameters);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getSections(MaintenanceDocument document,
			Maintainable oldMaintainable) {
		List sections = super.getSections(document, oldMaintainable);
		for (Object obj : sections) {
			Section sec = (Section) obj;
			if (document.isOldBusinessObjectInDocument()
					&& sec.getSectionId().equals("inactiveRoles")) {
				sec.setHidden(false);
			} else if (!document.isOldBusinessObjectInDocument()
					&& sec.getSectionId().equals("inactiveRoles")) {
				sec.setHidden(true);
			}
		}
		return sections;
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getWorkAreaService().getWorkArea(id);
	}

	@Override
	public void customSaveLogic(HrBusinessObject hrObj) {
		WorkArea workArea = (WorkArea)hrObj;
		List<TkRole> roles = workArea.getRoles();
		List<TkRole> rolesCopy = new ArrayList<TkRole>();
		rolesCopy.addAll(roles);
		if (workArea.getInactiveRoles() != null
				&& workArea.getInactiveRoles().size() > 0) {
			for (TkRole role : workArea.getInactiveRoles()) {
				roles.add(role);
			}
		}
		List<Task> tasks = workArea.getTasks();
		for(Task task : tasks){
			task.setTkTaskId(null);
			task.setTimestamp(new Timestamp(System.currentTimeMillis()));
		}
		
		workArea.setTasks(tasks);
		workArea.setRoles(roles);
		for (TkRole role : roles) {
			role.setWorkArea(workArea.getWorkArea());
			role.setUserPrincipalId(TKContext.getPrincipalId());
		}
		TkServiceLocator.getTkRoleService().saveOrUpdate(roles);
	}

	@Override
	public void processAfterNew(MaintenanceDocument document,
			Map<String, String[]> parameters) {
			WorkArea workArea = (WorkArea) this.getBusinessObject();
			if(workArea.getWorkArea() == null){
				workArea.setWorkArea(TkServiceLocator.getWorkAreaService().getNextWorkAreaKey());
			}
			super.processAfterNew(document, parameters);
	}
}
