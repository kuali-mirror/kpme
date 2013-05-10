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
package org.kuali.kpme.tklm.time.missedpunch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

public class MissedPunchAssignmentKeyValuesFinder extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = -6828936184460318588L;

	@Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> labels = new ArrayList<KeyValue>();
        
        if (model instanceof MissedPunchForm) {
	        MissedPunchForm missedPunchForm = (MissedPunchForm) model;
	        MissedPunchDocument missedPunchDocument = (MissedPunchDocument) missedPunchForm.getDocument();
	        
	        String timesheetDocumentId = missedPunchDocument != null ? missedPunchDocument.getMissedPunch().getTimesheetDocumentId() : missedPunchForm.getMissedPunch().getTimesheetDocumentId();
	        if (StringUtils.isNotBlank(timesheetDocumentId)) {
	            TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentId);
	            Map<String, String> assignmentDescriptions = timesheetDocument.getAssignmentDescriptions(true);

	            if (assignmentDescriptions.size() > 1) {
	            	labels.add(new ConcreteKeyValue("", ""));
	            }
	            for (Map.Entry<String, String> entry : assignmentDescriptions.entrySet()) {
	                labels.add(new ConcreteKeyValue(entry.getKey(), entry.getValue()));
	            }
	        }
        }

        return labels;
    }
    
}
