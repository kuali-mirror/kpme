package org.kuali.kpme.pm.positionflag.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.utils.OjbSubQueryUtil;
import org.kuali.kpme.pm.positionflag.PositionFlag;
import org.kuali.kpme.pm.util.PmValidationUtils;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class PositionFlagDaoObjImpl extends PlatformAwareDaoBaseOjb implements PositionFlagDao {

	@Override
	public PositionFlag getPositionFlagById(String pmPositionFlagId) {
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionFlagId", pmPositionFlagId);

        Query query = QueryFactory.newQuery(PositionFlag.class, crit);
        return (PositionFlag) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
	}
	
	@Override
	public List<PositionFlag> getAllActivePositionFlags(String category, String name, LocalDate effDate) {
		List<PositionFlag> pfList = new ArrayList<PositionFlag>();
		Criteria root = new Criteria();

		if(StringUtils.isNotEmpty(category) 
				&& !PmValidationUtils.isWildCard(category)) {
			root.addEqualTo("category", category);  
		}
		if(StringUtils.isNotEmpty(name) 
				&& !PmValidationUtils.isWildCard(name)) {
			root.addEqualTo("positionFlagName", name); 
		}
        
        root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionFlag.class, effDate, PositionFlag.EQUAL_TO_FIELDS, false));
        root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(PositionFlag.class, PositionFlag.EQUAL_TO_FIELDS, false));
        
        Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionFlag.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			pfList.addAll(c);
		
		return pfList;
	}
	
	@Override
	public List<String> getAllActiveFlagCategories() {
		List<String> categoryList = new ArrayList<String>();
		Criteria root = new Criteria();
		root.addEqualTo("active", true); 
		Query query = QueryFactory.newQuery(PositionFlag.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        List<PositionFlag> pfList = new ArrayList<PositionFlag>();
        if(!c.isEmpty())
			pfList.addAll(c);
       
        for(PositionFlag aFlag : pfList) {
        	if(aFlag != null && StringUtils.isNotEmpty(aFlag.getCategory())
        			&& !categoryList.contains(aFlag.getCategory())) {
        		categoryList.add(aFlag.getCategory());
        	}
        }
        return categoryList;
	}
	
	@Override
	public List<PositionFlag> getAllActivePositionFlagsWithCategory(String category, LocalDate effDate) {
		List<PositionFlag> pfList = new ArrayList<PositionFlag>();
		Criteria root = new Criteria();
		if(StringUtils.isEmpty(category)) {
			return pfList;
		}
		root.addEqualTo("category", category);
		
		if(effDate != null) {
			root.addLessOrEqualThan("effectiveDAte", effDate);
			root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQuery(PositionFlag.class, effDate, PositionFlag.EQUAL_TO_FIELDS, false));
		}
		Criteria activeFilter = new Criteria();
        activeFilter.addEqualTo("active", true);
        root.addAndCriteria(activeFilter);

        Query query = QueryFactory.newQuery(PositionFlag.class, root);
        
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		if(!c.isEmpty())
			pfList.addAll(c);
		
		return pfList;
	}

}
