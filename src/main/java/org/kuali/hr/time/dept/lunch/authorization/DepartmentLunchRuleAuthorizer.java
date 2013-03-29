package org.kuali.hr.time.dept.lunch.authorization;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.dept.lunch.DeptLunchRule;
import org.kuali.hr.time.service.base.TkServiceLocator;

@SuppressWarnings("deprecation")
public class DepartmentLunchRuleAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = -3734133844175107645L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		String department = StringUtils.EMPTY;
		String location = StringUtils.EMPTY;
		
		if (dataObject instanceof DeptLunchRule) {
			DeptLunchRule departmentLunchRuleObj = (DeptLunchRule) dataObject;
			
			if (departmentLunchRuleObj != null) {
				department = departmentLunchRuleObj.getDept();
				
				Department departmentObj = TkServiceLocator.getDepartmentService().getDepartment(department, departmentLunchRuleObj.getEffectiveDate());
			
				if (departmentObj != null) {
					location = departmentObj.getLocation();
				}
			}
		}
		
		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}
	
}