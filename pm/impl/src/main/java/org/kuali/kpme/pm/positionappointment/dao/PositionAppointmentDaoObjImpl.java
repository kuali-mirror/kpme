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
package org.kuali.kpme.pm.positionappointment.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.positionappointment.PositionAppointment;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PositionAppointmentDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionAppointmentDao {

	private static final ImmutableList<String> PRG_EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
			.add("positionAppointment").add("institution").add("location")
			.build();

	public PositionAppointment getPositionAppointmentById(String pmPositionAppointmentId) {
		
		Criteria crit = new Criteria();
		crit.addEqualTo("pmPositionAppointmentId", pmPositionAppointmentId);

		Query query = QueryFactory.newQuery(PositionAppointment.class, crit);
		return (PositionAppointment) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	public List<PositionAppointment> getPositionAppointmentList(String positionAppointment, String institution, String location, LocalDate asOfDate) {
		
		List<PositionAppointment> prgList = new ArrayList<PositionAppointment>();
		Criteria root = new Criteria();

		if (StringUtils.isNotEmpty(positionAppointment) 
				&& !ValidationUtils.isWildCard(positionAppointment)) {
			root.addEqualTo("positionAppointment", positionAppointment);
		}
		
		if (StringUtils.isNotEmpty(institution) 
				&& !ValidationUtils.isWildCard(institution)) {
			root.addEqualTo("institution", institution);
		}
		
		if (StringUtils.isNotEmpty(location) 
				&& !ValidationUtils.isWildCard(location)) {
			root.addEqualTo("location", location);
		}

		root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionAppointment.class, asOfDate, PRG_EQUAL_TO_FIELDS, false));
		root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionAppointment.class, PRG_EQUAL_TO_FIELDS, false));

		Criteria activeFilter = new Criteria();
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(PositionAppointment.class, root);

		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (!c.isEmpty())
			prgList.addAll(c);

		return prgList;
	}

}
