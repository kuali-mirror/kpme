package org.kuali.hr.core.institution.service;

import org.kuali.hr.core.HrBusinessObject;
import org.kuali.hr.core.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.pm.service.base.PmServiceLocator;

public class InstitutionMaintainableImpl extends HrBusinessObjectMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		// TODO Auto-generated method stub
		return PmServiceLocator.getInstitutionService().getInstitutionById(id);
	}

	
	
}
