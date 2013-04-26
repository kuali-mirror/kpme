package org.kuali.hr.tklm.time.rules.clocklocation.authorization;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.authorization.KPMEMaintenanceDocumentAuthorizerBase;
import org.kuali.hr.core.bo.department.Department;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.time.rules.clocklocation.ClockLocationRule;

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
				
				Department departmentObj = HrServiceLocator.getDepartmentService().getDepartment(department, clockLocationRuleObj.getEffectiveLocalDate());
			
				if (departmentObj != null) {
					location = departmentObj.getLocation();
				}
			}
		}
		
		attributes.put(KPMERoleMemberAttribute.DEPARTMENT.getRoleMemberAttributeName(), department);
		attributes.put(KPMERoleMemberAttribute.LOCATION.getRoleMemberAttributeName(), location);
	}

}