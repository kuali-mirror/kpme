package org.kuali.hr.time.dept.earncode.dao;

import java.sql.Date;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.util.TkConstants;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import uk.ltd.getahead.dwr.util.Logger;

public class DepartmentEarnCodeDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements DepartmentEarnCodeDao {

	private static final Logger LOG = Logger.getLogger(DepartmentEarnCodeDaoSpringOjbImpl.class);

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
	public List<DepartmentEarnCode> getDepartmentEarnCodeList(String dept, String salGroup, boolean employee, boolean approver, 
																	boolean orgAdmin, Date payEndDate) {
		Criteria crit = new Criteria();
		crit.addEqualTo("dept", dept);
		crit.addEqualTo("tkSalGroup", salGroup);
		crit.addEqualTo("employee", employee);
		crit.addEqualTo("approver", approver);
		crit.addEqualTo("org_admin", orgAdmin);
		crit.addLessOrEqualThan("effectiveDate", payEndDate);
		
		List<DepartmentEarnCode> deptErncds = new LinkedList<DepartmentEarnCode>();
		Collection<DepartmentEarnCode> c= this.getPersistenceBrokerTemplate().getCollectionByQuery(QueryFactory.newQuery(DepartmentEarnCode.class, crit));
		if(c.isEmpty()){
			crit = new Criteria();
		}
		
		deptErncds.addAll(c);

		return deptErncds;

	}
}
