package org.kuali.hr.pm.institution.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.core.util.OjbSubQueryUtil;
import org.kuali.hr.pm.institution.Institution;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import com.google.common.collect.ImmutableList;

public class InstitutionDaoOjbImpl extends PlatformAwareDaoBaseOjb implements InstitutionDao {
	
    private static final ImmutableList<String> EQUAL_TO_FIELDS = new ImmutableList.Builder<String>()
            .add("institutionCode")
            .build();
	
    
    @Override
    public Institution getInstitution(String institution, Date asOfDate) {
		Institution inst = null;

		Criteria root = new Criteria();

        java.sql.Date effDate = asOfDate == null ? null : new java.sql.Date(asOfDate.getTime());
		root.addEqualTo("institutionCode", institution);
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(Institution.class, effDate, EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(Institution.class, EQUAL_TO_FIELDS, false));
		
		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);
		
		
		Query query = QueryFactory.newQuery(Institution.class, root);
		Object obj = this.getPersistenceBrokerTemplate().getObjectByQuery(query);

		if (obj != null) {
			inst = (Institution) obj;
		}

		return inst;
    }
    
	@Override
	public List<Institution> getActiveInstitutions(Date asOfDate) {
		List<Institution> institutions = new ArrayList<Institution>();
		
		Criteria root = new Criteria();

		root.addEqualTo("effectiveDate",  OjbSubQueryUtil.getEffectiveDateSubQuery(Institution.class, asOfDate, EQUAL_TO_FIELDS, false));

		Criteria activeFilter = new Criteria(); // Inner Join For Activity
		activeFilter.addEqualTo("active", true);
		root.addAndCriteria(activeFilter);

		Query query = QueryFactory.newQuery(Institution.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(!c.isEmpty())
			institutions.addAll(c);
		
		return institutions;
	}

	@Override
	public List<Institution> getInstitutionByCode(String code) {
		List<Institution> institutions = new ArrayList<Institution>();
		
		Criteria root = new Criteria();

		root.addEqualTo("institutionCode", code);

		Query query = QueryFactory.newQuery(Institution.class, root);
		
		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if(!c.isEmpty())
			institutions.addAll(c);
		
		return institutions;

	}

	@Override
	public Institution getInstitutionById(String institutionId) {
		
		Criteria root = new Criteria();

		root.addEqualTo("institutionId", institutionId);

		Query query = QueryFactory.newQuery(Institution.class, root);
		
		return (Institution) this.getPersistenceBrokerTemplate().getObjectByQuery(query);

	}

}
