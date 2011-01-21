package org.kuali.hr.time.workarea.web;

import org.kuali.rice.kns.datadictionary.MaintenanceDocumentEntry;
import org.kuali.rice.kns.document.Document;

/**
 * 
 * This class provides a hook to WorkAreaMaintenanceDocument instead the default MaintenanceDocumentBase.
 * DD bean is in WorkAreaDataDictionaryBaseTypes.xml
 */
public class WorkAreaMaintenanceDocumentEntry extends MaintenanceDocumentEntry {
    public Class<? extends Document> getStandardDocumentBaseClass() {
            return WorkAreaMaintenanceDocument.class;
        }
    
    /**
     * @see org.kuali.rice.kns.datadictionary.DocumentEntry#setDocumentClass(java.lang.Class)
     */
    @Override
    public void setDocumentClass(Class<? extends Document> documentClass) {
        if (!WorkAreaMaintenanceDocument.class.isAssignableFrom(documentClass)) {
            throw new IllegalArgumentException("document class '" + documentClass + "' needs to have a superclass of '" + WorkAreaMaintenanceDocument.class + "'");
        }
        super.setDocumentClass(documentClass);
    }

}
