package org.kuali.hr.time.timesheet;


import org.apache.cxf.common.util.StringUtils;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.kns.lookup.valueFinder.ValueFinder;

public class TimesheetDocumentIdValueFinder implements ValueFinder {

    @Override
    /**
     * First checks the context for the current timesheet document id. Second,
     * it checks for the request parameter to be set.
     */
    public String getValue() {
        String val = null;

        val = TKContext.getCurrentTimesheetDocumentId();
        if (StringUtils.isEmpty(val)) {
            val = TKContext.getHttpServletRequest().getParameter(TkConstants.TIMESHEET_DOCUMENT_ID_REQUEST_NAME);
        }

        return val;
    }
}
