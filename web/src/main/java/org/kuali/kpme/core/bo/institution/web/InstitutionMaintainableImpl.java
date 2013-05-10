package org.kuali.kpme.core.bo.institution.web;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.pm.service.base.PmServiceLocator;

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
