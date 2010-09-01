package org.kuali.hr.time.dept.earncode.dao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import uk.ltd.getahead.dwr.util.Logger;

public class DepartmentEarnCodeDaoSpringObjImpl extends PersistenceBrokerDaoSupport implements DepartmentEarnCodeDao {

	private static final Logger LOG = Logger.getLogger(DepartmentEarnCodeDaoSpringObjImpl.class);

	public void saveOrUpdate(DepartmentEarnCode deptErncd) {
		this.getPersistenceBrokerTemplate().store(deptErncd);
	}

	public void saveOrUpdate(List<DepartmentEarnCode> deptErncdList) {
		if (deptErncdList != null) {
			for (DepartmentEarnCode deptErncd : deptErncdList) {
				this.getPersistenceBrokerTemplate().store(deptErncd);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<DepartmentEarnCode> getDepartmentEarnCodeList(String salGroup) {
		Criteria crit = new Criteria();
		crit.addEqualTo("tkSalGroup", salGroup);

		List<DepartmentEarnCode> deptErncds = new LinkedList<DepartmentEarnCode>();
		Collection<DepartmentEarnCode> c= this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(DepartmentEarnCode.class, crit));
		deptErncds.addAll(c);

		return deptErncds;

	}
}
