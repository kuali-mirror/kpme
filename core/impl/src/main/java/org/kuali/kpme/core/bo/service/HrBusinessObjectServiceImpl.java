/**
 * Copyright 2004-2014 The Kuali Foundation
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
package org.kuali.kpme.core.bo.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.bo.dao.HrBusinessObjectDao;
import org.kuali.kpme.core.api.bo.service.HrBusinessObjectService;
import org.kuali.kpme.core.service.HrServiceLocator;

public class HrBusinessObjectServiceImpl implements HrBusinessObjectService {

	private static final String HR_BUSINESS_OBJECT_DAO = "hrBusinessObjectDao";
	private HrBusinessObjectDao hrBusinessObjectDao; 
	
	@Override
	public boolean doesNewerVersionExist(HrBusinessObjectContract bo) {
		boolean retVal = true;
		List<HrBusinessObjectContract> boList = getHrBusinessObjectDao().getNewerVersionsOf(bo);
		if(CollectionUtils.isEmpty(boList)) {
			retVal = false;
		}
		return retVal;
	}

	public void setHrBusinessObjectDao(HrBusinessObjectDao hrBusinessObjectDao) {
		this.hrBusinessObjectDao = hrBusinessObjectDao;
	}

	public HrBusinessObjectDao getHrBusinessObjectDao() {
		if(this.hrBusinessObjectDao == null) {
			this.hrBusinessObjectDao = HrServiceLocator.getService(getHrBusinessObjectDaoName());
		}
		return this.hrBusinessObjectDao;
	}

	protected String getHrBusinessObjectDaoName() {
		return HR_BUSINESS_OBJECT_DAO;
	}

}
