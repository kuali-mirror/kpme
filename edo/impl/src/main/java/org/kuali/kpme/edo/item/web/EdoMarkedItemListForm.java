package org.kuali.kpme.edo.item.web;

import java.util.LinkedList;
import java.util.List;

import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.base.web.EdoForm;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 7/15/13
 * Time: 10:46 AM
 */
public class EdoMarkedItemListForm extends EdoForm {

	/* This is not used anywhere in EdoMarkedItemListAction
    private List<EdoChecklistV> checklistView = EdoServiceLocator.getChecklistVService().getCheckListView("IU", "ALL", "ALL"); */
    private int checklistItemID;
    List<EdoItem> itemList = new LinkedList<EdoItem>();

    public int getChecklistItemID() {
        return checklistItemID;
    }

    public void setChecklistItemID(int checklistItemID) {
        this.checklistItemID = checklistItemID;
    }

    public List<EdoItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<EdoItem> itemList) {
        this.itemList = itemList;
    }

}
