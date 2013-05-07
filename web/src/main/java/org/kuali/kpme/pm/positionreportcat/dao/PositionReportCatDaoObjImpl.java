package org.kuali.kpme.pm.positionreportcat.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.utils.OjbSubQueryUtil;
import org.kuali.kpme.pm.positionreportcat.PositionReportCategory;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PositionReportCatDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionReportCatDao {

	@Override
	public PositionReportCategory getPositionReportCatById(
			String pmPositionReportCatId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportCatId", pmPositionReportCatId);

        Query query = QueryFactory.newQuery(PositionReportCategory.class, crit);
        return (PositionReportCategory) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PositionReportCategory> getPositionReportCatList(String positionReportCat, String positionReportType, String institution, String campus, LocalDate asOfDate) {
		List<PositionReportCategory> prcList = new ArrayList<PositionReportCategory>();
		Criteria root = new Criteria();
		
		if(StringUtils.isNotEmpty(positionReportCat) 
				&& !PmValidationUtils.isWildCard(positionReportCat)) {
			root.addEqualTo("positionReportCat", positionReportCat);  
		}
		if(StringUtils.isNotEmpty(positionReportType) 
				&& !PmValidationUtils.isWildCard(positionReportType)) {
			root.addEqualTo("positionReportType", positionReportType);  
		}
		if(StringUtils.isNotEmpty(institution) 
				&& !PmValidationUtils.isWildCard(institution)) {
			root.addEqualTo("institution", institution); 
		}
		if(StringUtils.isNotEmpty(campus) 
				&& !PmValidationUtils.isWildCard(campus)) {
			root.addEqualTo("campus", campus); 
		}
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportCategory.class, asOfDate, PositionReportCategory.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportCategory.class, PositionReportCategory.EQUAL_TO_FIELDS, false));
        
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
