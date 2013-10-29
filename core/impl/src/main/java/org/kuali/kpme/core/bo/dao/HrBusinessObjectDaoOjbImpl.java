package org.kuali.kpme.core.bo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.core.api.bo.HrBusinessObjectContract;
import org.kuali.kpme.core.api.bo.dao.HrBusinessObjectDao;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class HrBusinessObjectDaoOjbImpl extends PlatformAwareDaoBaseOjb implements HrBusinessObjectDao {

	private static final String YES = "Y";
	private static final String ACTIVE = "active";
	private static final String EFFECTIVE_DATE = "effectiveDate";


	@SuppressWarnings("unchecked")
	@Override
	public <T extends HrBusinessObjectContract> List<T> getNewerVersionsOf(T bo) {
		List<T> retVal = new ArrayList<T>();
		
		// use bo's effective local date and the business key values to query T's table
		Date effectiveDate = this.extractEffectiveDate(bo);
		if(effectiveDate != null) {
			Criteria criteria = new Criteria();
			this.addBusinessKeyValuesToCriteria(criteria, bo);
			criteria.addEqualTo(ACTIVE, YES);
			criteria.addGreaterThan(EFFECTIVE_DATE, effectiveDate);
			Query query = QueryFactory.newQuery(bo.getClass(), criteria);
			retVal.addAll(this.getPersistenceBrokerTemplate().getCollectionByQuery(query));
		}
		
       	return retVal;
	}
	
	
	protected void addBusinessKeyValuesToCriteria(Criteria criteria, HrBusinessObjectContract bo) {
		// populate the criteria with the bo's business key value map
		for(Entry<String, Object> entry: bo.getBusinessKeyValuesMap().entrySet() ) {
			criteria.addEqualTo(entry.getKey(), entry.getValue());
		}
	}
	

	protected Date extractEffectiveDate(HrBusinessObjectContract bo) {
		Date retVal = null;
		if(bo.getEffectiveLocalDate() != null) {
			retVal = bo.getEffectiveLocalDate().toDate();
		}
		return retVal;				
	}

}
