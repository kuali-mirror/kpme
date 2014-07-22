package org.kuali.kpme.edo.checklist.service;

import java.util.List;

import org.kuali.kpme.edo.api.checklist.EdoChecklistSection;

public interface EdoChecklistSectionService {

    public EdoChecklistSection getChecklistSectionById(String edoChecklistSectionId);
    public List<EdoChecklistSection> getChecklistSectionsByChecklistId(String edoChecklistId);
}
