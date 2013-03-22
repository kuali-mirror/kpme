package org.kuali.hr.pm.paystep.dao;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.pm.paystep.PayStep;
import org.kuali.hr.pm.positionreportcat.PositionReportCategory;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PayStepDaoOjbImpl extends PlatformAwareDaoBaseOjb implements
		PayStepDao {

	private static final ImmutableList<String> PRG_EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
																									    .add("payStep")
																									    .build();
	
	@Override
	public PayStep getPayStepById(String payStepId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPayStepId", payStepId);

        Query query = QueryFactory.newQuery(PayStep.class, crit);
        return (PayStep) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

	}

}
