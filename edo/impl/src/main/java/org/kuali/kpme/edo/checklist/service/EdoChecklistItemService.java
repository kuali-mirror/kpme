package org.kuali.kpme.edo.checklist.service;

import java.util.List;
import java.util.SortedMap;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;

public interface EdoChecklistItemService {

    public EdoChecklistItem getChecklistItemById(String edoChecklistItemId);
    public List<EdoChecklistItem> getChecklistItemsBySectionId(String edoChecklistSectionId);
    
    // from EdoChecklistVService
    public List<EdoChecklistItem> getChecklistItems(String groupKey, String schoolId, String departmentId);  // from getChecklistVService().getChecklistView
    public EdoChecklistItem getChecklistItemByDossierId(String edoDossierId, String itemName); // from getChecklistVService().getChecklistItemByName
    public SortedMap<String, List<EdoChecklistItem>> getCheckListHash(String groupKey, String schoolId, String departmentId);  // from getChecklistVService().getCheckListHash
}
