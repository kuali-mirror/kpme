package org.kuali.hr.pm.positionreporttype.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.pm.positionreporttype.PositionReportType;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class PositionReportTypeDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionReportTypeDao {
	
	 private static final ImmutableList<String> PRT_EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
																	     .add("positionReportType")
																	     .build();
	
	@Override
	public PositionReportType getPositionReportTypeById(String pmPositionReportTypeId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionReportTypeId", pmPositionReportTypeId);

        Query query = QueryFactory.newQuery(PositionReportType.class, crit);
        return (PositionReportType) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public PositionReportType getPositionReportTypeByTypeAndDate(String positionReportType, Date asOfDate) {
		Criteria root = new Criteria();

        root.addEqualTo("positionReportType", positionReportType); 
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionReportType.class, asOfDate, PRT_EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionReportType.class, PRT_EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionReportType.class, root);
        Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

        return (PositionReportType) obj;
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
