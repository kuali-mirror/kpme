package org.kuali.kpme.edo.item.web;

import java.util.LinkedList;
import java.util.List;

import org.apache.struts.upload.FormFile;
import org.kuali.kpme.edo.api.checklist.EdoChecklistItem;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.service.EdoServiceLocator;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 6/24/13
 * Time: 10:03 AM
 */
public class EdoSectionSummaryForm extends EdoForm {
    private List<EdoChecklistItem> checklistItems = EdoServiceLocator.getChecklistItemService().getChecklistItems("IU-IN", "ALL", "ALL");
    private FormFile uploadFile;
    private int checklistItemID;
    private int itemID;
    private String outputJson;
    private String nidFwd;
    private String formData;
    List<EdoItem> itemList = new LinkedList<EdoItem>();
    /* KPME-3705 This has been changed to int count from EdoItemService
    List<EdoItemCountV> itemCount = new LinkedList<EdoItemCou ntV>(); */
    private int itemCount;
    
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public FormFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(FormFile file) {
        this.uploadFile = file;
    }

    public List<EdoChecklistItem> getChecklistItems() {
		return checklistItems;
	}

	public void setChecklistItems(List<EdoChecklistItem> checklistItems) {
		this.checklistItems = checklistItems;
	}

	public int getChecklistItemID() {
        return checklistItemID;
    }

    public void setChecklistItemID(int checklistItemID) {
        this.checklistItemID = checklistItemID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getOutputJson() {
        return outputJson;
    }

    public void setOutputJson(String outputJson) {
        this.outputJson = outputJson;
    }

    public List<EdoItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<EdoItem> itemList) {
        this.itemList = itemList;
    }

    public String getNidFwd() {
        return nidFwd;
    }

    public void setNidFwd(String nidFwd) {
        this.nidFwd = nidFwd;
    }

}
