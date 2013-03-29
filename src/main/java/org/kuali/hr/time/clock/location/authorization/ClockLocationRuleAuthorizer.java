package org.kuali.hr.time.clock.location.authorization;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.time.clock.location.ClockLocationRule;
import org.kuali.hr.time.department.Department;
import org.kuali.hr.time.service.base.TkServiceLocator;

@SuppressWarnings("deprecation")
public class ClockLocationRuleAuthorizer extends KPMEMaintenanceDocumentAuthorizerBase {

	private static final long serialVersionUID = 4041790300091832925L;

	protected void addRoleQualification(Object dataObject, Map<String, String> attributes) {
		super.addRoleQualification(dataObject, attributes);

		String department = StringUtils.EMPTY;
		String location = StringUtils.EMPTY;
		
		if (dataObject instanceof ClockLocationRule) {
			ClockLocationRule clockLocationRuleObj = (ClockLocationRule) dataObject;
			
			if (clockLocationRuleObj != null) {
				department = clockLocationRuleObj.getDept();
				
				Department departmentObj = TkServiceLocator.getDepartmentService().getDepartment(department, clockLocationRuleObj.getEffectiveDate());
			
				if (departmentObj != null) {
					location = departmentObj.getLocation();
				}
			}
		}
		
		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}

}