package org.kuali.kpme.edo.checklist.service;

import java.util.List;
import java.util.SortedMap;

import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;

public interface EdoChecklistItemService {

    public EdoChecklistItem getChecklistItemByID(String edoChecklistItemId);
    public List<EdoChecklistItem> getChecklistItemsBySectionID(String edoChecklistSectionId, LocalDate asOfDate);
    
    // from EdoChecklistVService
    public List<EdoChecklistItem> getChecklistItems(String groupKey, String schoolID, String departmentID);  // from getChecklistVService().getChecklistView
    public EdoChecklistItem getChecklistItemByDossierID(String edoDossierID, String itemName); // from getChecklistVService().getChecklistItemByName
    public SortedMap<String, List<EdoChecklistItem>> getCheckListHash(String groupKey, String schoolID, String departmentID);  // from getChecklistVService().getCheckListHash
}
