package org.kuali.hr.pm.pstnrptgrpsubcat.service;

import org.kuali.hr.pm.pstnrptgrpsubcat.PositionReportGroupSubCategory;
import org.kuali.hr.pm.pstnrptgrpsubcat.dao.PstnRptGrpSubCatDao;

public class PstnRptGrpSubCatServiceImpl implements PstnRptGrpSubCatService {
	
	private PstnRptGrpSubCatDao pstnRptGrpSubCatDao;

	@Override
	public PositionReportGroupSubCategory getPstnRptGrpSubCatById(
			String pmPstnRptGrpSubCatId) {
		return pstnRptGrpSubCatDao.getPstnRptGrpSubCatById(pmPstnRptGrpSubCatId);
	}

	public void setPstnRptGrpSubCatDao(PstnRptGrpSubCatDao pstnRptGrpSubCatDao) {
		this.pstnRptGrpSubCatDao = pstnRptGrpSubCatDao;
	}

	public PstnRptGrpSubCatDao getPstnRptGrpSubCatDao() {
		return pstnRptGrpSubCatDao;
	}

}
