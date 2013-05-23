package org.kuali.kpme.pm.classification.duty.service;

import java.util.List;

import org.kuali.kpme.pm.classification.duty.ClassificationDuty;
import org.kuali.kpme.pm.classification.duty.dao.ClassificationDutyDao;

public class ClassificationDutyServiceImpl implements ClassificationDutyService {

	private ClassificationDutyDao classificationDutyDao;
	
	@Override
	public List<ClassificationDuty> getDutyListForClassification(String pmClassificationId) {
		return classificationDutyDao.getDutyListForClassification(pmClassificationId);
	}

	public ClassificationDutyDao getClassificationDutyDao() {
		return classificationDutyDao;
	}

	public void setClassificationDutyDao(ClassificationDutyDao classificationDutyDao) {
		this.classificationDutyDao = classificationDutyDao;
	}
}
