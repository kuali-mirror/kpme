package org.kuali.kpme.pm.pstnqlfrtype.service;

import java.util.List;

import org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType;

public interface PstnQlfrTypeService {
	/**
	 * retrieve the Position Qualifier Type with given id
	 * @param pmPstnQlfrTypeId
	 * @return
	 */
	public PstnQlfrType getPstnQlfrTypeById(String pmPstnQlfrTypeId);
	
	/**
	 * retrieve all active Position Qualifier Types
	 * @return
	 */
	public List<PstnQlfrType> getAllActivePstnQlfrTypes();
}
