package org.kuali.hr.time.workarea.web;

import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;

public class WorkAreaActionForm extends KualiTransactionalDocumentFormBase {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
	
	@Override
	public String getDefaultDocumentTypeName(){
		return "WorkAreaMaintenanceDocument";
	}

	@Override
	public String getDocTypeName() {
		return "WorkAreaMaintenanceDocument";
	}
}
