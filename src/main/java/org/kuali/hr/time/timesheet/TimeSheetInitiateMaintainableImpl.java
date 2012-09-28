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
package org.kuali.hr.time.timesheet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;

public class TimeSheetInitiateMaintainableImpl extends KualiMaintainableImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void saveBusinessObject() {
		super.saveBusinessObject();
	}
	
	
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if(StringUtils.equals(getMaintenanceAction(),"New")){
			if (!fieldValues.containsKey("documentId") || StringUtils.isEmpty(fieldValues.get("documentId"))) {
				TimeSheetInitiate tsi = (TimeSheetInitiate) this.getBusinessObject();
				fieldValues.put("documentId", tsi.getDocumentId());
			}
		}
		if (fieldValues.containsKey("endPeriodDateTime") && !StringUtils.isEmpty(fieldValues.get("endPeriodDateTime"))) {
			try {
				if(fieldValues.get("endPeriodDateTime").length() > 10) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
					java.util.Date aDate = dateFormat.parse(fieldValues.get("endPeriodDateTime"));
					dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					fieldValues.put("endPeriodDateTime", dateFormat.format(aDate));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument, methodToCall);
	}
		
}
