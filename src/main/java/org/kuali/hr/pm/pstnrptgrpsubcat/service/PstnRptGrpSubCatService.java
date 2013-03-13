package org.kuali.hr.pm.pstnrptgrpsubcat.service;

import org.kuali.hr.pm.pstnrptgrpsubcat.PositionReportGroupSubCategory;

public interface PstnRptGrpSubCatService {
	
	/**
	 * Retrieve the PositionReportGroupSubCategory with given id
	 * @param pmPstnRptGrpSubCatId
	 * @return
	 */
	public PositionReportGroupSubCategory getPstnRptGrpSubCatById(String pmPstnRptGrpSubCatId);


}
