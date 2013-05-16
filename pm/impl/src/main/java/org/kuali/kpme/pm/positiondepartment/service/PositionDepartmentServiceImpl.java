package org.kuali.kpme.pm.positiondepartment.service;

import org.kuali.kpme.pm.positiondepartment.PositionDepartment;
import org.kuali.kpme.pm.positiondepartment.dao.PositionDepartmentDao;

public class PositionDepartmentServiceImpl implements PositionDepartmentService {

	private PositionDepartmentDao positionDepartmentDao;

	/**
	 * @return the positionDepartmentDao
	 */
	public PositionDepartmentDao getPositionDepartmentDao() {
		return positionDepartmentDao;
	}

	/**
	 * @param positionDepartmentDao the positionDepartmentDao to set
	 */
	public void setPositionDepartmentDao(PositionDepartmentDao positionDepartmentDao) {
		this.positionDepartmentDao = positionDepartmentDao;
	}

	/* (non-Javadoc)
	 * @see org.kuali.hr.pm.positiondepartment.service.PositionDepartmentService#getPositionDepartmentById(java.lang.String)
	 */
	@Override
	public PositionDepartment getPositionDepartmentById(String pmPositionDeptId) {
		return positionDepartmentDao.getPositionDepartmentById(pmPositionDeptId);
	}
	
	
	
}
