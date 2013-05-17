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
package org.kuali.kpme.core.permission.service;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kns.kim.permission.PermissionTypeServiceBase;
import org.kuali.rice.krad.util.KRADConstants;

@SuppressWarnings("deprecation")
public class DocumentTypeAndMaintenanceActionServiceImpl extends PermissionTypeServiceBase {

    @Override
    protected boolean performMatch(Map<String, String> inputMap, Map<String, String> storedMap) {
    	String requestedDocumentType = MapUtils.getString(inputMap, KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME);
		String permissionDocumentType = MapUtils.getString(storedMap, KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME);
		
		if (requestedDocumentType == null || permissionDocumentType == null) {
			return false;
		}
		
		if (!requestedDocumentType.equals(permissionDocumentType)) {
			return false;
		}
		
		String existingRecordsOnly = MapUtils.getString(storedMap, KimConstants.AttributeConstants.EXISTING_RECORDS_ONLY);
		
		if (existingRecordsOnly == null) {
			return true;
		}
		
		if (!Boolean.parseBoolean(existingRecordsOnly)) {
			return StringUtils.equals(inputMap.get(KRADConstants.MAINTENANCE_ACTN), KRADConstants.MAINTENANCE_NEW_ACTION);
		} else {
			return StringUtils.equals(inputMap.get(KRADConstants.MAINTENANCE_ACTN), KRADConstants.MAINTENANCE_EDIT_ACTION);
		}
	}
    
}