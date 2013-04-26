/**
 * Copyright 2004-2013 The Kuali Foundation
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
package org.kuali.kpme.core.bo.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.common.TKContext;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kns.web.struts.form.KualiForm;
import org.kuali.rice.kns.web.struts.form.KualiMaintenanceForm;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

/**
 * AssignmentValuesFinder provides the values for the MissedPunch maintenance
 * document's Assignment selection.
 *
 * NOTE: Clock only assignments.
 */
public class AssignmentValuesFinder extends KeyValuesBase {

    @Override
    /**
     * The KeyLabelPair values returned match up with the data the current
     * user would get if they were selecting assignments from their clock.
     *
     * NOTE: These are Clock-Only assignments.
     */
    public List getKeyValues() {
        List<KeyValue> labels = new ArrayList<KeyValue>();

        // We need the current timesheet document id. depending on where this
        // was set up, we may need to look in two different places. Primarily
        // we look directly at the context's function.
        
        String tdocId = TKContext.getCurrentTimesheetDocumentId();
        if (tdocId == null) {
            tdocId = TKContext.getHttpServletRequest().getParameter(TkConstants.TIMESHEET_DOCUMENT_ID_REQUEST_NAME);
        }
        if(tdocId == null){
        	KualiForm kualiForm = (KualiForm)TKContext.getHttpServletRequest().getAttribute("KualiForm");
        	if(kualiForm instanceof KualiMaintenanceForm){
        		tdocId = ((KualiMaintenanceForm)kualiForm).getDocId();
        	}
        }
        
        
        if (tdocId != null) {
            TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(tdocId);
            Map<String,String> adMap = HrServiceLocator.getAssignmentService().getAssignmentDescriptions(tdoc, true); // Grab clock only assignments

            for (Map.Entry entry : adMap.entrySet()) {
                labels.add(new ConcreteKeyValue((String)entry.getKey(), (String)entry.getValue()));
            }
        } 

        return labels;
    }
}
