package org.kuali.hr.time.assignment;

import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.timesheet.TimesheetDocument;
import org.kuali.hr.time.util.TKContext;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AssignmentValuesFinder provides the values for the MissedPunch maintenance
 * document's Assignment selection.
 */
public class AssignmentValuesFinder extends KeyValuesBase {

    @Override
    public List getKeyValues() {
        List<KeyLabelPair> labels = new ArrayList<KeyLabelPair>();

        String tdocId = TKContext.getCurrentTimesheetDocumentId();
        if (tdocId != null) {
            TimesheetDocument tdoc = TkServiceLocator.getTimesheetService().getTimesheetDocument(tdocId);
            Map<String,String> adMap = TkServiceLocator.getAssignmentService().getAssignmentDescriptions(tdoc, true); // Grab clock only assignments

            for (Map.Entry entry : adMap.entrySet()) {
                labels.add(new KeyLabelPair(entry.getKey(), (String)entry.getValue()));
            }
        } else {
            // Not sure how we'd arrive here with no values to fill in.
        }

        return labels;
    }
}
