package org.kuali.kpme.pm.classification.qual.service;

import java.util.List;

import org.kuali.kpme.pm.classification.qual.ClassificationQualification;

public interface ClassificationQualificationService {
	/**
	 * Get the Qualification list with given Classification Id
	 * @param pmClassificationId
	 * @return
	 */
	public List<ClassificationQualification> getQualListForClassification(String pmClassificationId);
}
