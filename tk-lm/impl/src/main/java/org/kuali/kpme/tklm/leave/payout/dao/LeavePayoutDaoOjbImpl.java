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
package org.kuali.kpme.tklm.leave.payout.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.tklm.leave.payout.LeavePayout;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;


public class LeavePayoutDaoOjbImpl extends PlatformAwareDaoBaseOjb implements
        LeavePayoutDao {

    private static final Logger LOG = Logger.getLogger(LeavePayout.class);

    @Override
    public List<LeavePayout> getAllLeavePayoutsForPrincipalId(
            String principalId) {
        Criteria crit = new Criteria();
        List<LeavePayout> leavePayouts = new ArrayList<LeavePayout>();
        crit.addEqualTo("principalId",principalId);
        Query query = QueryFactory.newQuery(LeavePayout.class,crit);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if(c != null)
            leavePayouts.addAll(c);

        return leavePayouts;
    }

    @Override
    public List<LeavePayout> getAllLeavePayoutsForPrincipalIdAsOfDate(
            String principalId, LocalDate effectiveDate) {
        List<LeavePayout> leavePayouts = new ArrayList<LeavePayout>();
        Criteria crit = new Criteria();
        crit.addEqualTo("principalId",principalId);
        Criteria effDate = new Criteria();
        effDate.addGreaterOrEqualThan("effectiveDate", effectiveDate.toDate());
        crit.addAndCriteria(effDate);
        Query query = QueryFactory.newQuery(LeavePayout.class,crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if(c != null)
            leavePayouts.addAll(c);

        return leavePayouts;
    }

    @Override
    public List<LeavePayout> getAllLeavePayoutsByEffectiveDate(
    		LocalDate effectiveDate) {
        List<LeavePayout> leavePayouts = new ArrayList<LeavePayout>();
        Criteria effDate = new Criteria();
        effDate.addGreaterOrEqualThan("effectiveDate", effectiveDate.toDate());
        Query query = QueryFactory.newQuery(LeavePayout.class,effDate);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if(c != null)
            leavePayouts.addAll(c);

        return leavePayouts;
    }

    @Override
    public LeavePayout getLeavePayoutById(String lmLeavePayoutId) {
        Criteria crit = new Criteria();
        crit.addEqualTo("lmLeavePayoutId",lmLeavePayoutId);
        Query query = QueryFactory.newQuery(LeavePayout.class,crit);
        return (LeavePayout) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public List<LeavePayout> getAllLeavePayoutsMarkedPayoutForPrincipalId(
            String principalId) {
        Criteria crit = new Criteria();
        List<LeavePayout> leavePayouts = new ArrayList<LeavePayout>();
        crit.addEqualTo("principalId",principalId);
        Criteria payoutCrit = new Criteria();
        payoutCrit.addNotNull("earnCode");
        crit.addAndCriteria(payoutCrit);
        Query query = QueryFactory.newQuery(LeavePayout.class,crit);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if(c != null)
            leavePayouts.addAll(c);

        return leavePayouts;
    }

	@Override
	public List<LeavePayout> getLeavePayouts(String viewPrincipal,
			LocalDate beginPeriodDate, LocalDate endPeriodDate) {
		// TODO Auto-generated method stub
		List<LeavePayout> leavePayouts = new ArrayList<LeavePayout>();
		Criteria crit = new Criteria();
		crit.addEqualTo("principalId",viewPrincipal);
		
		Criteria effDate = new Criteria();
		effDate.addGreaterOrEqualThan("effectiveDate", beginPeriodDate.toDate());
		effDate.addLessOrEqualThan("effectiveDate", endPeriodDate.toDate());
		
		crit.addAndCriteria(effDate);
		
		Query query = QueryFactory.newQuery(LeavePayout.class,crit);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(c != null)
			leavePayouts.addAll(c);
		
		return leavePayouts;
	}

	@Override
	public void saveOrUpdate(LeavePayout payout) {
		this.getPersistenceBrokerTemplate().store(payout);
	}

	@Override
	public List<LeavePayout> getLeavePayouts(String principalId, String fromAccrualCategory, String payoutAmount, String earnCode, String forfeitedAmount, LocalDate fromEffdt, LocalDate toEffdt) {
        List<LeavePayout> results = new ArrayList<LeavePayout>();
    	
    	Criteria root = new Criteria();

        if (StringUtils.isNotBlank(principalId)) {
            root.addLike("principalId", principalId);
        }
        
        if (StringUtils.isNotBlank(fromAccrualCategory)) {
            root.addLike("fromAccrualCategory", fromAccrualCategory);
        }
        
        if (StringUtils.isNotBlank(payoutAmount)) {
        	root.addLike("payoutAmount", payoutAmount);
        }
        
        if (StringUtils.isNotBlank(earnCode)) {
        	root.addLike("earnCode", earnCode);
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

        Query query = QueryFactory.newQuery(LeavePayout.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
	}

}
