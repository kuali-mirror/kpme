package org.kuali.kpme.pm.pstnqlfrtype.dao;

import java.util.List;
import org.kuali.kpme.pm.pstnqlfrtype.PstnQlfrType;

public interface PstnQlfrTypeDao {

	public PstnQlfrType getPstnQlfrTypeById(String pmPstnQlfrTypeId);
	
	public List<PstnQlfrType> getAllActivePstnQlfrTypes();
}
