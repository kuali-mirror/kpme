package org.kuali.kpme.pm.positionappointment.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.utils.OjbSubQueryUtil;
import org.kuali.kpme.core.util.HrConstants;
import org.kuali.kpme.pm.positionappointment.PositionAppointment;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PositionAppointmentDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionAppointmentDao {

	private static final ImmutableList<String> PRG_EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
			.add("positionAppointment").add("institution").add("campus")
			.build();

	public PositionAppointment getPositionAppointmentById(String pmPositionAppointmentId) {
		
		Criteria crit = new Criteria();
		crit.addEqualTo("pmPositionAppointmentId", pmPositionAppointmentId);

		Query query = QueryFactory.newQuery(PositionAppointment.class, crit);
		return (PositionAppointment) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	public List<PositionAppointment> getPositionAppointmentList(String positionAppointment, String institution, String campus, LocalDate asOfDate) {
		
		List<PositionAppointment> prgList = new ArrayList<PositionAppointment>();
		Criteria root = new Criteria();

		if (StringUtils.isNotEmpty(positionAppointment) && !StringUtils.equals(positionAppointment, HrConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("positionAppointment", positionAppointment);
		}
		
		if (StringUtils.isNotEmpty(institution) && !StringUtils.equals(institution, HrConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("institution", institution);
		}
		
		if (StringUtils.isNotEmpty(campus) && !StringUtils.equals(campus, HrConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("campus", campus);
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
