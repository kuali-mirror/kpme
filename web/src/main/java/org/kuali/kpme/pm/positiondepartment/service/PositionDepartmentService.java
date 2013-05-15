package org.kuali.kpme.pm.positiondepartment.service;

import org.kuali.kpme.pm.positiondepartment.PositionDepartment;

public interface PositionDepartmentService {

	/**
	 * retrieve the PositionDepartment with given id
	 * @param pmPositionDeptId
	 * @return
	 */
	public PositionDepartment getPositionDepartmentById(String pmPositionDeptId);
	
}
