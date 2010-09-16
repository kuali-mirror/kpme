package org.kuali.hr.time.dept.earncode.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.hr.time.dept.earncode.DepartmentEarnCode;
import org.kuali.hr.time.earncode.EarnCode;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class DepartmentEarnCodeDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements DepartmentEarnCodeDao {

	@SuppressWarnings("unused")
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

	@Override
	public List<EarnCode> getDepartmentEarnCodes(String department, String tkSalGroup, Date asOfDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
