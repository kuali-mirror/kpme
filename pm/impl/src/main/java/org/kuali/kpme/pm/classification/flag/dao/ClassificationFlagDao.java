package org.kuali.kpme.pm.classification.flag.dao;

import java.util.List;

import org.kuali.kpme.pm.classification.flag.ClassificationFlag;

public interface ClassificationFlagDao {
	public List<ClassificationFlag> getFlagListForClassification(String pmClassificationId);
}
