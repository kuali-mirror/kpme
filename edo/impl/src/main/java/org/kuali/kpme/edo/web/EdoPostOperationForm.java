package org.kuali.kpme.edo.web;

import org.kuali.kpme.edo.base.web.EdoForm;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 3/12/13
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class EdoPostOperationForm extends EdoForm {

    public String formData;
    public String nidFwd;

    public String getFormData() {
        return formData;
    }

    public void setFormData(String data) {
        this.formData = data;
    }

    public String getNidFwd() {
        return nidFwd;
    }

    public void setNidFwd(String nid) {
        this.nidFwd = nid;
    }
}
