package org.kuali.hr.time.workflow.postprocessor;

import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;

public class TkPostProcessor extends DefaultPostProcessor{

	@Override
	public ProcessDocReport doRouteStatusChange(
			DocumentRouteStatusChange statusChangeEvent) throws Exception {
		// TODO Auto-generated method stub
		return super.doRouteStatusChange(statusChangeEvent);
	}

}
