package org.kuali.kpme.edo.item.web;

import org.apache.struts.upload.FormFile;
import org.kuali.kpme.edo.base.web.EdoForm;
import org.kuali.kpme.edo.item.EdoItemV;
import org.kuali.kpme.edo.reviewlayerdef.EdoReviewLayerDefinition;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 7/19/13
 * Time: 10:53 AM
 */
public class EdoReviewLetterForm extends EdoForm {

    // private List<EdoChecklist> checklistView = EdoServiceLocator.getChecklistViewService().getCheckListView("IU", "ALL", "ALL");
    private FormFile uploadFile;
    private int checklistItemID;
    private int itemID;
    private String outputJson;
    private String nidFwd;
    private String formData;
    private int selectedRvwLayer;
    Collection<EdoItemV> itemList = new LinkedList<EdoItemV>();
    Collection<EdoReviewLayerDefinition> layerList = new TreeSet<EdoReviewLayerDefinition>();
    private boolean canSeeAcknowledgeAction = false;
    private boolean canSeeAcknowledgeWithNoAction = false;

    public Collection<EdoReviewLayerDefinition> getLayerList() {
        return this.layerList;
    }

    public void setLayerList(Collection<EdoReviewLayerDefinition> layerList) {
        this.layerList = layerList;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public int getSelectedRvwLayer() {
        return selectedRvwLayer;
    }

    public void setSelectedRvwLayer(int selectedRvwLayer) {
        this.selectedRvwLayer = selectedRvwLayer;
    }

    public FormFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(FormFile file) {
        this.uploadFile = file;
    }

    //public List<EdoChecklist> getChecklistView() {
    //    return checklistView;
    //}

    //public void setChecklistView(List<EdoChecklist> checklistView) {
    //    this.checklistView = checklistView;
    //}

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

    public Collection<EdoItemV> getItemList() {
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

	public boolean isCanSeeAcknowledgeAction() {
		return canSeeAcknowledgeAction;
	}

	public void setCanSeeAcknowledgeAction(boolean canSeeAcknowledgeAction) {
		this.canSeeAcknowledgeAction = canSeeAcknowledgeAction;
	}

	public boolean isCanSeeAcknowledgeWithNoAction() {
		return canSeeAcknowledgeWithNoAction;
	}

	public void setCanSeeAcknowledgeWithNoAction(
			boolean canSeeAcknowledgeWithNoAction) {
		this.canSeeAcknowledgeWithNoAction = canSeeAcknowledgeWithNoAction;
	}
    

}
