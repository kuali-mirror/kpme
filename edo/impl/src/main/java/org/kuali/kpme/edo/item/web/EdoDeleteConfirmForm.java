package org.kuali.kpme.edo.item.web;

import org.kuali.kpme.edo.base.web.EdoForm;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 3/13/13
 * Time: 11:04 AM
 */
public class EdoDeleteConfirmForm extends EdoForm {
    private String operation;
    private String formData;
    private String nidFwd;
    private String itemID;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public String getNidFwd() {
        return nidFwd;
    }

    public void setNidFwd(String nid) {
        this.nidFwd = nid;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
}
