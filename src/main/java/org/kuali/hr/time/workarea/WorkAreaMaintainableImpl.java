package org.kuali.hr.time.workarea;

import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.maintenance.Maintainable;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.web.ui.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkAreaMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = 6264585236631982347L;

	@Override
	public void saveBusinessObject() {

		WorkArea workArea = (WorkArea) this.getBusinessObject();
		List<TkRole> roles = workArea.getRoles();

		if (workArea.getInactiveRoles() != null
				&& workArea.getInactiveRoles().size() > 0) {
			for (TkRole role : workArea.getInactiveRoles()) {
				roles.add(role);
			}
		}

		List<Task> tasks = workArea.getTasks();

		for (Task task : tasks) {
			task.setEffectiveDate(workArea.getEffectiveDate());
		}
		for (TkRole role : roles) {
			role.setWorkArea(workArea.getWorkArea());
			role.setUserPrincipalId(TKContext.getPrincipalId());
			role.setEffectiveDate(workArea.getEffectiveDate());
		}
		
		workArea.setTasks(tasks);
		workArea.setRoles(roles);
		KNSServiceLocator.getBusinessObjectService().save(workArea);
		TkServiceLocator.getTkRoleService().saveOrUpdate(roles);
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

	@Override
	public List getSections(MaintenanceDocument document,
			Maintainable oldMaintainable) {
		List sections = super.getSections(document, oldMaintainable);
		for (Object obj : sections) {
			Section sec = (Section) obj;
			if (document.isOldBusinessObjectInDocument()
					&& sec.getSectionId().equals("inactiveRoles")) {
				sec.setHidden(false);
			}
		}
		return sections;
	}

}
