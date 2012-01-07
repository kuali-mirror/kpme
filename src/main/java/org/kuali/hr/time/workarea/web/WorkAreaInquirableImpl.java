package org.kuali.hr.time.workarea.web;

import java.util.Map;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workarea.WorkArea;
import org.kuali.rice.kns.bo.BusinessObject;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;

public class WorkAreaInquirableImpl extends KualiInquirableImpl {

	@Override
	public BusinessObject getBusinessObject(Map fieldValues) {
		WorkArea workArea = (WorkArea) super.getBusinessObject(fieldValues);

		TkServiceLocator.getWorkAreaService().populateWorkAreaRoles(workArea);

		return workArea;
	}
}
