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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.kuali.kpme.core.bo.job.Job;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.HrContext;
import org.kuali.kpme.tklm.common.TkConstants;
import org.kuali.kpme.tklm.time.clocklog.ClockLog;
import org.kuali.kpme.tklm.time.missedpunch.web.MissedPunchForm;
import org.kuali.kpme.tklm.time.service.TkServiceLocator;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

public class MissedPunchClockActionKeyValuesFinder extends UifKeyValuesFinderBase {

	private static final long serialVersionUID = 3671922976500404818L;

	@Override
	public List<KeyValue> getKeyValues(ViewModel model) {
		List<KeyValue> keyLabels = new ArrayList<KeyValue>();
		
		Set<String> availableActions = new HashSet<String>();
		
		if (model instanceof MissedPunchForm) {
			MissedPunchForm missedPunchForm = (MissedPunchForm) model;
			MissedPunchDocument missedPunchDocument = (MissedPunchDocument) missedPunchForm.getDocument();
			
			String clockAction = missedPunchDocument != null ? missedPunchDocument.getMissedPunch().getClockAction() : missedPunchForm.getMissedPunch().getClockAction();
	        if (StringUtils.isNotBlank(clockAction)) {
	        	availableActions.addAll(TkConstants.CLOCK_AVAILABLE_ACTION_MAP.get(clockAction));
	        } else {
	            ClockLog lastClock = TkServiceLocator.getClockLogService().getLastClockLog(HrContext.getTargetPrincipalId());
	            availableActions.addAll(lastClock != null ?
	                    TkConstants.CLOCK_ACTION_TRANSITION_MAP.get(lastClock.getClockAction()) :
	                    TkConstants.CLOCK_ACTION_TRANSITION_MAP.get(TkConstants.CLOCK_OUT));
	
	            if (lastClock != null) {
	            	Job jobObj = HrServiceLocator.getJobService().getJob(HrContext.getTargetPrincipalId(), lastClock.getJobNumber(), LocalDate.now());
		            String department = jobObj != null ? jobObj.getDept() : null;
	            	Long workArea = lastClock.getWorkArea();
		            Long jobNumber = lastClock.getJobNumber();
		
		            if (!TkServiceLocator.getSystemLunchRuleService().getSystemLunchRule(LocalDate.now()).getShowLunchButton()) {
		            	availableActions.remove(TkConstants.LUNCH_OUT);
		            	availableActions.remove(TkConstants.LUNCH_IN);
		            } else {
		            	if (TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule(department, workArea, HrContext.getTargetPrincipalId(), jobNumber, LocalDate.now()) != null) {
			            	availableActions.remove(TkConstants.LUNCH_OUT);
			            	availableActions.remove(TkConstants.LUNCH_IN);
		            	}
		            }
	            }
	        }
		} else {
			availableActions.addAll(TkConstants.CLOCK_ACTION_STRINGS.keySet());
		}
		
        if (availableActions.size() > 1) {
        	keyLabels.add(new ConcreteKeyValue("", ""));
        }
		for (String availableAction : availableActions) {
			keyLabels.add(new ConcreteKeyValue(availableAction, TkConstants.CLOCK_ACTION_STRINGS.get(availableAction)));
        }
		
        return keyLabels;
	}

}
