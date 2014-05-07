/**
 * Copyright 2004-2014 The Kuali Foundation
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
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.api.assignment.Assignment;
import org.kuali.kpme.core.api.assignment.AssignmentDescriptionKey;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.core.util.TKUtils;
import org.kuali.kpme.tklm.api.time.missedpunch.MissedPunch;
import org.kuali.kpme.tklm.common.LMConstants;
import org.kuali.kpme.tklm.time.missedpunch.web.MissedPunchForm;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.kpme.tklm.time.timesheet.TimesheetDocument;
import org.kuali.rice.core.api.config.property.ConfigContext;
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
			MissedPunchBo mp = missedPunchDocument == null ? missedPunchForm.getMissedPunch() : missedPunchDocument.getMissedPunch();
			LocalDate mpDate = mp.getLocalDate();
			if(mpDate == null) {
				mpDate = LocalDate.now();
			}
			String timesheetDocumentId = missedPunchDocument != null ? missedPunchDocument.getMissedPunch().getTimesheetDocumentId() : missedPunchForm.getMissedPunch().getTimesheetDocumentId();
			if (StringUtils.isNotBlank(timesheetDocumentId)) {
				TimesheetDocument timesheetDocument = TkServiceLocator.getTimesheetService().getTimesheetDocument(timesheetDocumentId);

				Map<LocalDate, List<Assignment>> assignmentMap = timesheetDocument.getAssignmentMap();
				List<Assignment> assignments = assignmentMap.get(mpDate);

				if (assignments.size() > 1) {
					labels.add(new ConcreteKeyValue("", ""));
				}

				if(missedPunchForm.getIpAddress()!=null){
					String ipAddress = TKUtils.getIPAddressFromRequest(missedPunchForm.getIpAddress());

					Map<String, String> assignmentDescMap = timesheetDocument.getAssignmentDescriptions(true, LocalDate.now());
					String targetPrincipalId = HrContext.getTargetPrincipalId(); 	            
					String principalId = HrContext.getPrincipalId();
					if(targetPrincipalId.equals(principalId)){
						DateTime currentDateTime = new DateTime();
						for (Map.Entry<String, String> entry : assignmentDescMap.entrySet()) {
							Assignment assignment = timesheetDocument.getAssignment(AssignmentDescriptionKey.get(entry.getKey()), LocalDate.now());
							String allowActionFromInvalidLocaiton = ConfigContext.getCurrentContextConfig().getProperty(LMConstants.ALLOW_CLOCKINGEMPLOYYE_FROM_INVALIDLOCATION);
							if(StringUtils.equals(allowActionFromInvalidLocaiton, "false")) {
								boolean isInValid = TkServiceLocator.getClockLocationRuleService().isInValidIPClockLocation(assignment.getDept(), assignment.getWorkArea(), assignment.getPrincipalId(), assignment.getJobNumber(), ipAddress, currentDateTime.toLocalDate());
								if(!isInValid){
									labels.add(new ConcreteKeyValue(assignment.getAssignmentKey(),assignment.getAssignmentDescription()));
								}
							}
						}
					}else{
						for (Assignment assignment : assignments) {
							labels.add(new ConcreteKeyValue(assignment.getAssignmentKey(),assignment.getAssignmentDescription()));
						}
					}
				}else{
					for (Assignment assignment : assignments) {
						labels.add(new ConcreteKeyValue(assignment.getAssignmentKey(),assignment.getAssignmentDescription()));
					}
				}
			}
		}
		return labels;
	}

}
