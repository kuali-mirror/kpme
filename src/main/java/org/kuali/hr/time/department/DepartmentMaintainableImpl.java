package org.kuali.hr.time.department;

import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

import java.util.List;
import java.util.Map;

public class DepartmentMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = -330523155799598560L;

	@Override
	public void saveBusinessObject() {

		Department dept = (Department) this.getBusinessObject();
		List<TkRole> roles = dept.getRoles();

		for(TkRole role : roles) {
			role.setDepartment(dept.getDept());
			role.setUserPrincipalId(TKContext.getPrincipalId());
			role.setEffectiveDate(dept.getEffectiveDate());
		}

		dept.setRoles(roles);
		KNSServiceLocator.getBusinessObjectService().save(dept);
        TkServiceLocator.getTkRoleService().saveOrUpdate(roles);

	}

    @Override
    public void processAfterEdit( MaintenanceDocument document, Map<String,String[]> parameters ) {
        Department dOld = (Department)document.getOldMaintainableObject().getBusinessObject();
        Department dNew = (Department)document.getNewMaintainableObject().getBusinessObject();

        TkServiceLocator.getDepartmentService().populateDepartmentRoles(dOld);
        TkServiceLocator.getDepartmentService().populateDepartmentRoles(dNew);
    }
}
