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
package org.kuali.kpme.tklm.time.rules.overtime.weekly.web;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.kuali.kpme.tklm.time.rules.overtime.weekly.WeeklyOvertimeRule;
import org.kuali.kpme.tklm.time.rules.overtime.weekly.WeeklyOvertimeRuleGroup;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

public class WeeklyOvertimeRuleGroupMaintainableImpl extends KualiMaintainableImpl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
    public void addNewLineToCollection(String collectionName) {
        if (collectionName.equals("lstWeeklyOvertimeRules")) {
        	WeeklyOvertimeRule aRule = (WeeklyOvertimeRule)newCollectionLines.get(collectionName );
            if ( aRule != null ) {
            	WeeklyOvertimeRuleGroup aGroup = (WeeklyOvertimeRuleGroup)this.getBusinessObject();
            	Set<BigDecimal> steps = new HashSet<BigDecimal>();
            	for(WeeklyOvertimeRule wotr : aGroup.getLstWeeklyOvertimeRules()){
            		steps.add(wotr.getStep());
            	}
            	if(steps.contains(aRule.getStep())){
            		GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"lstWeeklyOvertimeRules", 
            				"weeklyOvertimeRule.duplicate.step",aRule.getStep().toString());
            		return;
    			} 
            }
        }
       super.addNewLineToCollection(collectionName);
    }

}
