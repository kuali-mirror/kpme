package org.kuali.kpme.pm.classification.qual.service;

import java.util.List;

import org.kuali.kpme.pm.classification.qual.ClassificationQualification;
import org.kuali.kpme.pm.classification.qual.dao.ClassificationQualificationDao;

public class ClassificationQualificationServiceImpl implements ClassificationQualificationService {

	private ClassificationQualificationDao classQualDao;
	
	@Override
	public List<ClassificationQualification> getQualListForClassification(String pmClassificationId) {
		return classQualDao.getQualListForClassification(pmClassificationId);
	}
	
	public ClassificationQualificationDao getClassQualDao() {
		return classQualDao;
	}
	public void setClassQualDao(ClassificationQualificationDao classQualDao) {
		this.classQualDao = classQualDao;
	}

}
