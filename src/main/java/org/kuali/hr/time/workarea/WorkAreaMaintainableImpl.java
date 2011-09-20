package org.kuali.hr.time.workarea;

import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.web.ui.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
			task.setWorkArea(workArea.getWorkArea()); // jira870
		}
		super.setNewCollectionLineDefaultValues(arg0, arg1);
	}

    @Override
    public PersistableBusinessObject getNewCollectionLine(String collectionName) {
        return super.getNewCollectionLine(collectionName);    //To change body of overridden methods use File | Settings | File Templates.
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
	public HrBusinessObject getObjectById(Long id) {
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
