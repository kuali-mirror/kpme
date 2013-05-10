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
package org.kuali.kpme.core.bo.principal.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.kpme.core.bo.HrBusinessObject;
import org.kuali.kpme.core.bo.HrBusinessObjectMaintainableImpl;
import org.kuali.kpme.core.bo.principal.PrincipalHRAttributes;
import org.kuali.kpme.core.cache.CacheUtils;
import org.kuali.kpme.core.service.HrServiceLocator;
import org.kuali.rice.kim.api.identity.principal.EntityNamePrincipalName;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.document.MaintenanceDocument;

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
		return HrServiceLocator.getPrincipalHRAttributeService().getPrincipalHRAttributes(id);
	}
	
	
}
