package org.kuali.hr.time.workarea;

import org.kuali.rice.krad.document.TransactionalDocumentBase;

public class WorkAreaMaintenanceDocument extends TransactionalDocumentBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    

    private WorkArea workArea;

    public WorkArea getWorkArea() {
        return workArea;
    }

    public void setWorkArea(WorkArea workArea) {
        this.workArea = workArea;
    }
    
}
