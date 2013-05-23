package org.kuali.kpme.pm.classification.duty.service;

import java.util.List;

import org.kuali.kpme.pm.classification.duty.ClassificationDuty;

public interface ClassificationDutyService {
	/**
	 * Get the Duty list with given Classification Id
	 * @param pmClassificationId
	 * @return
	 */
	public List<ClassificationDuty> getDutyListForClassification(String pmClassificationId);
}
