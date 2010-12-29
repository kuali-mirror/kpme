package org.kuali.hr.time.department;

import java.util.List;

import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class DepartmentMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = -330523155799598560L;
	
	@Override
	public void saveBusinessObject() {
		
		Department dept = (Department) this.getBusinessObject();
		List<TkRole> roles = dept.getRoles();
		
		for(TkRole role : roles) {
			role.setDepartment(dept.getDept());
			role.setUserPrincipalId(TKContext.getPrincipalId());
		}
		
		dept.setRoles(roles);
		KNSServiceLocator.getBusinessObjectService().save(dept);
		
	}
}
