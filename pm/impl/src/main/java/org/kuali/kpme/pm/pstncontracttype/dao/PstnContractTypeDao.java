package org.kuali.kpme.pm.pstncontracttype.dao;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.pm.pstncontracttype.PstnContractType;

public interface PstnContractTypeDao {
	
	public PstnContractType getPstnContractTypeById(String pmCntrctTypeId);
	
	public List<PstnContractType> getPstnContractTypeList(String institution, String campus, LocalDate asOfDate);
}
