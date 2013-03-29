package org.kuali.hr.time.timecollection.rule.authorization;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.time.collection.rule.TimeCollectionRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.service.base.TkServiceLocator;

@SuppressWarnings("deprecation")
public class TimeCollectionRuleAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = 4244084290191633140L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		String department = StringUtils.EMPTY;
		String location = StringUtils.EMPTY;
		
		if (dataObject instanceof TimeCollectionRule) {
			TimeCollectionRule timeCollectionRuleObj = (TimeCollectionRule) dataObject;
			
			if (timeCollectionRuleObj != null) {
				department = timeCollectionRuleObj.getDept();
				
				Department departmentObj = TkServiceLocator.getDepartmentService().getDepartment(department, timeCollectionRuleObj.getEffectiveDate());
			
				if (departmentObj != null) {
					location = departmentObj.getLocation();
				}
			}
		}
		
		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}
	
}