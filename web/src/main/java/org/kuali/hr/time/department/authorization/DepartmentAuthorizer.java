package org.kuali.hr.time.department.authorization;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.time.department.Department;

@SuppressWarnings("deprecation")
public class DepartmentAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = 7203195042405628702L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		String department = StringUtils.EMPTY;
		String location = StringUtils.EMPTY;
		
		if (dataObject instanceof Department) {
			Department departmentObj = (Department) dataObject;
			
			if (department != null) {
				department = departmentObj.getDept();
				location = departmentObj.getLocation();
			}
		}
		
		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}

}