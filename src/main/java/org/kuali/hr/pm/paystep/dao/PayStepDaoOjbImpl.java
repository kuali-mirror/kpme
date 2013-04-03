package org.kuali.hr.pm.paystep.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.pm.paystep.PayStep;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PayStepDaoOjbImpl extends PlatformAwareDaoBaseOjb implements
		PayStepDao {

	private static final ImmutableList<String> PS_EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
																									    .add("payStep")
																									    .add("institution")
																									    .add("campus")
																									    .add("salaryGroup")
																									    .add("payGrade")
																									    .build();
	
	@Override
	public PayStep getPayStepById(String payStepId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPayStepId", payStepId);

        Query query = QueryFactory.newQuery(PayStep.class, crit);
        return (PayStep) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayStep> getPaySteps(String payStep, String institution,
			String campus, String salaryGroup, String payGrade, String active) {
		List<PayStep> results = new ArrayList<PayStep>();
		
		Criteria crit = new Criteria();
		if(StringUtils.isNotBlank(payStep))
			crit.addEqualTo("payStep", payStep);
		if(StringUtils.isNotBlank(institution)
				&& !StringUtils.equals(institution, TkConstants.WILDCARD_CHARACTER))
			crit.addEqualTo("institution", institution);
		if(StringUtils.isNotBlank(campus)
				&& !StringUtils.equals(campus, TkConstants.WILDCARD_CHARACTER))
			crit.addEqualTo("campus", campus);
		if(StringUtils.isNotBlank(salaryGroup))
			crit.addEqualTo("salaryGroup", salaryGroup);
		if(StringUtils.isNotBlank(payGrade))
			crit.addEqualTo("payGrade", payGrade);
		
		Criteria activeFilter = new Criteria();
		if(StringUtils.isNotBlank(active)) {
			activeFilter.addEqualTo("active",active);
		}
		else
			activeFilter.addNotNull("active");

		crit.addAndCriteria(activeFilter);
		
		Query query = QueryFactory.newQuery(PayStep.class, crit);
		
		results.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
		
		return results;
	}

}
