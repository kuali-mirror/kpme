package org.kuali.kpme.pm.pstnqlfctnvl.service;

import org.kuali.kpme.pm.pstnqlfctnvl.PositionQualificationValue;

public interface PositionQualificationValueService {
	/**
	 * retrieve the PositionQualificationValue with given value
	 * @param value
	 * @return
	 */
	public PositionQualificationValue getPositionQualificationValueByValue(String value);

}
