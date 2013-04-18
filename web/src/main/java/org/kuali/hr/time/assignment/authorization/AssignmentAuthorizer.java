package org.kuali.hr.time.assignment.authorization;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.time.assignment.Assignment;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.service.base.TkServiceLocator;

@SuppressWarnings("deprecation")
public class AssignmentAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = 1962036374728477204L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		String location = StringUtils.EMPTY;
		
		if (dataObject instanceof Assignment) {
			Assignment assignmentObj = (Assignment) dataObject;
			
			if (assignmentObj != null) {
				Department departmentObj = TkServiceLocator.getDepartmentService().getDepartment(assignmentObj.getDept(), assignmentObj.getEffectiveLocalDate());
				
				if (departmentObj != null) {
					location = departmentObj.getLocation();
				}
			}
		}
		
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}

}
