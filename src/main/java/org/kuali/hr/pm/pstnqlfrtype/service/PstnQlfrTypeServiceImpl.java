package org.kuali.hr.pm.pstnqlfrtype.service;

import org.kuali.hr.pm.pstnqlfrtype.PstnQlfrType;
import org.kuali.hr.pm.pstnqlfrtype.dao.PstnQlfrTypeDao;

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
