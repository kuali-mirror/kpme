package org.kuali.kpme.pm.classification.service;

import org.kuali.kpme.pm.classification.Classification;
import org.kuali.kpme.pm.classification.dao.ClassificationDao;

public class ClassificationServiceImpl implements ClassificationService {

	private ClassificationDao classificationDao;
	
	@Override
	public Classification getClassificationById(String pmClassificationId) {
		return classificationDao. getClassificationById(pmClassificationId);
	}

	public ClassificationDao getClassificationDao() {
		return classificationDao;
	}

	public void setClassificationDao(ClassificationDao classificationDao) {
		this.classificationDao = classificationDao;
	}

	

}
