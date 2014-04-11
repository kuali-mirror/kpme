package org.kuali.kpme.core.api.groupkey;


import org.joda.time.LocalDate;

public interface HrGroupKeyService {
    HrGroupKey getHrGroupKeyById(String id);

    HrGroupKey getHrGroupKey(String groupKeyCode, LocalDate asOfDate);

    HrGroupKey populateSubObjects(HrGroupKey groupKey, LocalDate asOfDate);
}
