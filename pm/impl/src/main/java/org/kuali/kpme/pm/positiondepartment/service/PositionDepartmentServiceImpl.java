/**
 * Copyright 2004-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
