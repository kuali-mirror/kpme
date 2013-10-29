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
