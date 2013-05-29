package org.kuali.kpme.pm.classification.qual.dao;

import java.util.List;

import org.kuali.kpme.pm.classification.qual.ClassificationQualification;

public interface ClassificationQualificationDao {
	public List<ClassificationQualification> getQualListForClassification(String pmClassificationId);
}
