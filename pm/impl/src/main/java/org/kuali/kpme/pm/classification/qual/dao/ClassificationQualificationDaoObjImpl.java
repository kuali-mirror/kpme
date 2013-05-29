package org.kuali.kpme.pm.classification.qual.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.kuali.kpme.pm.classification.qual.ClassificationQualification;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

public class ClassificationQualificationDaoObjImpl extends PlatformAwareDaoBaseOjb implements ClassificationQualificationDao {

	@Override
	public List<ClassificationQualification> getQualListForClassification(String pmClassificationId) {
		List<ClassificationQualification> qualList = new ArrayList<ClassificationQualification>();
		Criteria crit = new Criteria();
        crit.addEqualTo("pmPositionClassId", pmClassificationId);

        Query query = QueryFactory.newQuery(ClassificationQualification.class, crit);
        Collection c = this.getPersistenceBrokerTemplate().getCollectionByQuery(query);

        if (c != null) {
        	qualList.addAll(c);
        }
        return qualList;
	}

}
