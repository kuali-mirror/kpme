package org.kuali.kpme.pm.pstncontracttype.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.pstncontracttype.PstnContractType;
import org.kuali.kpme.pm.pstncontracttype.dao.PstnContractTypeDao;

public class PstnContractTypeServiceImpl implements PstnContractTypeService {

	private PstnContractTypeDao pstnContractTypeDao;

	public PstnContractTypeDao getPstnContractTypeDao() {
		return pstnContractTypeDao;
	}

	public void setPstnContractTypeDao(
			PstnContractTypeDao pstnContractTypeDao) {
		this.pstnContractTypeDao = pstnContractTypeDao;
	}


	@Override
	public PstnContractType getPstnContractTypeById(
			String pmPositionTypeId) {
		return pstnContractTypeDao.getPstnContractTypeById(pmPositionTypeId);
	}

	@Override
	public List<PstnContractType> getPstnContractTypeList(String institution, String campus, LocalDate asOfDate) {
		return pstnContractTypeDao.getPstnContractTypeList( institution, campus, asOfDate);
	}

}
