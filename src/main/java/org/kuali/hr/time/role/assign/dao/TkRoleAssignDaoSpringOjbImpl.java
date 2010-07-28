package org.kuali.hr.time.role.assign.dao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.role.assign.TkRoleAssign;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class TkRoleAssignDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements TkRoleAssignDao {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(TkRoleAssignDaoSpringOjbImpl.class);

    @Override
    public List<TkRoleAssign> getTkRoleAssignments(Long workAreaId) {
	Criteria criteria = new Criteria();
	criteria.addEqualTo("workAreaId", workAreaId);
	return this.getTkRoleAssignments(criteria);	
    }
    
    @Override
    public List<TkRoleAssign> getTkRoleAssignments(Long workAreaId, String role) {
	Criteria criteria = new Criteria();
	criteria.addEqualTo("workAreaId", workAreaId);
	criteria.addEqualTo("roleName", role);
	return this.getTkRoleAssignments(criteria);	
    }
    
    @Override
    public List<TkRoleAssign> getTkRoleAssignments(String principalId) {
	Criteria criteria = new Criteria();
	criteria.addEqualTo("principalId", principalId);
	return this.getTkRoleAssignments(criteria);		
    }

    private List<TkRoleAssign> getTkRoleAssignments(Criteria criteria) {
	List<TkRoleAssign> list = null;	
	Collection<TkRoleAssign> collection = this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(TkRoleAssign.class, criteria));
	list = new LinkedList<TkRoleAssign>();
	list.addAll(collection);
	return list;	
    }
}
