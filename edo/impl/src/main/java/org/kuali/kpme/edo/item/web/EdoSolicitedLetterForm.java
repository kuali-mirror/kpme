package org.kuali.kpme.edo.item.web;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.struts.upload.FormFile;
import org.kuali.kpme.edo.api.item.EdoItem;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinitionBo;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 7/19/13
 * Time: 10:53 AM
 */
public class EdoSolicitedLetterForm extends EdoForm {

	/* This is not used anywhere in EdoSolicitedLetterAction
    private List<EdoChecklistV> checklistView = EdoServiceLocator.getChecklistVService().getCheckListView("IU", "ALL", "ALL"); */
    private FormFile uploadFile;
    private int checklistItemID;
    private int itemID;
    private String outputJson;
    private String nidFwd;
    private String formData;
    List<EdoItem> itemList = new LinkedList<EdoItem>();
    Collection<EdoReviewLayerDefinitionBo> layerList = new LinkedList<EdoReviewLayerDefinitionBo>();

    public Collection<EdoReviewLayerDefinitionBo> getLayerList() {
        return this.layerList;
    }

    public void setLayerList(Collection<EdoReviewLayerDefinitionBo> reviewLayerDefinitions) {
        this.layerList = reviewLayerDefinitions;
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
