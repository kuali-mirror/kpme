package org.kuali.kpme.pm.classification.duty.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.classification.duty.ClassificationDuty;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class ClassificationDutyDaoObjImpl extends PlatformAwareDaoBaseOjb implements ClassificationDutyDao{

	@Override
	public List<ClassificationDuty> getDutyListForClassification(String pmClassificationId) {
		List<ClassificationDuty> dutyList = new ArrayList<ClassificationDuty>();
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionClassId", pmClassificationId);

        Query query = QueryFactory.newQuery(ClassificationDuty.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
            dutyList.addAll(c);
        }
        return dutyList;
	}

}
