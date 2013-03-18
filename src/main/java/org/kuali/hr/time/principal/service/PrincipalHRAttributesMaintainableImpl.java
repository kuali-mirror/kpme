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
package org.kuali.hr.time.principal.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.hr.core.cache.CacheUtils;
import org.kuali.hr.time.HrBusinessObject;
import org.kuali.hr.time.principal.PrincipalHRAttributes;
import org.kuali.hr.time.service.base.TkServiceLocator;
import org.kuali.hr.time.util.HrBusinessObjectMaintainableImpl;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;

import java.util.Map;

public class PrincipalHRAttributesMaintainableImpl extends HrBusinessObjectMaintainableImpl {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Override
	public Map populateBusinessObject(Map<String, String> fieldValues,
			MaintenanceDocument maintenanceDocument, String methodToCall) {
		if (fieldValues.containsKey("principalId")
				&& StringUtils.isNotEmpty(fieldValues.get("principalId"))) {
			EntityNamePrincipalName p = KimApiServiceLocator.getIdentityService().getDefaultNamesForPrincipalId(fieldValues.get("principalId"));
			if (p != null
                    && p.getDefaultName() != null) {
				fieldValues.put("name", p.getDefaultName().getCompositeName());
			}else{
				fieldValues.put("name", "");
			}
		}
		return super.populateBusinessObject(fieldValues, maintenanceDocument,
				methodToCall);
	}

	@Override
	public void saveBusinessObject() {
		super.saveBusinessObject();
		CacheUtils.flushCache(PrincipalHRAttributes.CACHE_NAME);
	}

	@Override
	public HrBusinessObject getObjectById(String id) {
		return TkServiceLocator.getPrincipalHRAttributeService().getPrincipalHRAttributes(id);
	}
	
	
}
