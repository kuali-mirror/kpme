package org.kuali.kpme.pm.pstnqlfrtype.service;

import org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType;
import org.kuali.kpme.pm.pstnqlfrtype.dao.PstnQlfrTypeDao;

public class PstnQlfrTypeServiceImpl implements PstnQlfrTypeService {

	private PstnQlfrTypeDao pstnQlfrTypeDao;
	@Override
	public PstnQlfrType getPstnQlfrTypeById(String pmPstnQlfrTypeId) {
		return pstnQlfrTypeDao.getPstnQlfrTypeById(pmPstnQlfrTypeId);
	}
	public PstnQlfrTypeDao getPstnQlfrTypeDao() {
		return pstnQlfrTypeDao;
	}
	public void setPstnQlfrTypeDao(PstnQlfrTypeDao pstnQlfrTypeDao) {
		this.pstnQlfrTypeDao = pstnQlfrTypeDao;
	}

}
