package org.kuali.kpme.edo.item.web;

import org.apache.struts.upload.FormFile;
import org.kuali.kpme.edo.service.EdoServiceLocator;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.checklist.EdoChecklist;
import org.kuali.kpme.edo.item.count.EdoItemCountV;
import org.kuali.kpme.edo.item.EdoItemV;
import org.kuali.kpme.edo.util.EdoConstants;

import java.util.LinkedList;
import java.util.List;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 11/9/12
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoChecklistItemForm extends EdoForm {

    private List<EdoChecklist> checklistView = EdoServiceLocator.getChecklistViewService().getCheckListView("IU", "ALL", "ALL");
    private FormFile uploadFile;
    private int checklistItemID;
    private int itemID;
    private String outputJson;
    private String nidFwd;
    private String formData;
    private String newFilename;
    private String fileDescription;
    List<EdoItemV> itemList = new LinkedList<EdoItemV>();
    List<EdoItemCountV> itemCount = new LinkedList<EdoItemCountV>();

    public List<EdoItemCountV> getItemCount() {
        return itemCount;
    }

    public void setItemCount(List<EdoItemCountV> itemCount) {
        this.itemCount = itemCount;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public String getNewFilename() {
        return newFilename;
    }

    public void setNewFilename(String newFilename) {
        this.newFilename = newFilename;
    }

    public FormFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(FormFile file) {
        this.uploadFile = file;
    }

    public List<EdoChecklist> getChecklistView() {
        return checklistView;
    }

    public void setChecklistView(List<EdoChecklist> checklistView) {
        this.checklistView = checklistView;
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

    public List<EdoItemV> getItemList() {
        return itemList;
    }

    public void setItemList(List<EdoItemV> itemList) {
        this.itemList = itemList;
    }

    public String getNidFwd() {
        return nidFwd;
    }

    public void setNidFwd(String nidFwd) {
        this.nidFwd = nidFwd;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    protected void customInitMaxUploadSizes() {
        addMaxUploadSize(EdoConstants.FILE_UPLOAD_PARAMETERS.KUALI_MAX_FILE_UPLOAD_SIZE);
    }
}
