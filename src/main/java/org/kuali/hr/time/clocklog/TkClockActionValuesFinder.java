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
package org.kuali.hr.time.clocklog;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.time.missedpunch.MissedPunchDocument;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.TKContext;
import org.kuali.hr.time.util.TKUser;
import org.kuali.hr.time.util.TKUtils;
import org.kuali.hr.time.util.TkConstants;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kns.web.struts.form.KualiForm;
import org.kuali.rice.kns.web.struts.form.KualiTransactionalDocumentFormBase;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

public class TkClockActionValuesFinder extends KeyValuesBase {

	@Override
	public List getKeyValues() {
		List<KeyValue> keyLabels = new LinkedList<KeyValue>();
        // get the missed punch doc Id if this is an existing document
        String mpDocId = (String)TKContext.getHttpServletRequest().getParameter(TkConstants.DOCUMENT_ID_REQUEST_NAME);
        if(StringUtils.isBlank(mpDocId)) {
        	mpDocId = (String)TKContext.getHttpServletRequest().getAttribute(TkConstants.DOCUMENT_ID_REQUEST_NAME);
        	if(StringUtils.isBlank(mpDocId)){
            	KualiForm kualiForm = (KualiForm)TKContext.getHttpServletRequest().getAttribute("KualiForm");
            	if(kualiForm instanceof KualiTransactionalDocumentFormBase){
            		mpDocId = ((KualiTransactionalDocumentFormBase)kualiForm).getDocId();
            	}
            }
        	if(StringUtils.isBlank(mpDocId)){
        		 mpDocId = (String)TKContext.getHttpServletRequest().getParameter("docId");
            }
        }
        // if the user is working on an existing missed punch doc, only return available actions based on the initial clock action
        if(!StringUtils.isEmpty(mpDocId)) {
        	MissedPunchDocument mp = TkServiceLocator.getMissedPunchService().getMissedPunchByRouteHeader(mpDocId);
        	if(mp != null) {
        		String clockAction = mp.getClockAction();
        		if(!StringUtils.isEmpty(clockAction)) {
        			Set<String> availableActions = TkConstants.CLOCK_AVAILABLE_ACTION_MAP.get(clockAction);
        			for (String entry : availableActions) {
        				keyLabels.add(new ConcreteKeyValue(entry, TkConstants.CLOCK_ACTION_STRINGS.get(entry)));
        	        }
        		}
        	}
        } else {
            String targetPerson = TKUser.getCurrentTargetPerson().getPrincipalId();
            ClockLog lastClock = TkServiceLocator.getClockLogService().getLastClockLog(targetPerson);
            Set<String> validEntries = lastClock != null ?
                    TkConstants.CLOCK_ACTION_TRANSITION_MAP.get(lastClock.getClockAction()) :
                    TkConstants.CLOCK_ACTION_TRANSITION_MAP.get(TkConstants.CLOCK_OUT); // Force CLOCK_IN as next valid action.
            for (String entry : validEntries) {
                keyLabels.add(new ConcreteKeyValue(entry, TkConstants.CLOCK_ACTION_STRINGS.get(entry)));
            } 

            String dept = TkServiceLocator.getJobService().getJob(targetPerson, lastClock.getJobNumber(), TKUtils.getCurrentDate()).getDept();
            Long workArea = lastClock.getWorkArea();
            Long jobNumber = lastClock.getJobNumber();

            if (!TkServiceLocator.getSystemLunchRuleService().getSystemLunchRule(TKUtils.getCurrentDate()).getShowLunchButton()) {
                keyLabels.remove(new ConcreteKeyValue("LO", "Lunch Out"));
                keyLabels.remove(new ConcreteKeyValue("LI", "Lunch In"));
            } else {
                   if (TkServiceLocator.getDepartmentLunchRuleService().getDepartmentLunchRule(dept, workArea, targetPerson, jobNumber,TKUtils.getCurrentDate()) != null) {
                       keyLabels.remove(new ConcreteKeyValue("LO", "Lunch Out"));
                       keyLabels.remove(new ConcreteKeyValue("LI", "Lunch In"));
                   }
            }
        }
        
        if(keyLabels.isEmpty()) {		// default is returning all options
        	for (Map.Entry entry : TkConstants.CLOCK_ACTION_STRINGS.entrySet()) {
              keyLabels.add(new ConcreteKeyValue((String)entry.getKey(), (String)entry.getValue()));
          }
        }
  		return keyLabels;
	}

}