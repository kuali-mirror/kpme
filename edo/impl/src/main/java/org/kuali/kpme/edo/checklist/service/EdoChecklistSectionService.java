package org.kuali.kpme.edo.checklist.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.checklist.EdoChecklistSection;

public interface EdoChecklistSectionService {

    public EdoChecklistSection getChecklistSectionByID(String edoChecklistSectionID);
    public List<EdoChecklistSection> getChecklistSectionsByChecklistID(String edoChecklistID, LocalDate asOfDate);
}
