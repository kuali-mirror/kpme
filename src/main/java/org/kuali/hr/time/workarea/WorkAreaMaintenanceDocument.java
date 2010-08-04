package org.kuali.hr.time.workarea;

import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.document.TransactionalDocumentBase;

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
