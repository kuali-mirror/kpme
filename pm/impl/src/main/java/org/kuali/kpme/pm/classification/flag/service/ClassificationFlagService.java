package org.kuali.kpme.pm.classification.flag.service;

import java.util.List;

import org.kuali.kpme.pm.classification.flag.ClassificationFlag;

public interface ClassificationFlagService {
	/**
	 * Get the Flag list with given Classification Id
	 * @param pmClassificationId
	 * @return
	 */
	public List<ClassificationFlag> getFlagListForClassification(String pmClassificationId);
}
