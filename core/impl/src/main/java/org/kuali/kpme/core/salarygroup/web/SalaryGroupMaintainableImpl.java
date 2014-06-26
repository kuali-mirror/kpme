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
package org.kuali.kpme.core.salarygroup.web;

import java.util.HashSet;
import java.util.Set;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.bo.HrKeyedSetBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.bo.derived.HrBusinessObjectKey;
import org.kuali.kpme.core.salarygroup.SalaryGroupBo;
import org.kuali.kpme.core.salarygroup.SalaryGroupKeyBo;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

public class SalaryGroupMaintainableImpl extends HrKeyedSetBusinessObjectMaintainableImpl<SalaryGroupBo, SalaryGroupKeyBo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String EFFECTIVE_KEY_LIST = "effectiveKeyList";

	@Override
    protected void processAfterAddLine(View view, CollectionGroup collectionGroup, Object model, Object addLine, boolean isValidLine) {
        if (addLine instanceof SalaryGroupKeyBo) {
        	SalaryGroupKeyBo inputSalaryGroupKey = (SalaryGroupKeyBo)addLine;
            if ( inputSalaryGroupKey != null ) {
            	SalaryGroupBo salaryGroup = (SalaryGroupBo)this.getDataObject();
            	Set<String> groupKeyCodes = new HashSet<String>();
            	for(SalaryGroupKeyBo salaryGroupKey : salaryGroup.getEffectiveKeyList()){
            		groupKeyCodes.add(salaryGroupKey.getGroupKeyCode());
            	}
            	if(groupKeyCodes.contains(inputSalaryGroupKey.getGroupKeyCode())){
            		GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"effectiveKeyList", 
            				"keyedSet.duplicate.groupKeyCode", inputSalaryGroupKey.getGroupKeyCode());
            		return;
    			} 
            	if (!ValidationUtils.validateGroupKey(inputSalaryGroupKey.getGroupKeyCode(), salaryGroup.getEffectiveLocalDate())) {
    				GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"earnCodeGroups", 
    							"error.existence", "Group key code: '" + inputSalaryGroupKey.getGroupKeyCode() + "'");
    				return;
    			}
            }
        }
       super.processAfterAddLine(view, collectionGroup, model, addLine, isValidLine);
    }
	
	@Override
	public HrBusinessObject getObjectById(String id) {
		return SalaryGroupBo.from(HrServiceLocator.getSalaryGroupService().getSalaryGroup(id));
	}

}
