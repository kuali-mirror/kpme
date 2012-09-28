/**
 * Copyright 2004-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.hr.time.workarea.web;

import org.kuali.rice.kns.datadictionary.MaintenanceDocumentEntry;
import org.kuali.rice.krad.document.Document;

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
