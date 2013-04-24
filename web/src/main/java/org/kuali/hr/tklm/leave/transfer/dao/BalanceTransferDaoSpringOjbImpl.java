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
package org.kuali.hr.tklm.leave.transfer.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.hr.tklm.leave.transfer.BalanceTransfer;
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
			String principalId, LocalDate effectiveDate) {
		List<BalanceTransfer> balanceTransfers = new ArrayList<BalanceTransfer>();
		Criteria crit = new Criteria();
		crit.addEqualTo("principalId",principalId);
		Criteria effDate = new Criteria();
		effDate.addGreaterOrEqualThan("effectiveDate", effectiveDate.toDate());
		crit.addAndCriteria(effDate);
		Query query = QueryFactory.newQuery(BalanceTransfer.class,crit);
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(c != null)
			balanceTransfers.addAll(c);
		
		return balanceTransfers;
	}

	@Override
	public List<BalanceTransfer> getAllBalanceTransferByEffectiveDate(
			LocalDate effectiveDate) {
		List<BalanceTransfer> balanceTransfers = new ArrayList<BalanceTransfer>();
		Criteria effDate = new Criteria();
		effDate.addGreaterOrEqualThan("effectiveDate", effectiveDate.toDate());
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
			String accrualRuleId, LocalDate asOfDate) {
		Criteria crit = new Criteria();
		List<BalanceTransfer> balanceTransfers = new ArrayList<BalanceTransfer>();
		crit.addGreaterOrEqualThan("effectiveDate",asOfDate.toDate());
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
	public List<BalanceTransfer> getBalanceTransfers(String viewPrincipal, LocalDate beginPeriodDate, LocalDate endPeriodDate) {
		// TODO Auto-generated method stub
		List<BalanceTransfer> balanceTransfers = new ArrayList<BalanceTransfer>();
		Criteria crit = new Criteria();
		crit.addEqualTo("principalId",viewPrincipal);
		
		Criteria effDate = new Criteria();
		effDate.addGreaterOrEqualThan("effectiveDate", beginPeriodDate.toDate());
		effDate.addLessOrEqualThan("effectiveDate", endPeriodDate.toDate());
		
		crit.addAndCriteria(effDate);
		
		Query query = QueryFactory.newQuery(BalanceTransfer.class,crit);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(c != null)
			balanceTransfers.addAll(c);
		
		return balanceTransfers;
	}

    @Override
    public void saveOrUpdate(BalanceTransfer balanceTransfer) {
        this.getPersistenceBrokerTemplate().store(balanceTransfer);
    }
    
    @Override
    public List<BalanceTransfer> getBalanceTransfers(String principalId, String fromAccrualCategory, String transferAmount, String toAccrualCategory, String amountTransferred, String forfeitedAmount, LocalDate fromEffdt, LocalDate toEffdt) {
        List<BalanceTransfer> results = new ArrayList<BalanceTransfer>();
    	
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(principalId)) {
            root.addLike("principalId", principalId);
        }
        
        if (StringUtils.isNotBlank(fromAccrualCategory)) {
            root.addLike("fromAccrualCategory", fromAccrualCategory);
        }
        
        if (StringUtils.isNotBlank(transferAmount)) {
        	root.addLike("transferAmount", transferAmount);
        }
        
        if (StringUtils.isNotBlank(toAccrualCategory)) {
        	root.addLike("toAccrualCategory", toAccrualCategory);
        }
        
        if (StringUtils.isNotBlank(amountTransferred)) {
        	root.addLike("amountTransferred", amountTransferred);
        }
        
        if (StringUtils.isNotBlank(forfeitedAmount)) {
        	root.addLike("forfeitedAmount", forfeitedAmount);
        }
        
        Criteria effectiveDateFilter = new Criteria();
        if (fromEffdt != null) {
            effectiveDateFilter.addGreaterOrEqualThan("effectiveDate", fromEffdt.toDate());
        }
        if (toEffdt != null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", toEffdt.toDate());
        }
        if (fromEffdt == null && toEffdt == null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", LocalDate.now().toDate());
        }
        root.addAndCriteria(effectiveDateFilter);

        Query query = QueryFactory.newQuery(BalanceTransfer.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
    }

}
