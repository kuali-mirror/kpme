package org.kuali.hr.time.workflow.postprocessor;

import com.opensymphony.oscache.base.NeedsRefreshException;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.workflow.TimesheetDocumentHeader;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;

public class TkMissedPunchPostProcessor extends DefaultPostProcessor {

	@Override
	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {
		ProcessDocReport pdr = super.doRouteStatusChange(statusChangeEvent);

		Long documentId = statusChangeEvent.getRouteHeaderId();


        // TODO: Not sure that we need to hook into the maint doc here or not.
        // TODO: Somehow, do we need the timesheet?

		return pdr;
	}

}
