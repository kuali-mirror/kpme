package org.kuali.kpme.pm.classification.dao;

import org.kuali.kpme.pm.classification.Classification;

public interface ClassificationDao {
    public Classification getClassificationById(String pmClassificationId);
}
