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
package org.kuali.hr.lm.leavepayout.dao;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;

import org.kuali.hr.lm.leavepayout.LeavePayout;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;


public class LeavePayoutDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements
        LeavePayoutDao {

    private static final Logger LOG = Logger.getLogger(LeavePayout.class);

    @Override
    public List<LeavePayout> getAllLeavePayoutsForPrincipalId(
            String principalId) {
        Criteria crit = new Criteria();
        List<LeavePayout> leavePayouts = new ArrayList<LeavePayout>();
        crit.addEqualToField("principalId",principalId);
        Query query = QueryFactory.newQuery(LeavePayout.class,crit);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if(c != null)
            leavePayouts.addAll(c);

        return leavePayouts;
    }

    @Override
    public List<LeavePayout> getAllLeavePayoutsForPrincipalIdAsOfDate(
            String principalId, Date effectiveDate) {
        List<LeavePayout> leavePayouts = new ArrayList<LeavePayout>();
        Criteria crit = new Criteria();
        crit.addEqualToField("principalId",principalId);
        Criteria effDate = new Criteria();
        effDate.addGreaterOrEqualThanField("effectiveDate", effectiveDate);
        crit.addAndCriteria(effDate);
        Query query = QueryFactory.newQuery(LeavePayout.class,crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if(c != null)
            leavePayouts.addAll(c);

        return leavePayouts;
    }

    @Override
    public List<LeavePayout> getAllLeavePayoutsByEffectiveDate(
            Date effectiveDate) {
        List<LeavePayout> leavePayouts = new ArrayList<LeavePayout>();
        Criteria effDate = new Criteria();
        effDate.addGreaterOrEqualThanField("effectiveDate", effectiveDate);
        Query query = QueryFactory.newQuery(LeavePayout.class,effDate);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if(c != null)
            leavePayouts.addAll(c);

        return leavePayouts;
    }

    @Override
    public LeavePayout getLeavePayoutById(String lmLeavePayoutId) {
        Criteria crit = new Criteria();
        crit.addEqualToField("lmLeavePayoutId",lmLeavePayoutId);
        Query query = QueryFactory.newQuery(LeavePayout.class,crit);
        return (LeavePayout) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    @Override
    public List<LeavePayout> getAllLeavePayoutsMarkedPayoutForPrincipalId(
            String principalId) {
        Criteria crit = new Criteria();
        List<LeavePayout> leavePayouts = new ArrayList<LeavePayout>();
        crit.addEqualToField("principalId",principalId);
        Criteria payoutCrit = new Criteria();
        payoutCrit.addNotNull("earnCode");
        crit.addAndCriteria(payoutCrit);
        Query query = QueryFactory.newQuery(LeavePayout.class,crit);

        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if(c != null)
            leavePayouts.addAll(c);

        return leavePayouts;
    }

}
