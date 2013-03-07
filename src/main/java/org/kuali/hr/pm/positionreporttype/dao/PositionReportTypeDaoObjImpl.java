package org.kuali.hr.pm.positionreporttype.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.pm.positionreporttype.PositionReportType;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PositionReportTypeDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionReportTypeDao {
	
	 private static final ImmutableList<String> PRT_EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
																	     .add("positionReportType")
																	     .add("institution")
																	     .add("campus")
																	     .build();
	
	@Override
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportTypeId", pmPositionReportTypeId);

        Query query = QueryFactory.newQuery(PositionReportType.class, crit);
        return (PositionReportType) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PositionReportType> getPositionReportTypeList(String positionReportType, String institution, String campus, Date asOfDate) {
		List<PositionReportType> prtList = new ArrayList<PositionReportType>();
		Criteria root = new Criteria();
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
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportType.class, asOfDate, PRT_EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportType.class, PRT_EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);
        Query query = QueryFactory.newQuery(PositionReportType.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prtList.addAll(c);
		
		return prtList;
	}
	
	@Override
	public List<PositionReportType> getPositionReportTypeListByType(String positionReportType) {
		List<PositionReportType> prtList = new ArrayList<PositionReportType>();
		Criteria root = new Criteria();
		root.addEqualTo("positionReportType", positionReportType);
		Query query = QueryFactory.newQuery(PositionReportType.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prtList.addAll(c);
		
		return prtList;
	}
	
	@Override
	public List<PositionReportType> getPrtListWithInstitutionCodeAndDate(String institutionCode, Date asOfDate) {
		List<PositionReportType> prtList = new ArrayList<PositionReportType>();
		Criteria root = new Criteria();

        root.addEqualTo("institution", institutionCode); 
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportType.class, asOfDate, PRT_EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportType.class, PRT_EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportType.class, root);		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prtList.addAll(c);
		
		return prtList;	
	}
	
	@Override
	public List<PositionReportType> getPrtListWithCampusAndDate(String campus,Date asOfDate) {
		List<PositionReportType> prtList = new ArrayList<PositionReportType>();
		Criteria root = new Criteria();

        root.addEqualTo("campus", campus); 
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportType.class, asOfDate, PRT_EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportType.class, PRT_EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportType.class, root);		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			prtList.addAll(c);
		
		return prtList;	
	}
}
