package org.kuali.kpme.pm.pstnrptgrpsubcat.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.pstnrptgrpsubcat.PositionReportGroupSubCategory;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PstnRptGrpSubCatDaoObjImpl extends PlatformAwareDaoBaseOjb  implements PstnRptGrpSubCatDao{

	@Override
	public PositionReportGroupSubCategory getPstnRptGrpSubCatById(
			String pmPstnRptGrpSubCatId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPstnRptGrpSubCatId", pmPstnRptGrpSubCatId);

        Query query = QueryFactory.newQuery(PositionReportGroupSubCategory.class, crit);
        return (PositionReportGroupSubCategory) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

}
