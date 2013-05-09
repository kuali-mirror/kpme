package org.kuali.kpme.pm.pstncontracttype.dao;

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
import org.kuali.kpme.pm.pstncontracttype.PstnContractType;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PstnContractTypeDaoObjImpl extends PlatformAwareDaoBaseOjb implements PstnContractTypeDao {

	@Override
	public PstnContractType getPstnContractTypeById(
			String pmCntrctTypeId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmCntrctTypeId", pmCntrctTypeId);

        Query query = QueryFactory.newQuery(PstnContractType.class, crit);
        return (PstnContractType) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}

	@Override
	public List<PstnContractType> getPstnContractTypeList(String institution, String campus, LocalDate asOfDate) {
		List<PstnContractType> pctList = new ArrayList<PstnContractType>();
		Criteria root = new Criteria();
 		if(StringUtils.isNotEmpty(institution) 
				&& !StringUtils.equals(institution, HrConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("institution", institution); 
		}
		if(StringUtils.isNotEmpty(campus) 
				&& !StringUtils.equals(campus, HrConstants.WILDCARD_CHARACTER)) {
			root.addEqualTo("campus", campus); 
		}
        
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PstnContractType.class, asOfDate, PstnContractType.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PstnContractType.class, PstnContractType.EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PstnContractType.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			pctList.addAll(c);
		
		return pctList;
	}

}
