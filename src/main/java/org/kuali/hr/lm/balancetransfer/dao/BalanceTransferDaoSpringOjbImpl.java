/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.hr.lm.balancetransfer.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;

import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.lm.balancetransfer.BalanceTransfer;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class BalanceTransferDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements
		BalanceTransferDao {
	
	private static final Logger LOG = Logger.getLogger(BalanceTransfer.class);

	@Override
	public List<BalanceTransfer> getAllBalanceTransfersForPrincipalId(
			String principalId) {
		Criteria crit = new Criteria();
		List<BalanceTransfer> balanceTransfers = new ArrayList<BalanceTransfer>();
		crit.addEqualTo("principalId",principalId);
		Query query = QueryFactory.newQuery(BalanceTransfer.class,crit);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(c != null)
			balanceTransfers.addAll(c);
		
		return balanceTransfers;
	}

	@Override
	public List<BalanceTransfer> getAllBalanceTransferForPrincipalIdAsOfDate(
			String principalId, Date effectiveDate) {
		List<BalanceTransfer> balanceTransfers = new ArrayList<BalanceTransfer>();
		Criteria crit = new Criteria();
		crit.addEqualTo("principalId",principalId);
		Criteria effDate = new Criteria();
		effDate.addGreaterOrEqualThan("effectiveDate", effectiveDate);
		crit.addAndCriteria(effDate);
		Query query = QueryFactory.newQuery(BalanceTransfer.class,crit);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(c != null)
			balanceTransfers.addAll(c);
		
		return balanceTransfers;
	}

	@Override
	public List<BalanceTransfer> getAllBalanceTransferByEffectiveDate(
			Date effectiveDate) {
		List<BalanceTransfer> balanceTransfers = new ArrayList<BalanceTransfer>();
		Criteria effDate = new Criteria();
		effDate.addGreaterOrEqualThan("effectiveDate", effectiveDate);
		Query query = QueryFactory.newQuery(BalanceTransfer.class,effDate);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(c != null)
			balanceTransfers.addAll(c);
		
		return balanceTransfers;
	}

	@Override
	public BalanceTransfer getBalanceTransferById(String balanceTransferId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("balanceTransferId",balanceTransferId);
		Query query = QueryFactory.newQuery(BalanceTransfer.class,crit);
		return (BalanceTransfer) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<BalanceTransfer> getAllBalanceTransfersMarkedPayoutForPrincipalId(
			String principalId) {
		Criteria crit = new Criteria();
		List<BalanceTransfer> balanceTransfers = new ArrayList<BalanceTransfer>();
		crit.addEqualTo("principalId",principalId);
		Criteria payoutCrit = new Criteria();
		payoutCrit.addNotNull("earnCode");
		crit.addAndCriteria(payoutCrit);
		Query query = QueryFactory.newQuery(BalanceTransfer.class,crit);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(c != null)
			balanceTransfers.addAll(c);
		
		return balanceTransfers;
	}

	@Override
	public List<BalanceTransfer> getAllBalanceTransfersForAccrualCategoryRuleByDate(
			String accrualRuleId, Date asOfDate) {
		Criteria crit = new Criteria();
		List<BalanceTransfer> balanceTransfers = new ArrayList<BalanceTransfer>();
		crit.addGreaterOrEqualThan("effectiveDate",asOfDate);
		Criteria accrualCategory = new Criteria();
		accrualCategory.addEqualTo("accrualCategoryRule", accrualRuleId);
		crit.addAndCriteria(accrualCategory);
		Query query = QueryFactory.newQuery(BalanceTransfer.class,crit);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(c != null)
			balanceTransfers.addAll(c);
		
		return balanceTransfers;
	}

	@Override
	public List<BalanceTransfer> getBalanceTransfers(String viewPrincipal, Date beginPeriodDate, Date endPeriodDate) {
		// TODO Auto-generated method stub
		List<BalanceTransfer> balanceTransfers = new ArrayList<BalanceTransfer>();
		Criteria crit = new Criteria();
		crit.addEqualTo("principalId",viewPrincipal);
		
		Criteria effDate = new Criteria();
		effDate.addGreaterOrEqualThan("effectiveDate", beginPeriodDate);
		effDate.addLessOrEqualThan("effectiveDate", endPeriodDate);
		
		crit.addAndCriteria(effDate);
		
		Query query = QueryFactory.newQuery(BalanceTransfer.class,crit);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(c != null)
			balanceTransfers.addAll(c);
		
		return balanceTransfers;
	}
}
