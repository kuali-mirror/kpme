package org.kuali.kpme.pm.positiondepartmentaffiliation.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.utils.OjbSubQueryUtil;
import org.kuali.kpme.pm.positiondepartmentaffiliation.PositionDepartmentAffiliation;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionDepartmentAffiliationDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionDepartmentAffiliationDao {

	@Override
	public PositionDepartmentAffiliation getPositionDepartmentAffiliationById(String pmPositionDeptAfflId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionDeptAfflId", pmPositionDeptAfflId);
        Query query = QueryFactory.newQuery(PositionDepartmentAffiliation.class, crit);
        return (PositionDepartmentAffiliation) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PositionDepartmentAffiliation> getPositionDepartmentAffiliationList(String positionDeptAfflType, LocalDate asOfDate) {
	
		List<PositionDepartmentAffiliation> pdaList = new ArrayList<PositionDepartmentAffiliation>();
		Criteria root = new Criteria();

		
		if(StringUtils.isNotEmpty(positionDeptAfflType)) {
			root.addEqualTo("positionDeptAfflType", positionDeptAfflType); 
		}
        
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionDepartmentAffiliation.class, asOfDate, PositionDepartmentAffiliation.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionDepartmentAffiliation.class, PositionDepartmentAffiliation.EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionDepartmentAffiliation.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			pdaList.addAll(c);
		
		return pdaList;
	}

}
