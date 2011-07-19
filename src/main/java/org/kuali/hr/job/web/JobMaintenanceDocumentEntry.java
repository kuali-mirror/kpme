package org.kuali.hr.job.web;

import org.kuali.rice.kns.datadictionary.MaintenanceDocumentEntry;
import org.kuali.rice.kns.document.Document;

public class JobMaintenanceDocumentEntry extends MaintenanceDocumentEntry {
	 public Class<? extends Document> getStandardDocumentBaseClass() {
         return JobMaintenanceDocument.class;
     }
 
 /**
  * @see org.kuali.rice.kns.datadictionary.DocumentEntry#setDocumentClass(java.lang.Class)
  */
 @Override
 public void setDocumentClass(Class<? extends Document> documentClass) {
     if (!JobMaintenanceDocument.class.isAssignableFrom(documentClass)) {
         throw new IllegalArgumentException("document class '" + documentClass + "' needs to have a superclass of '" + JobMaintenanceDocument.class + "'");
     }
     super.setDocumentClass(documentClass);
 }

}
