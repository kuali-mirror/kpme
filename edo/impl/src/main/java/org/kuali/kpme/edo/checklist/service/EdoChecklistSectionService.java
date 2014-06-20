package org.kuali.kpme.edo.checklist.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.checklist.EdoChecklistSection;

public interface EdoChecklistSectionService {

    public EdoChecklistSection getChecklistSectionByID(String checklistSectionId);
    public List<EdoChecklistSection> getChecklistSectionsByChecklistID(String checklistId, LocalDate asOfDate);
}
