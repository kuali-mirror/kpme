package org.kuali.kpme.pm.pstnqlfctnvl.dao;

import org.kuali.kpme.pm.pstnqlfctnvl.PositionQualificationValue;

public interface PositionQualificationValueDao {
	public PositionQualificationValue getPositionQualificationValueByValue(String value);
	
	public PositionQualificationValue getPositionQualificationValueById(String id);

}
