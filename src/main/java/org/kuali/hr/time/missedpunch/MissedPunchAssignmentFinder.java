package org.kuali.hr.time.missedpunch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
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
            Map<String,String> adMap = TkServiceLocator.getAssignmentService().getAssignmentDescriptions(tdoc, true); // Grab clock only assignments

            for (Map.Entry entry : adMap.entrySet()) {
                labels.add(new ConcreteKeyValue((String)entry.getKey(), (String)entry.getValue()));
            }
        } 

        return labels;
    }
}

