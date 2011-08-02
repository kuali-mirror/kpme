package org.kuali.hr.time.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.web.struts.form.KualiForm;
import org.kuali.rice.kns.web.struts.form.KualiMaintenanceForm;

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
        List<KeyLabelPair> labels = new ArrayList<KeyLabelPair>();

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
            Map<String,String> adMap = TkServiceLocator.getAssignmentService().getAssignmentDescriptions(tdoc, true); // Grab clock only assignments

            for (Map.Entry entry : adMap.entrySet()) {
                labels.add(new KeyLabelPair(entry.getKey(), (String)entry.getValue()));
            }
        } 

        return labels;
    }
}
