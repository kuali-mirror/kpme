package org.kuali.kpme.pm.classification.flag.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.classification.flag.ClassificationFlag;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class ClassificationFlagDaoObjImpl extends PlatformAwareDaoBaseOjb implements ClassificationFlagDao {

	@Override
	public List<ClassificationFlag> getFlagListForClassification(String pmClassificationId) {
		List<ClassificationFlag> flagList = new ArrayList<ClassificationFlag>();
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionClassId", pmClassificationId);

        Query query = QueryFactory.newQuery(ClassificationFlag.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	flagList.addAll(c);
        }
        return flagList;
	}

}
