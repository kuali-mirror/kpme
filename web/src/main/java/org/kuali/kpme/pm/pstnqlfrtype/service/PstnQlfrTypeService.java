package org.kuali.kpme.pm.pstnqlfrtype.service;

import org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType;

public interface PstnQlfrTypeService {
	/**
	 * retrieve the Position Qualifier Type with given id
	 * @param pmPstnQlfrTypeId
	 * @return
	 */
	public PstnQlfrType getPstnQlfrTypeById(String pmPstnQlfrTypeId);
}
