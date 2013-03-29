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
package org.kuali.hr.time.position.service;

import java.sql.Timestamp;
import java.util.Map;

import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.core.role.KPMERoleMemberAttribute;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.position.Position;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.hr.time.workarea.WorkArea;
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
        return TkServiceLocator.getPositionService().getPosition(id);
    }

    @Override
    public void saveBusinessObject() {
        Position position = (Position) this.getBusinessObject();
        //String nextUniqueNumber = TkServiceLocator.getPositionService().getNextUniquePositionNumber();
        //position.setPositionNumber(nextUniqueNumber);

        KRADServiceLocator.getBusinessObjectService().save(position);
        CacheUtils.flushCache(Position.CACHE_NAME);
    }
    
    @Override
    protected void setNewCollectionLineDefaultValues(String collectionName, PersistableBusinessObject addLine) {
        Position position = (Position) getBusinessObject();
        
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
    				WorkArea workAreaObj = TkServiceLocator.getWorkAreaService().getWorkArea(workArea, new java.sql.Date(roleMember.getActiveFromDateValue().getTime()));
    			
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