package org.kuali.hr.time.workarea;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.web.ui.Section;

public class WorkAreaMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = 6264585236631982347L;

	@Override
	public void saveBusinessObject() {

		WorkArea workArea = (WorkArea) this.getBusinessObject();
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
		KNSServiceLocator.getBusinessObjectService().save(workArea);

		for (TkRole role : roles) {
			role.setWorkArea(workArea.getWorkArea());
			role.setUserPrincipalId(TKContext.getPrincipalId());
		}
		TkServiceLocator.getTkRoleService().saveOrUpdate(roles);
		workArea.setRoles(rolesCopy);
	}

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
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		WorkArea waOld = (WorkArea) document.getOldMaintainableObject()
				.getBusinessObject();
		WorkArea waNew = (WorkArea) document.getNewMaintainableObject()
				.getBusinessObject();

		TkServiceLocator.getWorkAreaService().populateWorkAreaRoles(waOld);
		TkServiceLocator.getWorkAreaService().populateWorkAreaRoles(waNew);

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

	/**
	 * Used to show the work area id after submit on new 
	 * This is because real saving is occurring in a background thread now
	 */
	@Override
	public void prepareForSave() {
		super.prepareForSave();
		
		if(!StringUtils.equals(getMaintenanceAction(),"Edit")){
			saveBusinessObject();
			WorkArea workArea = (WorkArea) this.getBusinessObject();
			workArea.setTkWorkAreaId(null);
		}
	}

}
