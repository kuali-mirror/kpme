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
package org.kuali.hr.lm.leavedonation.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.lm.leavedonation.LeaveDonation;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class LeaveDonationDaoSpringOjbImpl extends PlatformAwareDaoBaseOjb implements LeaveDonationDao {
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("donorsPrincipalID")
            .add("donatedAccrualCategory")
            .add("amountDonated")
            .add("recipientsPrincipalID")
            .add("recipientsAccrualCategory")
            .add("amountReceived")
            .build();
    
	private static final Logger LOG = Logger.getLogger(LeaveDonationDaoSpringOjbImpl.class);

	@Override
	public LeaveDonation getLeaveDonation(String lmLeaveDonationId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("lmLeaveDonationId", lmLeaveDonationId);
		Query query = QueryFactory.newQuery(LeaveDonation.class, crit);
		return (LeaveDonation) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<LeaveDonation> getLeaveDonations(LocalDate fromEffdt, LocalDate toEffdt, String donorsPrincipalId, String donatedAccrualCategory, String amountDonated, String recipientsPrincipalId, String recipientsAccrualCategory, String amountReceived, String active, String showHist) {
        List<LeaveDonation> results = new ArrayList<LeaveDonation>();
    	
    	Criteria root = new Criteria();
    	
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

        if (StringUtils.isNotBlank(donorsPrincipalId)) {
            root.addLike("donorsPrincipalID", donorsPrincipalId);
        }
        
        if (StringUtils.isNotBlank(donatedAccrualCategory)) {
            root.addLike("donatedAccrualCategory", donatedAccrualCategory);
        }
        
        if (StringUtils.isNotBlank(amountDonated)) {
        	root.addLike("amountDonated", amountDonated);
        }
        
        if (StringUtils.isNotBlank(recipientsPrincipalId)) {
        	root.addLike("recipientsPrincipalID", recipientsPrincipalId);
        }
        
        if (StringUtils.isNotBlank(recipientsAccrualCategory)) {
        	root.addLike("recipientsAccrualCategory", recipientsAccrualCategory);
        }
        
        if (StringUtils.isNotBlank(amountReceived)) {
        	root.addLike("amountReceived", amountReceived);
        }
        
        if (StringUtils.isNotBlank(active)) {
        	Criteria activeFilter = new Criteria();
            if (StringUtils.equals(active, "Y")) {
                activeFilter.addEqualTo("active", true);
            } else if (StringUtils.equals(active, "N")) {
                activeFilter.addEqualTo("active", false);
            }
            root.addAndCriteria(activeFilter);
        }

        if (StringUtils.equals(showHist, "N")) {
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(LeaveDonation.class, effectiveDateFilter, EQUAL_TO_FIELDS, false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(LeaveDonation.class, EQUAL_TO_FIELDS, false));
        }

        Query query = QueryFactory.newQuery(LeaveDonation.class, root);
        results.addAll(getPersistenceBrokerTemplate().getCollectionByQuery(query));

        return results;
	}
	
}
