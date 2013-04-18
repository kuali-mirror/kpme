package org.kuali.hr.pm.pstnqlfrtype.service;

import org.kuali.hr.pm.pstnqlfrtype.PstnQlfrType;

public interface PstnQlfrTypeService {
	/**
	 * retrieve the Position Qualifier Type with given id
	 * @param pmPstnQlfrTypeId
	 * @return
	 */
	public PstnQlfrType getPstnQlfrTypeById(String pmPstnQlfrTypeId);
}
