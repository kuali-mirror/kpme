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
package org.kuali.hr.tklm.time.missedpunch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.service.HrServiceLocator;
import org.kuali.hr.tklm.common.TKContext;
import org.kuali.hr.tklm.common.TkConstants;
import org.kuali.hr.tklm.time.service.TkServiceLocator;
import org.kuali.hr.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kns.web.struts.form.KualiForm;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class MissedPunchAssignmentFinder extends KeyValuesBase {

    @Override
    /**
     * The KeyLabelPair values returned match up with the data the current
     * user would get if they were selecting assignments from their clock.
     *
     * NOTE: These are Clock-Only assignments.
     */
    public List getKeyValues() {
        List<KeyValue> labels = new ArrayList<KeyValue>();
        String tdocId = "";
        String mpDocId = (String)TKContext.getHttpServletRequest().getParameter(TkConstants.DOCUMENT_ID_REQUEST_NAME);
        if(StringUtils.isBlank(mpDocId)){
        	KualiForm kualiForm = (KualiForm)TKContext.getHttpServletRequest().getAttribute("KualiForm");
        	if(kualiForm instanceof KualiTransactionalDocumentFormBase){
        		mpDocId = ((KualiTransactionalDocumentFormBase)kualiForm).getDocId();
        	}
        }
        
        if(StringUtils.isBlank(mpDocId)){
           tdocId = TKContext.getHttpServletRequest().getParameter(TkConstants.TIMESHEET_DOCUMENT_ID_REQUEST_NAME);   
        }
        
        if(StringUtils.isNotBlank(mpDocId)){
        	MissedPunchDocument mp = TkServiceLocator.getMissedPunchService().getMissedPunchByRouteHeader(mpDocId);
        	if(mp != null) {
        		tdocId = mp.getTimesheetDocumentId();
        	}
        }
        
        mpDocId = (String)TKContext.getHttpServletRequest().getParameter("docId");
        if(StringUtils.isNotBlank(mpDocId)){
        	MissedPunchDocument mp = TkServiceLocator.getMissedPunchService().getMissedPunchByRouteHeader(mpDocId);
        	if(mp != null) {
        		tdocId = mp.getTimesheetDocumentId();
        	}
        }
        
        if(StringUtils.isBlank(tdocId)) {
        	tdocId = (String) TKContext.getHttpServletRequest().getAttribute(TkConstants.TIMESHEET_DOCUMENT_ID_REQUEST_NAME);   
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

