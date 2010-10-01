package org.kuali.hr.time.clocklog.service;

import java.util.Map;

import org.kuali.hr.time.clocklog.ClockLog;


import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;

public class ClockLogMaintainableImpl extends org.kuali.rice.kns.maintenance.KualiMaintainableImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 @Override
	public void processAfterNew(MaintenanceDocument document,
			Map<String, String[]> parameters) {		 
		super.processAfterNew(document, parameters);
	}
	 
	@Override
	public void processAfterPost(MaintenanceDocument document,
			Map<String, String[]> parameters) {		
		ClockLog clockLog = (ClockLog) document.getDocumentBusinessObject();
		clockLog.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
		super.processAfterPost(document, parameters);
	}
	
	@Override
	public void processAfterEdit(MaintenanceDocument document,
			Map<String, String[]> parameters) {
		ClockLog clockLog = (ClockLog) document.getDocumentBusinessObject();
		clockLog.setUserPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
		super.processAfterEdit(document, parameters);
	}
	
	@Override
	public void saveBusinessObject() {
		ClockLog clockLog = (ClockLog) this.getBusinessObject();
		ClockLog oldClockLog = (ClockLog) KNSServiceLocator
				.getBusinessObjectService().findBySinglePrimaryKey(
						ClockLog.class,
						clockLog.getTkClockLogId());
		if (oldClockLog != null) {
			//oldClockLog.setActive(false);
			//KNSServiceLocator.getBusinessObjectService().save(oldClockLog);
		}
		clockLog.setTkClockLogId(null);
		clockLog.setTimestamp(null);
		KNSServiceLocator.getBusinessObjectService().save(clockLog);
	}

}