/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kpme.pm.pstnrptgrpsubcat.service;

import org.kuali.kpme.pm.api.pstnrptgrpsubcat.PositionReportGroupSubCategory;
import org.kuali.kpme.pm.api.pstnrptgrpsubcat.service.PstnRptGrpSubCatService;
import org.kuali.kpme.pm.pstnrptgrpsubcat.PositionReportGroupSubCategoryBo;
import org.kuali.kpme.pm.pstnrptgrpsubcat.dao.PstnRptGrpSubCatDao;

public class PstnRptGrpSubCatServiceImpl implements PstnRptGrpSubCatService {
	
	private PstnRptGrpSubCatDao pstnRptGrpSubCatDao;

	@Override
	public PositionReportGroupSubCategory getPstnRptGrpSubCatById(String pmPstnRptGrpSubCatId) {
		return PositionReportGroupSubCategoryBo.to(pstnRptGrpSubCatDao.getPstnRptGrpSubCatById(pmPstnRptGrpSubCatId));
	}

	public void setPstnRptGrpSubCatDao(PstnRptGrpSubCatDao pstnRptGrpSubCatDao) {
		this.pstnRptGrpSubCatDao = pstnRptGrpSubCatDao;
	}

	public PstnRptGrpSubCatDao getPstnRptGrpSubCatDao() {
		return pstnRptGrpSubCatDao;
	}

}
