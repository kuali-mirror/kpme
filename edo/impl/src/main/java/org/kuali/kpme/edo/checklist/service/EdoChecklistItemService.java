package org.kuali.kpme.edo.checklist.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;

public interface EdoChecklistItemService {

    public EdoChecklistItem getChecklistItemByID(String checklistItemId);
    public List<EdoChecklistItem> getChecklistItemsBySectionID(String sectionId, LocalDate asOfDate);
}
