package org.kuali.kpme.edo.candidate.dao;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.edo.candidate.EdoCandidate;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tcbradley
 * Date: 10/25/12
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoCandidateDaoImpl extends PlatformAwareDaoBaseOjb implements EdoCandidateDao {

    private static final Logger LOG = Logger.getLogger(EdoCandidateDaoImpl.class);

    public List<EdoCandidate> getEdoCandidateList () {

        List<EdoCandidate> candidateLists = new LinkedList<EdoCandidate>();

        Criteria cConditions = new Criteria();

        Query query = QueryFactory.newQuery(EdoCandidate.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            candidateLists.addAll(c);
        }
        return candidateLists;
    }

    public EdoCandidate getCandidate(BigDecimal userID) {

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("candidateID", userID);

        Query query = QueryFactory.newQuery(EdoCandidate.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
           return (EdoCandidate)c.toArray()[0];
        }
        return null;
    }

    public EdoCandidate getCandidateByUsername(String userName) {

        Criteria cConditions = new Criteria();

        cConditions.addEqualTo("userName", userName);

        Query query = QueryFactory.newQuery(EdoCandidate.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            return (EdoCandidate)c.toArray()[0];
        }
        return null;
    }

    public List<EdoCandidate> getCandidateListByUsername( String userName ) {

        List<EdoCandidate> candidateLists = new LinkedList<EdoCandidate>();

        Criteria cConditions = new Criteria();
        cConditions.addEqualTo("userName", userName);

        Query query = QueryFactory.newQuery(EdoCandidate.class, cConditions);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null && c.size() != 0) {
            candidateLists.addAll(c);
        }
        return candidateLists;
    }

}
