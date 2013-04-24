package org.kuali.hr.tklm.time.rules.lunch.department.authorization;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.hr.core.department.Department;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.tklm.time.rules.lunch.department.DeptLunchRule;
import org.kuali.hr.tklm.time.service.base.TkServiceLocator;

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
				
				Department departmentObj = TkServiceLocator.getDepartmentService().getDepartment(department, departmentLunchRuleObj.getEffectiveLocalDate());
			
				if (departmentObj != null) {
					location = departmentObj.getLocation();
				}
			}
		}
		
		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}
	
}