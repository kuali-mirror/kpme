package org.kuali.hr.time.earncode.dao;

import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.hr.time.earncode.EarnCode;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

import uk.ltd.getahead.dwr.util.Logger;

public class EarnCodeDaoSpringOjbImpl extends PersistenceBrokerDaoSupport implements EarnCodeDao {

	private static final Logger LOG = Logger.getLogger(EarnCodeDaoSpringOjbImpl.class);

	public void saveOrUpdate(EarnCode earnCode) {
		this.getPersistenceBrokerTemplate().store(earnCode);
	}

	public void saveOrUpdate(List<EarnCode> earnCodeList) {
		if (earnCodeList != null) {
			for (EarnCode earnCode : earnCodeList) {
				this.getPersistenceBrokerTemplate().store(earnCode);
			}
		}
	}

	public EarnCode getEarnCodeById(Long earnCodeId) {
		Criteria crit = new Criteria();
		crit.addEqualTo("earnCodeId", earnCodeId);
		crit.addEqualTo("active", true);

		return (EarnCode) this.getPersistenceBrokerTemplate().getObjectByQuery(QueryFactory.newQuery(EarnCode.class, crit));

	}
}
