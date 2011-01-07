package org.kuali.hr.time.workarea;

import java.util.List;

import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class WorkAreaMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = 6264585236631982347L;
	
	@Override
	public void saveBusinessObject() {
		
		WorkArea workArea = (WorkArea) this.getBusinessObject();
		List<TkRole> roles = workArea.getRoles();
		
		for(TkRole role : roles) {
			role.setWorkArea(workArea.getWorkArea());
			role.setUserPrincipalId(TKContext.getPrincipalId());
		}
		
		workArea.setRoles(roles);
		KNSServiceLocator.getBusinessObjectService().save(workArea);
		
	}
	
	
}
