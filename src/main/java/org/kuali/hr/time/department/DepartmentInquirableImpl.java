package org.kuali.hr.time.department;

import java.util.Map;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.rice.krad.bo.BusinessObject;

public class DepartmentInquirableImpl extends KualiInquirableImpl {

	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		Department department = (Department) super.getBusinessObject(fieldValues);

		TkServiceLocator.getDepartmentService().populateDepartmentRoles(department);

		return department;
	}
}
