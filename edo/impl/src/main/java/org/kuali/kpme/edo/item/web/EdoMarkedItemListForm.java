package org.kuali.kpme.edo.item.web;

import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.checklist.EdoChecklistV;
import org.kuali.kpme.edo.item.EdoItemV;

import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 7/15/13
 * Time: 10:46 AM
 */
public class EdoMarkedItemListForm extends EdoForm {

    private List<EdoChecklistV> checklistView = EdoServiceLocator.getChecklistVService().getCheckListView("IU", "ALL", "ALL");
    private int checklistItemID;
    List<EdoItemV> itemList = new LinkedList<EdoItemV>();

    public List<EdoChecklistV> getChecklistView() {
        return checklistView;
    }

    public int getChecklistItemID() {
        return checklistItemID;
    }

    public void setChecklistItemID(int checklistItemID) {
        this.checklistItemID = checklistItemID;
    }

    public List<EdoItemV> getItemList() {
        return itemList;
    }

    public void setItemList(List<EdoItemV> itemList) {
        this.itemList = itemList;
    }

}
