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
package org.kuali.kpme.pm.positionappointment.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.positionappointment.PositionAppointmentBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PositionAppointmentDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionAppointmentDao {

	private static final ImmutableList<String> PRG_BUSINESS_KEYS  = new ImmutableList.Builder<String>()
			.add("positionAppointment").add("institution").add("location")
			.build();

	public PositionAppointmentBo getPositionAppointmentById(String pmPositionAppointmentId) {
		
		Criteria crit = new Criteria();
		crit.addEqualTo("pmPositionAppointmentId", pmPositionAppointmentId);

		Query query = QueryFactory.newQuery(PositionAppointmentBo.class, crit);
		return (PositionAppointmentBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	public List<PositionAppointmentBo> getPositionAppointmentList(String positionAppointment, String description, String groupKeyCode, 
			LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory) {
		
		List<PositionAppointmentBo> prgList = new ArrayList<PositionAppointmentBo>();
		Criteria root = new Criteria();

		if (StringUtils.isNotEmpty(positionAppointment) 
				&& !ValidationUtils.isWildCard(positionAppointment)) {
			root.addLike("UPPER(positionAppointment)", positionAppointment.toUpperCase());
		}
		
		if (StringUtils.isNotEmpty(description) 
				&& !ValidationUtils.isWildCard(description)) {
			root.addLike("UPPER(description)", description.toUpperCase());
		}
		
		if (StringUtils.isNotEmpty(groupKeyCode)) {
			root.addLike("UPPER(groupKeyCode)", groupKeyCode.toUpperCase());
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
        
		if (StringUtils.isNotBlank(active)) {
        	Criteria activeFilter = new Criteria();
            if (StringUtils.equals(active, "Y")) {
                activeFilter.addEqualTo("active", true);
            } else if (StringUtils.equals(active, "N")) {
                activeFilter.addEqualTo("active", false);
            }
            root.addAndCriteria(activeFilter);
        }
		
		//root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionAppointment.class, asOfDate, PRG_BUSINESS_KEYS , false));
		//root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionAppointment.class, PRG_BUSINESS_KEYS , false));
		if (StringUtils.equals(showHistory, "N")) {
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(PositionAppointmentBo.class, effectiveDateFilter, PRG_BUSINESS_KEYS , false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionAppointmentBo.class, PRG_BUSINESS_KEYS , false));
        }
		

		Query query = QueryFactory.newQuery(PositionAppointmentBo.class, root);

		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (!c.isEmpty())
			prgList.addAll(c);

		return prgList;
	}
	
	public List<PositionAppointmentBo> getPositionAppointmentList(String positionAppointment, String groupKeyCode, LocalDate asOfDate) {
	
		List<PositionAppointmentBo> prgList = new ArrayList<PositionAppointmentBo>();
		Criteria root = new Criteria();

		if (StringUtils.isNotEmpty(positionAppointment) 
				&& !ValidationUtils.isWildCard(positionAppointment)) {
			root.addLike("UPPER(`pstn_appointment`)", positionAppointment.toUpperCase());
		}
		
		if (StringUtils.isNotEmpty(groupKeyCode)) {
			root.addLike("UPPER(groupKeyCode)", groupKeyCode.toUpperCase());
		}

		root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionAppointmentBo.class, asOfDate, PositionAppointmentBo.BUSINESS_KEYS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionAppointmentBo.class, PositionAppointmentBo.BUSINESS_KEYS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(PositionAppointmentBo.class, root);

		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (!c.isEmpty())
			prgList.addAll(c);

		return prgList;
	}
}
