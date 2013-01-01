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
package org.kuali.hr.time.workarea.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kns.kim.role.RoleTypeServiceBase;

/**
 * This class is required to enable role-qualifiers on the roles we add to the system.  
 * For it to work, you need to have inserted a row into the table:
 * 
 * 'KRIM_TYP_T'
 * 
 * Additionally, in SpringBeans.xml you will need to have service definition that defines this
 * class.  Make sure that your entry in KRIM_TYP_T references this class by bean name.
 * 
 * To add attributes to the role, you need to have the KRIM_ATTRIBUTE set up:
 * 
 * KimAttributeImpl documentTypeAttribute = new KimAttributeImpl();
 * documentTypeAttribute.setKimAttributeId("30");
 * documentTypeAttribute.setAttributeName("workAreaId");
 * documentTypeAttribute.setNamespaceCode("KUALI");
 * documentTypeAttribute.setAttributeLabel("");
 * documentTypeAttribute.setComponentName(KimAttributes.class.getName());
 * documentTypeAttribute.setActive(true);
 * KNSServiceLocator.getBusinessObjectService().save(documentTypeAttribute);
 * 
 * Set the variables appropriately.  This only needs to be done once ever.
 * 
 * @author djunk
 *
 */
public class WorkAreaQualifierRoleTypeService extends RoleTypeServiceBase {
	
	/**
	 * The default performMatch is implemented in a way that makes it un-usable, even though they are
	 * doing esentially the same thing.
	 * 
	 * This method will fail fast on its conditionals.  It is intentionally verbose.
	 */
	@Override
	public boolean performMatch(Map<String, String> inputAttributes, Map<String, String> storedAttributes) {
		boolean matches = true;
		
		if (inputAttributes == null) {
			return true;
		}
		
		if (storedAttributes == null) {
			return false;
		}
		
		for (String key : inputAttributes.keySet()) {
			if (!storedAttributes.containsKey(key)) {
				return false;
			} else {
				String storedValue = storedAttributes.get(key);
				String qualifierValue = inputAttributes.get(key);
				
				matches = StringUtils.equals(qualifierValue, storedValue);
				if (!matches) {
					return false;
				}
			}
		}
		
		
		return matches;
	}

}
