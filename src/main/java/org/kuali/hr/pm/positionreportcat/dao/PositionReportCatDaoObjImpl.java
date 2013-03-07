package org.kuali.hr.pm.positionreportcat.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.pm.positionreportcat.PositionReportCategory;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PositionReportCatDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionReportCatDao {

	private static final ImmutableList<String> PRG_EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
																		    .add("positionReportCat")
																		    .build();
	@Override
	public PositionReportCategory getPositionReportCatById(
			String pmPositionReportCatId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportCatId", pmPositionReportCatId);

        Query query = QueryFactory.newQuery(PositionReportCategory.class, crit);
        return (PositionReportCategory) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PositionReportCategory> getPositionReportCatList(String positionReportCat, String positionReportType, String institution, String campus, Date asOfDate) {
		List<PositionReportCategory> prcList = new ArrayList<PositionReportCategory>();
		Criteria root = new Criteria();
		
		if(StringUtils.isNotEmpty(positionReportCat) 
				&& !StringUtils.equals(positionReportCat, TkConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("positionReportCat", positionReportCat);  
		}
		if(StringUtils.isNotEmpty(positionReportType) 
				&& !StringUtils.equals(positionReportType, TkConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("positionReportType", positionReportType);  
		}
		if(StringUtils.isNotEmpty(institution) 
				&& !StringUtils.equals(institution, TkConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("institution", institution); 
		}
		if(StringUtils.isNotEmpty(campus) 
				&& !StringUtils.equals(campus, TkConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("campus", campus); 
		}
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportCategory.class, asOfDate, PRG_EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportCategory.class, PRG_EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportCategory.class, root);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prcList.addAll(c);
		
		return prcList;
	}

}
