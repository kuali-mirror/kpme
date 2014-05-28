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
package org.kuali.kpme.pm.positionreportgroup.web;

import java.util.HashSet;
import java.util.Set;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrKeyedSetBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.util.ValidationUtils;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroupBo;
import org.kuali.kpme.pm.positionreportgroup.PositionReportGroupKeyBo;
import org.kuali.kpme.pm.service.base.PmServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

public class PositionReportGroupMaintainableImpl extends HrKeyedSetBusinessObjectMaintainableImpl<PositionReportGroupBo, PositionReportGroupKeyBo> {
	
	private static final String EFFECTIVE_KEY_LIST = "effectiveKeyList";

	@SuppressWarnings("deprecation")
	@Override
    public void addNewLineToCollection(String collectionName) {
        if (collectionName.equals(EFFECTIVE_KEY_LIST)) {
        	PositionReportGroupKeyBo inputPositionReportGroupKey = (PositionReportGroupKeyBo)newCollectionLines.get(collectionName );
            if ( inputPositionReportGroupKey != null ) {
            	PositionReportGroupBo positionReportGroup = (PositionReportGroupBo)this.getBusinessObject();
            	Set<String> groupKeyCodes = new HashSet<String>();
            	for(PositionReportGroupKeyBo positionReportGroupKey : positionReportGroup.getEffectiveKeyList()){
            		groupKeyCodes.add(positionReportGroupKey.getGroupKeyCode());
            	}
            	if(groupKeyCodes.contains(inputPositionReportGroupKey.getGroupKeyCode())){
            		GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"effectiveKeyList", 
            				"keyedSet.duplicate.groupKeyCode", inputPositionReportGroupKey.getGroupKeyCode());
            		return;
    			} 
            	if (!ValidationUtils.validateGroupKey(inputPositionReportGroupKey.getGroupKeyCode(), positionReportGroup.getEffectiveLocalDate())) {
    				GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"earnCodeGroups", 
    							"error.existence", "Group key code: '" + inputPositionReportGroupKey.getGroupKeyCode() + "'");
    				return;
    			}
            }
        }
       super.addNewLineToCollection(collectionName);
    }
	
	
	
	private static final long serialVersionUID = 1L;

	@Override
	public HrBusinessObject getObjectById(String id) {
		return PositionReportGroupBo.from(PmServiceLocator.getPositionReportGroupService().getPositionReportGroupById(id));
	}
}
