package org.kuali.kpme.edo.admin.web;

import org.kuali.kpme.edo.base.web.EdoForm;

/**
 * $HeadURL$
 * $Revision$
 * Created with IntelliJ IDEA.
 * User: bradleyt
 * Date: 12/6/12
 * Time: 8:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class EdoGenAdminForm extends EdoForm {

    private String suppDocId;

    public String getSuppDocId() {
        return suppDocId;
    }

    public void setSuppDocId(String suppDocId) {
        this.suppDocId = suppDocId;
    }
}
