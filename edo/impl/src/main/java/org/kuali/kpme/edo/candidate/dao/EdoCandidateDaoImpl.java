package org.kuali.kpme.edo.candidate.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.util.OjbSubQueryUtil;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.edo.candidate.EdoCandidateBo;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

/**
 * Created with IntelliJ IDEA.
 * User: tcbradley
 * Date: 10/25/12
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoCandidateDaoImpl extends PlatformAwareDaoBaseOjb implements EdoCandidateDao {
	

    private static final Logger LOG = Logger.getLogger(EdoCandidateDaoImpl.class);

    public EdoCandidateBo getCandidate(String edoCandidateID) {
    	
        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("edoCandidateID", edoCandidateID);

        Query query = QueryFactory.newQuery(EdoCandidateBo.class, cConditions);
        
        return (EdoCandidateBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    public EdoCandidateBo getCandidateByUsername(String principalName) {

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("principalName", principalName);

        Query query = QueryFactory.newQuery(EdoCandidateBo.class, cConditions);
        
        return (EdoCandidateBo) this.getPersistenceBrokerTemplate().getObjectByQuery(query);
    }

    public List<EdoCandidateBo> getCandidateListByUsername(String principalName) {
        List<EdoCandidateBo> candidateLts = new ArrayList<EdoCandidateBo>();

        Criteria cConditions = new Criteria();
       
        cConditions.addEqualTo("principalName", principalName);

        Query query = QueryFactory.newQuery(EdoCandidateBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
       
        if (c != null && c.size() != 0) {
        	candidateLts.addAll(c);
        }
       
        return candidateLts;
    }


    public List<EdoCandidateBo> getEdoCandidateList () {

        List<EdoCandidateBo> candidateLists = new LinkedList<EdoCandidateBo>();

        Criteria cConditions = new Criteria();

        Query query = QueryFactory.newQuery(EdoCandidateBo.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
        
        if (c != null && c.size() != 0) {
            candidateLists.addAll(c);
        }
       
        return candidateLists;
    }
    
    public List<EdoCandidateBo> getEdoCandidateList(String principalName, String groupKeyCode, LocalDate fromEffdt, LocalDate toEffdt, String active, String showHistory){
    	
    	List<EdoCandidateBo> candidateLists = new LinkedList<EdoCandidateBo>();
		Criteria root = new Criteria();

		if (StringUtils.isNotEmpty(principalName) 
				&& !ValidationUtils.isWildCard(principalName)) {
			root.addLike("UPPER(principalName)", principalName.toUpperCase());
		}
		
		if (StringUtils.isNotEmpty(groupKeyCode)) {
			root.addLike("UPPER(groupKeyCode)", groupKeyCode.toUpperCase());
		}

		Criteria effectiveDateFilter = new Criteria();
        if (fromEffdt != null) {
            effectiveDateFilter.addGreaterOrEqualThan("effectiveDate", fromEffdt.toDate());
        }
        if (toEffdt != null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", toEffdt.toDate());
        }
        if (fromEffdt == null && toEffdt == null) {
            effectiveDateFilter.addLessOrEqualThan("effectiveDate", LocalDate.now().toDate());
        }
        root.addAndCriteria(effectiveDateFilter);
        
		if (StringUtils.isNotBlank(active)) {
        	Criteria activeFilter = new Criteria();
            if (StringUtils.equals(active, "Y")) {
                activeFilter.addEqualTo("active", true);
            } else if (StringUtils.equals(active, "N")) {
                activeFilter.addEqualTo("active", false);
            }
            root.addAndCriteria(activeFilter);
        }
		
		if (StringUtils.equals(showHistory, "N")) {
            root.addEqualTo("effectiveDate", OjbSubQueryUtil.getEffectiveDateSubQueryWithFilter(EdoCandidateBo.class, effectiveDateFilter, EdoCandidateBo.BUSINESS_KEYS , false));
            root.addEqualTo("timestamp", OjbSubQueryUtil.getTimestampSubQuery(EdoCandidateBo.class, EdoCandidateBo.BUSINESS_KEYS , false));
        }
		

		Query query = QueryFactory.newQuery(EdoCandidateBo.class, root);

		Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);
		
		if (!c.isEmpty())
			candidateLists.addAll(c);

		return candidateLists;
	}

}
