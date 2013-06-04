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
package org.kuali.kpme.core.position.web;

import java.sql.Timestamp;
import java.util.Map;

import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.cache.CacheUtils;
import org.kuali.kpme.core.position.PositionBase;
import org.kuali.kpme.core.role.KPMERoleMemberAttribute;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.kpme.core.workarea.WorkArea;
import org.kuali.rice.kim.impl.role.RoleMemberBo;
import org.kuali.rice.krad.bo.PersistableBusinessObject;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;

public class PositionMaintainableServiceImpl extends HrBusinessObjectMaintainableImpl {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    @Override
    public HrBusinessObject getObjectById(String id) {
        return HrServiceLocator.getPositionService().getPosition(id);
    }

    @Override
    public void saveBusinessObject() {
        PositionBase position = (PositionBase) this.getBusinessObject();
        //String nextUniqueNumber = HrServiceLocator.getPositionService().getNextUniquePositionNumber();
        //position.setPositionNumber(nextUniqueNumber);
        
        // This will not be needed once the copy link gets suppressed
        // KPME- 2247 set position number to null so that it will be auto generated
        if (getMaintenanceAction().equals(KRADConstants.MAINTENANCE_COPY_ACTION)) {
        	position.setPositionNumber(null);
        	position.setTimestamp(null);
        }

        KRADServiceLocator.getBusinessObjectService().save(position);
        CacheUtils.flushCache(PositionBase.CACHE_NAME);
    }
    
    @Override
    protected void setNewCollectionLineDefaultValues(String collectionName, PersistableBusinessObject addLine) {
        PositionBase position = (PositionBase) getBusinessObject();
        
        if (addLine instanceof RoleMemberBo) {
        	RoleMemberBo roleMember = (RoleMemberBo) addLine;
        	roleMember.setActiveFromDateValue(new Timestamp(position.getEffectiveDate().getTime()));
        }
        
        super.setNewCollectionLineDefaultValues(collectionName, addLine);
    }

    @Override
    public void addNewLineToCollection(String collectionName) {
		if (collectionName.equals("roleMembers")) {
			RoleMemberBo roleMember = (RoleMemberBo) newCollectionLines.get(collectionName);
            if (roleMember != null) {
            	Map<String, String> attributes = roleMember.getAttributes();
            	if (attributes.containsKey(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName())) {
    				Long workArea = Long.valueOf(attributes.get(KPMERoleMemberAttribute.WORK_AREA.getRoleMemberAttributeName()));
    				WorkArea workAreaObj = HrServiceLocator.getWorkAreaService().getWorkArea(workArea, roleMember.getActiveFromDate().toLocalDate());
    			
    				if (workAreaObj == null) {
            			GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRADConstants.MAINTENANCE_NEW_MAINTAINABLE +"roles", 
                				"error.role.workArea.notexist", String.valueOf(workArea));
                		return;
            		}
    			}
            }
        }
		
        super.addNewLineToCollection(collectionName);
    }

}