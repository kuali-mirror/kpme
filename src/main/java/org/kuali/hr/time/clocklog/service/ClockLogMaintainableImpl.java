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
package org.kuali.hr.time.clocklog.service;

import org.kuali.hr.time.clocklog.ClockLog;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;

import java.util.Map;

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
		clockLog.setTkClockLogId(null);
		clockLog.setTimestamp(null);
		KRADServiceLocator.getBusinessObjectService().save(clockLog);
	}

}