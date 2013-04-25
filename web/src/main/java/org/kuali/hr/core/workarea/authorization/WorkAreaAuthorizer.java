package org.kuali.hr.core.workarea.authorization;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.hr.core.department.Department;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.core.workarea.WorkArea;

@SuppressWarnings("deprecation")
public class WorkAreaAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = -2555420929126094696L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		String department = StringUtils.EMPTY;
		String location = StringUtils.EMPTY;
		
		if (dataObject instanceof WorkArea) {
			WorkArea workAreaObj = (WorkArea) dataObject;
			
			if (workAreaObj != null) {
				department = workAreaObj.getDept();
				
				Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, workAreaObj.getEffectiveLocalDate());
			
				if (departmentObj != null) {
					location = departmentObj.getLocation();
				}
			}
		}
		
		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}
	
}