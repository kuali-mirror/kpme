package org.kuali.kpme.pm.classification.duty.dao;

import java.util.List;

import org.kuali.kpme.pm.classification.duty.ClassificationDuty;

public interface ClassificationDutyDao {
	public List<ClassificationDuty> getDutyListForClassification(String pmClassificationId);
}
