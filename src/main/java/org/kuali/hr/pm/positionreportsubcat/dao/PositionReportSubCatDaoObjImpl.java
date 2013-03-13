package org.kuali.hr.pm.positionreportsubcat.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.pm.positionreportsubcat.PositionReportSubCategory;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PositionReportSubCatDaoObjImpl extends PlatformAwareDaoBaseOjb  implements PositionReportSubCatDao {
	 private static final ImmutableList<String> PRSC_EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
																		     .add("positionReportSubCat")
																		     .add("institution")
																		     .add("campus")
																		     .build();
	
	public PositionReportSubCategory getPositionReportSubCatById(
			String pmPositionReportSubCatId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportSubCatId", pmPositionReportSubCatId);

        Query query = QueryFactory.newQuery(PositionReportSubCategory.class, crit);
        return (PositionReportSubCategory) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	public List<PositionReportSubCategory> getPositionReportSubCat(String pstnRptSubCat, String institution, String campus, Date asOfDate) {
		List<PositionReportSubCategory> prscList = new ArrayList<PositionReportSubCategory>();
		Criteria root = new Criteria();
		if(StringUtils.isNotEmpty(pstnRptSubCat) 
				&& !StringUtils.equals(pstnRptSubCat, TkConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("positionReportSubCat", pstnRptSubCat); 
		}
		if(StringUtils.isNotEmpty(institution) 
				&& !StringUtils.equals(institution, TkConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("institution", institution); 
		}
		if(StringUtils.isNotEmpty(campus) 
				&& !StringUtils.equals(campus, TkConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("campus", campus); 
		}
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportSubCategory.class, asOfDate, PRSC_EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportSubCategory.class, PRSC_EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);
        Query query = QueryFactory.newQuery(PositionReportSubCategory.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prscList.addAll(c);
		
		return prscList;
	}

}
