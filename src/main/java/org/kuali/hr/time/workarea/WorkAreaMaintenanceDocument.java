package org.kuali.hr.time.workarea;

import org.kuali.rice.kns.document.TransactionalDocumentBase;

public class WorkAreaMaintenanceDocument extends TransactionalDocumentBase {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String description;
    
    public int getFoo() {
	return 9;
    }

    private WorkArea workArea;

    public WorkArea getWorkArea() {
        return workArea;
    }

    public void setWorkArea(WorkArea workArea) {
        this.workArea = workArea;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
