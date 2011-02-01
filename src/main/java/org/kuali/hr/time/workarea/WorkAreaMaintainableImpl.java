package org.kuali.hr.time.workarea;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.roles.TkRole;
import org.kuali.hr.time.task.Task;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.kns.service.KNSServiceLocator;

public class WorkAreaMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = 6264585236631982347L;
	
	@Override
	public void saveBusinessObject() {
		
		WorkArea workArea = (WorkArea) this.getBusinessObject();
		List<TkRole> roles = workArea.getRoles();
		List<Task> tasks = workArea.getTasks();
		
		for(TkRole role : roles) {
			role.setWorkArea(workArea.getWorkArea());
			role.setUserPrincipalId(TKContext.getPrincipalId());
		}
		
		workArea.setRoles(roles);
		KNSServiceLocator.getBusinessObjectService().save(workArea);
		this.resetWorkArea(workArea);
	}
	
	public void resetWorkArea(WorkArea workArea) {
		if(workArea.getWorkArea() == null) {
			Map<String,Object> criteria = new HashMap<String,Object>();
			criteria.put("tkWorkAreaId", workArea.getTkWorkAreaId());
			Collection aCol = KNSServiceLocator.getBusinessObjectService().findMatching(WorkArea.class, criteria);
			if(!aCol.isEmpty()) {
				WorkArea aWorkArea = (WorkArea) aCol.toArray()[0];
				workArea.setWorkArea(aWorkArea.getWorkArea());
			}
		}
	}
}
