package org.kuali.kpme.pm.pstnqlfctnvl.service;

import org.kuali.kpme.pm.pstnqlfctnvl.PositionQualificationValue;
import org.kuali.kpme.pm.pstnqlfctnvl.dao.PositionQualificationValueDao;

public class PositionQualificationValueServiceImpl implements PositionQualificationValueService {
	
	private PositionQualificationValueDao pstnQlfctnVlDao; 
	
	@Override
	public PositionQualificationValue getPositionQualificationValueByValue(String value) {
		return pstnQlfctnVlDao.getPositionQualificationValueByValue(value);
	}

	public PositionQualificationValueDao getPstnQlfctnVlDao() {
		return pstnQlfctnVlDao;
	}

	public void setPstnQlfctnVlDao(PositionQualificationValueDao pstnQlfctnVlDao) {
		this.pstnQlfctnVlDao = pstnQlfctnVlDao;
	}

	
}
