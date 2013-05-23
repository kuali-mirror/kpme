package org.kuali.kpme.pm.classification.flag.service;

import java.util.List;

import org.kuali.kpme.pm.classification.flag.ClassificationFlag;
import org.kuali.kpme.pm.classification.flag.dao.ClassificationFlagDao;

public class ClassificationFlagServiceImpl implements ClassificationFlagService{
	
	private ClassificationFlagDao classificationFlagDao;
	@Override
	public List<ClassificationFlag> getFlagListForClassification(String pmClassificationId) {
		return classificationFlagDao.getFlagListForClassification(pmClassificationId);
	}
	public ClassificationFlagDao getClassificationFlagDao() {
		return classificationFlagDao;
	}
	public void setClassificationFlagDao(ClassificationFlagDao classificationFlagDao) {
		this.classificationFlagDao = classificationFlagDao;
	}

}
