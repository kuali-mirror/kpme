package org.kuali.kpme.pm.pstnrptgrpsubcat.service;

import org.kuali.kpme.pm.pstnrptgrpsubcat.PositionReportGroupSubCategory;

public interface PstnRptGrpSubCatService {
	
	/**
	 * Retrieve the PositionReportGroupSubCategory with given id
	 * @param pmPstnRptGrpSubCatId
	 * @return
	 */
	public PositionReportGroupSubCategory getPstnRptGrpSubCatById(String pmPstnRptGrpSubCatId);


}
