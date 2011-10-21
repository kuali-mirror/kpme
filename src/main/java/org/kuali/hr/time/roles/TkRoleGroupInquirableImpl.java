package org.kuali.hr.time.roles;

import java.util.Map;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;

public class TkRoleGroupInquirableImpl extends KualiInquirableImpl {

	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		TkRoleGroup tkRoleGroup = (TkRoleGroup) super.getBusinessObject(fieldValues);

		TkServiceLocator.getTkRoleGroupService().populateRoles(tkRoleGroup);

		return tkRoleGroup;
	}
}
