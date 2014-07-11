package org.kuali.kpme.edo.checklist.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.edo.api.checklist.EdoChecklist;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;
import org.kuali.kpme.edo.api.checklist.EdoChecklistSection;
import org.kuali.kpme.edo.checklist.EdoChecklistItemBo;
import org.kuali.kpme.edo.checklist.dao.EdoChecklistItemDao;
import org.kuali.kpme.edo.dossier.service.EdoDossierService;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.rice.core.api.mo.ModelObjectUtils;

public class EdoChecklistItemServiceImpl implements EdoChecklistItemService {

    private EdoChecklistItemDao edoChecklistItemDao;
    private EdoChecklistService edoChecklistService = EdoServiceLocator.getChecklistService();
    private EdoChecklistSectionService edoChecklistSectionService = EdoServiceLocator.getChecklistSectionService();
    private EdoDossierService edoDossierService = EdoServiceLocator.getEdoDossierService();
    
    protected List<EdoChecklistItem> convertToImmutable(List<EdoChecklistItemBo> bos) {
		return ModelObjectUtils.transform(bos, EdoChecklistItemBo.toImmutable);
	}

    public EdoChecklistItemDao getEdoChecklistItemDao() {
        return edoChecklistItemDao;
    }

    public void setEdoChecklistItemDao(EdoChecklistItemDao edoChecklistItemDao) {
        this.edoChecklistItemDao = edoChecklistItemDao;
    }

    public EdoChecklistItem getChecklistItemByID(String edoChecklistItemID) {
    	return EdoChecklistItemBo.to(edoChecklistItemDao.getChecklistItemByID(edoChecklistItemID));
    }
    
    public List<EdoChecklistItem> getChecklistItemsBySectionID(String edoChecklistSectionID, LocalDate asOfDate) {        
        List<EdoChecklistItemBo> bos = edoChecklistItemDao.getChecklistItemsBySectionID(edoChecklistSectionID, asOfDate);
		return convertToImmutable(bos);
    }
    
    public List<EdoChecklistItem> getChecklistItems(String groupKey, String organizationCode, String departmentID) {
    	
    	List <EdoChecklistItem> returnedItems = new ArrayList<EdoChecklistItem>();
    	LocalDate asOfDate = LocalDate.now();
    	
    	// Call EdoChecklist Service to find checklists for this group key, schoolID and departmentID
    	List<EdoChecklist> checklists = edoChecklistService.getChecklists(groupKey, organizationCode, departmentID, asOfDate);
    	for (EdoChecklist checklist : checklists) {
    		String edoChecklistID = checklist.getEdoChecklistID();
    		List<EdoChecklistSection> checklistSections = edoChecklistSectionService.getChecklistSectionsByChecklistID(edoChecklistID, asOfDate);

    		for (EdoChecklistSection checklistSection : checklistSections) {
    			String edoChecklistSectionID = checklistSection.getEdoChecklistSectionID();
    			
    			List<EdoChecklistItem> items = this.getChecklistItemsBySectionID(edoChecklistSectionID, asOfDate);
    			returnedItems.addAll(items);
    		}
    	}
    	
    	return returnedItems;
    }
    
    public EdoChecklistItem getChecklistItemByDossierID(String edoDossierID, String itemName) {
    	
    	LocalDate asOfDate = LocalDate.now();
    	List<String> idList = new ArrayList<String>();
    	
    	String edoChecklistID = edoDossierService.getEdoDossierById(edoDossierID).getEdoChecklistID();
    	List<EdoChecklistSection> checklistSections = edoChecklistSectionService.getChecklistSectionsByChecklistID(edoChecklistID, asOfDate);
    	
    	for (EdoChecklistSection checklistSection : checklistSections) {
    		idList.add(checklistSection.getEdoChecklistSectionID());
    	}
    	
    	List<EdoChecklistItemBo> bos = edoChecklistItemDao.getChecklistItemsBySectionIDs(idList, asOfDate);
    	
    	for (EdoChecklistItemBo bo : bos) {
    		if (StringUtils.equals(itemName, bo.getChecklistItemName())) {
    			return EdoChecklistItemBo.to(bo);
    		}
    	}
    	
    	return null;
    }
    
    public SortedMap<String, List<EdoChecklistItem>> getCheckListHash(String groupKey, String organizationCode, String departmentID) {

    	SortedMap<String, List<EdoChecklistItem>> checklisthash = new TreeMap<String, List<EdoChecklistItem>>(
            new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    return s1.compareTo(s2);
                }
            }
        );
    	
    	List<EdoChecklistItem> items = this.getChecklistItems(groupKey, organizationCode, departmentID);
        
        if (items != null && items.size() > 0) {
        	
        	for (EdoChecklistItem item : items) {
        		
        		EdoChecklistSection section = EdoServiceLocator.getChecklistSectionService().getChecklistSectionByID(item.getEdoChecklistSectionID());
                String ordSectionName = section.getChecklistSectionOrdinal() + "_" + section.getChecklistSectionName();
                if (checklisthash.containsKey(ordSectionName)) {
                    checklisthash.get(ordSectionName).add(item);
                } else {
                    List<EdoChecklistItem> tmpChecklist = new LinkedList<EdoChecklistItem>();
                    tmpChecklist.add(item);
                    checklisthash.put(ordSectionName, tmpChecklist);
                }
        	}
        }

        return checklisthash;
    }
}
