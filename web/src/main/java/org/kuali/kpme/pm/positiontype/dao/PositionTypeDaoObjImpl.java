package org.kuali.kpme.pm.positiontype.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.pm.positiontype.PositionType;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PositionTypeDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionTypeDao {

	private static final ImmutableList<String> PSTNTYP_EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
																		    .add("positionType")
																		    .add("institution")
																		    .add("campus")
																		    .build();
	@Override
	public PositionType getPositionTypeById(
			String pmPositionTypeId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionTypeId", pmPositionTypeId);

        Query query = QueryFactory.newQuery(PositionType.class, crit);
        return (PositionType) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PositionType> getPositionTypeList(String positionType, String institution, String campus, LocalDate asOfDate) {
		List<PositionType> prgList = new ArrayList<PositionType>();
		Criteria root = new Criteria();

		if(StringUtils.isNotEmpty(positionType) 
				&& !StringUtils.equals(positionType, TkConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("positionType", positionType);  
		}
		if(StringUtils.isNotEmpty(institution) 
				&& !StringUtils.equals(institution, TkConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("institution", institution); 
		}
		if(StringUtils.isNotEmpty(campus) 
				&& !StringUtils.equals(campus, TkConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("campus", campus); 
		}
        
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionType.class, asOfDate, PSTNTYP_EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionType.class, PSTNTYP_EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionType.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prgList.addAll(c);
		
		return prgList;
	}

}
